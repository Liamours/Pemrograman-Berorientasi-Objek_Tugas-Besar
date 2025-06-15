import React, { useState, useEffect } from 'react';
import './EditBarangAdminStyle.css';

const ProductCard = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);

  useEffect(() => {
    const barang_id = localStorage.getItem('selectedProductId');
    fetch('http://localhost:8080/barang/detail', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        barang_id
      })
    })
      .then(res => {
        if (!res.ok) {
          throw new Error(`HTTP error! Status: ${res.status}`);
        }
        return res.json();
      })
      .then(data => {
        if (data && data.success) {
          console.log('Fetched product:', data.data);
          setProduct(data.data);
        } else {
          console.error("Failed to fetch product details:", data.message);
        }
      })
      .catch(err => {
        console.error("Fetch error:", err);
        alert("An error occurred while fetching product details.");
      });
  }, []);


  const handleIncrease = () => {
    if (product && quantity < product.stock) {
      setQuantity(prevQuantity => {
        const newQuantity = prevQuantity + 1;
        console.log('Increasing quantity:', newQuantity);
        return newQuantity;
      });
    } else {
      console.log('Cannot increase: Maximum stock reached');
    }
  };

  const handleDecrease = () => {
    if (quantity > 1) {
      setQuantity(prevQuantity => {
        const newQuantity = prevQuantity - 1;
        console.log('Decreasing quantity:', newQuantity);
        return newQuantity;
      });
    } else {
      console.log('Cannot decrease: Minimum quantity reached');
    }
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  const price = product.harga;
  const subtotal = price * quantity;
  console.log('Subtotal:', subtotal);

  return (
    <div className='detail-barang-admin'>
      <header className="detailbarang-admin-header">
        <div className="detailbarang-admin-logo">G & C</div>
        <div className="detailbarang-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
      </header>
      <div className='detailbarang-admin-detail-content'>
        <div className="detailbarang-admin-product-card">
          <div className="detailbarang-admin-product-info">
            <img 
              src={product.image_url || "/images/grownncheer_logo.png"}
              alt={product.nama_barang}
              className="detailbarang-admin-product-image"
            />
            <div className="detailbarang-admin-product-details">
              <h1 className="detailbarang-admin-product-title">{product.nama_barang}</h1>
              <p className="detailbarang-admin-product-price">Rp {price.toLocaleString()}</p>
              <hr/>
              <p className="detailbarang-admin-product-description">
                Deskripsi:
                <br />
                {product.deskripsi_barang}
              </p>
              <hr/>
              <div className="detailbarang-admin-quantity-section">
                <button 
                  className="detailbarang-admin-quantity-btn" 
                  onClick={handleDecrease}
                  disabled={quantity <= 1}
                >
                  -
                </button>
                <span className="detailbarang-admin-quantity">{quantity}</span>
                <button 
                  className="detailbarang-admin-quantity-btn" 
                  onClick={handleIncrease}
                  disabled={quantity >= product.stokBarang}
                >
                  +
                </button>
              </div>
              <div className="detailbarang-admin-subtotal">
                <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
              </div>
              <button className="detailbarang-admin-add-to-cart-btn">Tambah ke Keranjang</button>
              <p className="detailbarang-admin-category">Kategori: {product.tipe_barang_id}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
