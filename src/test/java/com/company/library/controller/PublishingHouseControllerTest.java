package com.company.library.controller;

import com.company.library.dto.common.PublishingHouseDTO;
import com.company.library.service.inter.PublishingHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublishingHouseControllerTest {

    @Mock
    private PublishingHouseService publishingHouseService;

    @InjectMocks
    private PublishingHouseController publishingHouseController;

    private PublishingHouseDTO publishingHouseDTO;

    @BeforeEach
    void setUp() {
        publishingHouseDTO = new PublishingHouseDTO(1L, "Publisher A", "123 Main St", "Cityville", "Countryland",
                "0501234567", "publisher@example.com", "http://www.publisher.com", 2000);
    }

    @Test
    public void testGetAllPublishingHouses() {
        Page<PublishingHouseDTO> page = new PageImpl<>(Collections.singletonList(publishingHouseDTO));
        when(publishingHouseService.getAllPublishingHouses(any(Pageable.class))).thenReturn(page);

        Page<PublishingHouseDTO> result = publishingHouseController.getAllPublishingHouses(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetPublishingHouseById() {
        when(publishingHouseService.getPublishingHouseById(1L)).thenReturn(publishingHouseDTO);

        PublishingHouseDTO result = publishingHouseController.getPublishingHouseById(1L).getData();

        assertEquals(publishingHouseDTO, result);
    }

    @Test
    public void testGetPublishingHousesByCountry() {
        List<PublishingHouseDTO> list = Collections.singletonList(publishingHouseDTO);
        when(publishingHouseService.getPublishingHousesByCountry("Countryland")).thenReturn(list);

        List<PublishingHouseDTO> result = publishingHouseController.getPublishingHousesByCountry("Countryland").getData();

        assertEquals(list, result);
    }

    @Test
    public void testCreatePublishingHouse() {
        when(publishingHouseService.createPublishingHouse(any(PublishingHouseDTO.class))).thenReturn(publishingHouseDTO);

        PublishingHouseDTO result = publishingHouseController.createPublishingHouse(publishingHouseDTO).getData();

        assertEquals(publishingHouseDTO, result);
    }

    @Test
    public void testUpdatePublishingHouse() {
        when(publishingHouseService.updatePublishingHouse(eq(1L), any(PublishingHouseDTO.class))).thenReturn(publishingHouseDTO);

        PublishingHouseDTO result = publishingHouseController.updatePublishingHouse(1L, publishingHouseDTO).getData();

        assertEquals(publishingHouseDTO, result);
    }

    @Test
    public void testDeletePublishingHouse() {
        doNothing().when(publishingHouseService).deletePublishingHouse(1L);

        publishingHouseController.deletePublishingHouse(1L);

        verify(publishingHouseService, times(1)).deletePublishingHouse(1L);
    }
}
