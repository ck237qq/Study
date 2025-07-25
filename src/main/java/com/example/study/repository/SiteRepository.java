package com.example.study.repository;

import com.example.study.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

}
