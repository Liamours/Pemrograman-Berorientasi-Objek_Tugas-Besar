import React, { useState, useEffect } from "react";
import "./GalleryStyle.css";
import { useNavigate } from 'react-router-dom';

const GalleryPage = () => {
  const [products, setProducts] = useState([]);
  const [namaBarang, setNamaBarang] = useState('');
  const [kategori, setKategori] = useState('');
  const navigate = useNavigate();

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
        const availableProducts = data.data.filter(product => product.stock > 0);
        setProducts(availableProducts);
      } else {
        console.error("Data tidak valid atau tidak ada produk yang ditemukan");
      }
    } catch (error) {
      console.error('There was an error fetching the products:', error);
    }
  };

  useEffect(() => {
    handleSearch();
  }, [namaBarang, kategori]);

  const handleProductClick = (productId) => {
    localStorage.setItem('selectedProductId', productId);
    navigate(`/DetailBarang`);
  };

  return (
    <div className="gallery-container" id="main">
      <div id="Sidebar" className="gallery-sidenav">
        <a style={{ cursor: "pointer" }} className="closebtn" onClick={closeSidebar}>&times;</a>
        <a onClick={() => navigate('/gallery')}>Home</a>
        <a onClick={() => navigate('/keranjang')}>Keranjang</a>
        <a onClick={() => navigate('/profile')}>Profil</a>
      </div>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <header className="gallery-header">
        <span style={{ cursor: "pointer", fontSize: "40px" }} className="glyphicon glyphicon-list" onClick={sidebar}></span>
        <div className="gallery-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>

      <div className="gallery-search-container">
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
      </div>
      <main className="gallery-grid-container">
        {products.length === 0 ? (
          <p>Produk tidak tersedia.</p>
        ) : (
          products.map((product, index) => (
            <div key={index} className="gallery-product-card" onClick={() => handleProductClick(product.id)}>
              <img
                src={product.image_url}
                alt={product.name}
                className="gallery-product-image"
              />
              <div className="gallery-product-info">
                <p className="gallery-product-name">{product.name}</p>
                <p className="gallery-product-price">{`Rp ${product.price.toLocaleString()}`}</p>
              </div>
            </div>
          ))
        )}
      </main>
    </div>
  );
};

export default GalleryPage;
