# 🌱 Grow and Cheer - Tugas Besar PBO 👨‍💻👩‍💻

> **Mata Kuliah:** Pemrograman Berorientasi Objek  
> **Nama Aplikasi:** Grow and Cheer  
> **Kelompok:** 06

## 👥 Anggota Kelompok
- Fathan Arya Maulana (103012300083)  
- Samuel Yohanes Michael Kaunang (103012300247)  
- Dzaky Alfaris (103012300391)  
- M Rifqi Dzaky Azhad (103012330009)  
- Ezra Mangasi Andika Sibuea (103012330117)  
- Rafiq Labib (103012330353)

---

## ⚙️ Teknologi yang Digunakan

- 🧠 **Backend:** Spring Boot `☕`
- 💻 **Frontend:** ReactJS `⚛️`
- 🗃️ **Database:** MySQL via XAMPP `🐬`
- 📂 **Database File:** `growandcheer_final.sql`

---

## 🔐 Auth API

### 🔑 `POST /api/auth/login`
```json
{
  "email": "test200@mail.com",
  "password": "123456"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "role": "Client",
    "address": "aalalallaalal",
    "isMember": false,
    "userId": 20,
    "email": "test200@mail.com",
    "token": "..."
  }
}
```

### 🧪 `POST /api/auth/login (Dummy Client)`
Simulasi login dummy dengan format request dan response yang sama seperti login biasa.

### 📝 `POST /api/auth/register`
```json
{
  "email": "test00@mail.com",
  "password": "fesfesfse",
  "name": 6
}
```

