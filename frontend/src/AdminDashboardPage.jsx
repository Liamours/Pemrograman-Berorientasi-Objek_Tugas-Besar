import React, { useState, useEffect } from 'react';
import './AdminDashboardStyle.css';
import { useNavigate } from 'react-router-dom';
import './LogoutStyle.css';

function AdminDashboardPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);

  const logout = () => {
    document.getElementById("myNav").style.width = "100%";
  };

  const cancelLogout = () => {
    document.getElementById("myNav").style.width = "0%";
  };

  const hapus = () => {
    document.getElementById("Hapus").style.width = "100%";
  };

  const cancelHapus = () => {
    document.getElementById("Hapus").style.width = "0%";
  };

useEffect(() => {
  const token = localStorage.getItem('token');
  console.log("Token dari localStorage:", token);

  fetch('http://localhost:8080/api/user/profile/admin', {
  method: 'GET',
  credentials: 'include',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})
    .then(res => {
      if (!res.ok) {
        throw new Error(`Error: ${res.status} ${res.statusText}`);
      }
      return res.json();
    })
    .then(data => {
      if (data.name && data.email && data.id) {
        setName(data.name);
        setEmail(data.email);
      }
    })
    .catch((err) => {
      console.error("Fetch error:", err);
      navigate('/login');
    });
}, []);


  const handleSaveAccountSettings = () => {
    fetch('http://localhost:8080/api/user/profile/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        name,
        email
      })
    })
      .then(res => res.json())
      .then(data => {
        alert(data.message);
      })
      .catch(() => {
        alert("Terjadi kesalahan saat menghubungi server.");
      });
  };

  const handleChangePassword = () => {
    if (newPassword !== confirmPassword) {
      alert("Password baru dan konfirmasi password tidak cocok!");
      return;
    }

    fetch('http://localhost:8080/api/user/password/change', {
      method: 'PUT', 
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        currentPassword,
        newPassword,
        confirmPassword
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          alert("Password berhasil diubah!");
        } else {
          alert(data.message || "Gagal mengubah password.");
        }
      })
      .catch(() => {
        alert("Terjadi kesalahan saat menghubungi server.");
      });
  };

  return (
    <div className="account-settings">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
      <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
      </header>
      <div id="LogOut" class="overlay">
        <div class="popup-container">
          <h2>Yakin Ingin Keluar?</h2>
          <p>Anda perlu login lagi jika sudah keluar</p>
        <div class="popup-actions">
          <button class="btn-cancel" onClick={cancelLogout}>Batal</button>
          <button class="btn-confirm" onClick={() => {
            localStorage.removeItem("token");
            navigate('/login');
          }}>Terima</button>
          
        </div>
      </div>
      <div id="Hapus" class="overlay">
        <div class="popup-container">
          <h2>Yakin Ingin Hapus Akun?</h2>
          <p style={{ color: "#FF0000" }}>Akun anda akan dihapus sepenuhnya</p>
          <div class="popup-actions">
            <button class="btn-confirm" onClick={cancelHapus}>Batal</button>
            <button class="btn-cancel" onClick={() => navigate('/login')}>Terima</button>
          </div>
        </div>
      </div>
    </div>
     <div className="content">
        <nav className="sidebar">
          <ul><h2>Navigation</h2>
            <li><a href="#"><i class="glyphicon glyphicon-shopping-cart"></i> Shopping Cart</a></li>
            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> Settings</a></li>
            <li><a style={{ cursor: "pointer" }} onClick={logout}><i class="glyphicon glyphicon-log-out"></i> Log-out</a></li>
            <li><a style={{ color: "#ff0000", cursor: "pointer" }} onClick={hapus}><i class="glyphicon glyphicon-trash"></i> Hapus Akun</a></li>
          </ul>
        </nav> 
        <main className="main">
          <div className="card">
            <h2>Informasi Akun</h2>
            <form>
              <label htmlFor="name">Nama</label>
              <input
                type="text"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
              <label htmlFor="email">Email</label>
              <div className="hanya info" id="email">
                {email}
              </div>
              <button type="button" onClick={handleSaveAccountSettings}>Simpan Perubahan</button>
            </form>
          </div>
          <div className="card">
            <h2>Ubah Password</h2>
            <form>
              <label htmlFor="current-password">Password Sekarang</label>
              <input
                type="password"
                id="current-password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
              />
              <label htmlFor="new-password">Password Baru</label>
              <input
                type="password"
                id="new-password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
              <label htmlFor="confirm-password">Konfirmasi Password</label>
              <input
                type="password"
                id="confirm-password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <button type="button" onClick={handleChangePassword}>Ubah Password</button>
            </form>
          </div>
        </main>
      </div>
    </div>
  );
}

export default AdminDashboardPage;