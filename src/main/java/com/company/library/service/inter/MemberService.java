package com.company.library.service.inter;

import com.company.library.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    Page<MemberDTO> getAllMembers(Pageable pageable);

    MemberDTO getMemberById(Long id);

    MemberDTO createMember(MemberDTO authorDTO);

    MemberDTO updateMember(Long id, MemberDTO authorDTO);

    void deleteMember(Long id);
}
