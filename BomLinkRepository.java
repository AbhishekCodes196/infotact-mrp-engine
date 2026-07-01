package com.example.demo.repository;

import com.example.demo.model.BomLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BomLinkRepository extends JpaRepository<BomLink, Long> {
    // This will help us find all children for a specific parent part
    List<BomLink> findByParentItemId(Long parentItemId);
}