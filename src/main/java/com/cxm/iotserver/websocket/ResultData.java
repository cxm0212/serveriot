package com.cxm.iotserver.websocket;

import lombok.Getter;
import lombok.Setter;

/**
 * create by
 * 三和智控: cxm on 2020/4/2
 */

@Getter @Setter
public class ResultData {

    private String iotId;
    private String OutputVoltage;
    private String CurrentTemperature;
    private String PowerStatus;
    private String OutputElectricity;

    private String voltageTime;
    private String temperatureTime;
    private String powerTime;
    private String electricityTime;


}
