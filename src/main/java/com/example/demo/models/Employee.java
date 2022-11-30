package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float height;
    private String  surname;
    private boolean lovecookie;
    private int favnumber;
    private double weight;
    private int views;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Post post;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="passport_id")
    private Passport passport;

    @ManyToMany
    @JoinTable (name="worker_supervisor",
            joinColumns=@JoinColumn (name="worker_id"),
            inverseJoinColumns=@JoinColumn(name="supervisor_id"))
    private List<Supervisor> supervisors;

    public Employee(float height, String surname, boolean lovecookie, int favnumber, double weight, Post post, Passport passport) {
        this.surname = surname;
        this.height = height;
        this.lovecookie = lovecookie;
        this.favnumber = favnumber;
        this.weight = weight;
        this.post = post;
        this.passport = passport;
    }

    public Employee() {

    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean getLovecookie() {
        return lovecookie;
    }

    public void setLovecookie(boolean lovecookie) {
        this.lovecookie = lovecookie;
    }

    public int getFavnumber() {
        return favnumber;
    }

    public void setFavnumber(int favnumber) {
        this.favnumber = favnumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
