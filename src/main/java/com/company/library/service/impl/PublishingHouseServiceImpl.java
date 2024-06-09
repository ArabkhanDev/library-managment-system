package com.company.library.service.impl;

import com.company.library.dto.PublishingHouseDTO;
import com.company.library.exception.types.DuplicateResourceException;
import com.company.library.exception.types.ResourceInUseException;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.mapper.PublishingHouseMapper;
import com.company.library.model.PublishingHouse;
import com.company.library.repository.BookRepository;
import com.company.library.repository.PublishingHouseRepository;
import com.company.library.service.inter.PublishingHouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;
    private final BookRepository bookRepository;

    public Page<PublishingHouseDTO> getAllPublishingHouses(Pageable pageable) {
        Page<PublishingHouse> publishingHouses = publishingHouseRepository.findAll(pageable);
        return publishingHouses.map(PublishingHouseMapper.INSTANCE::toDTO);
    }

    public PublishingHouseDTO getPublishingHouseById(Long id) {
        return publishingHouseRepository.findById(id)
                .map(PublishingHouseMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("PublishingHouse not found with id: " + id));
    }

    public List<PublishingHouseDTO> getPublishingHousesByCountry(String country) {
        return publishingHouseRepository.findByCountry(country).stream()
                .map(PublishingHouseMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public PublishingHouseDTO createPublishingHouse(PublishingHouseDTO publishingHouseDTO) {
        if (publishingHouseRepository.findByName(publishingHouseDTO.getName()) != null) {
            log.info("PublishingHouse already exists with name: {}", publishingHouseDTO.getName());
            throw new DuplicateResourceException("PublishingHouse already exists with name: " + publishingHouseDTO.getName());
        }
        PublishingHouse publishingHouse = PublishingHouseMapper.INSTANCE.toEntity(publishingHouseDTO);
        PublishingHouse savedPublishingHouse = publishingHouseRepository.save(publishingHouse);
        return PublishingHouseMapper.INSTANCE.toDTO(savedPublishingHouse);
    }

    public PublishingHouseDTO updatePublishingHouse(Long id, PublishingHouseDTO publishingHouseDTO) {
        PublishingHouse existingPublishingHouse = publishingHouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PublishingHouse not found with id: " + id));

        PublishingHouse sameNamePublishingHouse = publishingHouseRepository.findByName(publishingHouseDTO.getName());
        if (sameNamePublishingHouse != null && !sameNamePublishingHouse.getId().equals(id)) {
            throw new DuplicateResourceException("Another PublishingHouse already exists with name: " + publishingHouseDTO.getName());
        }

        existingPublishingHouse.setName(publishingHouseDTO.getName());
        existingPublishingHouse.setAddress(publishingHouseDTO.getAddress());
        existingPublishingHouse.setCity(publishingHouseDTO.getCity());
        existingPublishingHouse.setCountry(publishingHouseDTO.getCountry());
        existingPublishingHouse.setContactNumber(publishingHouseDTO.getContactNumber());
        existingPublishingHouse.setEmail(publishingHouseDTO.getEmail());
        existingPublishingHouse.setWebsite(publishingHouseDTO.getWebsite());
        existingPublishingHouse.setFoundationYear(publishingHouseDTO.getFoundationYear());

        PublishingHouse updatedPublishingHouse = publishingHouseRepository.save(existingPublishingHouse);
        return PublishingHouseMapper.INSTANCE.toDTO(updatedPublishingHouse);
    }

    public void deletePublishingHouse(Long id) {
        PublishingHouse publishingHouse = publishingHouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PublishingHouse not found with id: " + id));

        if (bookRepository.existsByPublishingHouseId(id)) {
            throw new ResourceInUseException("Cannot delete PublishingHouse because it has associated books.");
        }

        publishingHouseRepository.delete(publishingHouse);
    }
}