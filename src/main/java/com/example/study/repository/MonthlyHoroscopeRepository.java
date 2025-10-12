package com.example.study.repository;

import com.example.study.entity.MonthlyHoroscope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MonthlyHoroscopeRepository extends JpaRepository<MonthlyHoroscope, Long> {

    List<MonthlyHoroscope> findByCustomerInfoIdAndYearAndMonth(Long customerInfoId, Long year,Long month);

    List<MonthlyHoroscope> findByCustomerInfoIdAndYear(Long customerInfoId, Long year);

}
