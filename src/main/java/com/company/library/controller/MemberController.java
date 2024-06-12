package com.company.library.controller;

import com.company.library.dto.common.MemberDTO;
import com.company.library.model.base.BaseResponse;
import com.company.library.service.inter.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public BaseResponse<Page<MemberDTO>> getAllMembers(Pageable pageable) {
        Page<MemberDTO> members = memberService.getAllMembers(pageable);
        return BaseResponse.success(members);
    }

    @GetMapping("/{id}")
    public BaseResponse<MemberDTO> getMemberById(@PathVariable Long id) {
        MemberDTO member = memberService.getMemberById(id);
        return BaseResponse.success(member);
    }

    @PostMapping
    public BaseResponse<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO createdMember = memberService.createMember(memberDTO);
        return BaseResponse.created(createdMember);
    }

    @PutMapping("/{id}")
    public BaseResponse<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO updatedMember = memberService.updateMember(id, memberDTO);
        return BaseResponse.success(updatedMember);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return BaseResponse.notContent();
    }

}
