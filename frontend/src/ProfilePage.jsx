import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './ProfileStyle.css';

function ProfilePage() {
    const [name, setName] = useState("Dzaky Alfaris");
    const [email, setEmail] = useState("dianne.russell@gmail.com");
    const [memberNumber, setMemberNumber] = useState("999-555-0123");
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const handleSaveAccountSettings = () => {
        alert(`Account settings saved:\nName: ${name}\nEmail: ${email}\nMember Number: ${memberNumber}`);
    };

    const handleChangePassword = () => {
    if (newPassword !== confirmPassword) {
        alert("Password baru dan konfirmasi password tidak cocok!");
        return;
    }
        alert("Password berhasil diubah!");
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
