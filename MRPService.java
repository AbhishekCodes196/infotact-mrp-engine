package com.example.demo.service;

import com.example.demo.model.BomLink;
import com.example.demo.model.BomNode;
import com.example.demo.repository.BomLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MRPService {

    @Autowired
    private BomLinkRepository bomLinkRepository;

    public BomNode getBomTree(Long id, Double qty) {
        BomNode node = new BomNode(id, qty);
        
         List<BomLink> children = bomLinkRepository.findByParentItemId(id);
        for (BomLink link : children) {
         	Long childId = link.getChildItemId();
        	Double childQty = Double.valueOf(link.getQuantityRequired());
            node.children.add(getBomTree(childId, childQty));
        }
        return node;
    }
}
