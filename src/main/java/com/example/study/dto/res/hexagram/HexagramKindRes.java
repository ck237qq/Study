package com.example.study.dto.res.hexagram;

import lombok.Data;

@Data
public class HexagramKindRes {

    private Long hexagramKindId;

    private Long hexagramKindNo;

    private String hexagramKindName;

    public HexagramKindRes(){
    }

    public HexagramKindRes(Long hexagramKindNoParm,String hexagramKindNameParm){
        hexagramKindNo = hexagramKindNoParm;
        hexagramKindName = hexagramKindNameParm;
    }

}
