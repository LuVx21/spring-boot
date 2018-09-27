package org.luvx.utilsTest;

import org.luvx.sign.RSACoder;

import java.util.HashMap;
import java.util.Map;

public class RSACoderTest {
    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap();
        params.put("supplierId", "35");
        params.put("aliaccount", "hbgj-lxszhifubaofx@alipay.com");
        params.put("aliaccountName", "XXXX有限公司");
        params.put("channeltype", "1");

        String sign = RSACoder.getSupplierSign(params);
        System.out.println(sign);
        System.out.println(RSACoder.verifySupplierSign(params, sign));
    }
}