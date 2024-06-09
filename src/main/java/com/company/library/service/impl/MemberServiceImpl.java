package com.company.library.service.impl;

import com.company.library.dto.MemberDTO;
import com.company.library.enums.MembershipType;
import com.company.library.exception.ResourceNotFoundException;
import com.company.library.mapper.MemberMapper;
import com.company.library.model.Member;
import com.company.library.repository.MemberRepository;
import com.company.library.service.inter.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream().map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = MemberMapper.INSTANCE.toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.INSTANCE.toDTO(savedMember);
    }

    @Override
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());
        member.setEmail(memberDTO.getEmail());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setAddress(memberDTO.getAddress());
        member.setJoinDate(memberDTO.getJoinDate());
        member.setMembershipType(MembershipType.fromString(memberDTO.getMembershipType()));
        member.setIsActive(memberDTO.getIsActive());

        Member updatedMember = memberRepository.save(member);
        return MemberMapper.INSTANCE.toDTO(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        if (Optional.ofNullable(member.getBorrowingRecords()).isPresent()) {
            member.getBorrowingRecords().forEach(borrowingRecord -> borrowingRecord.setMember(null));
        }

        memberRepository.delete(member);
    }
}
