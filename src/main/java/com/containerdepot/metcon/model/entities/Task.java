package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestEnum type;
    @ManyToOne(optional = false)
    private Company company;
    @Column(name = "container_number", nullable = false)
    private String containerNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "container_type", nullable = false)
    private ContainerIsoType containerType;
    private String truck;
    private LocalDateTime time;
    @OneToOne(optional = false)
    private Request request;

    public Task() {
    }

    public RequestEnum getType() {
        return type;
    }

    public void setType(RequestEnum type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public ContainerIsoType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerIsoType containerType) {
        this.containerType = containerType;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
