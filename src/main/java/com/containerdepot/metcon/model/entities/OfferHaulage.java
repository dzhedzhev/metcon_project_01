package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.CargoTypeEnum;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "offers")
public class OfferHaulage extends BaseEntity {
    @OneToOne(optional = false)
    private Company company;
    @Enumerated(EnumType.STRING)
    @Column(name = "container_type", nullable = false)
    private ContainerIsoType containerType;
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type", nullable = false)
    private CargoTypeEnum cargoType;
    @OneToOne(optional = false)
    private Location pickUp;
    @OneToOne(optional = false)
    private Location stuffing;
    @Column(nullable = false)
    private BigDecimal price;
    @OneToMany(mappedBy = "offer")
    private List<BookingHaulage> bookings;

    public OfferHaulage() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ContainerIsoType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerIsoType containerType) {
        this.containerType = containerType;
    }

    public CargoTypeEnum getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoTypeEnum cargoType) {
        this.cargoType = cargoType;
    }

    public Location getPickUp() {
        return pickUp;
    }

    public void setPickUp(Location pickUp) {
        this.pickUp = pickUp;
    }

    public Location getStuffing() {
        return stuffing;
    }

    public void setStuffing(Location stuffing) {
        this.stuffing = stuffing;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
