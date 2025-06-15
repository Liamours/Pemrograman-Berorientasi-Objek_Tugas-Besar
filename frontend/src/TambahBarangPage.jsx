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
          image_url: image
        })
      });

      const data = await response.json();
      console.log(data);
      if (data.success) {
        navigate('/gallery');
      } else if (data.message) {
        setBackendMessage(data.message);
      }

    } catch (err) {
      setBackendMessage('Terjadi kesalahan saat menghubungi server.');
    }
  };

  return (
    <div className="tambah-barang-container">
      <h2>Tambah Barang</h2>
      <form onSubmit={handleSubmit} className="tambah-barang-form">
        <div className="form-group">
          <label htmlFor="namaBarang">Nama Barang</label>
          <input
            type="text"
            id="namaBarang"
            value={namaBarang}
            onChange={(e) => setNamaBarang(e.target.value)}
            placeholder="Masukkan nama barang"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kategori">Kategori</label>
          <select
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

        <div className="form-group">
          <label htmlFor="harga">Harga</label>
          <input
            type="number"
            id="harga"
            value={harga}
            onChange={(e) => setHarga(e.target.value)}
            placeholder="Masukkan harga"
          />
        </div>

        <div className="form-group">
          <label htmlFor="stok">Stok</label>
          <input
            type="number"
            id="stok"
            value={stok}
            onChange={(e) => setStok(e.target.value)}
            placeholder="Masukkan stok"
          />
        </div>

        <div className="form-group">
          <label htmlFor="deskripsi">Deskripsi Barang</label>
          <textarea
            id="deskripsi"
            value={deskripsi}
            onChange={(e) => setDeskripsi(e.target.value)}
            placeholder="Masukkan deskripsi barang"
          />
        </div>

        <div className="form-group">
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
  );
};

export default TambahBarang;
