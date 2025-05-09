import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './LoginStyle.css';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const handleLogin = async () => {
    if (!email || !password) {
      setMessage("Email dan password harus diisi");
      return;
    }
  
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password, rememberMe }),
      });
  
      const data = await response.json();
  
      if (response.ok) {
        setMessage(`Login berhasil. Selamat datang, ${data.user?.name || 'User'}`);
        localStorage.setItem('token', data.token); 
      } else {
        setMessage(data.error || "Login gagal");
      }
    } catch (err) {
      setMessage("Terjadi kesalahan saat menghubungi server");
      console.error("Login error:", err);
    }
  };

  return (
    <div className="login-page">
      <header className="login-header"></header>
      <div className="login-header-image">
        <img src="/images/grownncheer_logo.png" alt="Logo" />
      </div>
      <div className="login-container">
        <h2>Login</h2>
        <input
          type="email"
          placeholder="Email"
          className="login-input-field"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <div className="login-password-wrapper">
          <input
            type={showPassword ? 'text' : 'password'}
            placeholder="Password"
            className="login-input-field"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button
            type="button"
            className="login-toggle-password"
            onClick={() => setShowPassword(!showPassword)}
          >
            {showPassword ? '🙈' : '👁️'}
          </button>
        </div>

        <div className="login-remember-me">
          <input
            type="checkbox"
            id="remember"
            checked={rememberMe}
            onChange={() => setRememberMe(!rememberMe)}
          />
          <label htmlFor="remember">Ingat saya</label>
        </div>

        <button className="login-button" onClick={handleLogin}>Login</button>
        <p className="login-message">{message}</p>
        <p className="login-register-text">
          Belum punya akun? <Link className="login-register-link" to="/register">Daftar disini</Link>
        </p>
      </div>
    </div>
  );
}

export default LoginPage;
