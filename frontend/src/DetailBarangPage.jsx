import React, { useState, useEffect } from 'react';
import './DetailBarangStyle.css';

const ProductCard = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);

  useEffect(() => {
    // const id = localStorage.getItem('id_barang');
    const barang_id = 1;
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
        return res.json(); // Parse the response as JSON
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
        // Log any errors, such as network issues or unexpected response format
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
              src={product.image_url || "/images/grownncheer_logo.png"}
              alt={product.nama_barang}
              className="product-image"
            />
            <div className="product-details">
              <h1 className="product-title">{product.nama_barang}</h1>
              <p className="product-price">Rp {price.toLocaleString()}</p>
              <p className="product-description">
                {product.deskripsi_barang}
              </p>
              <div className="quantity-section">
                <button 
                  className="quantity-btn" 
                  onClick={handleDecrease}
                  disabled={quantity <= 1}
                >
                  -
                </button>
                <span className="quantity">{quantity}</span>
                <button 
                  className="quantity-btn" 
                  onClick={handleIncrease}
                  disabled={quantity >= product.stokBarang}
                >
                  +
                </button>
              </div>
              <div className="subtotal">
                <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
              </div>
              <button className="add-to-cart-btn">Tambah ke Keranjang</button>
              <p className="category">Kategori: {product.tipe_barang_id}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
