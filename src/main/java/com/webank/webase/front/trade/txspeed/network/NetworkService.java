package com.webank.webase.front.trade.txspeed.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
