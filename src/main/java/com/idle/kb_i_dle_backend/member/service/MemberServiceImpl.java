package com.idle.kb_i_dle_backend.member.service;

import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.entity.Member;
import com.idle.kb_i_dle_backend.member.repository.MemberRepository;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

//    private final MemberMapper memberMapper;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // 임시로 인증 코드를 저장할 Map (실제 구현에서는 데이터베이스나 캐시를 사용해야 함)
    private Map<String, String> verificationCodes = new HashMap<>();

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean checkDupl(String id) {
        return memberRepository.existsById(id);
    }


    @Override
    @Transactional
    public Member getMember(String id) {
        return memberRepository.findById(id);
    }

    @Override
    @Transactional
    public void MemberJoin(MemberJoinDTO memberjoindto) {
        try {
            log.debug("Starting MemberJoin process for ID: {}", memberjoindto.getId());

            if (memberjoindto.isAgreementInfo()) {
                memberjoindto.setAgreementInfo(true);
            } else {
                memberjoindto.setAgreementInfo(false);
            }
            if (memberjoindto.isAgreementFinance()) {
                memberjoindto.setAgreementFinance(true);
            } else {
                memberjoindto.setAgreementFinance(false);
            }
            if (memberjoindto.getAuth() == null || memberjoindto.getAuth().isEmpty()) {
                memberjoindto.setAuth("ROLE_MEMBER");
            }

            log.debug("Checking if user exists");
            if (memberRepository.existsById(memberjoindto.getId())) {
                throw new IllegalStateException("User already exists");
            }

            log.debug("Validating nickname");
            if (memberjoindto.getNickname() == null || memberjoindto.getNickname().length() > 50) {
                throw new IllegalArgumentException("Nickname must not be null and should not exceed 50 characters");
            }

            log.debug("Validating ID");
            if (memberjoindto.getId() == null || memberjoindto.getId().isEmpty()) {
                throw new IllegalStateException("User ID is required");
            }

            log.debug("Encoding password");
            String encodePassword = passwordEncoder.encode(memberjoindto.getPassword());

            log.debug("Building User entity");
            Member newMember = Member.builder()
                    .id(memberjoindto.getId())
                    .password(encodePassword)
                    .nickname(memberjoindto.getNickname())
                    .gender(String.valueOf(memberjoindto.getGender()))
                    .email(memberjoindto.getEmail())
                    .birth_year(memberjoindto.getBirth_year())
                    .auth(memberjoindto.getAuth())

                    .agreementInfo(memberjoindto.isAgreementInfo())
                    .agreementFinance(memberjoindto.isAgreementFinance())
                    .build();

            log.debug("Saving new user: {}", newMember);
            memberRepository.save(newMember);
            log.debug("User saved successfully");
        } catch (Exception e) {
            log.error("Error in MemberJoin: ", e);
            throw e;
        }
    }

    @Override
    public MemberDTO findById(String id) {
        Member member = memberRepository.findById(id);
        if (member != null) {
            return new MemberDTO(
                    member.getUid(),
                    member.getId(),
                    member.getPassword(),
                    member.getEmail(),
                    member.getSocial(),
                    member.getBirth_year(),
                    member.getGender().charAt(0), // Assuming gender is stored as a String
                    member.getProfile(),
                    member.getAgreementInfo(),
                    member.getAgreementFinance(),
                    member.getIsMentor(),
                    member.getIsCertification(),
                    member.getNickname(),
                    member.getAuth()
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
        Member member = memberRepository.findById(id);
        if (member != null) {
            member.setAgreementInfo(info);
            member.setAgreementFinance(finance);
            memberRepository.save(member); // Save updated user
            return true;
        }
        return false; // User not found
    }

    @Override
    public String findIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null ? member.getId() : null;
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
        Member member = memberRepository.findById(id);
        if (member != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            member.setPassword(encodedPassword); // Update password
            memberRepository.save(member); // Save updated user
            return true;
        }
        return false; // User not found
    }

    @Override
    public boolean checkEmailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public MemberDTO findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return new MemberDTO(
                    member.getUid(),
                    member.getId(),
                    member.getPassword(),
                    member.getEmail(),
                    member.getSocial(),
                    member.getBirth_year(),
                    member.getGender().charAt(0),
                    member.getProfile(),
                    member.getAgreementInfo(),
                    member.getAgreementFinance(),
                    member.getIsMentor(),
                    member.getIsCertification(),
                    member.getNickname(),
                    member.getAuth()
            );
        }
        return null; // 또는 예외를 던질 수 있습니다.
    }

}