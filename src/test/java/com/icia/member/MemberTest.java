package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberMapperDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.repository.MemberMapperRepository;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Autowired
    private MemberMapperRepository mmr;

    @Test
    @DisplayName("회원데이터생성")
    public void newMembers() {
        IntStream.rangeClosed(21, 40).forEach(i -> {
            ms.save(new MemberSaveDTO("email" + i, "pw" + i, "name" + i));
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제 테스트")
    public void memberDeleteTest() {

        // 삭제를 하기 전 신규 회원 등록
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("삭제용회원이메일1", "삭제용회원비번1", "삭제용회원이름1");
        // 등록한 회원을 저장하고 그 중 memberId를 가져온다.
        Long memberId = ms.save(memberSaveDTO);
        // 잘 가져오는지 확인해본다.
        System.out.println(ms.findById(memberId));


        // 삭제처리를 한다.
        ms.deleteById(memberId);
        // 삭제처리가 되었는지 확인해본다. 콘솔에 에러가 뜨며 에러메세지를 확인.(NoSuchElementException: no value present)
//        System.out.println(ms.findById(memberId));

        // 삭제한 회원의 id로 조회를 시도했을 때 null이어야 테스트통과
        // NoSuchElementException은 무시하고 테스트를 수헹
        assertThrows(NoSuchElementException.class, () -> {
            assertThat(ms.findById(memberId)).isNull(); // 삭제회원 id 조회결과가 null이면 테스트 통과
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원수정 테스트")
    // 테스트 한 데이터를 삭제하고자 할 때는 @Rollback을 쓰지 않으면 된다.
    // commit 하기 전의 CRUD 작업은 임시 저장이고 commit이 되어야 db에 저장된다.
    // STS는 auto-commit으로 항상 db에 저장됨에 유의한다.
    public void memberUpdateTest() {
        /*
        1. 신규회원 등록
        2. 신규등록 회원에 대한 이름 수정
        3. 신규등록 시 사용한 이름과 DB에 저장한 이름이 일치하는지 판단
        4. 일치하지 않아야 테스트 통과
         */
        // 신규회원 등록
        String memberEmail = "수정회원1";
        String memberPassword = "수정비번1";
        String memberName = "수정이름1";
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(memberEmail, memberPassword, memberName);
        Long saveMemberId = ms.save(memberSaveDTO);

        // 가입 후 DB에서 이름 조회
        String saveMemberName = ms.findById(saveMemberId).getMemberName();

        // 2.
        String updateName = "수정용이름1";
        MemberDetailDTO updateMember = new MemberDetailDTO(saveMemberId, memberEmail, memberPassword, updateName);
        ms.update(updateMember);
        // 수정 후 DB에서 이름 조회
        String updateMemberName = ms.findById(saveMemberId).getMemberName();
        // 이름 비교
        assertThat(saveMemberName).isNotEqualTo(updateMemberName);
    }

    @Test
    @Transactional
    @DisplayName("mybatis 목록 출력 테스트")
    public void memberListTest() {
        List<MemberMapperDTO> memberList = mmr.memberList();
        for(MemberMapperDTO m:memberList) {
            System.out.println("m.toString() = " + m.toString());
        }
    }

    @Test
    @Transactional
    @DisplayName("mybatis 회원가입 테스트")
    public void memberSaveTest() {
        MemberMapperDTO memberMapperDTO = new MemberMapperDTO("회원이메일11", "회원비번11", "회원이름11");
        mmr.save(memberMapperDTO);
        MemberMapperDTO memberMapperDTO2 = new MemberMapperDTO("회원이메일2", "회원비번2", "회원이름2");
        mmr.save2(memberMapperDTO2);
    }

}


