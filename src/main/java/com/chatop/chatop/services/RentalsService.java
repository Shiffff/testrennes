package com.chatop.chatop.services;

import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class RentalsService {

    private final RentalsRepository rentalsRepository;

    @Autowired
    public RentalsService(RentalsRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    public boolean addRental(String name, int surface, int price, String description, MultipartFile picture) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            // Enregistrer l'image sur le disque
            String picturePath = savePictureOnDisk(picture);

            // Créer une nouvelle instance de Rentals
            Rentals rental = new Rentals();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setPicture(picturePath);
            rental.setOwner_id(user.getId()); // Définir l'owner_id avec l'ID de l'utilisateur connecté

            // Enregistrer les détails de la location dans la base de données
            rentalsRepository.save(rental);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String savePictureOnDisk(MultipartFile picture) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "src/main/resources/static";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String fileName = picture.getOriginalFilename();
        File file = new File(uploadPath, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(picture.getBytes());
        fileOutputStream.close();
        return fileName;
    }


}