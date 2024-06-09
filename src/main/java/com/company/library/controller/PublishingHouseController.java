package com.company.library.controller;

import com.company.library.dto.PublishingHouseDTO;
import com.company.library.service.inter.PublishingHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publishing-houses")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    @GetMapping
    public ResponseEntity<List<PublishingHouseDTO>> getAllPublishingHouses() {
        List<PublishingHouseDTO> publishingHouses = publishingHouseService.getAllPublishingHouses();
        return ResponseEntity.ok(publishingHouses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublishingHouseDTO> getPublishingHouseById(@PathVariable Long id) {
        PublishingHouseDTO publishingHouse = publishingHouseService.getPublishingHouseById(id);
        return ResponseEntity.ok(publishingHouse);
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<PublishingHouseDTO>> getPublishingHousesByCountry(@RequestParam String country) {
        List<PublishingHouseDTO> publishingHouses = publishingHouseService.getPublishingHousesByCountry(country);
        return ResponseEntity.ok(publishingHouses);
    }

    @PostMapping
    public ResponseEntity<PublishingHouseDTO> createPublishingHouse(@Valid @RequestBody PublishingHouseDTO publishingHouseDTO) {
        PublishingHouseDTO createdPublishingHouse = publishingHouseService.createPublishingHouse(publishingHouseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublishingHouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublishingHouseDTO> updatePublishingHouse(@PathVariable Long id, @Valid @RequestBody PublishingHouseDTO publishingHouseDTO) {
        PublishingHouseDTO updatedPublishingHouse = publishingHouseService.updatePublishingHouse(id, publishingHouseDTO);
        return ResponseEntity.ok(updatedPublishingHouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublishingHouse(@PathVariable Long id) {
        publishingHouseService.deletePublishingHouse(id);
        return ResponseEntity.noContent().build();
    }
}
