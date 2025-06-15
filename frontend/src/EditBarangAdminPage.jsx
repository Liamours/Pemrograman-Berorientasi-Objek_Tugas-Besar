import React, { useState, useEffect } from 'react';
import './EditBarangAdminStyle.css';

const ProductCard = () => {
  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);
  const [editableProduct, setEditableProduct] = useState(null);

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
      const response = await fetch('http://localhost:8080/barang/update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          barang_id: editableProduct.barang_id,
          nama_barang: editableProduct.nama_barang,
          deskripsi_barang: editableProduct.deskripsi_barang,
          harga: editableProduct.harga,
          tipe_barang_id: editableProduct.tipe_barang_id,
          image_url: editableProduct.image_url,
          stok_barang: editableProduct.stok_barang
        })
      });

      const data = await response.json();

      if (response.ok) {
        alert("Product updated successfully!");
      } else {
        alert("Error: " + (data.message || 'Failed to update product'));
      }
    } catch (error) {
      console.error('Error updating product:', error);
      alert("An error occurred while updating the product.");
    }
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  const price = editableProduct.harga;
  const subtotal = price * quantity;

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
                Deskripsi:
                <br />
                <textarea
                  name="deskripsi_barang"
                  value={editableProduct.deskripsi_barang}
                  onChange={handleChange}
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
                Stok Barang: <span>{editableProduct.stok_barang}</span>
                <input
                  type="number"
                  name="stok_barang"
                  value={editableProduct.stok_barang}
                  onChange={handleChange}
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

export default ProductCard;
