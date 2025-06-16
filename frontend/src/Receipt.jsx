import React, { useEffect, useState } from 'react';
import { jsPDF } from "jspdf";
import './Receipt.css';
import { useNavigate } from 'react-router-dom';

const ReceiptPage = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const [storeInfo] = useState({
    name: "Toko G&C",
    address: "Jl. Raya Purwadadi No. 45, Purwadadi, Subang, Jawa Barat, Indonesia",
  });
  const [buyerInfo, setBuyerInfo] = useState({ name: '', address: '' });
  const [orderDetails, setOrderDetails] = useState([]);
  const [isMember, setIsMember] = useState(false);

  useEffect(() => {
    fetch("http://localhost:8080/api/user/profile/client", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
        Accept: "application/json"
      }
    })
      .then(res => res.json())
      .then(data => {
        if (data?.data) {
          setBuyerInfo({
            name: data.data.name || '-',
            address: data.data.address || '-'
          });
          setIsMember(data.data.isMember || false);
        }
      })
      .catch(err => console.error("Error fetching user profile:", err));

    const selected = JSON.parse(localStorage.getItem("selectedOrders")) || [];

    fetch("http://localhost:8080/api/cart", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(async data => {
        if (data.orders) {
          const grouped = data.orders.reduce((acc, item) => {
            if (!acc[item.orderId]) {
              acc[item.orderId] = { orderId: item.orderId, barang: [] };
            }
            acc[item.orderId].barang.push(item);
            return acc;
          }, {});

          const selectedItems = Object.values(grouped)
            .filter(order => selected.includes(order.orderId))
            .flatMap(order => order.barang);

          const enrichedItems = await Promise.all(selectedItems.map(async (item) => {
            try {
              const res = await fetch('http://localhost:8080/barang/detail', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ barang_id: item.barangId })
              });
              const result = await res.json();
              return {
                ...item,
                nama_barang: result?.data?.nama_barang || "Tidak Diketahui"
              };
            } catch (err) {
              console.error("Fetch error:", err);
              return { ...item, nama_barang: "Error" };
            }
          }));

          setOrderDetails(enrichedItems);
        }
      })
      .catch(err => console.error("Error fetching order details:", err));
  }, [token]);

  const totalHarga = orderDetails.reduce(
    (total, item) => total + item.jumlahBarang * item.hargaPerUnit,
    0
  );

  const potongan = isMember ? totalHarga * 0.1 : 0;
  const totalSetelahDiskon = totalHarga - potongan;

  const generatePDF = () => {
    const doc = new jsPDF();
    doc.setFont("Helvetica", "normal");
    doc.setFontSize(16);
    doc.text(storeInfo.name, 10, 10);
    doc.setFontSize(12);
    doc.text(storeInfo.address, 10, 20);

    doc.text(`Nama Pembeli: ${buyerInfo.name}`, 10, 40);
    doc.text(`Alamat Pembeli: ${buyerInfo.address}`, 10, 50);

    let y = 70;
    doc.text("Detail Pembelian:", 10, y);
    orderDetails.forEach((item, index) => {
      y += 10;
      doc.text(
        `${index + 1}. ${item.nama_barang} - ${item.jumlahBarang} x Rp ${item.hargaPerUnit.toLocaleString()} = Rp ${(item.jumlahBarang * item.hargaPerUnit).toLocaleString()}`,
        10,
        y
      );
    });

    y += 20;
    doc.text(`Subtotal: Rp ${totalHarga.toLocaleString()}`, 10, y);
    if (isMember) {
      y += 10;
      doc.text(`Potongan Member (10%): Rp ${potongan.toLocaleString()}`, 10, y);
    }
    y += 10;
    doc.setFontSize(14);
    doc.text(`Total: Rp ${totalSetelahDiskon.toLocaleString()}`, 10, y);

    doc.save("receipt.pdf");
  };

  return (
    <div className='receipt-page'>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <header className="receipt-header">
        <span
          style={{ cursor: "pointer", fontSize: "40px" }}
          className="glyphicon glyphicon-home"
          onClick={() => {
            localStorage.removeItem("selectedOrders");
            navigate("/gallery");
          }}
        ></span>
        <div className="receipt-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>

      <div className="receipt-container" id="main">
        <h1>Receipt</h1>

        <div className="receipt-section">
          <h2>{storeInfo.name}</h2>
          <p>{storeInfo.address}</p>
        </div>

        <div className="receipt-section">
          <h3>Informasi Pembeli</h3>
          <p>Nama: {buyerInfo.name}</p>
          <p>Alamat: {buyerInfo.address}</p>
        </div>

        <div className="receipt-section">
          <h3>Detail Barang</h3>
          <table className="receipt-order-table">
            <thead>
              <tr>
                <th>Produk</th>
                <th>Jumlah</th>
                <th>Harga</th>
              </tr>
            </thead>
            <tbody>
              {orderDetails.map((item, index) => (
                <tr key={index}>
                  <td>{item.nama_barang}</td>
                  <td>{item.jumlahBarang}</td>
                  <td>Rp {(item.hargaPerUnit).toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="receipt-total-section">
          <h3>Ringkasan Harga</h3>
          <p>Subtotal: Rp {totalHarga.toLocaleString()}</p>
          <p>Potongan Member: Rp {potongan.toLocaleString()}</p>
          <h3>Total: Rp {totalSetelahDiskon.toLocaleString()}</h3>
        </div>

        <button className="receipt-btn-download" onClick={generatePDF}>
          Unduh Receipt
        </button>
      </div>
    </div>
  );
};

export default ReceiptPage;
