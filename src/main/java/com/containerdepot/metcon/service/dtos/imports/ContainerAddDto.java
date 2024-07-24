package com.containerdepot.metcon.service.dtos.imports;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ContainerAddDto {
    @NotBlank(message = "Container number must not be empty!")
    @Pattern(regexp = "([a-zA-Z]{3})([UJZujz])(\\d{6})(\\d)", message = "Container number is not in correct format!")
    private String number;
    @NotBlank(message = "Container type must be selected!")
    private String type;
    private boolean isDamaged;
    @NotBlank(message = "Company must be selected!")
    private String owner;
    @PastOrPresent(message = "Received date/time must be in the past!")
    @NotNull(message = "Received date/time must be filled!")
    private LocalDateTime received;
    @NotBlank(message = "Truck number must not be empty!")
    private String receivedByTruck;
    @FutureOrPresent
    private LocalDateTime released;
    private String releasedToTruck;

    public ContainerAddDto() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public void setReceived(LocalDateTime received) {
        this.received = received;
    }

    public String getReceivedByTruck() {
        return receivedByTruck;
    }

    public void setReceivedByTruck(String receivedByTruck) {
        this.receivedByTruck = receivedByTruck;
    }

    public LocalDateTime getReleased() {
        return released;
    }

    public void setReleased(LocalDateTime released) {
        this.released = released;
    }

    public String getReleasedToTruck() {
        return releasedToTruck;
    }

    public void setReleasedToTruck(String releasedToTruck) {
        this.releasedToTruck = releasedToTruck;
    }
}
