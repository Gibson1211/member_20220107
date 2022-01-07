package com.icia.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    public String memberEmail;
    @NotBlank
    @Length(min = 2, max = 8, message = "2~8자로 입력해주세요")
    public String memberPassword;

}
