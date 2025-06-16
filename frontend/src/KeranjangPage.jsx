import React, { useState, useEffect } from "react";
import "./KeranjangStyle.css";
import { useNavigate } from 'react-router-dom';

const ShoppingCart = () => {
  const [orders, setOrders] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [discountedTotal, setDiscountedTotal] = useState(0);
  const [selectedOrderIds, setSelectedOrderIds] = useState([]);
  const [isMember, setIsMember] = useState(false);
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

  const handleDetailClick = (item) => {
    localStorage.setItem("selectedProductId", item.barangId);
    navigate("/OrderDetail");
  };

  const handleUpdateQuantity = async (orderId, barangId, newQty) => {
    try {
      const res = await fetch(`http://localhost:8080/api/cart/orders/${orderId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ jumlahBarang: newQty }),
      });

      if (!res.ok) throw new Error(`Update gagal: ${res.status}`);

      const updatedOrders = orders.map(order => {
        if (order.orderId === orderId) {
          const updatedBarang = order.barang.map(item =>
            item.barangId === barangId ? { ...item, jumlahBarang: newQty } : item
          );
          return { ...order, barang: updatedBarang };
        }
        return order;
      });

      setOrders(updatedOrders);
      updateTotal(updatedOrders, selectedOrderIds);
    } catch (err) {
      console.error("Error updating quantity:", err);
    }
  };

  const handleCheckboxChange = (orderId, checked) => {
    let updated = [...selectedOrderIds];
    if (checked) {
      if (!updated.includes(orderId)) updated.push(orderId);
    } else {
      updated = updated.filter(id => id !== orderId);
    }
    setSelectedOrderIds(updated);
    localStorage.setItem("selectedOrders", JSON.stringify(updated));
    updateTotal(orders, updated);
  };

  const updateTotal = (orders, selectedIds) => {
    const filteredOrders = orders.filter(order => selectedIds.includes(order.orderId));
    const total = filteredOrders.reduce((sum, order) =>
      sum + order.barang.reduce((subtotal, item) =>
        subtotal + item.hargaPerUnit * item.jumlahBarang, 0), 0
    );
    setTotalPrice(total);
    const discount = isMember ? total * 0.1 : 0;
    setDiscountedTotal(total - discount);
  };

  useEffect(() => {
    fetch('http://localhost:8080/api/user/profile/client', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }
    })
      .then(res => {
        if (!res.ok) throw new Error(`Error: ${res.status} ${res.statusText}`);
        return res.json();
      })
      .then(data => {
        if (data.data && data.data.isMember === true) {
          setIsMember(true);
        }
      })
      .catch((err) => {
        console.error("Error fetching user profile:", err);
      });
  }, [token]);
  console.log(localStorage.getItem("selectedOrders"));
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

          for (let order of groupedOrders) {
            for (let item of order.barang) {
              const detail = await fetchNamaBarang(item.barangId);
              item.nama_barang = detail.nama_barang;
              item.image_url = detail.image_url;
            }
          }

          setOrders(groupedOrders);
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
                  <th>Detail</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
            <p style={{ justifySelf: "center" }}>Keranjang kosong.</p>
          </div>
        ) : (
          <div className="order-container">
            <table className="cart-table">
              <thead>
                <tr>
                  <th>Pilih</th>
                  <th>Produk</th>
                  <th>Harga Satuan</th>
                  <th>Kuantitas</th>
                  <th>Total Harga</th>
                  <th>Detail</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) =>
                  order.barang.map((item, idx) => (
                    <tr key={`${order.orderId}-${idx}`}>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedOrderIds.includes(order.orderId)}
                          onChange={(e) => handleCheckboxChange(order.orderId, e.target.checked)}
                        />
                      </td>
                      <td>
                        <img
                          src={item.image_url || ""}
                          alt={`Produk ${item.barangId}`}
                          className="product-image"
                        />
                        <span>{item.nama_barang}</span>
                      </td>
                      <td>{`Rp ${item.hargaPerUnit.toLocaleString("id-ID")}`}</td>
                      <td>
                        <div className="detailbarang-quantity-section">
                          <button
                            className="detailbarang-quantity-btn"
                            onClick={() =>
                              item.jumlahBarang > 1 &&
                              handleUpdateQuantity(order.orderId, item.barangId, item.jumlahBarang - 1)
                            }
                            disabled={item.jumlahBarang <= 1}
                          >
                            -
                          </button>
                          <span className="detailbarang-quantity">{item.jumlahBarang}</span>
                          <button
                            className="detailbarang-quantity-btn"
                            onClick={() =>
                              handleUpdateQuantity(order.orderId, item.barangId, item.jumlahBarang + 1)
                            }
                          >
                            +
                          </button>
                        </div>
                      </td>
                      <td>{`Rp ${(item.hargaPerUnit * item.jumlahBarang).toLocaleString("id-ID")}`}</td>
                      <td>
                        <button className="btn btn-info btn-sm" onClick={() => handleDetailClick(item)}>
                          Detail
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
            <hr />
          </div>
        )}
        <div className="cart-summary">
          <p>Subtotal: Rp {totalPrice.toLocaleString("id-ID")}</p>
          <p>Potongan: Rp {(isMember ? (totalPrice * 0.1).toLocaleString("id-ID") : "0")}</p>
          <h2>Total: Rp {discountedTotal.toLocaleString("id-ID")}</h2>
          <button className="btn-checkout" onClick={() => navigate("/checkout")}>Checkout</button>
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;
