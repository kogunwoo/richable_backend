//package com.idle.kb_i_dle_backend.domain.member.controller;
//
//import com.idle.kb_i_dle_backend.domain.member.dto.ApiDataDTO;
//import com.idle.kb_i_dle_backend.domain.member.dto.ResponseDataDTO;
//import com.idle.kb_i_dle_backend.domain.member.dto.TokenRequestDTO;
//import com.idle.kb_i_dle_backend.domain.member.dto.TokenResponseDTO;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/member")
//public class TokenController {
//
//    @PostMapping("/token")
//    public TokenResponseDTO getTokenCertification(@RequestBody TokenRequestDTO tokenRequestDTO) {
//        // 토큰 인증 로직을 수행하고, 인증 결과를 response로 감싼다.
//
//        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
//        tokenResponseDTO.setSuccess("true");
//
//        // ResponseData 설정
//        ResponseDataDTO responseDataDTO = new ResponseDataDTO();
//        responseDataDTO.setCertification(true);  // 인증 여부 설정
//
//        // API 데이터 설정
//        ApiDataDTO apiDataDTO = new ApiDataDTO();
//        apiDataDTO.setBank(tokenRequestDTO.getApi().getAccount());
//        apiDataDTO.setStock(tokenRequestDTO.getApi().getStock());
//        apiDataDTO.setCoin(tokenRequestDTO.getApi().getCoin());
//
//        // Response에 API 데이터 설정
//        responseDataDTO.setApi(apiDataDTO);
//        tokenResponseDTO.setResponse(responseDataDTO);
//
//        return tokenResponseDTO;
//    }
//}
