package com.example.demo.repo;

import com.example.demo.models.Supervisor;
import org.springframework.data.repository.CrudRepository;

public interface SupervisorRepository extends CrudRepository<Supervisor, Long> {
    Supervisor findByName(String name);
}
