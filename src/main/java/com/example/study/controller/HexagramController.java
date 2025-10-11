package com.example.study.controller;


import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.hexagram.EditHexagramReq;
import com.example.study.dto.res.hexagram.HexagramKindRes;
import com.example.study.dto.res.hexagram.HexagramRes;
import com.example.study.dto.res.hexagram.HexagramNameRes;
import com.example.study.dto.res.hexagram.YaoxiangRes;
import com.example.study.service.HexagramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/site")
@Tag(name = "卦象管理")
public class HexagramController {

    private final HexagramService hexagramService;


    @Operation(summary = "修改爻象", description = "修改爻象")
    @RequestMapping(value = "/editYaoxiangByhexagramId", method = RequestMethod.PUT)
    public EventMessage<String> editYaoxiangByhexagramId(@RequestBody EditHexagramReq editHexagramReq) {
        return hexagramService.editYaoxiangByhexagramId(editHexagramReq);
    }

    @Operation(summary = "查詢爻象", description = "查詢爻象")
    @RequestMapping(value = "/findYaoxiangByhexagramId", method = RequestMethod.GET)
    public EventMessage<List<YaoxiangRes>> findYaoxiangByhexagramId(Long hexagramId) {
        return hexagramService.findYaoxiangByhexagramId(hexagramId);
    }

    @Operation(summary = "查詢卦象名稱", description = "查詢卦象名稱")
    @RequestMapping(value = "/findHexagramName", method = RequestMethod.GET)
    public EventMessage<List<HexagramNameRes>> findHexagramName() {
        return hexagramService.findHexagramName();
    }

    @Operation(summary = "查詢全部卦象", description = "查詢全部卦象")
    @RequestMapping(value = "/findHexagramDto", method = RequestMethod.GET)
    public EventMessage<List<HexagramRes>> findHexagramDto() {
        return hexagramService.findHexagramDto();
    }

    @Operation(summary = "查詢全部卦象種類", description = "查詢全部卦象種類")
    @RequestMapping(value = "/findHexagramKindDto", method = RequestMethod.GET)
    public EventMessage<List<HexagramKindRes>> findHexagramKindDto() {
        return hexagramService.findHexagramKindDto();
    }


    @Operation(summary = "初始化卦象表", description = "初始化卦象表")
    @RequestMapping(value = "/initHexagram", method = RequestMethod.GET)
    public EventMessage<String> initHexagram() {
        return hexagramService.initHexagramKind();
    }
}
