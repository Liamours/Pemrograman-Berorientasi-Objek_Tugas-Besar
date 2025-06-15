import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './LoginStyle.css';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password}),
      });
  
      const data = await response.json();
  
      if (data.success) {
        localStorage.setItem("token", data.data.token);
        if (data.data.role == 'Admin') {
          navigate('/admin/dashboard');
          return;
        } else {
          navigate('/profile');
          return
        }
      } else {
        setMessage(data.message);
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
            {showPassword ? '🚫' : '👁️'}
          </button>
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
