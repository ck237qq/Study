package com.example.study.repository.airbnb;

import com.example.study.entity.airbnb.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
