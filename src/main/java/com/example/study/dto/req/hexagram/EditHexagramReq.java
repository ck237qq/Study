package com.example.study.dto.req.hexagram;

import lombok.Data;

import java.util.List;

@Data
public class EditHexagramReq {

    private Long hexagramId;

    private List<EditYaoxiangReq> editYaoxiangDtos;

}