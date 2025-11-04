package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.hexagram.EditHexagramReq;
import com.example.study.dto.res.hexagram.HexagramKindRes;
import com.example.study.dto.res.hexagram.HexagramNameRes;
import com.example.study.dto.res.hexagram.HexagramRes;
import com.example.study.dto.res.hexagram.YaoxiangRes;

import java.util.List;

public interface HexagramService {

    EventMessage<String> editYaoxiangByhexagramId(EditHexagramReq editHexagramReq);

    EventMessage<List<YaoxiangRes>> findYaoxiang();

    EventMessage<List<YaoxiangRes>> findYaoxiangByhexagramId(Long hexagram);

    EventMessage<List<HexagramNameRes>> findHexagramName();

    EventMessage<List<HexagramRes>> findHexagramDto();

    EventMessage<List<HexagramKindRes>> findHexagramKindDto();

    EventMessage<String> initHexagramKind();
}
