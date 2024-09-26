package com.idle.kb_i_dle_backend.member.dto;

public class ImageUploadDTO {
    private String imgType;
    private String imgPath;

    // 기본 생성자
    public ImageUploadDTO() {}

    // getter와 setter
    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
