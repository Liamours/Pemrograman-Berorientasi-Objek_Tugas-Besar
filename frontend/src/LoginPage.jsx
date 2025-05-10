import React, { useState } from 'react';
import { Link , useNavigate} from 'react-router-dom';
import './LoginStyle.css';
import api from './api';


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
      const response = await api.post('/auth/login', {
        email,
        password,
        rememberMe
      });

      const data = response.data;

      if (data.status === 'Login Sukses') {
        setMessage(`Login berhasil. Selamat datang, ${data.data?.name || 'User'}`);
        localStorage.setItem('token', data.token || '');
        navigate('/Profile') // token opsional, tergantung backend
      } else {
        // handle unexpected status
        setMessage(data.message || "Login gagal");
      }

    } catch (err) {
      // axios error format
      if (err.response) {
        const { status, data } = err.response;
        if (data.status === 'wrong_credentials') {
          setMessage("Email atau password salah");
        } else if (data.status === 'invalid_input') {
          setMessage(data.message || "Input tidak valid");
        } else {
          setMessage(data.message || "Terjadi kesalahan di server");
        }
      } else {
        setMessage("Gagal menghubungi server");
      }

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
