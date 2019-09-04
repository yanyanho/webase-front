package com.webank.webase.front.trade.trade.htlc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HTLCInfoService {


    @Autowired
    HTLCInfoRepository htlcInfoRepository;

    public HTLCInfo findByGroupId(int id) {
        return htlcInfoRepository.findByGroupId(id);
    }

     public List<HTLCInfo> findAll() {
         Iterable<HTLCInfo> htlcInfos  =     htlcInfoRepository.findAll();
         List<HTLCInfo> list =new ArrayList<>();
         htlcInfos.forEach(single ->{list.add(single);});
         return  list;
     }

     public void  save(HTLCInfo htlcInfo) {
        htlcInfoRepository.save(htlcInfo);
     }

}
