package com.example.study.repository;

import com.example.study.entity.Yaoxiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface YaoxiangRepository extends JpaRepository<Yaoxiang, Long> {

    List<Yaoxiang> findByHexagramId(long hexagramId);

}
