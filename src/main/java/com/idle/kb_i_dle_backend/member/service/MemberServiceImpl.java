package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.repository.UserRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

//    private final MemberMapper memberMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // 임시로 인증 코드를 저장할 Map (실제 구현에서는 데이터베이스나 캐시를 사용해야 함)
    private Map<String, String> verificationCodes = new HashMap<>();

    @Autowired
    public MemberServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean checkDupl(String id) {
        return userRepository.existsById(Long.valueOf(id));
    }


    @Override
    @Transactional
    public User getMember(String id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void MemberJoin(MemberJoinDTO memberjoindto) {
        if (userRepository.existsById(Long.valueOf(memberjoindto.getId()))) {
            throw new IllegalStateException("User already exists");
        }
        if (memberjoindto.getNickname() == null || memberjoindto.getNickname().length() > 50) {
            throw new IllegalArgumentException("Nickname must not be null and should not exceed 50 characters");
        }
        // Perform necessary checks and add try-catch blocks for any exceptions.
        if (memberjoindto.getId() == null || memberjoindto.getId().isEmpty()) {
            throw new IllegalStateException("User ID is required");
        }
        String encodePassword = passwordEncoder.encode(memberjoindto.getPassword());

        User newUser = User.builder() // Use Builder pattern if defined
                .id(memberjoindto.getId())
                .password(encodePassword)
                .nickname(memberjoindto.getNickname())
                .gender(String.valueOf(memberjoindto.getGender()))
                .email(memberjoindto.getEmail())
                .birth_year(memberjoindto.getBirth_year())
                .build();

        System.out.println("Inserting new user: " + newUser); // 디버깅을 위해 추가
        userRepository.save(newUser);
    }

    @Override
    public MemberDTO findById(String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user != null) {
            return new MemberDTO(
                    user.getUid(),
                    user.getId(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getSocial(),
                    user.getBirth_year(),
                    user.getGender().charAt(0), // Assuming gender is stored as a String
                    user.getProfile(),
                    user.isAgreement_info(),
                    user.isAgreement_finace(),
                    user.isMentor(),
                    user.isCertification(),
                    user.getNickname(),
                    user.getAuth()
            );
        }
        return null; // Or handle user not found as needed
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return false;
    }

    @Override
    public boolean checkAgree(boolean info, boolean finance, String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user != null) {
            user.setAgreement_info(info);
            user.setAgreement_finace(finance);
            userRepository.save(user); // Save updated user
            return true;
        }
        return false; // User not found
    }

    @Override
    public String findIdByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? user.getId() : null;
    }

    @Override
    public String generateAndSaveVerificationCode(String email) {
        String verificationCode = generateRandomCode();
        verificationCodes.put(email, verificationCode);

        // 이메일 전송
        String subject = "Richable 인증 코드";
        String text = "귀하의 인증 코드는 " + verificationCode + " 입니다.";
        emailService.sendSimpleMessage(email, subject, text);

        return verificationCode;
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        return savedCode != null && savedCode.equals(code);
    }
    private String generateRandomCode() {
        // 6자리 랜덤 숫자 생성 로직
        return String.format("%06d", new Random().nextInt(1000000));
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean resetPassword(String id, String newPassword) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword); // Update password
            userRepository.save(user); // Save updated user
            return true;
        }
        return false; // User not found
    }

}