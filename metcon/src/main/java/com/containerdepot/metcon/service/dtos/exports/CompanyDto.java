package com.containerdepot.metcon.service.dtos.exports;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CompanyDto {
    private long id;
    private String nameEn;
    private String nameBg;
    private String vatNumber;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;

    public CompanyDto() {
    }

    public CompanyDto(long id, String nameEn, String nameBg, String vatNumber, String city, String address, String email, String phoneNumber) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameBg = nameBg;
        this.vatNumber = vatNumber;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
