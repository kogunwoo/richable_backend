//@GetMapping("/naverlogin")
//public ResponseEntity<?> naverlogin(HttpSession session) throws Exception {
//    // 상태 토큰으로 사용할 랜덤 문자열 생성
//    String state = generateState();
//    // 세션 또는 별도의 저장 공간에 상태 토큰을 저장
//    session.setAttribute("naverState", state);
//    log.info("naverState: " + state);
//
//    String naverAuthUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
//            + "&client_id=" + clientId
//            + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
//            + "&state=" + state;
//
//    // state를 응답에 포함
//    return ResponseEntity.ok(Map.of("redirectUrl", naverAuthUrl, "state", state));
//}
