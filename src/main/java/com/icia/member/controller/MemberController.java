package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Long memberId = ms.save(memberSaveDTO);
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
        System.out.println(memberLoginDTO);


        if (loginResult) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
//            return "redirect:/member/";

//            로그인 후 마이페이지 띄우기
              return "member/mypage";
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

    // 회원 조회
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

    // 회원 삭제(/member/5)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        ms.deleteById(memberId);

        /*
         단순 화면 출력이 아닌 데이터를 리턴하고자 할 때 사용하는 리턴방식
         @ResponseEntity: 데이터 & 상태코드를 함께 리턴할 수 있음, Restful 방식으로 설계 시 많이 사용
                          데이터도 보낼 수 있고 상태코드도 보낼 수 있다.
                          상태코드(http 상태코드 ex. 200, 404, 405, 500 등)
                          400 : bad request
                          404 : 주소가 없거나 틀렸을때
                          405 : Method Not Allowed (ex. get post etc)
                          500 : Internal Server Error 자바 문법 오류, null point or exception 등
         @ResponseBody: 데이터를 리턴할 수 있음
       */
        // 여기서는 200 코드를 리턴
        return new ResponseEntity((HttpStatus.OK));

    }
//     회원 수정화면 요청 - 로그인을 하면 마이페이지가 보이고 거기서 수정버튼을 보이는 방식으로 진행
    @GetMapping("update")
    public String updateForm(Model model, HttpSession session) {
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO member = ms.findByEmail(memberEmail);
        model.addAttribute("member", member);
        return "member/update";
    }

    // 정보 수정
    @PostMapping("update")
    public String update(@ModelAttribute MemberDetailDTO memberDetailDTO) {
     Long memberId = ms.update(memberDetailDTO);
//       수정완료 후 해당 회원의 상세페이지(findById) 출력
        return "redirect:/member/" + memberDetailDTO.getMemberId();
    }

    // 정보 수정(put)
    // RequestBody : json으로 데이터가 전달되면 @Request body로 받아줘야 함.
    @PutMapping("{memberId}")
    public ResponseEntity update2(@RequestBody MemberDetailDTO memberDetailDTO){
       Long memberId =  ms.update(memberDetailDTO);
       return new ResponseEntity(HttpStatus.OK);

    }

}
