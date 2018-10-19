package org.luvx.sign;


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.luvx.utils.Base64;
import org.luvx.utils.DateUtil;
import org.luvx.utils.RandomUtils;
import org.luvx.utils.Tools;


public class RSACoder {
    public static Logger logger = Logger.getLogger(RSACoder.class);

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static Map<String, Object> keyMap;

    private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNoSwa8e61lM6JPpYraRrxnueXmidt/42nZOY9/JhVgSW2VRCwkwBbNtql38gMofOVgXIcl/x9Kh7l1xAXfxs/2UUs644Xhhe1adu1Ng3GCT48FbuEHbx+XkhcU701aBftLDubSEk8jAmLUK2Zm2NSPl/KCHTJRhVKJMSVhSLNvQIDAQAB";

    private static final String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM2hLBrx7rWUzok+litpGvGe55eaJ23/jadk5j38mFWBJbZVELCTAFs22qXfyAyh85WBchyX/H0qHuXXEBd/Gz/ZRSzrjheGF7Vp27U2DcYJPjwVu4QdvH5eSFxTvTVoF+0sO5tISTyMCYtQrZmbY1I+X8oIdMlGFUokxJWFIs29AgMBAAECgYB5ne6BDT/6w0UYZRzaFfLqnuoofvL9Gt1D+Og4FzJdt+F/InaZiIf4aswMuPsPhaWUchf0k2uw932IwJby3qlVHXt+lawXf/dnS0Pr7YeRd17Q+0UV+8f0FLVq6o4JPuTRwPMXEQHmUuIj2ZR8OnAiIU+tQlwUGzcpMGpdH7WzoQJBAOykwe6QLgj/sEFSCXlL3fxaJQOft3HRTStTgpe7h8YH82b2QbRoSalCfeRdV9Qz0yWfkz0X6xkpbvyE9njuJ5UCQQDecv2/WMvoB3ek7v2J5G9pXBstn3C0JgAQL8kfwyyB53+gh9nuK4uXecfYgelSz30UUFeKJZ4VLRSIsrc2nWOJAkEAxJpdRIYkuPU0yhAOLugJFY8PEWOwVK8/Ha/T35u9wZj5b6FIFUpPaIAFd6TVQ0eLNMbO3QLyH4V83+7XlsDFwQJAAQPPSo8yJvXZwuPgPj/PjlCAQz4F+pxtxaUiKsXuLw2KVIYqJV8HfQJuOfulv934eR5At/2h8gDJpjICifo/2QJAQ7f4eNk47RfHP8xgbKp8pJJoAk5yjrjmWAlDv/T2G9x7HGza6xo+wA75h/uRVG2GhDsCQKSUpBFa4cb+qjDlVQ==";

    public static String sign(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");


        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);


        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(data);

        return Base64.encode(signature.sign());
    }


    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);


        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");


        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(data);


        return signature.verify(Base64.decode(sign));
    }


    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = Base64.decode(key);


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);

        return cipher.doFinal(data);
    }


    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = Base64.decode(key);


        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);

        return cipher.doFinal(data);
    }


    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = Base64.decode(key);


        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);

        return cipher.doFinal(data);
    }


    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = Base64.decode(key);


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);

        return cipher.doFinal(data);
    }

    public static byte[] encryptSupplierByPrivateKey(byte[] data, String key)
            throws Exception {
        byte[] keyBytes = Base64.decode(key);


        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);


        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);

        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += 64) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 64));
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }

        return enBytes;
    }


    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("RSAPrivateKey");


        return Base64.encode(key.getEncoded());
    }


    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("RSAPublicKey");

        return Base64.encode(key.getEncoded());
    }


    public static Map<String, Object> initKey()
            throws Exception {
        KeyPairGenerator keyPairGen =
                KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();


        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();


        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap(2);

        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    public static void init() throws Exception {
        keyMap = initKey();
    }


    public static String getSign()
            throws Exception {
        String encryptStr = RandomUtils.getRandomString(10) + System.currentTimeMillis();

        String privateKey = Tools.getDirectory("privateKey");

        byte[] data = encryptStr.getBytes();
        byte[] encodedData = encryptByPrivateKey(data, privateKey);

        String sign = sign(encodedData, privateKey);

        String prefix = Base64.encode(encodedData);

        sign = Base64.encode(prefix + sign);
        return sign;
    }


    public static String getSupplierSign(Map<String, String> params)
            throws Exception {
        String encryptStr = Tools.getSignData(params);

        String privateKey = Tools.getDirectory("privateKey");

        byte[] data = encryptStr.getBytes("utf-8");
        byte[] encodedData = encryptSupplierByPrivateKey(data, privateKey);

        String sign = sign(encodedData, privateKey);
        return sign;
    }


    public static boolean verifySupplierSign(Map<String, String> params, String sign)
            throws Exception {
        byte[] content = Tools.getSignData(params).getBytes("utf-8");
        byte[] encodedData = encryptSupplierByPrivateKey(content, Tools.getDirectory("privateKey"));
        boolean result = verify(encodedData, Tools.getDirectory("publicKey"), sign);
        return result;
    }


    public static String getKey()
            throws Exception {
        Map<String, Object> keyMap = initKey();
        StringBuilder resBuf = new StringBuilder();
        resBuf.append("{\"publicKey\":\"" + getPublicKey(keyMap) + "\",\"privateKey\":\"" + getPrivateKey(keyMap) + "\"}");
        return resBuf.toString();
    }


    public static boolean verify(String sign) throws Exception {
        String publicKey = Tools.getDirectory("publicKey");
        String privateKey = Tools.getDirectory("privateKey");

        sign = new String(Base64.decode(sign));

        String prefix = sign.substring(0, 172);

        byte[] encodedData = Base64.decode(prefix);
        byte[] decodedData = decryptByPublicKey(encodedData, publicKey);

        prefix = new String(decodedData);
        String timestamp = prefix.substring(10, prefix.length());
        if ((timestamp == null) || (timestamp == "") || (timestamp.length() <= 0)) {
            logger.error("时间戳为空，无法判断！");
            return false;
        }
        Date date = new Date(Long.valueOf(timestamp).longValue());
        Date curDate = new Date();

        int diff = 0;
        try {
            diff = DateUtil.getDateSecond(curDate, date);
        } catch (Exception e) {
            logger.error("", e);
        }
        if (diff > 300000) {
            logger.error("签名已经过期！");
            return false;
        }

        sign = sign.substring(172, sign.length());
        encodedData = encryptByPrivateKey(decodedData, privateKey);

        boolean status = verify(encodedData, publicKey, sign);
        return status;
    }
}

