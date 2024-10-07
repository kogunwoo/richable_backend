package com.idle.kb_i_dle_backend.domain.member.controller;


import com.idle.kb_i_dle_backend.domain.member.dto.LoginDTO;
import com.idle.kb_i_dle_backend.domain.member.dto.MemberJoinDTO;
import com.idle.kb_i_dle_backend.domain.member.service.MemberApiService;
import com.idle.kb_i_dle_backend.domain.member.service.MemberInfoService;
import com.idle.kb_i_dle_backend.domain.member.service.MemberService;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@PropertySource({"classpath:/application.properties"})
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> loginResult = memberService.login(loginDTO);
        return ResponseEntity.ok(new SuccessResponseDTO(true, loginResult));
    }

    @GetMapping("/naverlogin")
    public ResponseEntity<?> naverlogin(HttpServletRequest request) throws Exception {
        Map<String, Object> naverLoginResult = memberService.initiateNaverLogin(request);
        return ResponseEntity.ok(naverLoginResult);
    }

    @GetMapping("/naverCallback")
    public ResponseEntity<?> naverCallback(@RequestParam(required = false) String code,
                                           @RequestParam(required = false) String state,
                                           HttpServletRequest request) throws Exception {
        Map<String, Object> callbackResult = memberService.processNaverCallback(code, state, request);
        return ResponseEntity.ok(callbackResult);
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody MemberJoinDTO signupDTO) {
        String result = memberService.registerMember(signupDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/checkDupl/{id}")
    public ResponseEntity<?> checkDuplicateUsername(@PathVariable String id) {
        boolean result = memberService.checkDupl(id);
        SuccessResponseDTO successResponse = new SuccessResponseDTO(true, result);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/terms/{id}")
    public ResponseEntity<Map<String, Boolean>> updateUserAgreement(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> agreementData) {
        Map<String, Boolean> result = memberService.updateUserAgreement(id, agreementData);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/findid")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> payload) {
        Map<String, String> result = memberService.findIdByEmail(payload.get("email"));
        boolean isSuccess = !result.containsKey("error");
        SuccessResponseDTO successResponse = new SuccessResponseDTO(isSuccess, result);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/findid/auth")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> payload) {
        try {
            Map<String, Object> result = memberService.verifyCode(payload.get("email"), payload.get("code"));
            boolean isVerified = (boolean) result.get("verified");
            return ResponseEntity.ok(new SuccessResponseDTO(isVerified, result));
        } catch (Exception e) {
            log.error("Error in verifyCode endpoint: ", e);
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SuccessResponseDTO(false, errorResult));
        }
    }

    @PostMapping("/find/pw/auth")
    public ResponseEntity<?> findPw(@RequestBody Map<String, String> payload) {
        String result = memberService.findPwByEmail(payload.get("email"));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/find_pw_auth")
    public ResponseEntity<?> verifyCode2(@RequestBody Map<String, String> payload) {
        String result = String.valueOf(memberService.verifyCode(payload.get("email"), payload.get("code")));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/set/pw")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        boolean result = memberService.resetPassword(payload.get("id"), payload.get("newPassword"));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfoByNickname(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Map<String, Object> memberInfo = memberService.getMemberInfoByToken(token);
        return ResponseEntity.ok(new SuccessResponseDTO(true, memberInfo));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMemberInfo(@RequestBody Map<String, Object> updatedMemberInfo,
                                              HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Map<String, Object> result = memberService.updateMemberInfo(updatedMemberInfo, token);
        return ResponseEntity.ok(new SuccessResponseDTO(true, result));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable String id) {
        boolean result = memberService.deleteMemberById(id);
        return ResponseEntity.ok(new SuccessResponseDTO(true, result));
    }


}