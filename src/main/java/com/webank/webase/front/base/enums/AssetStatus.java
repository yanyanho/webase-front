package com.webank.webase.front.base.enums;

public enum AssetStatus {
    normal(1),//正常
    suspend(2),//暂停
    destroy(3);//销毁


    private int value;

    private AssetStatus(int type) {
        this.value = type;
    }

    public int getValue() {
        return this.value;
    }
}
