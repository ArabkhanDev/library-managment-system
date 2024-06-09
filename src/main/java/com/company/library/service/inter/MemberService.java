package com.company.library.service.inter;

import com.company.library.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getAllMembers();

    MemberDTO getMemberById(Long id);

    MemberDTO createMember(MemberDTO authorDTO);

    MemberDTO updateMember(Long id, MemberDTO authorDTO);

    void deleteMember(Long id);
}
