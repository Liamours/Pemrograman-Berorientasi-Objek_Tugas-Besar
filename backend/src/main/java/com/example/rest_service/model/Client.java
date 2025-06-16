package com.example.rest_service.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private ClientDetail clientDetail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Keranjang> keranjangs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Client() {
        super();
    }

    public Client(String namaUser, String email, String password) {
        super(namaUser, email, password);
        this.setPeran(Role.Client);
    }

    public Client(String namaUser, String email, String password, boolean isMember, String alamat) {
        super(namaUser, email, password);
        this.setPeran(Role.Client);
        this.clientDetail = new ClientDetail(isMember, alamat);
    }

    public ClientDetail getClientDetails() { return clientDetail; }
    public void setClientDetails(ClientDetail clientDetails) { this.clientDetail = clientDetails; }

    public List<Keranjang> getKeranjangs() { return keranjangs; }
    public void setKeranjangs(List<Keranjang> keranjangs) { this.keranjangs = keranjangs; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    public boolean isMember() {
        return clientDetail != null ? clientDetail.isIsmember() : false;
    }

    public void setMember(boolean member) {
        if (clientDetail == null) {
            clientDetail = new ClientDetail();
        }
        clientDetail.setIsmember(member);
    }

    public String getAlamat() {
        return clientDetail != null ? clientDetail.getAlamat() : null;
    }

    public void setAlamat(String alamat) {
        if (clientDetail == null) {
            clientDetail = new ClientDetail();
        }
        clientDetail.setAlamat(alamat);
    }
}