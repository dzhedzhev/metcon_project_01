package com.containerdepot.metcon.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "drivers")
public class Driver extends BaseEntity{
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "phone_number", nullable = false)
    private String PhoneNumber;
    @OneToOne(optional = false)
    private Truck truck;

    public Driver() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
