package com.example.demo.repository;

import com.example.demo.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
     List<Part> findAllByOrderByIdAsc();
}
