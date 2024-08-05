package com.containerdepot.metcon.service.dtos.imports;

import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.service.validation.ExistingContainer;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ContainerAddDto {
    private long id;
    @NotBlank(message = "Container number must not be empty!")
    @Pattern(regexp = "([a-zA-Z]{3})([UJZujz])(\\d{6})(\\d)", message = "Container number is not in correct format!")
    @ExistingContainer
    private String number;
    @NotNull(message = "Container type must be selected!")
    private ContainerIsoType type;
    private boolean damaged;
    @NotBlank(message = "Company must be selected!")
    private String owner;
    @PastOrPresent(message = "Received date/time must be in the past!")
    @NotNull(message = "Received date/time must be filled!")
    private LocalDateTime received;
    @NotBlank(message = "Truck number must not be empty!")
    private String receivedByTruck;
    @PastOrPresent
    private LocalDateTime released;/*TODO custom validator checks for release after receiving*/
    private String releasedToTruck;

    public ContainerAddDto() {
    }
    public ContainerAddDto(String number, ContainerIsoType type, boolean damaged, String owner, LocalDateTime received, String receivedByTruck, LocalDateTime released, String releasedToTruck) {
        this.number = number;
        this.type = type;
        this.damaged = damaged;
        this.owner = owner;
        this.received = received;
        this.receivedByTruck = receivedByTruck;
        this.released = released;
        this.releasedToTruck = releasedToTruck;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
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
