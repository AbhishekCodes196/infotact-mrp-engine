package com.example.demo.controller;

import com.example.demo.model.BomLink;
import com.example.demo.repository.BomLinkRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bom")
public class BomController {

    private final BomLinkRepository bomLinkRepository;

    public BomController(BomLinkRepository bomLinkRepository) {
        this.bomLinkRepository = bomLinkRepository;
    }

    // This endpoint saves a new link (e.g., adding a Keyboard to a PC)
    @PostMapping("/add")
    public BomLink addBomLink(@RequestBody BomLink bomLink) {
        return bomLinkRepository.save(bomLink);
    }

    // This endpoint fetches all parts that belong to a specific parent
    @GetMapping("/get/{parentItemId}")
    public List<BomLink> getBomByParent(@PathVariable Long parentItemId) {
        return bomLinkRepository.findByParentItemId(parentItemId);
    }
}