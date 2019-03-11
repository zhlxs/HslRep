package com.jrwp.JSPay.entity;

import com.jrwp.follow.Until.ComUntil;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.SignatureException;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "2017121500832466";

    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOtenyMLhF2SaKYG2OqH32SJqCxcCFgDfl+4OW0MECKnnRuR4ffqZdkVJM4WA8pP33CSuMqhLGIeAqZGlZQm3yqfDvcXWj4/Q3GjfLpspO4cU0HUMsG8N5K3aivDau6fOAjjzhccrcoyZrVqJcEyK3jqik0N06uRRwc1yb0tauGJAgMBAAECgYAsokjzUF/NjtIhifLgptuKKUCnCNzcM+1nIXxiXYNBzx9P9x4u8VhXDldobWxSlUr+AhTg+Cd+g8pk1cXPzn9LLLSoVbPzibc//uc6z1al8WOWeOATrRMCKIujHdvigljna5THQIDXLpoDuQG0iVDeiVWXqEvV8hFsAyrB4T0EAQJBAPwgIpPkAartH5Fip1OE7KT4/LVUbyjSE+Cqohu/Sld6jbX9G4o0R/B08eij+omz3zpmXdXZSijZDYaX5WT6e+ECQQDu/JGPfE7rSdf//bIRGktKf5j9HdZiJs8ZsrDtJlHW9Dy2wIcfSPwhdM48wqvRTlLdnpl9SEeURsuIAA4UwlqpAkEA0OJq425fN1bX9aLvkJGrYg7rh+wZYI+UamnUfvqgRBPEPsghvgIMrBqnpZ/d3aKJyp+vFJK/D1KtExrbsiqY4QJBAN0LnXLyzmgQaAot1Cq/gE5K0O2lUGp+bOXfAoiG/gFs7E84qoJEVnh3el52zMcOw9SaYmuflB1Qkeab7mpDDekCQBA711TKOdVngxu/n4I4d4dORfNA0jBHDDDzgq7opFGb2pPjCp4bH55wfLg71NvFg/Mg7wWcpzUxyImZrvmAbJA=";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://" + ComUntil.COM + "/paymentOut/wx/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 商户可以自定义同步跳转地址
    public static String return_url = "http://" + ComUntil.COM + "/paymentOut/wx/return_url.jsp";

    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";

    // 编码
    public static String CHARSET = "UTF-8";

    // 返回格式
    public static String FORMAT = "json";

    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDrXp8jC4RdkmimBtjqh99kiagsXAhYA35fuDltDBAip50bkeH36mXZFSTOFgPKT99wkrjKoSxiHgKmRpWUJt8qnw73F1o+P0Nxo3y6bKTuHFNB1DLBvDeSt2orw2runzgI484XHK3KMma1aiXBMit46opNDdOrkUcHNcm9LWrhiQIDAQAB";

    // 日志记录目录
    public static String log_path = "/log";

    // RSA2
    public static String SIGNTYPE = "RSA";

}
