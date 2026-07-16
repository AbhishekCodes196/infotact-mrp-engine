package com.example.demo.controller;

import com.example.demo.model.BomLink;
import com.example.demo.model.Part;
import com.example.demo.repository.BomLinkRepository;
import com.example.demo.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MrpController {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private BomLinkRepository bomLinkRepository;

    @GetMapping("/mrp/calculate")
    public List<MrpResult> calculateMRP(@RequestParam Long partId, @RequestParam Integer quantity) {
        List<MrpResult> results = new ArrayList<>();

         List<BomLink> links = bomLinkRepository.findByParentItemId(partId);

         for (BomLink link : links) {
            Part component = partRepository.findById(link.getChildItemId()).orElse(null);
            
            if (component != null) {
                 int grossRequirement = quantity * link.getQuantityRequired();
                
                 int netRequirement = grossRequirement - component.getCurrentStock();
                if (netRequirement < 0) netRequirement = 0; // No negative shortages allowed

                 MrpResult result = new MrpResult(
                    component.getId(),
                    component.getPartName(),
                    grossRequirement,
                    component.getCurrentStock(),
                    netRequirement,
                    component.getLeadTimeDays()
                );
                results.add(result);
            }
        }
        return results;
    }

     public static class MrpResult {
        public Long id;
        public String partName;
        public int grossRequirement;
        public int currentStock;
        public int netRequirement;
        public int leadTimeDays;

        public MrpResult(Long id, String partName, int grossRequirement, int currentStock, int netRequirement, int leadTimeDays) {
            this.id = id;
            this.partName = partName;
            this.grossRequirement = grossRequirement;
            this.currentStock = currentStock;
            this.netRequirement = netRequirement;
            this.leadTimeDays = leadTimeDays;
        }
    }
}