**Response:**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": null
}
```

### 🚪 `POST /api/auth/logout`
**Response:**
```json
{
  "success": true,
  "message": "Logout successful",
  "data": null
}
```

---

## 👤 User API

### 👮‍♂️ `GET /api/user/profile/admin`
```json
{
  "success": true,
  "message": "Admin profile retrieved successfully",
  "data": {
    "createdAt": "...",
    "role": "Admin",
    "name": "atmin",
    "id": 1,
    "email": "admin@mail.com",
    "updatedAt": "..."
  }
}
```

### 👤 `GET /api/user/profile/client`
```json
{
  "success": true,
  "message": "Client profile retrieved successfully",
  "data": {
    "createdAt": "...",
    "role": "Client",
    "address": "aalalallaalal",
    "name": "adawdaw",
    "id": 20,
    "isMember": false,
    "email": "test200@mail.com",
    "updatedAt": "..."
  }
}
```

### ✏️ `PUT /api/user/profile/update`
```json
{
  "name": "batanbilek"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Profile updated successfully",
  "data": null
}
```

### 🔐 `PUT /api/user/password/change`
```json
{
  "currentPassword": "123456",
  "newPassword": "passwordBaru",
  "confirmPassword": "passwordBaru"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Password changed successfully",
  "data": null
}
```

### 🗑️ `DELETE /api/user/delete`
```json
{
  "password": "123456"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Account deleted successfully",
  "data": null
}
```

### 🌟 `PUT /api/user/member`
**Response:**
```json
{
  "success": true,
  "message": "Upgraded to member successfully",
  "data": null
}
```

### 📋 `GET /api/user/alluser`
Mengambil semua user dalam sistem (tidak ada contoh response).

---

## 📦 Barang API

### ➕ `POST /api/barang/new`
```json
{
  "nama_barang": "Kopi Arabika",
  "deskripsi_barang": "Kopi Arabika premium",
  "harga": 75000,
  "tipe_barang": "Makanan",
  "stok_barang": 1,
  "image_url": "/images/kopi_arabika.jpg"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Barang baru berhasil ditambahkan",
  "data": {
    "harga": 75000.0,
    "image_url": "/images/kopi_arabika.jpg",
    "nama_barang": "Kopi Arabika",
    "deskripsi_barang": "Kopi Arabika premium",
    "barang_id": 15,
    "stock": 1,
    "tipe_barang_id": "Makanan"
  }
}
```

### 📄 `POST /api/barang`
```json
{
  "nama_barang": "",
  "tipe_barang": ""
}
```
Digunakan untuk mengambil semua barang (tanpa filter). Response berupa array `data`.

### 🔍 `POST /api/barang/detail`
```json
{
  "barang_id": 8
}
```

**Response:**
```json
{
  "success": true,
  "message": "Data barang berhasil diambil",
  "data": {
    "harga": 192000.0,
    "image_url": "/images/box_sampo.png",
    "nama_barang": "Sampo",
    "deskripsi_barang": "...",
    "barang_id": 8,
    "stock": 55,
    "tipe_barang_id": "Hygine"
  }
}
```

### ❌ `DELETE /api/barang/delete`
```json
{
  "barang_id": 8
}
```

**Response:**
```json
{
  "success": true,
  "message": "Barang telah dihapus",
  "data": {
    "barangId": 8
  }
}
```

### 🛠️ `PUT /api/barang/update/detail`
```json
{
  "barang_id": "9",
  "nama_barang": "Nabati Suup",
  "deskripsi_barang": "Nabati Suup adalah snack lezat...",
  "harga": "2",
  "tipe_barang": "Makanan",
  "image_url": "/images/kopi_arabika.jpg",
  "stok_barang": "500"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Data barang berhasil diubah",
  "data": {
    "barangId": 9,
    "namaBarang": "Nabati Suup"
  }
}
```

---

## 📃 Order, 🛒 Keranjang, ✅ Checkout

### ➕ `POST /api/order/add`
```json
{
  "barangId": 9,
  "jumlahBarang": 10,
  "alamatTujuan": "Jl. Telekomunikasi No. 1, Bandung"
}
```

**Response:**
```json
{
  "orderId": 3,
  "barangId": 8,
  "jumlahBarang": 1,
  "hargaPerUnit": 192000.0,
  "tanggalOrder": "2025-06-16T11:31:59",
  "alamatTujuan": "rumah",
  "statusOrder": "Pending"
}
```

### 🔎 `GET /api/order/${orderId}`
**Response:**
```json
{
  "orderId": 3,
  "barangId": 8,
  "jumlahBarang": 1,
  "hargaPerUnit": 192000.0,
  "tanggalOrder": "2025-06-16T11:31:59",
  "alamatTujuan": "rumah",
  "statusOrder": "Pending"
}
```

### 🧺 `GET /api/cart`
**Response:**
```json
{
  "orders": [
    {
      "orderId": 6,
      "barangId": 8,
      "jumlahBarang": 2,
      "hargaPerUnit": 192000.0,
      "tanggalOrder": "2025-06-16T15:15:23",
      "alamatTujuan": "rumah",
      "statusOrder": "Pending"
    }
  ]
}
```

### 🔁 `PUT /api/cart/orders/${orderId}`
```json
{
  "jumlahBarang": 10
}
```

**Response:**
```json
{
  "orderId": 6,
  "barangId": 8,
  "jumlahBarang": 1,
  "hargaPerUnit": 192000.0,
  "tanggalOrder": "2025-06-16T15:15:23",
  "alamatTujuan": "rumah",
  "statusOrder": "Pending"
}
```

### ✅ `POST /api/checkout`
```json
[6]
```

**Response:**
```json
{
  "message": "Checkout berhasil",
  "checkedOutOrders": [
    {
      "orderId": 6,
      "barangId": 8,
      "jumlahBarang": 1,
      "hargaPerUnit": 192000.0,
      "tanggalOrder": "2025-06-16T15:15:23",
      "alamatTujuan": "rumah",
      "statusOrder": "Done"
    }
  ]
}
```

### 🗑️ `DELETE /api/cart/orders/{orderId}`
**Response:**
```json
{
  "mesaage": "Order Berhasil dihapus"
}
```

---