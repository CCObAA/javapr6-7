package com.example.demo.repo;

import com.example.demo.models.Passport;
import org.springframework.data.repository.CrudRepository;

public interface PassportRepository extends CrudRepository<Passport, Long> {
    Passport findByNumber(String number);
}
