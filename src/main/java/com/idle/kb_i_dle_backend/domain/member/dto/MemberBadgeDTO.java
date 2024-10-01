package com.idle.kb_i_dle_backend.domain.member.dto;

public class MemberBadgeDTO {
    private int badgeNo;
    private String name;
    private String img;
    private String desc;
    private boolean having;

    // 기본 생성자
    public MemberBadgeDTO() {}

    // getter & setter methods

    public int getBadgeNo() {
        return badgeNo;
    }

    public void setBadgeNo(int badgeNo) {
        this.badgeNo = badgeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isHaving() {
        return having;
    }

    public void setHaving(boolean having) {
        this.having = having;
    }
}
