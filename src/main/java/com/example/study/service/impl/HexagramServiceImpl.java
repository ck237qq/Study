package com.example.study.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.hexagram.EditHexagramReq;
import com.example.study.dto.req.hexagram.EditYaoxiangReq;
import com.example.study.dto.res.hexagram.HexagramKindRes;
import com.example.study.dto.res.hexagram.HexagramNameRes;
import com.example.study.dto.res.hexagram.HexagramRes;
import com.example.study.dto.res.hexagram.YaoxiangRes;
import com.example.study.entity.Hexagram;
import com.example.study.entity.HexagramKind;
import com.example.study.entity.Yaoxiang;
import com.example.study.repository.HexagramKindRepository;
import com.example.study.repository.HexagramRepository;
import com.example.study.repository.YaoxiangRepository;
import com.example.study.service.HexagramService;
import com.example.study.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HexagramServiceImpl implements HexagramService {

    private final HexagramKindRepository hexagramKindRepository;

    private final HexagramRepository hexagramRepository;

    private final YaoxiangRepository yaoxiangRepository;

    public EventMessage<String> editYaoxiangByhexagramId(EditHexagramReq editHexagramDto) {
        List<Yaoxiang> yaoxiangList = yaoxiangRepository.findByHexagramId(editHexagramDto.getHexagramId());
        Map<Long, Yaoxiang> yaoxiangMap = new TreeMap<>();
        for (Yaoxiang yaoxiang : yaoxiangList) {
            yaoxiangMap.put(yaoxiang.getYaoxiangId(), yaoxiang);
        }


        List<EditYaoxiangReq> editYaoxiangReqs = editHexagramDto.getEditYaoxiangReqs();
        List<Yaoxiang> editYaoxiangList = new ArrayList<>();
        if (!editYaoxiangReqs.isEmpty()) {
            for (EditYaoxiangReq editYaoxiangReq : editYaoxiangReqs) {
                if (yaoxiangMap.containsKey(editYaoxiangReq.getYaoxiangId())) {
                    Yaoxiang yaoxiang = yaoxiangMap.get(editYaoxiangReq.getYaoxiangId());
                    BeanUtils.copyProperties(editYaoxiangReq, yaoxiang);
                    editYaoxiangList.add(yaoxiang);
                } else {
                    String code = ResultCode.ERR_2001.getCode();
                    String msg = ResultCode.ERR_2001.getDesc();
                    return CommonUtils.setExceptionEventMessage(code, msg, null);
                }
            }
        }

        yaoxiangRepository.saveAll(editYaoxiangList);
        return CommonUtils.setDefaultEventMessage("更新成功");
    }

    public EventMessage<List<YaoxiangRes>> findYaoxiangByhexagramId(Long hexagramId) {

        List<Yaoxiang> YaoxiangList = yaoxiangRepository.findByHexagramId(hexagramId);
        List<YaoxiangRes> yaoxiangResList = new ArrayList<>();
        for (Yaoxiang Yaoxiang : YaoxiangList) {
            YaoxiangRes yaoxiangRes = new YaoxiangRes();
            BeanUtils.copyProperties(Yaoxiang, yaoxiangRes);
            yaoxiangResList.add(yaoxiangRes);
        }
        return CommonUtils.setDefaultEventMessage(yaoxiangResList);
    }

    public EventMessage<List<HexagramNameRes>> findHexagramName() {
        List<Hexagram> hexagrams = hexagramRepository.findAll();
        List<HexagramNameRes> hexagramNameResList = new ArrayList<>();
        for (Hexagram hexagram : hexagrams) {
            HexagramNameRes hexagramNameRes = new HexagramNameRes();
            BeanUtils.copyProperties(hexagram, hexagramNameRes);
            hexagramNameResList.add(hexagramNameRes);
        }
        return CommonUtils.setDefaultEventMessage(hexagramNameResList);
    }


    public EventMessage<List<HexagramRes>> findHexagramDto() {
        List<Hexagram> hexagrams = hexagramRepository.findAll();
        List<HexagramRes> hexagramResList = new ArrayList<>();
        for (Hexagram hexagram : hexagrams) {
            HexagramRes HexagramDto = new HexagramRes();
            BeanUtils.copyProperties(hexagram, HexagramDto);
            hexagramResList.add(HexagramDto);
        }

        return CommonUtils.setDefaultEventMessage(hexagramResList);
    }

    public EventMessage<List<HexagramKindRes>> findHexagramKindDto() {

        List<HexagramKind> hexagramKinds = hexagramKindRepository.findAll();
        List<HexagramKindRes> hexagramKindDtos = new ArrayList<>();
        for (HexagramKind hexagramKind : hexagramKinds) {
            HexagramKindRes hexagramKindRes = new HexagramKindRes();
            BeanUtils.copyProperties(hexagramKind, hexagramKindRes);
            hexagramKindDtos.add(hexagramKindRes);
        }

        return CommonUtils.setDefaultEventMessage(hexagramKindDtos);
    }

    private EventMessage<String> initLines(List<Hexagram> hexagrams) {
        Map<String, Hexagram> hexagramMap = new TreeMap<>();
        for (Hexagram hexagram : hexagrams) {
            hexagramMap.put(hexagram.getHexagramName(), hexagram);
        }

        if (yaoxiangRepository.findAll().isEmpty()) {
            List<Yaoxiang> addYaoxiangList = new ArrayList<>();

            addYaoxiangList.addAll(getLines("明夷", new ArrayList<>(Arrays.asList("謙", "泰", "復", "豐", "既濟", "賁")), hexagramMap));
            addYaoxiangList.addAll(getLines("離", new ArrayList<>(Arrays.asList("旅", "大有", "噬嗑", "賁", "同人", "豐")), hexagramMap));
            addYaoxiangList.addAll(getLines("升", new ArrayList<>(Arrays.asList("泰", "謙", "師", "恆", "井", "蠱")), hexagramMap));
            addYaoxiangList.addAll(getLines("震", new ArrayList<>(Arrays.asList("豫", "歸妹", "豐", "復", "隨", "噬嗑")), hexagramMap));
            addYaoxiangList.addAll(getLines("節", new ArrayList<>(Arrays.asList("坎", "屯", "需", "兌", "臨", "中孚")), hexagramMap));
            addYaoxiangList.addAll(getLines("大畜", new ArrayList<>(Arrays.asList("蠱", "賁", "損", "大有", "小畜", "泰")), hexagramMap));
            addYaoxiangList.addAll(getLines("无妄", new ArrayList<>(Arrays.asList("否", "履", "同人", "益", "噬嗑", "隨")), hexagramMap));
            addYaoxiangList.addAll(getLines("復", new ArrayList<>(Arrays.asList("坤", "臨", "明夷", "震", "屯", "頤")), hexagramMap));
            addYaoxiangList.addAll(getLines("比", new ArrayList<>(Arrays.asList("屯", "坎", "蹇", "萃", "坤", "觀")), hexagramMap));
            addYaoxiangList.addAll(getLines("巽", new ArrayList<>(Arrays.asList("小畜", "漸", "渙", "姤", "蠱", "井")), hexagramMap));
            addYaoxiangList.addAll(getLines("遯", new ArrayList<>(Arrays.asList("同人", "姤", "否", "漸", "旅", "咸")), hexagramMap));
            addYaoxiangList.addAll(getLines("泰", new ArrayList<>(Arrays.asList("升", "明夷", "臨", "大壯", "需", "大畜")), hexagramMap));
            addYaoxiangList.addAll(getLines("隨", new ArrayList<>(Arrays.asList("萃", "兌", "革", "屯", "震", "无妄")), hexagramMap));
            addYaoxiangList.addAll(getLines("困", new ArrayList<>(Arrays.asList("兌", "萃", "大過", "坎", "解", "訟")), hexagramMap));
            addYaoxiangList.addAll(getLines("大壯", new ArrayList<>(Arrays.asList("恆", "豐", "歸妹", "泰", "夬", "大有")), hexagramMap));
            addYaoxiangList.addAll(getLines("咸", new ArrayList<>(Arrays.asList("革", "大過", "萃", "蹇", "小過", "遯")), hexagramMap));
            addYaoxiangList.addAll(getLines("萃", new ArrayList<>(Arrays.asList("隨", "困", "咸", "比", "豫", "否")), hexagramMap));
            addYaoxiangList.addAll(getLines("剝", new ArrayList<>(Arrays.asList("頤", "蒙", "艮", "晉", "觀", "坤")), hexagramMap));
            addYaoxiangList.addAll(getLines("蒙", new ArrayList<>(Arrays.asList("損", "剝", "蠱", "未濟", "渙", "師")), hexagramMap));
            addYaoxiangList.addAll(getLines("漸", new ArrayList<>(Arrays.asList("家人", "巽", "觀", "遯", "艮", "蹇")), hexagramMap));
            addYaoxiangList.addAll(getLines("蹇", new ArrayList<>(Arrays.asList("既濟", "井", "比", "咸", "謙", "漸")), hexagramMap));
            addYaoxiangList.addAll(getLines("坤", new ArrayList<>(Arrays.asList("復", "師", "謙", "豫", "比", "剝")), hexagramMap));
            addYaoxiangList.addAll(getLines("損", new ArrayList<>(Arrays.asList("蒙", "頤", "大畜", "睽", "中孚", "臨")), hexagramMap));
            addYaoxiangList.addAll(getLines("需", new ArrayList<>(Arrays.asList("井", "既濟", "節", "夬", "泰", "小畜")), hexagramMap));
            addYaoxiangList.addAll(getLines("訟", new ArrayList<>(Arrays.asList("履", "否", "姤", "渙", "未濟", "困")), hexagramMap));
            addYaoxiangList.addAll(getLines("頤", new ArrayList<>(Arrays.asList("剝", "損", "賁", "噬嗑", "益", "復")), hexagramMap));
            addYaoxiangList.addAll(getLines("渙", new ArrayList<>(Arrays.asList("中孚", "觀", "巽", "訟", "蒙", "坎")), hexagramMap));
            addYaoxiangList.addAll(getLines("革", new ArrayList<>(Arrays.asList("咸", "夬", "隨", "既濟", "豐", "同人")), hexagramMap));
            addYaoxiangList.addAll(getLines("師", new ArrayList<>(Arrays.asList("臨", "坤", "升", "解", "坎", "蒙")), hexagramMap));
            addYaoxiangList.addAll(getLines("履", new ArrayList<>(Arrays.asList("訟", "无妄", "乾", "中孚", "睽", "兌")), hexagramMap));
            addYaoxiangList.addAll(getLines("鼎", new ArrayList<>(Arrays.asList("大有", "旅", "未濟", "蠱", "姤", "恆")), hexagramMap));
            addYaoxiangList.addAll(getLines("中孚", new ArrayList<>(Arrays.asList("渙", "益", "小畜", "履", "損", "節")), hexagramMap));
            addYaoxiangList.addAll(getLines("夬", new ArrayList<>(Arrays.asList("大過", "革", "兌", "需", "大壯", "乾")), hexagramMap));
            addYaoxiangList.addAll(getLines("賁", new ArrayList<>(Arrays.asList("艮", "大畜", "頤", "離", "家人", "明夷")), hexagramMap));
            addYaoxiangList.addAll(getLines("既濟", new ArrayList<>(Arrays.asList("蹇", "需", "屯", "革", "明夷", "家人")), hexagramMap));
            addYaoxiangList.addAll(getLines("大有", new ArrayList<>(Arrays.asList("鼎", "離", "睽", "大畜", "乾", "大壯")), hexagramMap));
            addYaoxiangList.addAll(getLines("艮", new ArrayList<>(Arrays.asList("賁", "蠱", "剝", "旅", "漸", "謙")), hexagramMap));
            addYaoxiangList.addAll(getLines("同人", new ArrayList<>(Arrays.asList("遯", "乾", "无妄", "家人", "離", "革")), hexagramMap));
            addYaoxiangList.addAll(getLines("未濟", new ArrayList<>(Arrays.asList("睽", "晉", "鼎", "蒙", "訟", "解")), hexagramMap));
            addYaoxiangList.addAll(getLines("噬嗑", new ArrayList<>(Arrays.asList("晉", "睽", "離", "頤", "无妄", "震")), hexagramMap));
            addYaoxiangList.addAll(getLines("豐", new ArrayList<>(Arrays.asList("小過", "大壯", "震", "明夷", "革", "離")), hexagramMap));
            addYaoxiangList.addAll(getLines("解", new ArrayList<>(Arrays.asList("歸妹", "豫", "恆", "師", "困", "未濟")), hexagramMap));
            addYaoxiangList.addAll(getLines("家人", new ArrayList<>(Arrays.asList("漸", "小畜", "益", "同人", "賁", "既濟")), hexagramMap));
            addYaoxiangList.addAll(getLines("蠱", new ArrayList<>(Arrays.asList("大畜", "艮", "蒙", "鼎", "巽", "升")), hexagramMap));
            addYaoxiangList.addAll(getLines("臨", new ArrayList<>(Arrays.asList("師", "復", "泰", "歸妹", "節", "損")), hexagramMap));
            addYaoxiangList.addAll(getLines("益", new ArrayList<>(Arrays.asList("觀", "中孚", "家人", "无妄", "頤", "屯")), hexagramMap));
            addYaoxiangList.addAll(getLines("睽", new ArrayList<>(Arrays.asList("未濟", "噬嗑", "大有", "損", "履", "歸妹")), hexagramMap));
            addYaoxiangList.addAll(getLines("否", new ArrayList<>(Arrays.asList("无妄", "訟", "遯", "觀", "晉", "萃")), hexagramMap));
            addYaoxiangList.addAll(getLines("姤", new ArrayList<>(Arrays.asList("乾", "遯", "訟", "巽", "鼎", "大過")), hexagramMap));
            addYaoxiangList.addAll(getLines("豫", new ArrayList<>(Arrays.asList("震", "解", "小過", "坤", "萃", "晉")), hexagramMap));
            addYaoxiangList.addAll(getLines("歸妹", new ArrayList<>(Arrays.asList("解", "震", "大壯", "臨", "兌", "睽")), hexagramMap));
            addYaoxiangList.addAll(getLines("旅", new ArrayList<>(Arrays.asList("離", "鼎", "晉", "艮", "遯", "小過")), hexagramMap));
            addYaoxiangList.addAll(getLines("乾", new ArrayList<>(Arrays.asList("姤", "同人", "履", "小畜", "大有", "夬")), hexagramMap));
            addYaoxiangList.addAll(getLines("屯", new ArrayList<>(Arrays.asList("比", "節", "既濟", "隨", "復", "益")), hexagramMap));
            addYaoxiangList.addAll(getLines("小過", new ArrayList<>(Arrays.asList("豐", "恆", "豫", "謙", "咸", "旅")), hexagramMap));
            addYaoxiangList.addAll(getLines("井", new ArrayList<>(Arrays.asList("需", "蹇", "坎", "大過", "升", "巽")), hexagramMap));
            addYaoxiangList.addAll(getLines("晉", new ArrayList<>(Arrays.asList("噬嗑", "未濟", "旅", "剝", "否", "豫")), hexagramMap));
            addYaoxiangList.addAll(getLines("恆", new ArrayList<>(Arrays.asList("大壯", "小過", "解", "升", "大過", "鼎")), hexagramMap));
            addYaoxiangList.addAll(getLines("坎", new ArrayList<>(Arrays.asList("節", "比", "井", "困", "師", "渙")), hexagramMap));
            addYaoxiangList.addAll(getLines("大過", new ArrayList<>(Arrays.asList("夬", "咸", "困", "井", "恆", "姤")), hexagramMap));
            addYaoxiangList.addAll(getLines("兌", new ArrayList<>(Arrays.asList("困", "隨", "夬", "節", "歸妹", "履")), hexagramMap));
            addYaoxiangList.addAll(getLines("謙", new ArrayList<>(Arrays.asList("明夷", "升", "坤", "小過", "蹇", "艮")), hexagramMap));
            addYaoxiangList.addAll(getLines("小畜", new ArrayList<>(Arrays.asList("巽", "家人", "中孚", "乾", "大畜", "需")), hexagramMap));
            addYaoxiangList.addAll(getLines("觀", new ArrayList<>(Arrays.asList("益", "渙", "漸", "否", "剝", "比")), hexagramMap));

            yaoxiangRepository.saveAll(addYaoxiangList);
        }

        return CommonUtils.setDefaultEventMessage("完成更新");
    }

    private List<Yaoxiang> getLines(String main, List<String> YaoxiangNameList, Map<String, Hexagram> hexagramMap) {
        long hexagramId = hexagramMap.get(main).getHexagramId();

        List<Yaoxiang> YaoxiangList = new ArrayList<>();
        for (int i = 0; i < YaoxiangNameList.size(); i++) {
            String YaoxiangName = YaoxiangNameList.get(i);
            if (hexagramMap.containsKey(YaoxiangName)) {
                Yaoxiang Yaoxiang = new Yaoxiang();
                Yaoxiang.setHexagramId(hexagramId);
                Yaoxiang.setHexagramYaoxiangId(hexagramMap.get(YaoxiangName).getHexagramId());
                Yaoxiang.setIndex(i);
                YaoxiangList.add(Yaoxiang);
            } else {
                System.out.println(YaoxiangName);
            }
        }
        return YaoxiangList;
    }

    private EventMessage<String> initHexagram(List<HexagramKind> hexagramKinds) {
        Map<Long, HexagramKind> hexagramKindMap = new TreeMap<>();
        for (HexagramKind hexagramKind : hexagramKinds) {
            hexagramKindMap.put(hexagramKind.getHexagramKindNo(), hexagramKind);
        }

        List<Hexagram> hexagrams = hexagramRepository.findAll();

        if (hexagrams.isEmpty()) {
            List<HexagramRes> hexagramResList = new ArrayList<>();

            long i = 1L;
            hexagramResList.add(new HexagramRes("乾", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 1L));
            hexagramResList.add(new HexagramRes("夬", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 43L));
            hexagramResList.add(new HexagramRes("大有", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 13L));
            hexagramResList.add(new HexagramRes("大壯", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 34L));
            hexagramResList.add(new HexagramRes("小畜", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 9L));
            hexagramResList.add(new HexagramRes("需", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 5L));
            hexagramResList.add(new HexagramRes("大畜", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 26L));
            hexagramResList.add(new HexagramRes("泰", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 11L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("履", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 10L));
            hexagramResList.add(new HexagramRes("兌", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 58L));
            hexagramResList.add(new HexagramRes("睽", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 38L));
            hexagramResList.add(new HexagramRes("歸妹", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 54L));
            hexagramResList.add(new HexagramRes("中孚", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 61L));
            hexagramResList.add(new HexagramRes("節", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 60L));
            hexagramResList.add(new HexagramRes("損", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 41L));
            hexagramResList.add(new HexagramRes("臨", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 19L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("同人", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 13L));
            hexagramResList.add(new HexagramRes("革", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 49L));
            hexagramResList.add(new HexagramRes("離", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 30L));
            hexagramResList.add(new HexagramRes("豐", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 55L));
            hexagramResList.add(new HexagramRes("家人", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 37L));
            hexagramResList.add(new HexagramRes("既濟", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 63L));
            hexagramResList.add(new HexagramRes("賁", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 22L));
            hexagramResList.add(new HexagramRes("明夷", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 36L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("无妄", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 25L));
            hexagramResList.add(new HexagramRes("隨", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 17L));
            hexagramResList.add(new HexagramRes("噬嗑", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 21L));
            hexagramResList.add(new HexagramRes("震", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 51L));
            hexagramResList.add(new HexagramRes("益", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 42L));
            hexagramResList.add(new HexagramRes("屯", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 3L));
            hexagramResList.add(new HexagramRes("頤", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 27L));
            hexagramResList.add(new HexagramRes("復", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 24L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("姤", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 44L));
            hexagramResList.add(new HexagramRes("大過", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 28L));
            hexagramResList.add(new HexagramRes("鼎", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 50L));
            hexagramResList.add(new HexagramRes("恆", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 32L));
            hexagramResList.add(new HexagramRes("巽", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 57L));
            hexagramResList.add(new HexagramRes("井", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 48L));
            hexagramResList.add(new HexagramRes("蠱", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 18L));
            hexagramResList.add(new HexagramRes("升", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 46L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("訟", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 6L));
            hexagramResList.add(new HexagramRes("困", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 47L));
            hexagramResList.add(new HexagramRes("未濟", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 64L));
            hexagramResList.add(new HexagramRes("解", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 40L));
            hexagramResList.add(new HexagramRes("渙", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 59L));
            hexagramResList.add(new HexagramRes("坎", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 29L));
            hexagramResList.add(new HexagramRes("蒙", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 4L));
            hexagramResList.add(new HexagramRes("師", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 7L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("遯", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 33L));
            hexagramResList.add(new HexagramRes("咸", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 31L));
            hexagramResList.add(new HexagramRes("旅", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 56L));
            hexagramResList.add(new HexagramRes("小過", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 62L));
            hexagramResList.add(new HexagramRes("漸", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 53L));
            hexagramResList.add(new HexagramRes("蹇", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 39L));
            hexagramResList.add(new HexagramRes("艮", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 52L));
            hexagramResList.add(new HexagramRes("謙", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 15L));

            i = i + 1;
            hexagramResList.add(new HexagramRes("否", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(1L).getHexagramKindId(), 12L));
            hexagramResList.add(new HexagramRes("萃", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(2L).getHexagramKindId(), 45L));
            hexagramResList.add(new HexagramRes("晉", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(3L).getHexagramKindId(), 35L));
            hexagramResList.add(new HexagramRes("豫", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(4L).getHexagramKindId(), 16L));
            hexagramResList.add(new HexagramRes("觀", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(5L).getHexagramKindId(), 20L));
            hexagramResList.add(new HexagramRes("比", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(6L).getHexagramKindId(), 8L));
            hexagramResList.add(new HexagramRes("剝", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(7L).getHexagramKindId(), 23L));
            hexagramResList.add(new HexagramRes("坤", hexagramKindMap.get(i).getHexagramKindId(),
                    hexagramKindMap.get(8L).getHexagramKindId(), 2L));

            List<Hexagram> addHexagrams = new ArrayList<>();
            for (HexagramRes HexagramRes : hexagramResList) {
                Hexagram hexagram = new Hexagram();
                BeanUtils.copyProperties(HexagramRes, hexagram);
                addHexagrams.add(hexagram);
            }

            hexagramRepository.saveAll(addHexagrams);
            hexagrams = addHexagrams;
        }

        return initLines(hexagrams);
    }

    public EventMessage<String> initHexagramKind() {

        List<HexagramKind> hexagramKinds = hexagramKindRepository.findAll();

        if (hexagramKinds.isEmpty()) {
            Map<Long, HexagramKindRes> hexagramKindDtoMap = new TreeMap<>();
            hexagramKindDtoMap.put(1L, new HexagramKindRes(1L, "天"));
            hexagramKindDtoMap.put(2L, new HexagramKindRes(2L, "澤"));
            hexagramKindDtoMap.put(3L, new HexagramKindRes(3L, "火"));
            hexagramKindDtoMap.put(4L, new HexagramKindRes(4L, "雷"));
            hexagramKindDtoMap.put(5L, new HexagramKindRes(5L, "風"));
            hexagramKindDtoMap.put(6L, new HexagramKindRes(6L, "水"));
            hexagramKindDtoMap.put(7L, new HexagramKindRes(7L, "山"));
            hexagramKindDtoMap.put(8L, new HexagramKindRes(8L, "地"));

            List<HexagramKind> addHexagramKinds = new ArrayList<>();
            for (HexagramKindRes hexagramKindDto : hexagramKindDtoMap.values()) {
                HexagramKind hexagramKind = new HexagramKind();
                BeanUtils.copyProperties(hexagramKindDto, hexagramKind);
                addHexagramKinds.add(hexagramKind);
            }
            hexagramKindRepository.saveAll(addHexagramKinds);
            hexagramKinds = addHexagramKinds;
        }
        return initHexagram(hexagramKinds);
    }

}
