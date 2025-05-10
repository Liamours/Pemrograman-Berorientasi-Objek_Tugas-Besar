import React from 'react';
import { Link } from 'react-router-dom';
import './ProfilePage.css';
import api from './api';

const ProfilePage = ({ name, email, loggedIn }) => {
  return (
    <div className="profile-page">
      <header className="profile-header"></header>
      <div className="profile-header-image">
        <img src="/images/grownncheer_logo.png" alt="Logo" />
      </div>
      <div className="profile-container">
        <h2>Profile</h2>

        <div className="profile-info">
          <p><strong>Nama:</strong> {name}</p>
          <p><strong>Email:</strong> {email}</p>
          <p><strong>Status:</strong> {loggedIn ? 'Logged In' : 'Logged Out'}</p>
        </div>

        <Link to="/edit-profile" className="edit-profile-link">Edit Profile</Link>

        <p className="profile-message">
          <Link to="/login" className="profile-logout-link">Logout</Link>
        </p>
      </div>
    </div>
  );
};

export default ProfilePage;
