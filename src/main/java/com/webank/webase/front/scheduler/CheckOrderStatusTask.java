package com.webank.webase.front.scheduler;

import com.webank.webase.front.trade.exchange.Order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 检查订单状态的定时任务
 */
@Slf4j
@Component
public class CheckOrderStatusTask {
    @Autowired
    private OrderService orderService;


    /**
     * fixedDelay: 从本次任务执行完算起，隔一段时间开始执行下一个(毫秒)
     * fixedRate : 从本次任务开始执行算起，隔一段时间开始执行下一个(毫秒)
     * cron: 按照配置的规则执行任务
     */
    @Scheduled(fixedDelayString = "${constant.checkOrderStatusTaskDelay}")
    public void taskStart() {
        updateExchangeOrderStatus();
    }

    /**
     * 更改链内交易订单已超时的状态
     */
    public void updateExchangeOrderStatus() {
        orderService.updateExpireOrder();
    }
}
