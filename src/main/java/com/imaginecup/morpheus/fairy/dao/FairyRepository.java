package com.imaginecup.morpheus.fairy.dao;

import com.imaginecup.morpheus.fairy.domain.Fairy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FairyRepository extends JpaRepository<Fairy, Long> {

    List<Fairy> findByMemberMemberId(String memberId);

}
