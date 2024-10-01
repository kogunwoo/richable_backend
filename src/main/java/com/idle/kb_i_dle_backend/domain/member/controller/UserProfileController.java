//package com.idle.kb_i_dle_backend.domain.member.controller;
//
//import com.idle.kb_i_dle_backend.common.CommonConst;
//import com.idle.kb_i_dle_backend.common.FileUploadUtil;
//import com.idle.kb_i_dle_backend.domain.member.dto.ImageUploadDTO;
//import com.idle.kb_i_dle_backend.common.dto.SuccessResponseDTO;
//import com.idle.kb_i_dle_backend.domain.member.service.UserProfileService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/member")
//public class UserProfileController {
//
//    private final UserProfileService userProfileService;
//
//    public UserProfileController(UserProfileService userProfileService) {
//        this.userProfileService = userProfileService;
//    }
//
//    @PostMapping("/profile/upload")
//    public SuccessResponseDTO uploadProfileImage(@RequestBody ImageUploadDTO imageUploadDTO) {
//        try {
//            // imgPath에서 확장자 추출 (예: /image/asdf.jpg -> 확장자 jpg)
//            String imgPath = imageUploadDTO.getImgPath();
//            String imgType = imgPath.substring(imgPath.lastIndexOf(".") + 1);  // 확장자 추출
//
//            // 이미지 경로에서 파일명 추출 (/image/asdf.jpg -> asdf)
//            String fileNameWithoutExtension = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("."));
//
//            // 이미지를 저장하고 URL 반환 (이미지 경로는 저장 로직에 맞춰 사용)
//            String fileUrl = userProfileService.saveProfileImage(fileNameWithoutExtension, imgType);
//
//            // ID만 JSON 응답으로 반환
//            Map<String, Object> responseData = new HashMap<>();
//            responseData.put("id", userProfileService.getIdByFileUrl(fileUrl));  // fileUrl로부터 ID 추출
//
//            return new SuccessResponseDTO("true", responseData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new SuccessResponseDTO("false", null);
//        }
//    }
//
//    @PostMapping("/insert")
//    public ResponseEntity<HashMap<String, Object>> insert(MultipartFile file , ImageUploadDTO dto, HttpServletRequest req)
//    {
//
////        System.out.println(dto.getTitle());
////        System.out.println(dto.getWriter());
////        System.out.println(dto.getContents());
//
//        String uploadDir = CommonConst.FILEUPLOADPATH+ "/image" ;
//
//        //http://localhost:9090/image/1582531436.jpeg
//        if(file!=null)
//        {
//            try {
//                String filename= FileUploadUtil.upload(uploadDir, file);
//                //dto.setImgPath(filename);
//                dto.setImgPath(CommonConst.DOMAIN +"/"+ uploadDir + "/"+ filename);
//                //System.out.println(dto.getFilename());
//                System.out.println(dto.getImgPath());
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
////
////        boardService.board_insert(dto);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("result", "OK");
//        //map.put("boardDto", dto);
//        ResponseEntity<HashMap<String, Object>> entity = new ResponseEntity<>(map, HttpStatus.OK);
//        return entity;
//    }
//}
