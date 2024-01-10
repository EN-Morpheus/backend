package com.imaginecup.morpheus.fairy.dao;

import com.imaginecup.morpheus.fairy.domain.Fairy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FairyRepository extends JpaRepository<Fairy, Long> {
}
