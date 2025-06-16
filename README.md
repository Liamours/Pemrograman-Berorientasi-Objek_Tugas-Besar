# Tugas besar mata kuliah Pemrograman Berorientasi Objek

Nama Aplikasi: Grow and Cheer

Kelompok: 06\
Anggota:

- Fathan Arya Maulana (103012300083)
- Samuel Yohanes Michael Kaunang (103012300247)
- Dzaky Alfaris (103012300391)
- M Rifqi Dzaky Azhad (103012330009)
- Ezra Mangasi Andika Sibuea (103012330117)
- Rafiq Labib (103012330353)

---

## 🔐 Auth

### `POST /api/auth/login`

Login user (Admin/Client)

**Request:**

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

### `POST /api/auth/login (Dummy Client)`

Simulasi login dummy (sama formatnya dengan endpoint login biasa).

---

### `POST /api/auth/register`

Register akun baru

**Request:**

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

---

### `POST /api/auth/logout`

Logout user

**Response:**

```json
{
  "success": true,
  "message": "Logout successful",
  "data": null
}
```

---

## 👤 User

### `GET /api/user/profile/admin`

Ambil profil admin

**Response:**

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

---

### `GET /api/user/profile/client`

Ambil profil client

**Response:**

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

---

### `PUT /api/user/profile/update`

Update nama

**Request:**

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

---

### `PUT /api/user/password/change`

Ganti password

**Request:**

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

---

### `DELETE /api/user/delete`

Hapus akun user

**Request:**

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

---

### `PUT /api/user/member`

Upgrade menjadi member

**Response:**

```json
{
  "success": true,
  "message": "Upgraded to member successfully",
  "data": null
}
```

---

### `GET /api/user/alluser`

Ambil semua user

---

## 📦 Barang

### `POST /api/barang/new`

Tambah barang baru

**Request:**

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

---

### `POST /api/barang`

List semua barang

**Request:**

```json
{
  "nama_barang": "",
  "tipe_barang": ""
}
```

**Response:**

```json
{
  "success": true,
  "message": "List barang berhasil diambil",
  "data": [
    ...
  ]
}
```

---

### `POST /api/barang/detail`

Ambil detail barang

**Request:**

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

---

### `DELETE /api/barang/delete`

Hapus barang

**Request:**

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

---

### `PUT /api/barang/update/detail`

Update barang

**Request:**

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
    "namaBarang": "Nabati Suup",
    ...
  }
}
```

---