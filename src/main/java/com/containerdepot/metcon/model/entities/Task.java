package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.RequestEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{
    @ManyToOne(optional = false)
    private Request request;
    @OneToOne(optional = false)
    private Company company;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestEnum type;
    @OneToOne(optional = false)
    private Container container;
    @Column
    private String truck;
    @Column
    private LocalDateTime time;

    public Task() {
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
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
}
