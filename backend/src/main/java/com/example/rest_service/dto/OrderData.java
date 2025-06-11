package com.example.rest_service.dto;

public class OrderData {
    private Integer orderId;
    private Double totalHarga;
    private String statusOrder;
    private String namaBarang;
    private Integer jumlahBarang;
    private Integer stock;

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(Double totalHarga) { this.totalHarga = totalHarga; }
    public String getStatusOrder() { return statusOrder; }
    public void setStatusOrder(String statusOrder) { this.statusOrder = statusOrder; }
    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public Integer getJumlahBarang() { return jumlahBarang; }
    public void setJumlahBarang(Integer jumlahBarang) { this.jumlahBarang = jumlahBarang; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}