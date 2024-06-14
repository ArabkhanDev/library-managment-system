package com.company.library.controller;

import com.company.library.dto.common.MemberDTO;
import com.company.library.enums.MembershipType;
import com.company.library.model.Member;
import com.company.library.service.inter.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private MemberDTO memberDTO;
    private Member member;

    @BeforeEach
    void setUp() {
        memberDTO = new MemberDTO(1L, "John", "Doe", "john.doe@example.com", "0501234567",
                "123 Street, City", LocalDate.now(), MembershipType.PREMIUM.name(), true);
        member = new Member(1L, "John", "Doe", "john.doe@example.com", "0501234567",
                "123 Street, City", LocalDate.now(), MembershipType.PREMIUM, true, null);
    }

    @Test
    public void testGetAllMembers() {
        Page<MemberDTO> page = new PageImpl<>(Collections.singletonList(memberDTO));
        when(memberService.getAllMembers(any(Pageable.class))).thenReturn(page);

        Page<MemberDTO> result = memberController.getAllMembers(Pageable.unpaged()).getData();

        assertEquals(page.getContent(), result.getContent());
    }

    @Test
    public void testGetMemberById() {
        when(memberService.getMemberById(1L)).thenReturn(memberDTO);

        MemberDTO result = memberController.getMemberById(1L).getData();

        assertEquals(memberDTO, result);
    }

    @Test
    public void testCreateMember() {
        when(memberService.createMember(any(MemberDTO.class))).thenReturn(memberDTO);

        MemberDTO result = memberController.createMember(memberDTO).getData();

        assertEquals(memberDTO, result);
    }

    @Test
    public void testUpdateMember() {
        when(memberService.updateMember(eq(1L), any(MemberDTO.class))).thenReturn(memberDTO);

        MemberDTO result = memberController.updateMember(1L, memberDTO).getData();

        assertEquals(memberDTO, result);
    }

    @Test
    public void testDeleteMember() {
        doNothing().when(memberService).deleteMember(1L);

        memberController.deleteMember(1L);

        verify(memberService, times(1)).deleteMember(1L);
    }
}
