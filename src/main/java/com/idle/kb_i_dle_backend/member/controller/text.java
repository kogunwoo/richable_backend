//@GetMapping("/naverCallback")
//public ResponseEntity<?> naverCallback(@RequestParam(required = false) String code,
//                                       @RequestParam(required = false) String state) {
//    log.info("Received callback - code: {}, state: {}", code, state);
//
//    if (code == null || state == null) {
//        log.error("Missing code or state parameter");
//        return ResponseEntity.badRequest().body("Missing required parameters");
//    }
//
//    try {
//        // 최종 인증 값인 접근 토큰을 발급
//        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
//                + "&client_id=" + clientId
//                + "&client_secret=" + clientSecret
//                + "&code=" + code
//                + "&state=" + state;
//
//        String authtoken = getHttpResponseWithRetry(tokenUrl);
//        log.info("Token Response: {}", authtoken);
//
//        if (authtoken == null || authtoken.isEmpty()) {
//            log.error("Failed to obtain auth token");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to obtain auth token");
//        }
//
//        // JSON 파싱
//        JSONObject tokenJson = new JSONObject(authtoken);
//        String accessToken = tokenJson.getString("access_token");
//
//        // 사용자 프로필 정보 조회
//        JSONObject userProfile = getUserProfile(accessToken);
//        log.info("User Profile: {}", userProfile.toString());
//
//        // 기존 로직 계속...
//
//    } catch (Exception e) {
//        log.error("Error in naverCallback", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during processing");
//    }
//}
//
//private String getHttpResponseWithRetry(String urlString) throws Exception {
//    int maxRetries = 3;
//    int retryDelayMs = 1000;
//
//    for (int i = 0; i < maxRetries; i++) {
//        try {
//            return getHttpResponse(urlString);
//        } catch (Exception e) {
//            log.warn("Attempt {} failed: {}", i + 1, e.getMessage());
//            if (i == maxRetries - 1) throw e;
//            Thread.sleep(retryDelayMs);
//        }
//    }
//    return null;
//}