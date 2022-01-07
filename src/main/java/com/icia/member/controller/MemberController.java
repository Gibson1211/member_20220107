package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final이 붙은 필드로만 생성자를 만들어 줌
public class MemberController {

    private final MemberService ms;

    // 회원가입 폼
    @GetMapping("save")
    public String saveForm() {
        return "member/save";
    }

    // 회원가입
    @PostMapping("/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) {
        Long member_id = ms.save(memberSaveDTO);
        return "member/login";
    }

    // 로그인 폼
    @GetMapping("login")
    public String loginForm() {
        return "member/login";
    }

    // 로그인처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session) {
        boolean loginResult = ms.login(memberLoginDTO);
        if (loginResult) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            return "redirect:/member/";
        } else {
            return "member/login";
        }
    }

    @GetMapping
    public String findAll(Model model) {
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        System.out.println(memberList);
        return "member/findAll";
    }

    @GetMapping("{memberId}")
    public String findById(@PathVariable Long memberId, Model model) {
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);
        return "member/findById";
    }

    //회원 조회_Ajax
    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable Long memberId) {
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }

    // 회원 삭제(/member/delete/5)
    @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);
        return "redirect:/member/";
    }



}
