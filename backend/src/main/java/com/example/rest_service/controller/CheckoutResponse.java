package com.example.backend.dto;

import java.util.List;

public class CheckoutResponse {
    private List<ItemDetail> details;
    private double totalHarga;

    public List<ItemDetail> getDetails() { return details; }
    public void setDetails(List<ItemDetail> details) { this.details = details; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public static class ItemDetail {
        private String nama;
        private int jumlah;
        private double hargaSatuan;
        private double subtotal;

        public String getNama() { return nama; }
        public void setNama(String nama) { this.nama = nama; }

        public int getJumlah() { return jumlah; }
        public void setJumlah(int jumlah) { this.jumlah = jumlah; }

        public double getHargaSatuan() { return hargaSatuan; }
        public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }

        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    }
}
