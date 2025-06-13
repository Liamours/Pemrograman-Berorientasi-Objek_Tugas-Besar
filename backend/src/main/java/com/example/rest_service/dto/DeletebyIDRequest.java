package com.example.rest_service.dto;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeletebyIDRequest {
    @NotNull(message = "ID Barang tidak boleh kosong.")
    @JsonProperty("barang_id")  // This tells Jackson to map "barang_id" to "barangId"
    private Integer barangId;

    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }
}
