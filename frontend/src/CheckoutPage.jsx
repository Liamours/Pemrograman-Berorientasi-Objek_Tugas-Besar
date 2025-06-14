import React, { useState } from 'react';
import './CheckoutStyle.css';

const CheckoutPage = () => {
  const [paymentMethod, setPaymentMethod] = useState('COD');
  const [selectedBank, setSelectedBank] = useState('');

  const handlePaymentChange = (event) => {
    setPaymentMethod(event.target.value);
    setSelectedBank('');
  };

  const handleBankChange = (event) => {
    setSelectedBank(event.target.value);
  };

  const handleConfirmCheckout = () => {
    alert('Checkout berhasil! Pesanan Anda sedang diproses.');
  };

  return (
    <div className="contain-checkout">
        <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
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

        {/* Payment Method */}
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
