package com.containerdepot.metcon.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class BookingHaulage extends BaseEntity{
    @ManyToOne(optional = false)
    private OfferHaulage offer;
    @OneToOne(optional = false)
    private Location pickUpLocation;
    @Column(name = "pick_up_address", nullable = false)
    private String pickUpAddress;
    @OneToOne(optional = false)
    private Location stuffingLocation;
    @Column(name = "stuffing_address", nullable = false)
    private String stuffingAddress;
    @OneToOne(optional = false)
    private Container container;
    @OneToOne(optional = false)
    private Truck truck;

    public BookingHaulage() {
    }

    public OfferHaulage getOffer() {
        return offer;
    }

    public void setOffer(OfferHaulage offer) {
        this.offer = offer;
    }

    public Location getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Location pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public Location getStuffingLocation() {
        return stuffingLocation;
    }

    public void setStuffingLocation(Location stuffingLocation) {
        this.stuffingLocation = stuffingLocation;
    }

    public String getStuffingAddress() {
        return stuffingAddress;
    }

    public void setStuffingAddress(String stuffingAddress) {
        this.stuffingAddress = stuffingAddress;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
