package com.idle.kb_i_dle_backend.member.controller;

import com.idle.kb_i_dle_backend.member.dto.AuthResultDTO;
import com.idle.kb_i_dle_backend.member.dto.CustomUser;
import com.idle.kb_i_dle_backend.member.dto.LoginDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberDTO;
import com.idle.kb_i_dle_backend.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.member.dto.UserInfoDTO;
import com.idle.kb_i_dle_backend.member.service.MemberService;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;
    private final MemberService memberService;

    public MemberController(AuthenticationManager authenticationManager, JwtProcessor jwtProcessor, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.jwtProcessor = jwtProcessor;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Perform authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Assuming you have a method to fetch UserInfoDTO (e.g., from the authenticated user details)
        UserInfoDTO userInfo = getUserInfoFromAuthentication(authentication);
        // Generate JWT token with uid
        String jwtToken = jwtProcessor.generateToken(userInfo.getId(), userInfo.getUid(), userInfo.getNickname());

        // Return the AuthResultDTO with token and user info
        return ResponseEntity.ok(new AuthResultDTO(jwtToken, userInfo));
    }

    // Helper method to retrieve user information from the authentication object
    private UserInfoDTO getUserInfoFromAuthentication(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        MemberDTO member = customUser.getMember();  // Retrieve the MemberDTO object
        return new UserInfoDTO(member.getUid(), member.getId(), member.getNickname(), member.getAuth().toString());
    }

//    @PostMapping("/naver/callback")
//    public ResponseEntity<?> handleNaverCallback(@RequestParam String code, @RequestParam String state) {
//        try {
//            // Step 1: Get access token using the provided code
//            String accessToken = memberService.getNaverAccessToken(code, state);
//
//            // Step 2: Retrieve user profile from Naver
//            Map<String, String> userProfile = memberService.getNaverUserProfile(accessToken);
//
//            // Step 3: Log in or sign up the user based on Naver profile info
//            MemberDTO member = memberService.processNaverLogin(userProfile);
//
//            // Step 4: Generate JWT for the logged-in user
//            String token = jwtProcessor.generateToken(member.getEmail());
//
//            return ResponseEntity.ok(new AuthResultDTO(token, new UserInfoDTO(member)));
//        } catch (Exception e) {
//            log.error("Naver login failed", e);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Naver login failed");
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody MemberJoinDTO signupDTO) {
        try {
            memberService.MemberJoin(signupDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    @GetMapping("/checkDupl/{id}")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateUsername(@PathVariable String id) {
        boolean exists = memberService.checkDupl(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/terms/{id}")
    public ResponseEntity<Map<String, Boolean>> updateUserAgreement(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> agreementData) {

        boolean info = agreementData.get("info");
        boolean finance = agreementData.get("finance");

        boolean success = memberService.checkAgree(info, finance, id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/findid")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String id = memberService.findIdByEmail(email);
        if (id != null) {
            String verificationCode = memberService.generateAndSaveVerificationCode(email);
            System.out.println(verificationCode);
            log.info("verification code: " + verificationCode);
            // TODO: 이메일로 verificationCode 전송 로직 구현
            return ResponseEntity.ok(Map.of("message", "인증 코드가 이메일로 전송되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "해당 이메일로 등록된 아이디가 없습니다."));
        }
    }

    @PostMapping("/findid/auth")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        boolean isValid = memberService.verifyCode(email, code);
        if (isValid) {
            String id = memberService.findIdByEmail(email);
            return ResponseEntity.ok(Map.of("id", id));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 인증 코드입니다."));
        }
    }

//    @GetMapping("/findid2/{id}")
//    public ResponseEntity<?> confirmFoundId(@PathVariable String id) {
//        // ID의 유효성을 확인하고 필요한 정보를 반환합니다.
//        // 보안상의 이유로 전체 ID를 반환하지 않고 일부만 마스킹하여 반환할 수 있습니다.
//        if (maskedId != null) {
//            return ResponseEntity.ok(Map.of("maskedId", maskedId));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "해당 아이디를 찾을 수 없습니다."));
//        }
//    }

    @PostMapping("/find/pw/auth")
    public ResponseEntity<?> findPw(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String id = memberService.findIdByEmail(email);
        if (id != null) {
            String verificationCode = memberService.generateAndSaveVerificationCode(email);
            System.out.println(verificationCode);
            log.info("verification code: " + verificationCode);
            // TODO: 이메일로 verificationCode 전송 로직 구현
            return ResponseEntity.ok(Map.of("message", "인증 코드가 이메일로 전송되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "해당 이메일로 등록된 아이디가 없습니다."));
        }
    }

    @PostMapping("/find_pw_auth")
    public ResponseEntity<?> verifyCode2(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        boolean isValid = memberService.verifyCode(email, code);
        if (isValid) {
            String id = memberService.findIdByEmail(email);
            return ResponseEntity.ok(Map.of("id", id));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 인증 코드입니다."));
        }
    }

    @PostMapping("/set/pw")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String newPassword = payload.get("newPassword");
        boolean success = memberService.resetPassword(id, newPassword);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 재설정되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호 재설정 중 오류가 발생했습니다."));
        }
    }


}
