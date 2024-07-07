package com.containerdepot.metcon.model.entities;

import com.containerdepot.metcon.model.enums.RequestEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requests")
public class Request extends BaseEntity{
    @ManyToOne(optional = false)
    private Company company;
    @OneToMany
    private List<Container> containers;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestEnum type;
    @OneToMany(mappedBy = "request")
    private List<Task> tasks;


    public Request() {
        this.containers = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public RequestEnum getType() {
        return type;
    }

    public void setType(RequestEnum type) {
        this.type = type;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
