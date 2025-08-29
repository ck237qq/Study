package com.example.study.dto.res.airbnb.carRent;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationRes {

    private Integer locationId;

    private String name;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String businessHours;

}
