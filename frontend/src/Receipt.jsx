import React from 'react';
import { jsPDF } from "jspdf";
import './Receipt.css';

const ReceiptPage = () => {
  const storeInfo = {
    name: "Toko G&C",
    address: "Jl. Mawar No. 45, Purwadadi, Subang, Jawa Barat, Indonesia",
  };

  const buyerInfo = {
    name: "John Doe",
    address: "Jl. Kenanga No. 10, Bandung, Jawa Barat, Indonesia",
  };

  const orderDetails = [
    { name: "1 Dus Kapal Api Mix", quantity: 1, price: 202000 },
    { name: "1 Dus Rinso Molto 1000", quantity: 2, price: 120000 },
    { name: "1 Dus Marjan Melon", quantity: 1, price: 64000 },
  ];

  const totalPrice = orderDetails.reduce(
    (total, item) => total + item.quantity * item.price,
    0
  );

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
        `${index + 1}. ${item.name} - ${item.quantity} x Rp ${item.price.toLocaleString()} = Rp ${(item.quantity * item.price).toLocaleString()}`,
        10,
        y
      );
    });

    y += 20;
    doc.text(`Total Harga: Rp ${totalPrice.toLocaleString()}`, 10, y);

    doc.save("receipt.pdf");
  };

  return (
    <div className="receipt-container">
      <h1>Receipt</h1>
      {/* Store Information */}
      <div className="section">
        <h2>{storeInfo.name}</h2>
        <p>{storeInfo.address}</p>
      </div>

      {/* Buyer Information */}
      <div className="section">
        <h3>Informasi Pembeli</h3>
        <p>Nama: {buyerInfo.name}</p>
        <p>Alamat: {buyerInfo.address}</p>
      </div>

      {/* Order Details */}
      <div className="section">
        <h3>Detail Barang</h3>
        <table className="order-table">
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
                <td>{item.name}</td>
                <td>{item.quantity}</td>
                <td>Rp {item.price.toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Total Price */}
      <div className="section total-section">
        <h3>Total Harga</h3>
        <p>Rp {totalPrice.toLocaleString()}</p>
      </div>

      {/* Download Button */}
      <button className="btn-download" onClick={generatePDF}>
        Unduh Receipt
      </button>
    </div>
  );
};

export default ReceiptPage;
