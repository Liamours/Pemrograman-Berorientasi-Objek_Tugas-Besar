import React, { useState, useEffect } from 'react';
import './ProfileStyle.css';

function ProfilePage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [memberNumber, setMemberNumber] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

 useEffect(() => {
    fetch('http://localhost:8080/api/user/profile', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
      .then(res => res.json())
      .then(data => {
        if (data.name && data.email) {
          setName(data.name);
          setEmail(data.email);
          setMemberNumber("");
        } else {
          setName("");
          setEmail("");
          setMemberNumber("");
        }
      })
      .catch(() => {
        setName("");
        setEmail("");
        setMemberNumber("");
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
        if (data.success) {
          alert("Perubahan akun berhasil disimpan!");
        } else {
          alert(data.message || "Gagal menyimpan perubahan akun.");
        }
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

    fetch('http://localhost:8080/api/user/profile/change-password', {
      method: 'PUT', // ganti dari PUSH ke PUT
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        currentPassword,
        newPassword
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          alert("Password berhasil diubah!");
          setCurrentPassword("");
          setNewPassword("");
          setConfirmPassword("");
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
      <header className="header">
        <div className="logo">G & C</div>
        <div className="location">Location: Purwadadi - Subang, Jawa Barat, Indonesia</div>
        <div className="cart">Keranjang: Rp 100.000</div>
      </header>
      <div className="content">
        <nav className="sidebar">
          <ul>
            <li><a href="#">Shopping Cart</a></li>
            <li><a href="#">Settings</a></li>
            <li><a href="#">Log-out</a></li>
          </ul>
        </nav>
        <main className="main">
          <div className="card">
            <h2>Setting Akun</h2>
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
              <label htmlFor="member-number">Nomor Member</label>
              <input
                type="text"
                id="member-number"
                value={memberNumber}
                onChange={(e) => setMemberNumber(e.target.value)}
              />
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

export default ProfilePage;