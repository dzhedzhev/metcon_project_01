package com.containerdepot.metcon.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    @Column(name = "name_en", nullable = false)
    private String nameEn;
    @Column(name = "name_bg", nullable = false)
    private String nameBg;
    @Column(name = "vat_number", nullable = false)
    private int vatNumber;
    @OneToOne(optional = false)
    private Location city;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @OneToMany(mappedBy = "owner")
    private List<Container> containers;

    public Company() {
        this.containers = new ArrayList<>();
    }

    public Company(String nameEn, String nameBg,
                   int vatNumber,
                   String address, String email,
                   String phoneNumber) {
        this.nameEn = nameEn;
        this.nameBg = nameBg;
        this.vatNumber = vatNumber;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.containers = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getCity() {
        return city;
    }

    public void setCity(Location address) {
        this.city = address;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameBg() {
        return nameBg;
    }

    public void setNameBg(String nameBg) {
        this.nameBg = nameBg;
    }

    public int getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(int vatNumber) {
        this.vatNumber = vatNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
