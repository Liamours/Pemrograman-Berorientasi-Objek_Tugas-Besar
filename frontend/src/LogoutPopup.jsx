import React, { useState, useEffect } from 'react';
import './LogoutStyle.css';
import { useNavigate } from 'react-router-dom';

function LogoutPopup() {
    const openNav = () => {
    document.getElementById("myNav").style.width = "100%";
  };

  const closeNav = () => {
    document.getElementById("myNav").style.width = "0%";
  };
  return (
    <div>
        <div id="myNav" class="overlay">
            <a style={{ cursor: "pointer" }} class="closebtn" onclick={closeNav}>&times;</a>
            <div class="overlay-content">
                <a href="#">About</a>
                <a href="#">Services</a>
                <a href="#">Clients</a>
                <a href="#">Contact</a>
            </div>
        </div>

        <h2>Fullscreen Overlay Nav Example</h2>
        <p>Click on the element below to open the fullscreen overlay navigation menu.</p>
        <p>In this example, the navigation menu will slide in, from left to right:</p>
        <span style={{ fontSize: '30px', cursor: 'pointer' }} onClick={openNav}>
      &#9776; open
    </span>
    </div>
  );
}

export default LogoutPopup;