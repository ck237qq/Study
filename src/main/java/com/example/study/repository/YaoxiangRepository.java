package com.example.study.repository;

import java.util.List;

import com.example.study.entity.Yaoxiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface YaoxiangRepository extends JpaRepository<Yaoxiang, Long> {

    List<Yaoxiang> findByHexagramId(long hexagramId);

}
