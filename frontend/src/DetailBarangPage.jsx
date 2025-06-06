import React, { useState } from 'react';
import './DetailBarangStyle.css'; // Import the CSS file

const ProductCard = () => {
  const [quantity, setQuantity] = useState(5);

  const handleIncrease = () => setQuantity(quantity + 1);
  const handleDecrease = () => setQuantity(quantity > 1 ? quantity - 1 : 1);

  const price = 202000;
  const subtotal = price * quantity;

  return (
    <div className="product-card">
      <div className="product-info">
        <img 
          src="https://via.placeholder.com/150" // Placeholder image for the product image
          alt="Kapal Api Mix"
          className="product-image"
        />
        <div className="product-details">
          <h1 className="product-title">1 Dus Kapal Api Mix</h1>
          <p className="product-price">Rp {price.toLocaleString()}</p>
          <p className="product-description">
            Kopi Kapal Api Special Mix terbuat dari paduan biji kopi berkualitas "special" dan gula, menghasilkan aroma dan rasa yang jelas lebih enak, siap memberikan semangat untuk memulai hari.
            <br />
            1 Dus berisi 12 Renteng
          </p>
        </div>
      </div>
      <div className="quantity-section">
        <button className="quantity-btn" onClick={handleDecrease}>-</button>
        <span className="quantity">{quantity}</span>
        <button className="quantity-btn" onClick={handleIncrease}>+</button>
      </div>
      <div className="subtotal">
        <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
      </div>
      <button className="add-to-cart-btn">Tambah ke Keranjang</button>
      <p className="category">Kategori: Minuman</p>
    </div>
  );
};

export default ProductCard;
