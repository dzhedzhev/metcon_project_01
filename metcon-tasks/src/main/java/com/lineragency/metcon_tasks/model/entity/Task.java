package com.lineragency.metcon_tasks.model.entity;

import com.lineragency.metcon_tasks.model.enums.ContainerIsoType;
import com.lineragency.metcon_tasks.model.enums.RequestEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestEnum type;
    @Column(nullable = false)
    private String company;
    @Column(name = "container_number", nullable = false)
    private String containerNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "container_type", nullable = false)
    private ContainerIsoType containerType;
    private String truck;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "request_id", nullable = false)
    private long requestId;

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestEnum getType() {
        return type;
    }

    public void setType(RequestEnum type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime time) {
        this.dateTime = time;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long request) {
        this.requestId = request;
    }
}
