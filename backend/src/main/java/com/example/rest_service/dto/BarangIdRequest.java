package com.example.rest_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarangIdRequest {
    @JsonProperty("barang_id")
    private Integer barangId;

    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }
}
