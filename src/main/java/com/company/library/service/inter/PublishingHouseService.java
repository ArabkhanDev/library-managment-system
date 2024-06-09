package com.company.library.service.inter;

import com.company.library.dto.PublishingHouseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublishingHouseService {

    Page<PublishingHouseDTO> getAllPublishingHouses(Pageable pageable);

    PublishingHouseDTO getPublishingHouseById(Long id);

    List<PublishingHouseDTO> getPublishingHousesByCountry(String country);

    PublishingHouseDTO createPublishingHouse(PublishingHouseDTO publishingHouseDTO);

    PublishingHouseDTO updatePublishingHouse(Long id, PublishingHouseDTO publishingHouseDTO);

    void deletePublishingHouse(Long id);

}
