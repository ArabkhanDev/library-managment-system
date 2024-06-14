package com.company.library.service;

import com.company.library.dto.common.MemberDTO;
import com.company.library.enums.MembershipType;
import com.company.library.exception.types.ResourceNotFoundException;
import com.company.library.model.BorrowingRecord;
import com.company.library.model.Member;
import com.company.library.repository.MemberRepository;
import com.company.library.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member1, member2;
    private BorrowingRecord borrowingRecord1, borrowingRecord2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member1 = new Member(1L, "John", "Doe", "john.doe@example.com", "0555555555", "123 Main St", LocalDate.now(), MembershipType.PREMIUM, true, null);
        member2 = new Member(2L, "Jane", "Doe", "jane.doe@example.com", "0555555556", "456 Park Ave", LocalDate.now().minusYears(1), MembershipType.PREMIUM, true, null);

        borrowingRecord1 = new BorrowingRecord();
        borrowingRecord2 = new BorrowingRecord();

        member1.setBorrowingRecords(Arrays.asList(borrowingRecord1));
        member2.setBorrowingRecords(Arrays.asList(borrowingRecord2));
    }

    @Test
    void testGetAllMembers() {
        List<Member> members = Arrays.asList(member1, member2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Member> memberPage = new PageImpl<>(members, pageable, members.size());
        when(memberRepository.findAll(pageable)).thenReturn(memberPage);

        Page<MemberDTO> result = memberService.getAllMembers(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetMemberById() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member1));

        MemberDTO result = memberService.getMemberById(1L);

        assertNotNull(result);
        assertEquals(member1.getFirstName(), result.getFirstName());
        assertEquals(member1.getLastName(), result.getLastName());
        assertEquals(member1.getEmail(), result.getEmail());
    }

    @Test
    void testGetMemberByIdNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.getMemberById(1L));
    }

    @Test
    void testCreateMember() {
        MemberDTO memberDTO = new MemberDTO(null, "NewFirst", "NewLast", "new@example.com", "0555555557", "789 Elm St", LocalDate.now(), "PREMIUM", true);
        Member savedMember = new Member(3L, "NewFirst", "NewLast", "new@example.com", "0555555557", "789 Elm St", LocalDate.now(), MembershipType.PREMIUM, true, null);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        MemberDTO result = memberService.createMember(memberDTO);

        assertNotNull(result);
        assertEquals(savedMember.getFirstName(), result.getFirstName());
        assertEquals(savedMember.getLastName(), result.getLastName());
        assertEquals(savedMember.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateMember() {
        MemberDTO memberDTO = new MemberDTO(1L, "UpdatedFirst", "UpdatedLast", "updated@example.com", "0555555558", "123 Main St", LocalDate.now(), "PREMIUM", true);
        Member existingMember = new Member(1L, "John", "Doe", "john.doe@example.com", "0555555555", "123 Main St", LocalDate.now(), MembershipType.PREMIUM, true, null);
        Member updatedMember = new Member(1L, "UpdatedFirst", "UpdatedLast", "updated@example.com", "0555555558", "123 Main St", LocalDate.now(), MembershipType.PREMIUM, true, null);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        MemberDTO result = memberService.updateMember(1L, memberDTO);

        assertNotNull(result);
        assertEquals(updatedMember.getFirstName(), result.getFirstName());
        assertEquals(updatedMember.getLastName(), result.getLastName());
        assertEquals(updatedMember.getEmail(), result.getEmail());
        assertEquals(updatedMember.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(updatedMember.getAddress(), result.getAddress());
        assertEquals(updatedMember.getJoinDate(), result.getJoinDate());
        assertEquals(updatedMember.getMembershipType(), MembershipType.fromString(result.getMembershipType()));
        assertEquals(updatedMember.getIsActive(), result.getIsActive());
    }

    @Test
    void testDeleteMemberWithoutBorrowingRecords() {
        Member memberWithoutRecords = new Member(3L, "NewFirst", "NewLast", "new@example.com", "0555555557", "789 Elm St", LocalDate.now(), MembershipType.PREMIUM, true, null);
        when(memberRepository.findById(3L)).thenReturn(Optional.of(memberWithoutRecords));

        assertDoesNotThrow(() -> memberService.deleteMember(3L));
        verify(memberRepository, times(1)).delete(memberWithoutRecords);
    }

    @Test
    void testDeleteMemberWithBorrowingRecords() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member1));

        assertDoesNotThrow(() -> memberService.deleteMember(1L));
        verify(memberRepository, times(1)).delete(member1);
        assertNull(borrowingRecord1.getMember());
    }
}
