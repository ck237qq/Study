package com.example.study.dto.req.site;

import lombok.Data;

@Data
public class UpdateSiteReq {
    private Long siteId;

    private String name;

    private String addr;
}
