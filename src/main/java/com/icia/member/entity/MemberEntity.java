package com.icia.member.entity;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "memberId") //별도 컬럼이름 지정 시 사용
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column
    private String memberName;

    // MemberSaveDTO -> MemberEntity 객체로 변환하기 위한 메서드
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
//       String memberEmail = memberEntity.getMemberEmail();
//          memberEntity.setMemberEmail(memberEmail);
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }

    // MemberDetailDTO -> MemberEntity 객체로 변환하기 위한 메서드
    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        return memberEntity;
    }

}
