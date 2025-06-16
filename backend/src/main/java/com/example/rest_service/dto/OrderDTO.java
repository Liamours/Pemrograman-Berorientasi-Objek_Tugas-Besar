package com.example.rest_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Integer orderId;
    private Integer barangId;
    private Integer jumlahBarang;
    private Double hargaPerUnit;
    private LocalDateTime tanggalOrder;
    private String alamatTujuan;
    private String statusOrder;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public Double getHargaPerUnit() {
        return hargaPerUnit;
    }

    public void setHargaPerUnit(Double hargaPerUnit) {
        this.hargaPerUnit = hargaPerUnit;
    }

    public LocalDateTime getTanggalOrder() {
        return tanggalOrder;
    }

    public void setTanggalOrder(LocalDateTime tanggalOrder) {
        this.tanggalOrder = tanggalOrder;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
}
