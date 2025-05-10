// src/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // otomatis nyambung ke proxy yang udah kamu set
  withCredentials: true, // penting buat kirim cookie kalau pakai session/JWT di cookie
  headers: {
    'Content-Type': 'application/json'
  }
});

export default api;
