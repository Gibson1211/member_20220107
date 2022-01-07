package com.icia.member;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원데이터생성")
    public void newMembers() {
        IntStream.rangeClosed(1, 15).forEach(i -> {
            ms.save(new MemberSaveDTO("email"+i, "pw"+i, "name"+i));
        });
    }

}

