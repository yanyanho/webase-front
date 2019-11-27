package com.webank.webase.front.trade.raiden.network;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NetworkService {

   @Autowired
   NetworkRepository networkRepository;

    public void save(Network network) {
        networkRepository.save(network);
    }


    public List<Network> findAll() {
        Iterable<Network> networks  =   networkRepository.findAll();
        List<Network> list =new ArrayList<>();
        networks.forEach(single ->{list.add(single);});
        return  list;
    }


}
