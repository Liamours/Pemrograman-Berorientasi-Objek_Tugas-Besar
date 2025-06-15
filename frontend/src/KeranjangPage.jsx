import React from "react";
import "./KeranjangStyle.css";

const ShoppingCart = () => {
  const items = [
    {
      id: 1,
      image: "link_to_kapal_api_image",
      name: "1 Dus Kapal Kapal Api mix",
      price: 202000,
      quantity: 1,
    },
    {
      id: 2,
      image: "link_to_rinso_image",
      name: "1 Dus Rinso Molto 1000",
      price: 120000,
      quantity: 1,
    },
    {
      id: 3,
      image: "link_to_marjan_image",
      name: "1 Dus Marjan Melon",
      price: 64000,
      quantity: 1,
    },
    {
      id: 4,
      image: "link_to_taro_image",
      name: "1 Dus Taro 1000",
      price: 53000,
      quantity: 1,
    },
  ];

  const subtotal = items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const discount = 30000;
  const total = subtotal - discount;

  return (
    <div className="shopping-cart">
      <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
      </header>
      <div className="contain-keranjang">
        <table className="cart-table">
            <thead>
            <tr>
                <th>Produk</th>
                <th>Harga Satuan</th>
                <th>Kuantitas</th>
                <th>Total Harga</th>
                <th>Aksi</th>
                </tr>
                </thead>
            <tbody>
            {items.map((item) => (
                <tr key={item.id}>
                <td>
                    <img src={item.image} alt={item.name} className="product-image"/>
                </td>
                <td>{item.name}</td>
                <td>Rp {item.price.toLocaleString("id-ID")}</td>
                <td>
                    <div className="quantity-control">
                    <button className="btn-quantity">-</button>
                    <span>{item.quantity}</span>
                    <button className="btn-quantity">+</button>
                    </div>
                </td>
                <td>Rp {(item.price * item.quantity).toLocaleString("id-ID")}</td>
                <td>
                    <button className="btn-remove">×</button>
                </td>
                </tr>
            ))}
            </tbody>
        </table>
        <div className="cart-summary">
            <p>Subtotal: Rp {subtotal.toLocaleString("id-ID")}</p>
            <p>Potongan: Rp {discount.toLocaleString("id-ID")}</p>
        <h2>Total: Rp {total.toLocaleString("id-ID")}</h2>
        <button className="btn-checkout">Checkout</button>
      </div>
    </div>
    </div>  
  );
};

export default ShoppingCart;
