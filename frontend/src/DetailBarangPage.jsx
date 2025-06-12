import React, { useState, useEffect } from 'react';
import './DetailBarangStyle.css';

const ProductCard = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);

  useEffect(() => {
    const id = localStorage.getItem('id_barang');
    fetch('http://localhost:8080/api/user/profile/update', {
      method: 'PUT',
      headers: {'Content-Type': 'application/json',},
      body: JSON.stringify({
        id
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.status) {
          setProduct(data.data);
        } else {
          console.error("Failed to fetch product details:", data.message);
        }
      })
      .catch(err => console.error("Fetch error:", err));
  }, []);

  const handleIncrease = () => {
    if (product && quantity < product.stokBarang) {
      setQuantity(quantity + 1);
    }
  };

  const handleDecrease = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  const price = product.harga;
  const subtotal = price * quantity;

  return (
    <div className='detail-barang'>
      <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
      </header>
      <div className='detail-content'>
        <div className="product-card">
          <div className="product-info">
            <img 
              src={product.imageUrl || "/images/grownncheer_logo.png"}
              alt={product.namaBarang}
              className="product-image"
            />
            <div className="product-details">
              <h1 className="product-title">{product.namaBarang}</h1>
              <p className="product-price">Rp {price.toLocaleString()}</p>
              <p className="product-description">
                {product.deskripsiBarang}
              </p>
              <div className="quantity-section">
                <button className="quantity-btn" onClick={handleDecrease}>-</button>
                <span className="quantity">{quantity}</span>
                <button className="quantity-btn" onClick={handleIncrease}>+</button>
              </div>
              <div className="subtotal">
                <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
              </div>
              <button className="add-to-cart-btn">Tambah ke Keranjang</button>
              <p className="category">Kategori: {product.tipeBarang}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
