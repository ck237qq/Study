package com.example.study.service;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.site.AddSiteReq;
import com.example.study.dto.req.site.UpdateSiteReq;
import com.example.study.dto.res.site.SiteRes;

import java.util.List;

public interface SiteService {

    EventMessage<String> updateSite(UpdateSiteReq updateSiteReq);

    EventMessage<SiteRes> getSiteById(Long siteId);

    EventMessage<List<SiteRes>> findSite();

    EventMessage<String> addSite(AddSiteReq addSiteReq);

}
