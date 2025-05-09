import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './RegistrationStyle.css';

function RegistrationPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [acceptTerms, setAcceptTerms] = useState(false);
  const [message, setMessage] = useState('');
  const [passwordMatch, setPasswordMatch] = useState(null);

  const handleRegistration = async () => {
    if (!email || !password || !confirmPassword || !acceptTerms) {
      setMessage('Harap isi semua kolom dan terima semua kondisi.');
      return;
    }

    if (password !== confirmPassword) {
      setMessage('Password dan konfirmasi password tidak cocok.');
      return;
    }

    try {
      const response = await fetch('http://localhost:3306/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      const data = await response.json();

      if (data.message === "Registrasi sukses") {
        setMessage('Registrasi berhasil. Silakan login.');
      } else if (data.error) {
        setMessage(data.error);
      }
    } catch (err) {
      setMessage('Terjadi kesalahan saat menghubungi server.');
    }
  };

  return (
    <div className="registration-page">
      <header className="registration-header"></header>
      <div className="registration-header-image">
        <img src="/images/grownncheer_logo.png" alt="Logo" />
      </div>
      <div className="registration-container">
        <h2>Registrasi</h2>
        <input
          type="email"
          placeholder="Email"
          className="registration-input-field"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          className="registration-input-field"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <input
          type="password"
          placeholder="Konfirmasi Password"
          className="registration-input-field"
          value={confirmPassword}
          onChange={(e) => {
            setConfirmPassword(e.target.value);
            if (e.target.value === password) {
              setPasswordMatch(true);
            } else if (e.target.value !== '') {
              setPasswordMatch(false);
            } else {
              setPasswordMatch(null);
            }
          }}
          style={{
            borderColor: passwordMatch === true ? 'green' : passwordMatch === false ? 'red' : 'initial',
          }}
        />

        <div className="registration-terms-and-conditions">
          <input
            type="checkbox"
            id="accept-terms"
            checked={acceptTerms}
            onChange={() => setAcceptTerms(!acceptTerms)}
          />
          <label htmlFor="accept-terms">Terima Semua Kondisi</label>
        </div>

        <button className="registration-button" onClick={handleRegistration}>Buat Akun</button>
        <p className="registration-message">{message}</p>
        <p className="registration-login-text">
          Sudah punya akun? <Link className="registration-login-link" to="/login">Login disini</Link>
        </p>
      </div>
    </div>
  );
}

export default RegistrationPage;
