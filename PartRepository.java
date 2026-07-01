package com.example.demo.repository;

import com.example.demo.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    // This fetches all parts and automatically sorts them by ID
    List<Part> findAllByOrderByIdAsc();
}