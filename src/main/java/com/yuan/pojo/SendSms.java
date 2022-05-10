package com.yuan.pojo;
import lombok.Data;

import java.util.Map;

@Data
public class SendSms {
    /**
     * 手机号
     */
    private String PhoneNumbers = "13098876085";

    /**
     *模板
     */
    private final String templateCode="SMS_154950909";

    /**
     * 签名
     */
    private final String SignName="阿里云短信测试";

    /**
     * 验证码
     */
    private Map<String,Object> code;
}
