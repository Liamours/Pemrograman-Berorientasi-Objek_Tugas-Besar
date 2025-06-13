package com.example.rest_service.dto;

import java.util.List;

public class CheckoutResponse {
    private boolean status;
    private Double totalHarga;
    private List<CheckoutItemResult> data;

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public Double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(Double totalHarga) { this.totalHarga = totalHarga; }
    public List<CheckoutItemResult> getData() { return data; }
    public void setData(List<CheckoutItemResult> data) { this.data = data; }

    public static class CheckoutItemResult {
        private Integer orderId;
        private boolean success;
        private String message;
        private String nama_barang;
        private int jumlah_barang;
        private double harga;

        public String getNama_barang() {
            return nama_barang;
        }

        public void setNama_barang(String nama_barang) {
            this.nama_barang = nama_barang;
        }

        public int getJumlah_barang() {
            return jumlah_barang;
        }

        public void setJumlah_barang(int jumlah_barang) {
            this.jumlah_barang = jumlah_barang;
        }

        public double getHarga() {
            return harga;
        }

        public void setHarga(double harga) {
            this.harga = harga;
        }

        public Integer getOrderId() { return orderId; }
        public void setOrderId(Integer orderId) { this.orderId = orderId; }
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}