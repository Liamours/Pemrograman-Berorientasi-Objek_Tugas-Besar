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

  const fetchNamaBarang = async (barangId) => {
    try {
      const response = await fetch("http://localhost:8080/barang/detail", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ barang_id: barangId })
      });
      const data = await response.json();
      if (data.success) {
        return {
          nama_barang: data.data.nama_barang,
          image_url: data.data.image_url
        };
      }
    } catch (err) {
      console.error("Error fetching detail for barangId", barangId, err);
    }
    return { nama_barang: "Tidak Diketahui", image_url: null };
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/cart", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then(async (data) => {
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
          const groupedOrders = Object.values(grouped);

          // Lengkapi setiap barang dengan nama dan image
          for (let order of groupedOrders) {
            for (let item of order.barang) {
              const detail = await fetchNamaBarang(item.barangId);
              item.nama_barang = detail.nama_barang;
              item.image_url = detail.image_url;
            }
          }

          setOrders(groupedOrders);
          setTotalPrice(data.totalPrice || 0);
        } else {
          console.error("Failed to fetch orders");
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
        <hr />
        <a style={{ cursor: "pointer" }} className="closebtn" onClick={closeSidebar}>&times;</a>
        <a onClick={closeSidebar}>Keranjang</a>
        <hr />
        <a onClick={() => navigate('/profile')}>Profil</a>
        <hr />
      </div>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <header className="keranjang-header">
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-list" onClick={sidebar}></span>
        <div className="gallery-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>

      <div className="contain-keranjang">
        {orders.length === 0 ? (
          <div className="order-container">
            <table className="cart-table">
              <thead>
                <tr>
                  <th>Pilih</th>
                  <th>Produk</th>
                  <th>Harga Satuan</th>
                  <th>Kuantitas</th>
                  <th>Total Harga</th>
                  <th style={{ textAlign:"right" }}>Hapus</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
            <p style={{ justifySelf:"center" }} >Keranjang kosong.</p>
          </div>
        ) : (
          orders.map((order) => (
            <div key={order.orderId} className="order-container">
              <h3>Order #{order.orderId} - {order.tanggal}</h3>
              <table className="cart-table">
                <thead>
                  <tr>
                    <th>Pilih</th>
                    <th>Produk</th>
                    <th>Harga Satuan</th>
                    <th>Kuantitas</th>
                    <th>Total Harga</th>
                    <th style={{ textAlign:"right" }}>Hapus</th>
                  </tr>
                </thead>
                <tbody>
                  {order.barang.map((item, idx) => (
                    <tr key={idx}>
                      <td>
                        <input type="checkbox" value="true" />
                      </td>
                      <td>
                        <img
                          src={item.image_url || "/images/grownncheer_logo.png"}
                          alt={`Produk ${item.barangId}`}
                          className="product-image"
                        />
                        <span>{item.nama_barang}</span>
                      </td>
                      <td>{`Rp ${item.hargaPerUnit.toLocaleString("id-ID")}`}</td>
                      <td>{item.jumlahBarang}</td>
                      <td>{`Rp ${(item.hargaPerUnit * item.jumlahBarang).toLocaleString("id-ID")}`}</td>
                      <td>
                        <span style={{ color:"red",fontSize:"20px" }} className="glyphicon glyphicon-remove"></span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
              <hr />
            </div>
          ))
        )}
        <div className="cart-summary">
          <p>Subtotal: Rp {totalPrice.toLocaleString("id-ID")}</p>
          <p>Potongan: Rp 0</p>
          <h2>Total: Rp {totalPrice.toLocaleString("id-ID")}</h2>
          <button className="btn-checkout" onClick={() => (navigate("/checkout"))}>Checkout</button>
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;
