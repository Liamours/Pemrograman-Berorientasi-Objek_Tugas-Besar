import React, { useState, useEffect } from "react";
import "./GalleryBarangAdmin.css";
import { useNavigate } from 'react-router-dom';

const GalleryAdminPage = () => {
  const [products, setProducts] = useState([]);
  const [namaBarang, setNamaBarang] = useState('');
  const [kategori, setKategori] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const sidebar = () => {
    document.getElementById("Sidebar").style.width = "200px";
    document.getElementById("main").style.marginLeft = "200px";
  };

  const closeSidebar = () => {
    document.getElementById("Sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  };

  const handleSearch = async () => {
    try {
      const response = await fetch('http://localhost:8080/barang', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          nama_barang: namaBarang,
          tipe_barang: kategori,
        })
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();

      if (data.success && Array.isArray(data.data)) {
        setProducts(data.data);
      } else {
        console.error("Data tidak valid atau tidak ada produk yang ditemukan");
      }
    } catch (error) {
      console.error('There was an error fetching the products:', error);
    }
  };

  const checkAdminStatus = async () => {
    if (!token) {
      navigate('/login');
      return;
    }
    try {
      const response = await fetch('http://localhost:8080/api/user/profile/admin', {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });

      if (response.ok) {
        const data = await response.json();
        if (data.data.role !== 'Admin') {
          navigate('/login');
        }
      } else {
        navigate('/login');
      }
    } catch (error) {
      console.error("Error checking admin status", error);
      navigate('/login');
    }
  };

  useEffect(() => {
    checkAdminStatus();
    handleSearch();
  }, []);
  const handleProductClick = (productId) => {
    localStorage.setItem('selectedProductId', productId);
    navigate('/admin/edit');
  };

  return (
    <div className="gallery-admin-container">
      <div id="Sidebar" className="gallery-admin-sidenav">
        <a style={{ cursor: "pointer" }} className="closebtn" onClick={closeSidebar}>&times;</a>
        <a onClick={() => navigate('/gallery-admin')}>Gallery Barang</a>
        <a onClick={() => navigate('/tambahBarang')}>Tambah Barang</a>
        <a onClick={() => navigate('/profile')}>Profil</a>
      </div>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <header className="gallery-admin-header">
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-list" onClick={sidebar}></span>
        <div className="gallery-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>

      <div className="gallery-admin-search-container">
        <input
          type="text"
          placeholder="Cari Nama Barang"
          value={namaBarang}
          onChange={(e) => setNamaBarang(e.target.value)}
        />
        <select
          value={kategori}
          onChange={(e) => setKategori(e.target.value)}
        >
          <option value="">Semua Kategori</option>
          <option value="Makanan">Makanan</option>
          <option value="Minuman">Minuman</option>
          <option value="Hygine">Hygine</option>
        </select>
        <button onClick={handleSearch}>Cari</button>
      </div>

      <main className="gallery-admin-grid-container" id="main">
        {products.length === 0 ? (
          <p>Produk tidak tersedia.</p>
        ) : (
          products.map((product, index) => (
            <div key={index} className="gallery-admin-product-card" onClick={() => handleProductClick(product.id)}>
              <img
                src={product.image_url}
                alt={product.name}
                className="gallery-admin-product-image"
              />
              <div className="gallery-admin-product-info">
                <p className="gallery-admin-product-name">{product.name}</p>
                <p className="gallery-admin-product-price">{`Rp ${product.price.toLocaleString()}`}</p>
                <p className="gallery-product-stock">Stok: {product.stock}</p>
              </div>
            </div>
          ))
        )}
      </main>
    </div>
  );
};

export default GalleryAdminPage;