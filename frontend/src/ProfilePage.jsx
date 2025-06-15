import React, { useState, useEffect } from 'react';
import './ProfileStyle.css';
import { useNavigate } from 'react-router-dom';
import './LogoutStyle.css';

function ProfilePage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [member, setMember] = useState("");
  const [password, setPassword] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [address, setAddress] = useState("");
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmNewPassword, setShowConfirmNewPassword] = useState(false);
  const token = localStorage.getItem('token');
  const [notification, setNotification] = useState({
    show: false,
    message: ''
  });
  const navigate = useNavigate();

  const showNotification = (message) => {
    setNotification({ show: true, message });
    setTimeout(() => {
      setNotification({ show: false, message: '' });
    }, 2000); 
  };

  const Notification = ({ message, onClose }) => {
    return (
      <div style={{ backgroundColor:"#dca42b", border:"solid" }}className="notification">
        <span>{message}</span>
        <span className="close-btn" onClick={onClose}>×</span>
      </div>
    );
  };

  const sidebar = () => {
    document.getElementById("Sidebar").style.width = "200px";
    document.getElementById("main").style.marginLeft = "200px";
  };

  const closeSidebar = () => {
    document.getElementById("Sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  };

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

    fetch('http://localhost:8080/api/user/profile/client', {
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
          setAddress(data.data.address);
          if (data.isMember == true){
            setMember("Yes");
          }else {
            setMember("No")
          }
        }
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        // navigate('/login');
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
        email,
        address
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          showNotification("Perubahan akun berhasil disimpan!");
        } else {
          showNotification(data.message || "Gagal menyimpan perubahan akun.");
        }
      })
      .catch(() => {
        showNotification("Terjadi kesalahan saat menghubungi server.");
      });
    cancelConfirmSave();
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
          showNotification("Password berhasil diubah!");
          setCurrentPassword("");
          setNewPassword("");
          setConfirmPassword("");
        } else {
          showNotification(data.message || "Gagal mengubah password.");
        }
      })
      .catch(() => {
        showNotification("Terjadi kesalahan saat menghubungi server.");
      });
    cancelConfirmChange();
  };

  return (
    <div className="profile-page">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
      {notification.show && (
        <Notification 
          message={notification.message} 
          onClose={() => setNotification({ show: false, message: '' })}
        />
      )}
      <div id="Sidebar" class="profile-sidenav">
        <a style={{ cursor:"pointer" }} class= "closebtn" onClick={closeSidebar}>&times;</a>
        <a onClick={() => navigate('/gallery')}>Home</a>
        <a onClick={() => navigate('/keranjang')}>Keranjang</a>
        <a onClick={() => navigate('/profile')}>Profil</a>
        <a onClick={() => navigate('/gallery-admin')}>Beli Member</a>
      </div>
      <div id="LogOut" className="profile-overlay">
        <div className="profile-popup-container">
          <h2>Yakin Ingin Keluar?</h2>
          <p>Anda perlu login lagi jika sudah keluar</p>
          <div className="profile-popup-actions">
            <button className="profile-btn-cancel" onClick={cancelLogout}>Batal</button>
            <button className="profile-btn-confirm" onClick={() => {
              localStorage.removeItem("token");
              navigate('/login');
            }}>Terima</button>
          </div>
        </div>
      </div>
      <div id="Hapus" className="profile-overlay">
        <div className="profile-popup-container">
          <h2>Yakin Ingin Hapus Akun?</h2>
          <p>Akun anda akan dihapus sepenuhnya</p>
          <div className="profile-popup-actions">
            <input type="password" placeholder='Password akun anda' value={password} onChange={(e) => setPassword(e.target.value)}></input>
            <button className="profile-btn-cancel" onClick={cancelHapus}>Batal</button>
            <button className="profile-btn-confirm" onClick={async () => {
              const response = await fetch('http://localhost:8080/api/user/delete', {
                method: 'DELETE',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                  token: localStorage.getItem('token'),
                  password: password
                })
              });
              const data = await response.json();
              if (response.ok) {
                if (data.success) {
                  localStorage.removeItem("token");
                  navigate('/login');
                }else {
                  alert(data.message);
                }
              }
            }}>Terima</button>
          </div>

        </div>
      </div>
      <div id="Change" className="profile-overlay">
        <div className="profile-popup-container">
          <h2>Yakin Ingin Ubah Password?</h2>
          <p>Perubahan tidak akan bisa dikembalikan</p>
          <div className="profile-popup-actions">
            <button className="profile-btn-cancel" onClick={cancelConfirmChange}>Batal</button>
            <button className="profile-btn-confirm" onClick={handleChangePassword}>Terima</button>
          </div>
        </div>
      </div>
      <div id="Save" className="profile-overlay">
        <div className="profile-popup-container">
          <h2>Yakin Ingin Simpan Perubahan?</h2>
          <p>Perubahan tidak akan bisa dikembalikan</p>
          <div className="profile-popup-actions">
            <button className="profile-btn-cancel" onClick={cancelConfirmSave}>Batal</button>
            <button className="profile-btn-confirm" onClick={handleSaveAccountSettings}>Terima</button>
          </div>
        </div>
      </div>
      <header className="profile-header">
        <span style={{ cursor:"pointer",fontSize:"40px" }} class="glyphicon glyphicon-list" onClick={sidebar}></span>
        <div className="profile-location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <img style={{ width:"100px" }} src="/images/logogncmin.png" alt="Logo" />
      </header>
      <div className="profile-content">
        <main id="main" className="profile-main">
          <div className="profile-card">
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
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <address htmlFor="address">Alamat</address>
              <input
                type="text"
                id="address"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
              />
              <label htmlFor="member">Member</label>
              <input
                type="text"
                id="member"
                value={member}
                onChange={(e) => setMember(e.target.value)}
              />
              <button type="button" onClick={confirmSave}>Simpan Perubahan</button>
            </form>
          </div>
          <div className="profile-card">
            <h2>Ubah Password</h2>
            <form>
              <label htmlFor="current-password">Password Sekarang</label>
              <input
                type="password"
                id="current-password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
              />
              <div className="profile-title-shownewpassword-wrapper">
                <label htmlFor="new-password">Password Baru</label>
                <button
                  type="button"
                  className="profile-toggle-new-password"
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
              <div className="profile-title-showconfirmnewpassword-wrapper">
                <label htmlFor="confirm-password">Konfirmasi Password</label>
                <button
                  type="button"
                  className="profile-toggle-confirm-new-password"
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
              <button type="button" onClick={confirmChange}>Ubah Password</button>
            </form>
          </div>
          <div className='profile-bottom'>
            <button className='profile-bottom-button' onClick={logout}><i className="glyphicon glyphicon-log-out"></i> Logout</button>
            <button className='profile-bottom-button' onClick={hapus}><i className="glyphicon glyphicon-trash"></i> Hapus Akun</button>
          </div>
        </main>
      </div>
    </div>
  );
}

export default ProfilePage;