package com.example.rest_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gudang")
public class Gudang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gudangId;

    @OneToOne
    @JoinColumn(name = "barang_id", nullable = false)
    private Barang barang;

    @Column(nullable = false)
    private Integer stokBarang;

    public Gudang() {}

    public Integer getGudangId() { return gudangId; }
    public void setGudangId(Integer gudangId) { this.gudangId = gudangId; }

    public Barang getBarang() { return barang; }
    public void setBarang(Barang barang) { this.barang = barang; }

    public Integer getStokBarang() { return stokBarang; }
    public void setStokBarang(Integer stokBarang) { this.stokBarang = stokBarang; }
}
