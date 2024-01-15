package com.chatop.chatop.controllers;


import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.repository.RentalsRepository;
import com.chatop.chatop.services.RentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rentals")
public class RentalsController {

    private final RentalsService rentalsService;
    private final RentalsRepository rentalsRepository; // Injection de RentalsRepository


    @Autowired
    public RentalsController(RentalsService rentalsService, RentalsRepository rentalsRepository) {
        this.rentalsService = rentalsService;
        this.rentalsRepository = rentalsRepository;
    }

    @PostMapping("")
    public ResponseEntity<String> addRental(
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture
    ) {
        boolean result = rentalsService.addRental(name, surface, price, description, picture);
        if (result) {
            return new ResponseEntity<>("Location ajoutée avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erreur lors de l'ajout de la location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<Rentals>>> getAllRentals() {
        List<Rentals> allRentals = rentalsRepository.findAll();
        Map<String, List<Rentals>> response = new HashMap<>();
        response.put("rentals", allRentals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
