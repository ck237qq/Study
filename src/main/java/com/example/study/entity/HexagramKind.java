package com.example.study.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 卦象種類
 */
@Data
@Entity
public class HexagramKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hexagramKindId;

    private Long hexagramKindNo;

    private String hexagramKindName;

}
