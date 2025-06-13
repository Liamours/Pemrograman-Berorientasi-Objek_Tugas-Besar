package com.example.rest_service.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detail_order")
public class DetailOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailOrderId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    private Barang barang;

    @Column(nullable = false)
    private Integer jumlahBarang;

    @Column(nullable = false)
    private BigDecimal hargaPerUnit;

    public DetailOrder() {}

    public Integer getDetailOrderId() { return detailOrderId; }
    public void setDetailOrderId(Integer detailOrderId) { this.detailOrderId = detailOrderId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Barang getBarang() { return barang; }
    public void setBarang(Barang barang) { this.barang = barang; }

    public Integer getJumlahBarang() { return jumlahBarang; }
    public void setJumlahBarang(Integer jumlahBarang) { this.jumlahBarang = jumlahBarang; }

    public BigDecimal getHargaPerUnit() { return hargaPerUnit; }
    public void setHargaPerUnit(BigDecimal hargaPerUnit) { this.hargaPerUnit = hargaPerUnit; }
}
