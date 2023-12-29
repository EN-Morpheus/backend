package com.imaginecup.morpheus.repository;

import com.imaginecup.morpheus.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemberId(String memberId);
    boolean existsByMemberId(String memberId);

}
