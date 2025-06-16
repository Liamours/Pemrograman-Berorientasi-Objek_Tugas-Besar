import React, { useState, useEffect } from 'react';
import './CheckoutStyle.css';
import { useNavigate } from 'react-router-dom';

const CheckoutPage = () => {
  const [paymentMethod, setPaymentMethod] = useState('COD');
  const [selectedBank, setSelectedBank] = useState('');
  const [orders, setOrders] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
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
        return {
          nama_barang: data.data.nama_barang,
        };
      }
    } catch (error) {
      console.error("Error fetching detail barang:", error);
    }
    return { nama_barang: "Tidak Diketahui" };
  };

  const fetchCartFallback = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/cart", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      const text = await res.text();
      if (!text) return;

      const data = JSON.parse(text);
      if (data.orders && data.orders.length > 0) {
        const grouped = data.orders.reduce((acc, item) => {
          if (!acc[item.orderId]) {
            acc[item.orderId] = { orderId: item.orderId, barang: [] };
          }
          acc[item.orderId].barang.push(item);
          return acc;
        }, {});
        const groupedOrders = Object.values(grouped).flatMap(order => order.barang);

        for (let item of groupedOrders) {
          const detail = await fetchNamaBarang(item.barangId);
          item.nama_barang = detail.nama_barang;
        }

        setOrders(groupedOrders);
        const total = groupedOrders.reduce((acc, item) => acc + item.hargaPerUnit * item.jumlahBarang, 0);
        setTotalPrice(total);
      }
    } catch (err) {
      console.error("Fallback fetch /api/cart error:", err);
    }
  };

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/checkput");
        if (!res.ok) {
          console.warn("checkput not OK:", res.status);
          return fetchCartFallback();
        }

        const text = await res.text();
        if (!text) {
          console.warn("checkput response empty");
          return fetchCartFallback();
        }

        const data = JSON.parse(text);
        if (data.orders && data.orders.length > 0) {
          const enrichedOrders = await Promise.all(
            data.orders.map(async (item) => {
              const detail = await fetchNamaBarang(item.barangId);
              return {
                ...item,
                nama_barang: detail.nama_barang,
              };
            })
          );

          setOrders(enrichedOrders);
          const total = enrichedOrders.reduce((acc, item) => acc + item.hargaPerUnit * item.jumlahBarang, 0);
          setTotalPrice(total);
        } else {
          fetchCartFallback();
        }
      } catch (error) {
        console.error("Error fetching checkput:", error);
        fetchCartFallback();
      }
    };

    fetchOrders();
  }, [token]);

  const closePopupConfirm = () => {
    document.getElementById("ConfirmCheckout").style.width = "0%";
    navigate("/Receipt");
  };

  const handlePaymentChange = (event) => {
    setPaymentMethod(event.target.value);
    setSelectedBank('');
  };

  const handleBankChange = (event) => {
    setSelectedBank(event.target.value);
  };

  const handleConfirmCheckout = () => {
    document.getElementById("ConfirmCheckout").style.width = "100%";
  };

  return (
    <div className="checkout-page">
      <div id="ConfirmCheckout" className="checkout-overlay">
        <div className="checkout-popup-container">
          <h2>Checkout Berhasil</h2>
          <p>Terima Kasih Sudah Berbelanja</p>
          <div className="checkout-popup-actions">
            <button className="checkout-btn-confirm" onClick={closePopupConfirm}>Tutup</button>
          </div>
        </div>
      </div>

      <header className="checkout-header">
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-menu-left" onClick={() => (navigate('/Keranjang'))}></span>
        <div className="gallery-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>

      <div className="checkout-container">
        <h1>Checkout</h1>

        <div className="section">
          <h2>Alamat Pengiriman</h2>
          <textarea className="address-input" placeholder="Masukkan alamat pengiriman" rows="3" />
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
              <input type="radio" name="payment-method" value="COD" checked={paymentMethod === 'COD'} onChange={handlePaymentChange} />
              COD (Bayar di Tempat)
            </label>
            <label>
              <input type="radio" name="payment-method" value="Transfer" checked={paymentMethod === 'Transfer'} onChange={handlePaymentChange} />
              Transfer Bank
            </label>
          </div>

          {paymentMethod === 'Transfer' && (
            <div className="bank-options">
              <h3>Pilihan Bank</h3>
              <select className="bank-select" value={selectedBank} onChange={handleBankChange}>
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
          <h2>Total Harga</h2>
          <p>{`Rp ${totalPrice.toLocaleString("id-ID")}`}</p>
        </div>

        <button className="btn-confirm-checkout" onClick={handleConfirmCheckout}>
          Konfirmasi Checkout
        </button>
      </div>
    </div>
  );
};

export default CheckoutPage;
