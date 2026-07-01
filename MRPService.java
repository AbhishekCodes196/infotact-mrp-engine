package com.example.demo.service;

import com.example.demo.repository.BomLinkRepository;
import com.example.demo.model.BomLink;
import com.example.demo.model.BomNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MRPService {

    @Autowired
    private BomLinkRepository bomLinkRepository;

    // This method builds the tree for JSON
    public BomNode getBomTree(Long id, Double qty) {
        BomNode node = new BomNode(id, qty);
        List<BomLink> children = bomLinkRepository.findByParentItemId(id);
        
        for (BomLink link : children) {
            node.children.add(getBomTree(link.getChildItemId(), link.getQuantityRequired()));
        }
        return node;
    }
}