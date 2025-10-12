package com.example.study.repository;

import com.example.study.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

}
