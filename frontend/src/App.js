import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './LoginPage';
import RegistrationPage from './RegistrationPage';
import ProfilePage from './ProfilePage';
import DetailBarang from "./DetailBarangPage"
import AdminDashboard from './AdminDashboardPage';
import TambahBarang from './TambahBarangPage';

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
      </Routes>
    </Router>
  );
}

export default App;
