package com.containerdepot.metcon.service.dtos.imports;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CompanyEditDto {
    private long id;
    @NotBlank(message = "Company name must not be empty!")
    @Size(min = 2, max = 50, message = "Company name length must be between 2 and 50 symbols!")
    private String nameEn;
    @NotBlank(message = "Company name must not be empty!")
    @Size(min = 2, max = 50, message = "Company name length must be between 2 and 50 symbols!")
    private String nameBg;
    @NotBlank(message = "VAT number must not be empty!")
    @Pattern(regexp = "(^BG\\d{9,10}$)", message = "Incorrect VAT number format!")
    private String vatNumber;
    @NotBlank(message = "City name must not be empty!")
    @Size(min = 2, max = 50, message = "City name length must be between 2 and 50 symbols!")
    private String city;
    @NotBlank(message = "Address must not be empty!")
    @Size(min = 2, max = 250, message = "Address length must be between 2 and 50 symbols!")
    private String address;
    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Invalid e-mail format!")
    private String email;
    @NotBlank(message = "Phone number must not be empty!")
    @Size(min = 8, message = "Phone number length must be at least 8 symbols!")
    private String phoneNumber;/*TODO add regexp validation?*/

    public CompanyEditDto() {
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
