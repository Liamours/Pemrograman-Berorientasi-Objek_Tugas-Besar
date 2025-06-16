import React, { useState } from 'react';
import './CheckoutStyle.css';
import { useNavigate } from 'react-router-dom';

const CheckoutPage = () => {
  const [paymentMethod, setPaymentMethod] = useState('COD');
  const [selectedBank, setSelectedBank] = useState('');
  const navigate = useNavigate();

  const closePopupConfirm = () => {
    document.getElementById("ConfirmCheckout").style.width = "0%";
    navigate("/Receipt")
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
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
                <textarea
                className="address-input"
                placeholder="Masukkan alamat pengiriman"
                rows="3"
                />
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
                    <tr>
                        <td>1 Dus Kapal Api Mix</td>
                        <td>Rp 202.000</td>
                        <td>1</td>
                        <td>Rp 202.000</td>
                    </tr>
                    <tr>
                        <td>1 Dus Rinso Molto 1000</td>
                        <td>Rp 120.000</td>
                        <td>2</td>
                        <td>Rp 240.000</td>
                    </tr>
                </tbody>
                </table>
            </div>
            <div className="section">
                <h2>Metode Pembayaran</h2>
                <div className="payment-options">
                <label>
                    <input
                    type="radio"
                    name="payment-method"
                    value="COD"
                    checked={paymentMethod === 'COD'}
                    onChange={handlePaymentChange}
                    />
                    COD (Bayar di Tempat)
                </label>
                <label>
                    <input
                    type="radio"
                    name="payment-method"
                    value="Transfer"
                    checked={paymentMethod === 'Transfer'}
                    onChange={handlePaymentChange}
                    />
                    Transfer Bank
                </label>
                </div>
                {paymentMethod === 'Transfer' && (
                <div className="bank-options">
                    <h3>Pilihan Bank</h3>
                    <select
                    className="bank-select"
                    value={selectedBank}
                    onChange={handleBankChange}
                    >
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
                <p>Rp 442.000</p>
            </div>

            <button className="btn-confirm-checkout" onClick={handleConfirmCheckout}>
                Konfirmasi Checkout
            </button>
        </div>
    </div>
  );
};

export default CheckoutPage;
