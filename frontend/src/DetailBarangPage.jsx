import React, { useState, useEffect } from 'react';
import './DetailBarangStyle.css';
import { useNavigate } from 'react-router-dom';

const ProductCard = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);
  const navigate = useNavigate();

  const tambahBarangKeranjang = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/order/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({
          barang_id: product.barang_id,
          jumlah_barang: quantity,
        })
      });
      console.log('Response status:',product.barang_id, quantity);

      const data = await response.json();
      if (data.success) {
        console.log('Product added to cart:', data.data);
        
      } else {
        console.error("Failed to add product to cart:", data.message);
        alert("Gagal menambahkan barang ke keranjang.");
      }
    } catch (error) {
      console.error("Error saat menambahkan barang:", error);
      alert("Terjadi kesalahan saat menambahkan barang.");
    }
  };


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
    <div className='detail-barang'>
      <header className="detailbarang-header">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-menu-left" onClick={() => (navigate('/gallery'))}></span>
        <div className="gallery-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>
      <div className='detailbarang-detail-content'>
        <div className="detailbarang-product-card">
          <div className="detailbarang-product-info">
            <img 
              src={product.image_url || "/images/grownncheer_logo.png"}
              alt={product.nama_barang}
              className="detailbarang-product-image"
            />
            <div className="detailbarang-product-details">
              <h1 className="detailbarang-product-title">{product.nama_barang}</h1>
              <p className="detailbarang-product-price">Rp {price.toLocaleString()}</p>
              <hr/>
              <p className="detailbarang-product-description">
                Deskripsi:
                <br />
                {product.deskripsi_barang}
              </p>
              <hr/>
              <div className="detailbarang-quantity-section">
                <button 
                  className="detailbarang-quantity-btn" 
                  onClick={handleDecrease}
                  disabled={quantity <= 1}
                >
                  -
                </button>
                <span className="detailbarang-quantity">{quantity}</span>
                <button 
                  className="detailbarang-quantity-btn" 
                  onClick={handleIncrease}
                  disabled={quantity >= product.stokBarang}
                >
                  +
                </button>
              </div>
              <div className="detailbarang-subtotal">
                <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
              </div>
              <button className="detailbarang-add-to-cart-btn" onClick={tambahBarangKeranjang}>Tambah ke Keranjang</button>
              <p className="detailbarang-category">Kategori: {product.tipe_barang_id}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
