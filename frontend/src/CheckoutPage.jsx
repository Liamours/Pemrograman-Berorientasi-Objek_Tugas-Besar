import React, { useState, useEffect } from 'react';
import './CheckoutStyle.css';
import { useNavigate } from 'react-router-dom';

const CheckoutPage = () => {
  const [paymentMethod, setPaymentMethod] = useState('COD');
  const [selectedBank, setSelectedBank] = useState('');
  const [orders, setOrders] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [discountedTotal, setDiscountedTotal] = useState(0);
  const [selectedOrders, setSelectedOrders] = useState([]);
  const [isMember, setIsMember] = useState(false);
  const [alamat, setAlamat] = useState('');
  const [showConfirmPopup, setShowConfirmPopup] = useState(false);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchNamaBarang = async (barangId) => {
    try {
      const res = await fetch("http://localhost:8080/barang/detail", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ barang_id: barangId })
      });
      const data = await res.json();
      if (data.success) {
        return { nama_barang: data.data.nama_barang };
      }
    } catch (error) {
      console.error("Error fetching detail barang:", error);
    }
    return { nama_barang: "Tidak Diketahui" };
  };

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
          setIsMember(data.data.isMember || false);
          setAlamat(data.data.address || "-");
        }
      })
      .catch(err => console.error("Error fetching user profile:", err));
  }, [token]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const selected = JSON.parse(localStorage.getItem("selectedOrders")) || [];
        setSelectedOrders(selected);

        const res = await fetch("http://localhost:8080/api/cart", {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        });

        const data = await res.json();
        if (data.orders) {
          const grouped = data.orders.reduce((acc, item) => {
            if (!acc[item.orderId]) {
              acc[item.orderId] = {
                orderId: item.orderId,
                tanggal: item.tanggal,
                barang: [],
              };
            }
            acc[item.orderId].barang.push(item);
            return acc;
          }, {});

          const groupedOrders = Object.values(grouped).filter(order =>
            selected.includes(order.orderId)
          );

          let enrichedItems = [];
          for (let order of groupedOrders) {
            for (let item of order.barang) {
              const detail = await fetchNamaBarang(item.barangId);
              enrichedItems.push({
                ...item,
                nama_barang: detail.nama_barang,
              });
            }
          }

          setOrders(enrichedItems);
          const total = enrichedItems.reduce((acc, item) => acc + item.hargaPerUnit * item.jumlahBarang, 0);
          setTotalPrice(total);

          const discount = isMember ? total * 0.1 : 0;
          setDiscountedTotal(total - discount);
        }
      } catch (error) {
        console.error("Error fetching orders for checkout:", error);
      }
    };

    fetchOrders();
  }, [token, isMember]);

  const handleConfirmCheckout = async () => {
    try {
      const payload = {
        orders: orders.map(item => ({
          orderId: item.orderId,
          barangId: item.barangId,
          jumlahBarang: item.jumlahBarang,
        })),
        paymentMethod,
        bank: paymentMethod === "Transfer" ? selectedBank : null,
        total: discountedTotal
      };

      const temp = localStorage.getItem("selectedOrders")
      console.log("Selected Orders:", temp);
      const res = await fetch("http://localhost:8080/api/checkout", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify(
          temp
        )
      });

      if (!res.ok) throw new Error("Checkout gagal");

      setShowConfirmPopup(true);
    } catch (err) {
      console.error("Error saat checkout:", err);
    }
  };

  return (
    <div className="checkout-page">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />

      <div className={`checkout-overlay ${showConfirmPopup ? 'show' : ''}`}>
        <div className="checkout-popup-container">
          <h2>Checkout Berhasil</h2>
          <p>Terima Kasih Sudah Berbelanja</p>
          <div className="checkout-popup-actions">
            <button className="checkout-btn-confirm" onClick={() => navigate("/Receipt")}>Tutup</button>
          </div>
        </div>
      </div>

      <header className="checkout-header">
        <span className="glyphicon glyphicon-menu-left" style={{ cursor: "pointer", fontSize: "40px" }} onClick={() => {
          localStorage.removeItem("selectedOrders");
          navigate('/Keranjang');
        }}></span>
        <div className="gallery-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img src="/images/logogncmin.png" alt="Logo" style={{ width: "100px" }} />
      </header>

      <div className="checkout-container">
        <h1>Checkout</h1>

        <div className="section">
          <h2>Alamat Pengiriman</h2>
          <div className="address-display">
            <p>{alamat}</p>
          </div>
        </div>

        <div className="section">
          <h2>Detail Produk</h2>
          <table className="order-table">
            <thead>
              <tr>
                <th>Produk</th>
                <th>Harga Satuan</th>
                <th>Kuantitas</th>
                <th>Total Harga</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((item, idx) => (
                <tr key={idx}>
                  <td>{item.nama_barang || `Produk #${item.barangId}`}</td>
                  <td>{`Rp ${item.hargaPerUnit.toLocaleString("id-ID")}`}</td>
                  <td>{item.jumlahBarang}</td>
                  <td>{`Rp ${(item.hargaPerUnit * item.jumlahBarang).toLocaleString("id-ID")}`}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="section">
          <h2>Metode Pembayaran</h2>
          <div className="payment-options">
            <label>
              <input type="radio" name="payment-method" value="COD" checked={paymentMethod === 'COD'} onChange={(e) => setPaymentMethod(e.target.value)} />
              COD (Bayar di Tempat)
            </label>
            <label>
              <input type="radio" name="payment-method" value="Transfer" checked={paymentMethod === 'Transfer'} onChange={(e) => setPaymentMethod(e.target.value)} />
              Transfer Bank
            </label>
          </div>

          {paymentMethod === 'Transfer' && (
            <div className="bank-options">
              <h3>Pilihan Bank</h3>
              <select className="bank-select" value={selectedBank} onChange={(e) => setSelectedBank(e.target.value)}>
                <option value="">Pilih Bank</option>
                <option value="BCA">BCA</option>
                <option value="Mandiri">Mandiri</option>
                <option value="BNI">BNI</option>
                <option value="BRI">BRI</option>
              </select>
            </div>
          )}
        </div>

        <div className="section total-section">
          <h2>Ringkasan Harga</h2>
          <p>Subtotal: Rp {totalPrice.toLocaleString("id-ID")}</p>
          <p>Potongan Member: Rp {isMember ? (totalPrice * 0.1).toLocaleString("id-ID") : "0"}</p>
          <h3>Total: Rp {discountedTotal.toLocaleString("id-ID")}</h3>
        </div>

        <button className="btn-confirm-checkout" onClick={handleConfirmCheckout}>
          Konfirmasi Checkout
        </button>
      </div>
    </div>
  );
};

export default CheckoutPage;