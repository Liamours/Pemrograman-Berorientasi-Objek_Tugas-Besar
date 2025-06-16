import React, { useState } from 'react';
import './TambahBarangStyle.css';
import { useNavigate } from 'react-router-dom';

const TambahBarang = () => {
  const [namaBarang, setNamaBarang] = useState('');
  const [kategori, setKategori] = useState('');
  const [harga, setHarga] = useState('');
  const [stok, setStok] = useState('');
  const [deskripsi, setDeskripsi] = useState('');
  const [image, setImage] = useState('');
  const [backendMessage, setBackendMessage] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const sidebarTambahBarang = () => {
    document.getElementById("Sidebar").style.width = "200px";
    document.getElementById("main").style.marginLeft = "200px";
  };

  const closeSidebarTambahBarang = () => {
    document.getElementById("Sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/barang/new', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify({
          nama_barang: namaBarang,
          deskripsi_barang: deskripsi,
          harga: harga,
          tipe_barang: kategori,
          stok_barang: stok,
          image_url: "/images/" + image
        })
      });

      const data = await response.json();
      console.log(data);
      if (data.success) {
        navigate('/admin/gallery');
      } else if (data.message) {
        setBackendMessage(data.message);
      }

    } catch (err) {
      setBackendMessage('Terjadi kesalahan saat menghubungi server.');
    }
  };

  return (
    <div className="tambah-barang-page" id="main">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
      <div id="Sidebar" className="tambah-barang-sidenav">
        <a style={{ cursor: "pointer" }} className="closebtn" onClick={closeSidebarTambahBarang}>&times;</a>
        <a onClick={closeSidebarTambahBarang}>Daftar Barang</a>
        <hr></hr>
        <a onClick={() => navigate('/tambahBarang')}>Tambah Barang</a>
        <hr></hr>
        <a onClick={() => navigate('/admin/receipt')}>Nota</a>
        <hr></hr>
        <a onClick={() => navigate('/admin/dashboard')}>Profil</a>
        <hr></hr>
      </div>
      <header className="tambah-barang-header">
        <span style={{ fontSize:"40px" }} className="glyphicon glyphicon-list" onClick={sidebarTambahBarang}></span>
        <div className="tambah-barang-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width: "100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>
      <div className='tambah-barang-main'>
        <h2>Tambah Barang</h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="tambah-barang-input-group">
            <label htmlFor="namaBarang">Nama Barang</label>
            <input
              type="text"
              id="namaBarang"
              value={namaBarang}
              onChange={(e) => setNamaBarang(e.target.value)}
              placeholder="Masukkan nama barang"
            />
          </div>
          <div className="tambah-barang-input-group">
            <label htmlFor="kategori">Kategori</label>
            <select
              style={{ width:"100%",height:"30px" }}
              id="kategori"
              value={kategori}
              onChange={(e) => setKategori(e.target.value)}
            >
              <option value="">Pilih Kategori</option>
              <option value="Makanan">Makanan</option>
              <option value="Minuman">Minuman</option>
              <option value="Hygine">Hygine</option>
            </select>
          </div>

          <div className="tambah-barang-input-group">
            <label htmlFor="harga">Harga</label>
            <input
              type="number"
              id="harga"
              value={harga}
              onChange={(e) => setHarga(e.target.value)}
              placeholder="Masukkan harga"
            />
          </div>

          <div className="tambah-barang-input-group">
            <label htmlFor="stok">Stok</label>
            <input
              type="number"
              id="stok"
              value={stok}
              onChange={(e) => setStok(e.target.value)}
              placeholder="Masukkan stok"
            />
          </div>

          <div className="tambah-barang-input-group">
            <label htmlFor="deskripsi">Deskripsi Barang</label>
            <textarea
              style={{ width:"100%" }}
              id="deskripsi"
              value={deskripsi}
              onChange={(e) => setDeskripsi(e.target.value)}
              placeholder="Masukkan deskripsi barang"
            />
          </div>

          <div className="tambah-barang-input-group">
            <label htmlFor="image">Nama File Gambar</label>
            <input
              type="text"
              id="image"
              value={image}
              onChange={(e) => setImage(e.target.value)}
              placeholder="Masukkan nama file gambar"
            />
          </div>

          <button type="submit" className="submit-button">Tambah Barang</button>
        </form>

      {backendMessage && <p className="backend-message">{backendMessage}</p>}
      </div>
    </div>
  );
};

export default TambahBarang;
