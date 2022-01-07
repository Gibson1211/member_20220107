package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        // JpaRepository는 무조건 Entity만 받음.

        // MemberSaveDTO -> MemberEntity
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
//        Long id = mr.save(memberEntity).getId();
        return mr.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if (memberEntity != null) {
            if (memberLoginDTO.getMemberEmail().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        // List<MemberEntity> -> List<MemberDetailDTO> 옮겨담기
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for(MemberEntity m: memberEntityList) {
            // Entity 객체를 MemberDetailDTO로 변환하고 memberList에 담음.
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
//             MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(m);
//             memberList.add(memberDetailDTO);
        }
        return memberList;
    }

    @Override
    public MemberDetailDTO findById(Long memberID) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberID);
        MemberEntity memberEntity = memberEntityOptional.get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
//        위의 네줄의 코드를 한줄로 줄이기
//        return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberID).get());
    }
}
