package com.company.library.service.inter;

import com.company.library.dto.PublishingHouseDTO;

import java.util.List;

public interface PublishingHouseService {

    List<PublishingHouseDTO> getAllPublishingHouses();

    PublishingHouseDTO getPublishingHouseById(Long id);

    List<PublishingHouseDTO> getPublishingHousesByCountry(String country);

    PublishingHouseDTO createPublishingHouse(PublishingHouseDTO publishingHouseDTO);

    PublishingHouseDTO updatePublishingHouse(Long id, PublishingHouseDTO publishingHouseDTO);

    void deletePublishingHouse(Long id);

}
