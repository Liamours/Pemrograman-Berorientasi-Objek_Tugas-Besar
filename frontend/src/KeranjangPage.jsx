import React, { useState, useEffect } from "react";
import "./KeranjangStyle.css";
import { useNavigate } from 'react-router-dom';

const ShoppingCart = () => {
  const [orders, setOrders] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  const sidebar = () => {
    document.getElementById("Sidebar").style.width = "200px";
    document.getElementById("main").style.marginLeft = "200px";
  };

  const closeSidebar = () => {
    document.getElementById("Sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/order/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        token
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.orders) {
          setOrders(data.orders);
          setTotalPrice(data.totalPrice);
        } else {
          console.error("Failed to fetch orders.");
        }
      })
      .catch((err) => {
        console.error("Error fetching orders:", err);
      });
  }, [token]);

  return (
    <div className="gallery-admin-container" id="main">
      <div id="Sidebar" className="gallery-admin-sidenav">
        <a onClick={() => navigate('/gallery')}>Home</a>
        <hr></hr>
        <a style={{ cursor: "pointer" }} className="closebtn" onClick={closeSidebar}>&times;</a>
        <a onClick={closeSidebar}>Keranjang</a>
        <hr></hr>
        <a onClick={() => navigate('/profile')}>Profil</a>
        <hr></hr>
      </div>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <header className="gallery-admin-header">
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-list" onClick={sidebar}></span>
        <div className="gallery-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>
      <div className="contain-keranjang">
        <table className="cart-table">
          <thead>
            <tr>
              <th>Pilih</th>
              <th>Produk</th>
              <th>Harga Satuan</th>
              <th>Kuantitas</th>
              <th>Total Harga</th>
              <th>Aksi</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.orderId}>
                <td>
                  <img
                    src={`https://via.placeholder.com/100?text=Product+${order.barangId}`}
                    alt={`Produk ${order.barangId}`}
                    className="product-image"
                  />
                </td>
                <td>{`Rp ${order.hargaPerUnit.toLocaleString("id-ID")}`}</td>
                <td>
                  <div className="quantity-control">
                    <button className="btn-quantity">-</button>
                    <span>{order.jumlahBarang}</span>
                    <button className="btn-quantity">+</button>
                  </div>
                </td>
                <td>
                  {`Rp ${(order.hargaPerUnit * order.jumlahBarang).toLocaleString("id-ID")}`}
                </td>
                <td>
                  <button className="btn-remove">×</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="cart-summary">
          <p>Subtotal: Rp {totalPrice.toLocaleString("id-ID")}</p>
          <p>Potongan: Rp 0</p>
          <h2>Total: Rp {totalPrice.toLocaleString("id-ID")}</h2>
          <button className="btn-checkout">Checkout</button>
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;