package com.webank.webase.front.trade.trade.htlc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/htlc")
public class HTLCInfoController {


    @Autowired
    HTLCInfoService htlcInfoService;

    @GetMapping
    public List<HTLCInfo> getHTLCAddress() throws Exception {
        return  htlcInfoService.findAll();
    }

}
