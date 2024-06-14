package com.company.library.service;

import com.company.library.dto.common.PublishingHouseDTO;
import com.company.library.exception.types.DuplicateResourceException;
import com.company.library.exception.types.ResourceInUseException;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.Book;
import com.company.library.model.PublishingHouse;
import com.company.library.repository.BookRepository;
import com.company.library.repository.PublishingHouseRepository;
import com.company.library.service.impl.PublishingHouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublishingHouseServiceTest {

    @Mock
    private PublishingHouseRepository publishingHouseRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private PublishingHouseServiceImpl publishingHouseService;

    private PublishingHouse publishingHouse1, publishingHouse2;
    private Book book1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        publishingHouse1 = new PublishingHouse(1L, "Publisher 1", "123 Main St", "City 1", "Country 1", "0505555555", "publisher1@example.com", "www.publisher1.com", 2000);
        publishingHouse2 = new PublishingHouse(2L, "Publisher 2", "456 Park Ave", "City 2", "Country 2", "0505555556", "publisher2@example.com", "www.publisher2.com", 2010);
        book1 = new Book();
    }

    @Test
    void testGetAllPublishingHouses() {
        List<PublishingHouse> publishingHouses = Arrays.asList(publishingHouse1, publishingHouse2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<PublishingHouse> publishingHousePage = new PageImpl<>(publishingHouses, pageable, publishingHouses.size());
        when(publishingHouseRepository.findAll(pageable)).thenReturn(publishingHousePage);

        Page<PublishingHouseDTO> result = publishingHouseService.getAllPublishingHouses(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetPublishingHouseById() {
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(publishingHouse1));

        PublishingHouseDTO result = publishingHouseService.getPublishingHouseById(1L);

        assertNotNull(result);
        assertEquals(publishingHouse1.getName(), result.getName());
    }

    @Test
    void testGetPublishingHouseByIdNotFound() {
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> publishingHouseService.getPublishingHouseById(1L));
    }

    @Test
    void testGetPublishingHousesByCountry() {
        when(publishingHouseRepository.findByCountry("Country 1")).thenReturn(Arrays.asList(publishingHouse1));

        List<PublishingHouseDTO> result = publishingHouseService.getPublishingHousesByCountry("Country 1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(publishingHouse1.getName(), result.get(0).getName());
    }

    @Test
    void testCreatePublishingHouse() {
        PublishingHouseDTO publishingHouseDTO = new PublishingHouseDTO(null, "New Publisher", "789 Elm St", "City 3", "Country 3", "0505555557", "newpublisher@example.com", "www.newpublisher.com", 2020);
        PublishingHouse savedPublishingHouse = new PublishingHouse(3L, "New Publisher", "789 Elm St", "City 3", "Country 3", "0505555557", "newpublisher@example.com", "www.newpublisher.com", 2020);
        when(publishingHouseRepository.findByName("New Publisher")).thenReturn(null);
        when(publishingHouseRepository.save(any(PublishingHouse.class))).thenReturn(savedPublishingHouse);

        PublishingHouseDTO result = publishingHouseService.createPublishingHouse(publishingHouseDTO);

        assertNotNull(result);
        assertEquals(savedPublishingHouse.getName(), result.getName());
    }

    @Test
    void testCreatePublishingHouseDuplicate() {
        PublishingHouseDTO publishingHouseDTO = new PublishingHouseDTO(null, "Publisher 1", "789 Elm St", "City 3", "Country 3", "0505555557", "newpublisher@example.com", "www.newpublisher.com", 2020);
        when(publishingHouseRepository.findByName("Publisher 1")).thenReturn(publishingHouse1);

        assertThrows(DuplicateResourceException.class, () -> publishingHouseService.createPublishingHouse(publishingHouseDTO));
    }

    @Test
    void testUpdatePublishingHouse() {
        PublishingHouseDTO publishingHouseDTO = new PublishingHouseDTO(1L, "Updated Publisher", "789 Elm St", "City 3", "Country 3", "0505555558", "updatedpublisher@example.com", "www.updatedpublisher.com", 2025);
        PublishingHouse existingPublishingHouse = new PublishingHouse(1L, "Publisher 1", "123 Main St", "City 1", "Country 1", "0505555555", "publisher1@example.com", "www.publisher1.com", 2000);
        PublishingHouse updatedPublishingHouse = new PublishingHouse(1L, "Updated Publisher", "789 Elm St", "City 3", "Country 3", "0505555558", "updatedpublisher@example.com", "www.updatedpublisher.com", 2025);
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(existingPublishingHouse));
        when(publishingHouseRepository.findByName("Updated Publisher")).thenReturn(null);
        when(publishingHouseRepository.save(any(PublishingHouse.class))).thenReturn(updatedPublishingHouse);

        PublishingHouseDTO result = publishingHouseService.updatePublishingHouse(1L, publishingHouseDTO);

        assertNotNull(result);
        assertEquals(updatedPublishingHouse.getName(), result.getName());
        assertEquals(updatedPublishingHouse.getAddress(), result.getAddress());
        assertEquals(updatedPublishingHouse.getCity(), result.getCity());
        assertEquals(updatedPublishingHouse.getCountry(), result.getCountry());
        assertEquals(updatedPublishingHouse.getContactNumber(), result.getContactNumber());
        assertEquals(updatedPublishingHouse.getEmail(), result.getEmail());
        assertEquals(updatedPublishingHouse.getWebsite(), result.getWebsite());
        assertEquals(updatedPublishingHouse.getFoundationYear(), result.getFoundationYear());
    }

    @Test
    void testUpdatePublishingHouseDuplicate() {
        PublishingHouseDTO publishingHouseDTO = new PublishingHouseDTO(1L, "Publisher 2", "789 Elm St", "City 3", "Country 3", "0505555558", "updatedpublisher@example.com", "www.updatedpublisher.com", 2025);
        PublishingHouse existingPublishingHouse = new PublishingHouse(1L, "Publisher 1", "123 Main St", "City 1", "Country 1", "0505555555", "publisher1@example.com", "www.publisher1.com", 2000);
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(existingPublishingHouse));
        when(publishingHouseRepository.findByName("Publisher 2")).thenReturn(publishingHouse2);

        assertThrows(DuplicateResourceException.class, () -> publishingHouseService.updatePublishingHouse(1L, publishingHouseDTO));
    }

    @Test
    void testDeletePublishingHouse() {
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(publishingHouse1));
        when(bookRepository.existsByPublishingHouseId(1L)).thenReturn(false);

        assertDoesNotThrow(() -> publishingHouseService.deletePublishingHouse(1L));
        verify(publishingHouseRepository, times(1)).delete(publishingHouse1);
    }

    @Test
    void testDeletePublishingHouseWithBooks() {
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.of(publishingHouse1));
        when(bookRepository.existsByPublishingHouseId(1L)).thenReturn(true);

        assertThrows(ResourceInUseException.class, () -> publishingHouseService.deletePublishingHouse(1L));
        verify(publishingHouseRepository, never()).delete(any(PublishingHouse.class));
    }

    @Test
    void testDeletePublishingHouseNotFound() {
        when(publishingHouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> publishingHouseService.deletePublishingHouse(1L));
        verify(publishingHouseRepository, never()).delete(any(PublishingHouse.class));
    }
}
