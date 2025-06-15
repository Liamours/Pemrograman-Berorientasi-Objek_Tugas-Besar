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
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmNewPassword, setShowConfirmNewPassword] = useState(false);
  const token = localStorage.getItem('token');

  const confirmChange = () => {
    document.getElementById("Change").style.width = "100%";
  };

  const cancelConfirmChange = () => {
    document.getElementById("Change").style.width = "0%";
  };

  const confirmSave = () => {
    document.getElementById("Save").style.width = "100%";
  };

  const cancelConfirmSave = () => {
    document.getElementById("Save").style.width = "0%";
  };

  const logout = () => {
    document.getElementById("LogOut").style.width = "100%";
  };

  const cancelLogout = () => {
    document.getElementById("LogOut").style.width = "0%";
  };

  const hapus = () => {
    document.getElementById("Hapus").style.width = "100%";
  };

  const cancelHapus = () => {
    document.getElementById("Hapus").style.width = "0%";
  };

useEffect(() => {
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
      if (data.data.name && data.data.email && data.data.id) {
        setName(data.data.name);
        setEmail(data.data.email);
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
    <div className="profile-admin-page">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
      <header className="profile-admin-header">
        <div className="profile-admin-logo">G & C</div>
        <div className="profile-admin-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="profile-admin-cart">Keranjang: Rp 100.000</div>
      </header>
      <div id="LogOut" className="profile-admin-overlay">
        <div className="profile-admin-popup-container">
          <h2>Yakin Ingin Keluar?</h2>
          <p>Anda perlu login lagi jika sudah keluar</p>
          <div className="profile-admin-popup-actions">
            <button className="profile-admin-btn-cancel" onClick={cancelLogout}>Batal</button>
            <button className="profile-admin-btn-confirm" onClick={() => {
              localStorage.removeItem("token");
              navigate('/login');
            }}>Terima</button>
          </div>
        </div>
      </div>
      <div id="Hapus" className="profile-admin-overlay">
        <div className="profile-admin-popup-container">
          <h2>Yakin Ingin Hapus Akun?</h2>
          <p style={{ color: "#FF0000" }}>Akun anda akan dihapus sepenuhnya</p>
          <div className="profile-admin-popup-actions">
            <button className="profile-admin-btn-confirm" onClick={cancelHapus}>Batal</button>
            <button className="profile-admin-btn-cancel" onClick={() => {
              const token = localStorage.getItem('token');
              fetch('http://localhost:8080/api/user/delete', {
                method: 'DELETE',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                  token
                })
              })
              localStorage.removeItem("token");
              navigate('/login');
            }}>Terima</button>
          </div>
        </div>
      </div>
      <div className="profile-admin-content">
        <main className="profile-admin-main">
          <div className="profile-admin-card">
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
              <div className="admin-hanya-info" id="email">
                {email}
              </div>
              <button type="button" onClick={handleSaveAccountSettings}>Simpan Perubahan</button>
            </form>
          </div>
          <div className="profile-admin-card">
            <h2>Ubah Password</h2>
            <form>
              <label htmlFor="current-password">Password Sekarang</label>
              <input
                type="password"
                id="current-password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
              />
              <div className="profile-admin-title-shownewpassword-wrapper">
                <label htmlFor="new-password">Password Baru</label>
                <button
                  type="button"
                  className="profile-admin-toggle-new-password"
                  onClick={() => setShowNewPassword(!showNewPassword)}
                >
                  {showNewPassword ? '🚫' : '👁️'}
                </button>
              </div>
              <input
                type={showNewPassword ? 'text' : 'password'}
                id="new-password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
              <div className="profile-admin-title-showconfirmnewpassword-wrapper">
                <label htmlFor="confirm-password">Konfirmasi Password</label>
                <button
                  type="button"
                  className="profile-admin-toggle-confirm-new-password"
                  onClick={() => setShowConfirmNewPassword(!showConfirmNewPassword)}
                >
                  {showConfirmNewPassword ? '🚫' : '👁️'}
                </button>
              </div>
              <input
                type={showConfirmNewPassword ? 'text' : 'password'}
                id="confirm-password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <button type="button" onClick={handleChangePassword}>Ubah Password</button>
            </form>
          </div>
          <div className='profile-admin-bottom'>
            <button className='profile-admin-bottom-button' onClick={logout}><i className="glyphicon glyphicon-log-out"></i> Logout</button>
            <button className='profile-admin-bottom-button' onClick={hapus}><i className="glyphicon glyphicon-trash"></i> Hapus Akun</button>
          </div>
        </main>
      </div>
    </div>
  );
}

export default AdminDashboardPage;