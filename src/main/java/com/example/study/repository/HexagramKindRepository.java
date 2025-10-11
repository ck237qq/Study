package com.example.study.repository;

import com.example.study.entity.HexagramKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HexagramKindRepository extends JpaRepository<HexagramKind, Long> {

}
