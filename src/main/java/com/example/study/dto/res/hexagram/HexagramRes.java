package com.example.study.dto.res.hexagram;

import lombok.Data;

/**
 * 卦象
 */
@Data
public class HexagramRes {

    private Long hexagramId;

    private Long hexagramNo;

    private String hexagramName;

    private Long topHexagramKindId;

    private Long upHexagramKindId;

    public  HexagramRes() {

    }

    public HexagramRes(String hexagramNameParm, Long upHexagramKindParmId, Long topHexagramKindParmId,
                       Long hexagramNoParm) {
        hexagramName = hexagramNameParm;
        topHexagramKindId = topHexagramKindParmId;
        upHexagramKindId = upHexagramKindParmId;
        hexagramNo = hexagramNoParm;
    }

}
