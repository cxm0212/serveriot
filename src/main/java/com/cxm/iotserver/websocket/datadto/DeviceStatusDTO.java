package com.cxm.iotserver.websocket.datadto;

import lombok.Getter;
import lombok.Setter;

/**
 * create by
 * 三和智控: cxm on 2020/4/13
 */
public class DeviceStatusDTO extends CommonDataDTO{

    @Getter
    @Setter
    private String status;
}
