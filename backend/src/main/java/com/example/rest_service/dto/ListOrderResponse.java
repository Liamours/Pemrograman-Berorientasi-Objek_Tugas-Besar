package com.example.rest_service.dto;

import java.util.List;

public class ListOrderResponse {
    private boolean status;
    private String message;
    private boolean isMember;
    private List<OrderData> data;

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }
    public List<OrderData> getData() { return data; }
    public void setData(List<OrderData> data) { this.data = data; }
}