import React from "react";
import "./GalleryStyle.css";

const GalleryPage = () => {
  const products = Array(12).fill({
    name: "Barang Lain",
    price: "Rp 202.000",
    image: "", 
  });
  products[0] = { name: "1 Dus Kapal Api Mix", price: "Rp 202.000", image: "/path/to/image" };

  return (
    <div className="container">
      <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
      </header>
      <main className="grid-container">
        {products.map((product, index) => (
          <div key={index} className="product-card">
            <img
              src={product.image}
              alt={product.name}
              className="product-image"
            />
            <div className="product-info">
              <p className="product-name">{product.name}</p>
              <p className="product-price">{product.price}</p>
            </div>
          </div>
        ))}
      </main>
    </div>
  );
};

export default GalleryPage;
