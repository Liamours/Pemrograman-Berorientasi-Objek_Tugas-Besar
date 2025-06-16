import React, { useState, useEffect } from 'react';
import './OrderDetailStyle.css';
import { useNavigate } from 'react-router-dom';

const ProductCardOrder = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);
  const [address, setAddress] = useState("");
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const barangId = localStorage.getItem('selectedProductId');

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
        if (!res.ok) {
          throw new Error(`Error: ${res.status} ${res.statusText}`);
        }
        return res.json();
      })
      .then(data => {
        if (data.data.address) {
          setAddress(data.data.address);
        }
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        navigate('/login');
      });
  }, [token, navigate]);

  useEffect(() => {
    fetch('http://localhost:8080/barang/detail', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ barang_id: barangId })
    })
      .then(res => {
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        return res.json();
      })
      .then(data => {
        if (data.success) {
          setProduct(data.data);
        } else {
          console.error("Failed to fetch product details:", data.message);
        }
      })
      .catch(err => {
        console.error("Fetch error:", err);
        alert("Gagal mengambil detail barang.");
      });
  }, [barangId]);

  useEffect(() => {
    if (!barangId) return;

    fetch(`http://localhost:8080/api/cart/order/${barangId}`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
      .then(res => {
        if (!res.ok) throw new Error(`Gagal mengambil kuantitas barang. Status: ${res.status}`);
        return res.json();
      })
      .then(data => {
        if (data) {
          setQuantity(data.jumlahBarang);
        }
      })
      .catch(err => {
        console.error("Error fetching quantity:", err);
      });
  }, [barangId, token]);

  if (!product) {
    return <div>Loading...</div>;
  }

  const subtotal = product.harga * quantity;

  return (
    <div className='detail-barang'>
      <header className="detailbarang-header">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-menu-left" onClick={() => navigate('/keranjang')}></span>
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
              <p className="detailbarang-product-price">Rp {product.harga.toLocaleString()}</p>
              <hr/>
              <p className="detailbarang-product-description">
                Deskripsi:<br />{product.deskripsi_barang}
              </p>
              <hr/>
              <div className="detailbarang-quantity-static">
                <p>Jumlah: <strong>{quantity}</strong></p>
              </div>
              <div className="detailbarang-subtotal">
                <p>Subtotal: Rp {subtotal.toLocaleString()}</p>
              </div>
              <p className="detailbarang-category">Kategori: {product.tipe_barang_id}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCardOrder;
