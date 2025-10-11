package com.example.study.repository;

import com.example.study.entity.Hexagram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HexagramRepository extends JpaRepository<Hexagram, Long> {

}
