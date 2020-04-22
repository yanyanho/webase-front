package com.webank.webase.front.trade.txspeed.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/bac-network")
public class NetworkController {

        @Autowired
        NetworkService networkService;

        @GetMapping
        public List<Network> getNetworkAddress() throws Exception {
            return  networkService.findAll();
        }

    }
