package com.example.demo.repository;

import com.example.demo.model.BomLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;  
import java.util.List;

public interface BomLinkRepository extends JpaRepository<BomLink, Long> {
    
     List<BomLink> findByParentItemId(Long parentItemId);

    @Transactional
    void deleteByParentItemId(Long parentItemId);

    @Transactional
    void deleteByChildItemId(Long childItemId);
}
