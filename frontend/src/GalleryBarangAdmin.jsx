import React from "react";
import "./GalleryStyle.css";

const GalleryPageAdmin = () => {
  const products = Array(12).fill({
    name: "Barang Lain",
    price: "Rp 202.000",
    image: "", 
  });
  products[0] = { name: "1 Dus Kapal Api Mix", price: "Rp 202.000", image: "/path/to/image" };

  return (
    <div className="gallery-container">
      <header className="gallery-header">
        <div className="gallery-logo">G & C</div>
        <div className="gallery-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="gallery-cart">Keranjang: Rp 100.000</div>
      </header>
      <main className="gallery-grid-container">
        {products.map((product, index) => (
          <div key={index} className="gallery-product-card">
            <img
              src={product.image}
              alt={product.name}
              className="gallery-product-image"
            />
            <div className="gallery-product-info">
              <p className="gallery-product-name">{product.name}</p>
              <p className="gallery-product-price">{product.price}</p>
            </div>
          </div>
        ))}
      </main>
    </div>
  );
};

export default GalleryPageAdmin;