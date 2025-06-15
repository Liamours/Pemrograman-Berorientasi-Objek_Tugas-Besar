import React, { useState } from 'react';
import './TambahBarangStyle.css';
import { useNavigate } from 'react-router-dom';

const TambahBarang = () => {
  const [namaBarang, setNamaBarang] = useState('');
  const [kategori, setKategori] = useState('');
  const [harga, setHarga] = useState('');
  const [stok, setStok] = useState('');
  const [image, setImage] = useState(null); // For storing image
  const [error, setError] = useState('');
  const [errorMessages, setErrorMessages] = useState({
    namaBarang: '',
    kategori: '',
    harga: '',
    stok: '',
    image: ''
  });
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    let valid = true;
    const newErrorMessages = {
      namaBarang: '',
      kategori: '',
      harga: '',
      stok: '',
      image: ''
    };

    // Validate fields individually
    if (!namaBarang) {
      newErrorMessages.namaBarang = 'Nama Barang harus diisi!';
      valid = false;
    }
    if (!kategori) {
      newErrorMessages.kategori = 'Kategori harus diisi!';
      valid = false;
    }
    if (!harga) {
      newErrorMessages.harga = 'Harga harus diisi!';
      valid = false;
    }
    if (!stok) {
      newErrorMessages.stok = 'Stok harus diisi!';
      valid = false;
    }
    if (!image) {
      newErrorMessages.image = 'Foto barang harus diupload!';
      valid = false;
    }

    // If validation fails, update the error messages state
    if (!valid) {
      setErrorMessages(newErrorMessages);
      return;
    }

    setErrorMessages({
      namaBarang: '',
      kategori: '',
      harga: '',
      stok: '',
      image: ''
    });

    // Logic for saving the data
    console.log({ namaBarang, kategori, harga, stok, image });

    // Here you would typically send the image to the backend to be stored in the server's public directory.
    // For example, if you have a backend API, you could send it like this:

    // const formData = new FormData();
    // formData.append('image', image);
    // formData.append('namaBarang', namaBarang);
    // formData.append('kategori', kategori);
    // formData.append('harga', harga);
    // formData.append('stok', stok);

    // fetch('/upload-endpoint', { method: 'POST', body: formData })
    //   .then(response => response.json())
    //   .then(data => console.log(data))
    //   .catch(err => console.error('Error uploading image:', err));

    // Reset the form
    setNamaBarang('');
    setKategori('');
    setHarga('');
    setStok('');
    setImage(null); // Reset image after submit
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImage(file);
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
          {errorMessages.namaBarang && <p className="error-message">{errorMessages.namaBarang}</p>}
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
          {errorMessages.kategori && <p className="error-message">{errorMessages.kategori}</p>}
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
          {errorMessages.harga && <p className="error-message">{errorMessages.harga}</p>}
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
          {errorMessages.stok && <p className="error-message">{errorMessages.stok}</p>}
        </div>

        {/* File upload input */}
        <div className="form-group">
          <label htmlFor="image">Upload Foto</label>
          <input
            type="file"
            id="image"
            onChange={handleImageChange}
            accept="image/*"
          />
          {errorMessages.image && <p className="error-message">{errorMessages.image}</p>}
        </div>

        <button type="submit" className="submit-button">Tambah Barang</button>
      </form>
    </div>
  );
};

export default TambahBarang;
