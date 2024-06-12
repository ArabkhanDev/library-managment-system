package com.company.library.controller;

import com.company.library.dto.common.PublishingHouseDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.PublishingHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publishing-houses")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    @GetMapping
    public BaseResponse<Page<PublishingHouseDTO>> getAllPublishingHouses(Pageable pageable) {
        Page<PublishingHouseDTO> publishingHouses = publishingHouseService.getAllPublishingHouses(pageable);
        return BaseResponse.success(publishingHouses);
    }

    @GetMapping("/{id}")
    public BaseResponse<PublishingHouseDTO> getPublishingHouseById(@PathVariable Long id) {
        PublishingHouseDTO publishingHouse = publishingHouseService.getPublishingHouseById(id);
        return BaseResponse.success(publishingHouse);
    }

    @GetMapping("/by-country")
    public BaseResponse<List<PublishingHouseDTO>> getPublishingHousesByCountry(@RequestParam String country) {
        List<PublishingHouseDTO> publishingHouses = publishingHouseService.getPublishingHousesByCountry(country);
        return BaseResponse.success(publishingHouses);
    }

    @PostMapping
    public BaseResponse<PublishingHouseDTO> createPublishingHouse(@Valid @RequestBody PublishingHouseDTO publishingHouseDTO) {
        PublishingHouseDTO createdPublishingHouse = publishingHouseService.createPublishingHouse(publishingHouseDTO);
        return BaseResponse.created(createdPublishingHouse);
    }

    @PutMapping("/{id}")
    public BaseResponse<PublishingHouseDTO> updatePublishingHouse(@PathVariable Long id, @Valid @RequestBody PublishingHouseDTO publishingHouseDTO) {
        PublishingHouseDTO updatedPublishingHouse = publishingHouseService.updatePublishingHouse(id, publishingHouseDTO);
        return BaseResponse.success(updatedPublishingHouse);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deletePublishingHouse(@PathVariable Long id) {
        publishingHouseService.deletePublishingHouse(id);
        return BaseResponse.notContent();
    }
}
