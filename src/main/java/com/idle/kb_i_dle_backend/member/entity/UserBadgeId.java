package com.idle.kb_i_dle_backend.member.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserBadgeId implements Serializable {

    @Column(name = "uid")
    private int uid;

    @Column(name = "badge_no")
    private int badgeNo;

    // 기본 생성자
    public UserBadgeId() {}

    // 생성자
    public UserBadgeId(int uid, int badgeNo) {
        this.uid = uid;
        this.badgeNo = badgeNo;
    }

    // getter 메서드 추가
    public int getUid() {
        return uid;
    }

    public int getBadgeNo() {
        return badgeNo;
    }

    // equals()와 hashCode() 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBadgeId that = (UserBadgeId) o;

        if (uid != that.uid) return false;
        return badgeNo == that.badgeNo;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + badgeNo;
        return result;
    }
}
