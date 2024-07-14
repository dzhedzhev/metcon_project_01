package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request extends BaseEntity{
    @ManyToOne(optional = false)
    private Company company;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestEnum type;
    @Column(name = "container_number", nullable = false)
    private String containerNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "container_type", nullable = false)
    private ContainerIsoType containerType;
    private String truck;


    public Request() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public RequestEnum getType() {
        return type;
    }

    public void setType(RequestEnum type) {
        this.type = type;
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
}
