package com.example.study.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 卦象
 */
@Data
@Entity
public class Hexagram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hexagramId;

    private Long hexagramNo;

    private String hexagramName;

    private Long topHexagramKindId;

    private Long upHexagramKindId;

}
