package com.experiment.core.service.talk;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

import java.net.URLEncoder;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/20/2:14 下午
 * @Description:
 */
public class Sign {

    public static void main(String[] args) throws Exception{
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC13df27d857fe38be98e8c272236722448be19a9a37517bf67a3c508ed5873596";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign + "   " + timestamp);
    }

}
