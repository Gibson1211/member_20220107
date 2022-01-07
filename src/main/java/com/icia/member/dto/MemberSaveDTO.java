package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// @NoArgsConstructor : 기본생성자 생성 시 사용하는 어노테이션
@AllArgsConstructor
// @AllArgsConstructor : 매개변수가 있는 생성자 생성 시 사용하는 어노테이션
public class MemberSaveDTO {

    private String memberEmail;
    private String memberPassword;
    private String memberName;

    // alt+Enter : 생성자를 자동으로 생성하고자 할 때 사용

}
