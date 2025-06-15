import React, { useState, useEffect } from 'react';
import './EditBarangAdminStyle.css';
import { useNavigate } from 'react-router-dom';

const ProductCardAdmin = () => {
  const [product, setProduct] = useState(null);
  const [editableProduct, setEditableProduct] = useState(null);
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  const checkStatusAdmin = async () => {
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
    checkStatusAdmin();
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
          setEditableProduct({ ...data.data });
        } else {
          console.error("Failed to fetch product details:", data.message);
        }
      })
      .catch(err => {
        console.error("Fetch error:", err);
        alert("An error occurred while fetching product details.");
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditableProduct((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleUpdate = async () => {
    try {
      const response = await fetch('http://localhost:8080/barang/update/detail', {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify({
          barang_id: editableProduct.barang_id,
          nama_barang: editableProduct.nama_barang,
          deskripsi_barang: editableProduct.deskripsi_barang,
          harga: editableProduct.harga,
          tipe_barang_id: editableProduct.tipe_barang_id,
          image_url: editableProduct.image_url,
          stok_barang: editableProduct.stock
        })
      });

      const data = await response.json();
      let jsonData = {};
      try {
        jsonData = JSON.parse(data);
      } catch (error) {
        console.error('Error parsing JSON:', error);
      }

      if (response.ok) {
        alert("Product updated successfully!");
      } else {
        alert("Error: " + (jsonData.message || 'Failed to update product'));
      }
    } catch (error) {
      console.error('Error updating product:', error);
      alert("Error");
    }
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div className='detail-barang-admin'>
      <header className="detailbarang-admin-header">
        <div className="detailbarang-admin-logo">G & C</div>
        <div className="detailbarang-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
      </header>
      <div className='detailbarang-admin-detail-content'>
        <div className="detailbarang-admin-product-card">
          <div className="detailbarang-admin-product-info">
            <img
              src={editableProduct.image_url || "/images/grownncheer_logo.png"}
              alt={editableProduct.nama_barang}
              className="detailbarang-admin-product-image"
            />
            <div className="detailbarang-admin-product-details">
              <h1 className="detailbarang-admin-product-title">
                <input
                  type="text"
                  name="nama_barang"
                  className="detailbarang-admin-product-name"
                  value={editableProduct.nama_barang}
                  onChange={handleChange}
                />
              </h1>
              <p className="detailbarang-admin-product-price">
                <input
                  type="number"
                  name="harga"
                  value={editableProduct.harga}
                  onChange={handleChange}
                />
              </p>
              <p className="detailbarang-admin-product-description">
                <label htmlFor="deskripsi_barang">Deskripsi:</label>
                <textarea
                  name="deskripsi_barang"
                  id="deskripsi_barang"
                  value={editableProduct.deskripsi_barang}
                  onChange={handleChange}
                  className="detailbarang-admin-textarea"
                  placeholder="Masukkan deskripsi barang di sini"
                />
              </p>
              <p className="detailbarang-admin-category">
                Kategori:
                <select
                  name="tipe_barang_id"
                  value={editableProduct.tipe_barang_id}
                  onChange={handleChange}
                >
                  <option value="Makanan">Makanan</option>
                  <option value="Minuman">Minuman</option>
                  <option value="Hygine">Hygine</option>
                </select>
              </p>
              <p className="detailbarang-admin-stock">
                Stok Barang:
                <input
                  type="number"
                  name="stok_barang"
                  value={editableProduct.stock}
                  onChange={handleChange}
                  placeholder='Masukkan stok barang'
                />
              </p>
              <p className="detailbarang-admin-image-url">
                Nama File Gambar (/images/NamaFile.jpg atau .png):
                <input
                  type="text"
                  name="image_url"
                  value={editableProduct.image_url}
                  onChange={handleChange}
                  placeholder="Masukkan nama file gambar"
                />
              </p>
              <button className="detailbarang-admin-update-btn" onClick={handleUpdate}>
                Update Product
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCardAdmin;