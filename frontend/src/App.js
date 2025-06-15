import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './LoginPage';
import RegistrationPage from './RegistrationPage';
import ProfilePage from './ProfilePage';
import DetailBarang from "./DetailBarangPage"
import AdminDashboard from './AdminDashboardPage';
import TambahBarang from './TambahBarangPage';
import GalleryPage from './GalleryPage';
import Checkout from './CheckoutPage';
import Keranjang from './KeranjangPage';
import GalleryPageAdmin from './GalleryBarangAdmin';
impo

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegistrationPage />} />
        <Route path="*" element={<LoginPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/DetailBarang" element={<DetailBarang/>} />
        <Route path="/TambahBarang" element={<TambahBarang />} />
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/Home" element={<GalleryPage />} />
        <Route path="/Checkout" element={<Checkout />} />
        <Route path="/Keranjang" element={<Keranjang />} />
        <Route path="/Gallery" element={<GalleryPage />} />
        <Route path="/admin/gallery" element={<GalleryPageAdmin />} />
      </Routes>
    </Router>
  );
}

export default App;