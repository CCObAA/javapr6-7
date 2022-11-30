package com.example.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @ManyToMany
    @JoinTable(name="employee_supervisor",
            joinColumns=@JoinColumn(name="supervisor_id"),
            inverseJoinColumns=@JoinColumn(name="employee_id"))
    private List<Employee> workers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Employee> workers) {
        this.workers = workers;
    }

}
