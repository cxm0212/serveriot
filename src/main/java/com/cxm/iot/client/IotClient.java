/*
 * Copyright (c) 2014-2016 Alibaba Group. All rights reserved.
 * License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cxm.iot.client;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.cxm.iot.util.LogUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;

import java.util.Properties;


public class IotClient {

    private static String accessKeyID;
    private static String accessKeySecret;
    private static String regionId;
    private static String productCode;
    @Getter
    private static String domain;
    @Getter
    private static String version;
    private static String uid;

//	private static String accessKeyID="LTAI4FpZ2mhMXSLtLLLMLJJB";
//    private static String accessKeySecret="RpSfK7n1luxlWdnsmky24SX8srnKSv";
//    private static String regionId="cn-shanghai";
//    private static String productCode="Iot";
//    private static String domain="iot.cn-shanghai.aliyuncs.com";
//    private static String version="2018-01-20";
//    private static String uid="1970521698065046";
    // endPoint:  https://${uid}.iot-as-http2.${region}.aliyuncs.com
//    private static String endPoint= "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";
    private static String endPoint;



    public static DefaultAcsClient getClient() {
		DefaultAcsClient client = null;
		Properties prop = new Properties();
		try {
			prop.load(IotClient.class.getResourceAsStream("/iot-config.properties"));
			accessKeyID = prop.getProperty("iot.accessKeyID");
			accessKeySecret = prop.getProperty("iot.accessKeySecret");
			regionId = prop.getProperty("iot.regionId");
            domain = prop.getProperty("iot.domain");
            version = prop.getProperty("iot.version");
            productCode=prop.getProperty("iot.productCode");
            uid=prop.getProperty("iot.uid");

//            accessKeyID = property.getAccessKeyID();
//            accessKeySecret = property.getAccessKeySecret();
//            regionId = property.getRegionId();
//            domain = property.getDomain();
//            version = property.getVersion();
//            productCode=property.getProductCod();
//            uid = property.getUid();
//            endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";

			IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyID,accessKeySecret);
			DefaultProfile.addEndpoint(regionId, regionId, productCode, domain);
			// 初始化client
			client = new DefaultAcsClient(profile);

		} catch (Exception e) {
			LogUtil.print("初始化client失败！exception:" + e.getMessage());
		}

		return client;
	}

	public static MessageClient getMessageClient() {
        MessageClient client = null;
		try {
            endPoint= "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";
            // 连接配置
            Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKeyID, accessKeySecret);
			// 初始化client
            client = MessageClientFactory.messageClient(profile);

		} catch (Exception e) {
			LogUtil.print("初始化messageclient失败！exception:" + e.getMessage());
		}

		return client;
	}

}
