package com.example.study.service.impl;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto._event.ResultCode;
import com.example.study.dto.req.site.AddSiteReq;
import com.example.study.dto.req.site.UpdateSiteReq;
import com.example.study.dto.res.site.SiteRes;
import com.example.study.entity.Site;
import com.example.study.repository.SiteRepository;
import com.example.study.service.SiteService;
import com.example.study.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {
    private final SiteRepository siteRepository;

    public EventMessage<String> updateSite(UpdateSiteReq updateSiteReq) {
        Optional<Site> siteOptional = siteRepository.findById(updateSiteReq.getSiteId());
        if (siteOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }
        Site site = siteOptional.get();
        BeanUtils.copyProperties(updateSiteReq, site);
        siteRepository.save(site);
        return CommonUtils.setDefaultEventMessage("success");
    }

    public EventMessage<SiteRes> getSiteById(Long siteId) {
        Optional<Site> siteOptional = siteRepository.findById(siteId);
        if (siteOptional.isEmpty()) {
            return CommonUtils.setExceptionEventMessage(ResultCode.ERR_2001.getCode(), ResultCode.ERR_2001.getDesc(), null);
        }
        Site site = siteOptional.get();
        SiteRes siteRes = new SiteRes();
        BeanUtils.copyProperties(site, siteRes);
        return CommonUtils.setDefaultEventMessage(siteRes);
    }

    public EventMessage<List<SiteRes>> findSite() {
        List<Site> siteList = siteRepository.findAll();
        List<SiteRes> siteResList = new ArrayList<>();
        for (Site site : siteList) {
            SiteRes SiteRes = new SiteRes();
            BeanUtils.copyProperties(site, SiteRes);
            siteResList.add(SiteRes);
        }
        return CommonUtils.setDefaultEventMessage(siteResList);
    }


    public EventMessage<String> addSite(AddSiteReq addSiteReq) {
        Site site = new Site();
        BeanUtils.copyProperties(addSiteReq, site);
        siteRepository.save(site);
        return CommonUtils.setDefaultEventMessage("success");
    }

}
