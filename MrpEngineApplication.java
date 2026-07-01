package com.example.demo;

import com.example.demo.model.Part;
import com.example.demo.repository.PartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MrpEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrpEngineApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PartRepository partRepository) {
        return args -> {
            Part p = new Part();
            p.setPartName("Main Assembly");
            p.setCurrentStock(10);
            p.setLeadTimeDays(5);
            partRepository.save(p);
            System.out.println("First part saved: " + p.getPartName());
        };
    }
}