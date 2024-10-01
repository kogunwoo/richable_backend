//package com.idle.kb_i_dle_backend.domain.member.service;
//
//import com.idle.kb_i_dle_backend.domain.member.entity.UserProfile;
//import com.idle.kb_i_dle_backend.domain.member.repository.UserProfileRepository;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//
//@Service
//public class UserProfileService {
//
//    private final UserProfileRepository userProfileRepository;
//
//    public UserProfileService(UserProfileRepository userProfileRepository) {
//        this.userProfileRepository = userProfileRepository;
//    }
//
//    public String saveProfileImage(String imgPath, String imgType) {
//        // imgPath와 imgType을 활용해 파일 저장 경로 및 이름을 결정 (로컬 저장소 등)
//        String fileUrl = "/uploads/" + imgPath + "." + imgType;  // 경로와 타입 결합하여 URL 생성
//
//        // 데이터베이스에 저장 (필요 시 로직 변경)
//        UserProfile userProfile = new UserProfile("user1", fileUrl); // 예시로 'user1' 사용
//        userProfileRepository.save(userProfile);
//
//        return fileUrl;
//    }
//
//    // fileUrl을 기반으로 ID를 추출하는 로직
//    public String getIdByFileUrl(String fileUrl) {
//        UserProfile userProfile = userProfileRepository.findByProfileImageUrl(fileUrl);
//        return userProfile != null ? userProfile.getUsername() : "unknown";  // ID 반환 (없을 경우 unknown 반환)
//    }
//}
