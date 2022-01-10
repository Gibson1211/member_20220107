package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
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
}



