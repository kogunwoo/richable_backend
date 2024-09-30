package com.idle.kb_i_dle_backend.member.repository;

import com.idle.kb_i_dle_backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUid(Integer uid);

    Member findById(String id);

    Member findByEmail(String email);

    boolean existsById(String id);

    boolean existsByEmail(String email);
}
