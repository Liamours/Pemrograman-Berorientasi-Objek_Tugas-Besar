import React, { useState } from 'react';
import './TambahBarangStyle.css';
import { useNavigate } from 'react-router-dom';
import { Link, Navigate } from 'react-router-dom';

const TambahBarang = () => {
  const [namaBarang, setNamaBarang] = useState('');
  const [kategori, setKategori] = useState('');
  const [harga, setHarga] = useState('');
  const [stok, setStok] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!namaBarang || !kategori || !harga || !stok) {
      setError('Semua kolom harus diisi!');
      return;
    }
    setError('');
    // Logic untuk menyimpan data barang
    console.log({ namaBarang, kategori, harga, stok });
    // Reset form setelah submit
    setNamaBarang('');
    setKategori('');
    setHarga('');
    setStok('');
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
          <input
            type="text"
            id="kategori"
            value={kategori}
            onChange={(e) => setKategori(e.target.value)}
            placeholder="Masukkan kategori"
          />
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
        {error && <p className="error-message">{error}</p>}
        <button type="submit" className="submit-button">Tambah Barang</button>
      </form>
    </div>
  );
};

export default TambahBarang;
