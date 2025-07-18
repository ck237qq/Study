package com.example.study.controller;

import com.example.study.dto._event.EventMessage;
import com.example.study.dto.req.site.AddSiteReq;
import com.example.study.dto.req.site.UpdateSiteReq;
import com.example.study.dto.res.site.SiteRes;
import com.example.study.service.SiteService;
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
@Tag(name = "駐點管理")
public class SiteController {
    private final SiteService siteService;

    @Operation(summary = "編輯駐點", description = "新增駐點")
    @RequestMapping(value = "/updateSite", method = RequestMethod.PUT)
    public EventMessage<String> updateSite(@RequestBody UpdateSiteReq updateSiteReq) {
        return siteService.updateSite(updateSiteReq);
    }

    @Operation(summary = "查詢單筆駐點", description = "查詢單筆駐點")
    @RequestMapping(value = "/getSiteById", method = RequestMethod.GET)
    public EventMessage<SiteRes> getSiteById(Long siteId) {
        return siteService.getSiteById(siteId);
    }

    @Operation(summary = "查詢全部駐點", description = "查詢全部駐點")
    @RequestMapping(value = "/findSite", method = RequestMethod.GET)
    public EventMessage<List<SiteRes>> findSite() {
        return siteService.findSite();
    }

    @Operation(summary = "新增駐點", description = "新增駐點")
    @RequestMapping(value = "/addSite", method = RequestMethod.POST)
    public EventMessage<String> addSite(@RequestBody AddSiteReq addSiteReq) {
        return siteService.addSite(addSiteReq);
    }
}
