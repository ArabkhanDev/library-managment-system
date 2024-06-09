package com.company.library.repository;

import com.company.library.model.PublishingHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {
    List<PublishingHouse> findByCountry(String country);
    PublishingHouse findByName(String name);
}
