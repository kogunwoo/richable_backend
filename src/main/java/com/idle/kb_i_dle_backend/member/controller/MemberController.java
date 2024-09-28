package com.idle.kb_i_dle_backend.member.controller;

import com.idle.kb_i_dle_backend.common.dto.SuccessResponseDTO;
import com.idle.kb_i_dle_backend.member.dto.*;
import com.idle.kb_i_dle_backend.member.entity.User;
import com.idle.kb_i_dle_backend.member.entity.UserApiEntity;
import com.idle.kb_i_dle_backend.member.repository.UserApiRepository;
import com.idle.kb_i_dle_backend.member.service.MemberService;
import com.idle.kb_i_dle_backend.member.service.UserApiService;
import com.idle.kb_i_dle_backend.member.service.UserInfoService;
import com.idle.kb_i_dle_backend.member.util.JwtProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;
    private final MemberService memberService;
    private final UserInfoService userInfoService;
    private final UserApiService userApiService;


    // 생성자에서 UserInfoService를 추가로 주입
    public MemberController(AuthenticationManager authenticationManager, JwtProcessor jwtProcessor, MemberService memberService, UserInfoService userInfoService, UserApiRepository userApiRepository, UserApiService userApiService) {
        this.authenticationManager = authenticationManager;
        this.jwtProcessor = jwtProcessor;
        this.memberService = memberService;
        this.userInfoService = userInfoService;
        this.userApiService = userApiService;
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
    @GetMapping("/info/{nickname}")
    public ResponseEntity<?> getMemberInfoByNickname(@PathVariable String nickname, HttpServletRequest request) {
        // 사용자 정보 가져오기
        UserInfoDTO userInfoDTO = userInfoService.getUserInfoByNickname(nickname);

        // 사용자 정보가 없으면 404 반환
        if (userInfoDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 없습니다.");
        }

        // uid를 사용하여 UserApiEntity 가져오기 (단일 객체로 처리)
        UserApiEntity userApiEntity = userApiService.getUserApiByUid(userInfoDTO.getUid());

        // userApiEntity가 null인 경우 처리
        if (userApiEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("API 데이터가 없습니다.");
        }

        // JWT에서 현재 사용자의 닉네임 가져오기
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String tokenNickname = jwtProcessor.getNickname(token);

        // 본인 여부 판단
        if (tokenNickname.equals(nickname)) {
            // 본인일 경우 모든 정보 반환
            Map<String, Object> stockInfo = new HashMap<>();
            stockInfo.put("base", userApiEntity.getApi_stock());
            stockInfo.put("token", userApiEntity.getApi_stock_token());
            stockInfo.put("secret", userApiEntity.getApi_stock_secret());
            stockInfo.put("app", userApiEntity.getApi_stock_app());

            Map<String, Object> coinInfo = new HashMap<>();
            coinInfo.put("base", userApiEntity.getApi_coin());
            coinInfo.put("secret", userApiEntity.getApi_coin_secret());
            coinInfo.put("app", userApiEntity.getApi_coin_app());

            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("bank", userApiEntity.getApi_bank());
            apiInfo.put("stock", stockInfo);
            apiInfo.put("coin", coinInfo);

            Map<String, Object> data = new HashMap<>();
            data.put("nickname", userInfoDTO.getNickname());
            data.put("email", userInfoDTO.getEmail());
            data.put("img", userInfoDTO.getImg());
            data.put("birthYear", userInfoDTO.getBirthYear());
            data.put("gender", userInfoDTO.getGender());
            data.put("certification", userInfoDTO.getCertification());
            data.put("api", apiInfo);

            Map<String, Object> response = new HashMap<>();
            response.put("data", data);

            SuccessResponseDTO successResponse = new SuccessResponseDTO("true", response);
            return ResponseEntity.ok(successResponse);
        } else {
            // 본인이 아닌 경우 제한된 정보만 반환
            Map<String, Object> limitedData = new HashMap<>();
            limitedData.put("nickname", userInfoDTO.getNickname());
            limitedData.put("img", userInfoDTO.getImg());

            Map<String, Object> response = new HashMap<>();
            response.put("data", limitedData);

            SuccessResponseDTO successResponse = new SuccessResponseDTO("true", response);
            return ResponseEntity.ok(successResponse);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateMemberInfo(@RequestBody UserInfoDTO updatedUserInfo, HttpServletRequest request) {
        // JWT에서 현재 사용자의 닉네임 가져오기
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String tokenNickname = jwtProcessor.getNickname(token);

        // 본인 여부 판단
        if (!tokenNickname.equals(updatedUserInfo.getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자신의 정보만 수정할 수 있습니다.");
        }

        // 사용자 정보 업데이트 로직
        try {
            UserInfoDTO updatedInfo = userInfoService.updateUserInfo(updatedUserInfo);

            // 성공 응답 반환
            Map<String, Object> data = new HashMap<>();
            data.put("nickname", updatedInfo.getNickname());
            data.put("email", updatedInfo.getEmail());
            data.put("img", updatedInfo.getImg());
            data.put("birthYear", updatedInfo.getBirthYear());
            data.put("gender", updatedInfo.getGender());

            Map<String, Object> response = new HashMap<>();
            response.put("data", data);

            SuccessResponseDTO successResponse = new SuccessResponseDTO("true", response);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 수정 중 오류가 발생했습니다.");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable String id) {
        // 서비스 로직을 통해 회원 삭제 처리
        boolean isDeleted = memberService.deleteMemberById(id);

        if (isDeleted) {
            // 삭제 성공 시 응답 데이터 구성
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);

            Map<String, Object> response = new HashMap<>();
            response.put("data", data);

            SuccessResponseDTO successResponse = new SuccessResponseDTO("true", response);
            return ResponseEntity.ok(successResponse);
        } else {
            return ResponseEntity.status(404).body("회원 정보를 찾을 수 없습니다.");
        }
    }
}

