//package com.idle.kb_i_dle_backend.member.service;
//
//import com.idle.kb_i_dle_backend.member.dto.UserBadgeDTO;
//import com.idle.kb_i_dle_backend.member.entity.UserBadgeEntity;
//import com.idle.kb_i_dle_backend.member.repository.UserBadgeRepository;
//import com.idle.kb_i_dle_backend.member.repository.UserInfoRepository;
//import com.idle.kb_i_dle_backend.member.entity.UserInfoEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserBadgeService {
//
//    private final UserBadgeRepository userBadgeRepository;
//    private final UserInfoRepository userInfoRepository;
//
//    public UserBadgeService(UserBadgeRepository userBadgeRepository, UserInfoRepository userInfoRepository) {
//        this.userBadgeRepository = userBadgeRepository;
//        this.userInfoRepository = userInfoRepository;
//    }
//
//    // 전체 뱃지 조회 메서드
//    public List<UserBadgeDTO> getAllBadges(String nickname) {
//        // nickname으로 UserInfoEntity에서 uid를 조회
//        UserInfoEntity userInfo = userInfoRepository.findByNickname(nickname);
//        if (userInfo == null) {
//            throw new RuntimeException("유저를 찾을 수 없습니다.");
//        }
//
//        // uid로 모든 뱃지 정보 조회
//        List<UserBadgeEntity> badgeEntities = userBadgeRepository.findByIdUid(userInfo.getUid());
//
//        return badgeEntities.stream()
//                .map(entity -> {
//                    UserBadgeDTO badgeDTO = new UserBadgeDTO();
//                    badgeDTO.setBadgeNo(entity.getId().getBadgeNo());  // 복합키에서 badgeNo 가져오기
//                    badgeDTO.setName(entity.getBadge());
//                    badgeDTO.setImg(entity.isBadgeMain() ? "main_url" : "normal_url");
//                    badgeDTO.setDesc("자산 1억 달성시 수여");  // 설명 설정
//                    badgeDTO.setHaving(entity.isBadgeAchive());  // 보유 여부 설정
//                    return badgeDTO;
//                }).collect(Collectors.toList());
//    }
//
//    // 달성 여부에 따라 뱃지 조회
//    public List<UserBadgeDTO> getUserBadges(String nickname, boolean isAchived) {
//        UserInfoEntity userInfo = userInfoRepository.findByNickname(nickname);
//        if (userInfo == null) {
//            throw new RuntimeException("유저를 찾을 수 없습니다.");
//        }
//
//        // uid로 뱃지 정보 조회
//        List<UserBadgeEntity> badgeEntities = userBadgeRepository.findById_UidAndBadgeAchive(userInfo.getUid(), isAchived);
//
//        return badgeEntities.stream()
//                .map(entity -> {
//                    UserBadgeDTO badgeDTO = new UserBadgeDTO();
//                    badgeDTO.setBadgeNo(entity.getId().getBadgeNo());  // 복합키에서 badgeNo 가져오기
//                    badgeDTO.setName(entity.getBadge());
//                    badgeDTO.setImg(entity.isBadgeMain() ? "main_url" : "normal_url");
//                    badgeDTO.setDesc(entity.isBadgeAchive() ? "달성한 뱃지" : "미달성 뱃지");
//                    badgeDTO.setHaving(entity.isBadgeAchive());
//                    return badgeDTO;
//                }).collect(Collectors.toList());
//    }
//}
