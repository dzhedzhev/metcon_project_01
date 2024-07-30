package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.ContainerIsoType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "containers")
public class Container extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String number;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerIsoType type;
    @Column(name = "is_damaged", nullable = false)
    private boolean isDamaged;
    @ManyToOne
    private Company owner;
    @Column(nullable = false)
    private LocalDateTime received;
    @Column
    private LocalDateTime released;
    @Column(name = "received_by_truck", nullable = false)
    private String receivedByTruck;
    @Column(name = "released_to_truck")
    private String releasedToTruck;

    public Container() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContainerIsoType getType() {
        return type;
    }

    public void setType(ContainerIsoType type) {
        this.type = type;
    }

    public boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company owner) {
        this.owner = owner;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public void setReceived(LocalDateTime received) {
        this.received = received;
    }

    public LocalDateTime getReleased() {
        return released;
    }

    public void setReleased(LocalDateTime released) {
        this.released = released;
    }

    public String getReceivedByTruck() {
        return receivedByTruck;
    }

    public void setReceivedByTruck(String receivedByTruck) {
        this.receivedByTruck = receivedByTruck;
    }

    public String getReleasedToTruck() {
        return releasedToTruck;
    }

    public void setReleasedToTruck(String releasedToTruck) {
        this.releasedToTruck = releasedToTruck;
    }
}
