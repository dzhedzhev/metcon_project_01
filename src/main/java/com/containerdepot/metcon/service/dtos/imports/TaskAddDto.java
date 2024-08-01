package com.containerdepot.metcon.service.dtos.imports;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.enums.RequestEnum;

import java.time.LocalDateTime;

public class TaskAddDto {
    private long id;
    private String type;
    private String company;
    private String containerNumber;
    private String containerType;
    private String truck;

    private LocalDateTime dateTime;
    private long requestId;

    public TaskAddDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
