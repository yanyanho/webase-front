package com.webank.webase.front.trade.txspeed;

public enum MessageTypeId {

    None(0),
    BalanceProof(1),
    BalanceProofUpdate(2),
    Withdraw(3),
    CooperativeSettle(4),
    IOU(5),
    MSReward(6);

    private int value;

    private MessageTypeId(Integer dataStatus) {
        this.value = dataStatus;
    }

    public int getValue() {
        return this.value;
    }

}


