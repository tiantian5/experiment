package com.experiment.core.service.talk.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/27/1:22 下午
 * @Description:
 */

@Slf4j
public class SendMessageUtil {

    public static String httpPostMethod(String url, Map<String, String> headers,
                                        String requestBodyDto) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType
                    .parse(headers.get("Content-Type") == null ? "application/json"
                            : headers.get("Content-Type"));
            System.out.println("入参信息：" + requestBodyDto);
            RequestBody requestBody = RequestBody.create(requestBodyDto, mediaType);
            Headers headerMap = okhttp3.Headers.of(headers);
            Request request = new Request.Builder().url(url).headers(headerMap)
                    .post(requestBody).build();
            Response response = client.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            log.error("请求外部接口异常 e", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 钉钉签名验证
     *
     * @param timestamp 时间戳
     * @param secret 验证
     * @return 加密后验证
     */
    public static String sign (Long timestamp, String secret) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (Exception e) {
            log.error("请求外部接口异常 e", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 在线图片转换成base64字符串
     * @param imgUrl	图片线上路径
     * @return base64字符串
     */
    public static String imageToBase64(String imgUrl) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgUrl);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            log.error("在线图片转换成base64字符串", e);
        }
        // 对字节数组Base64编码
        return Base64.encodeBase64String(data.toByteArray());
    }

    /**
     * 图片生成md5
     *
     * @param imgUrl 图片线上路径
     * @return md5
     */
    public static String md5(String imgUrl) {
        try {
            return DigestUtils.md5DigestAsHex(new FileInputStream(imgUrl));
        } catch (IOException e) {
            log.error("图片生成md5", e);
        }

        return StringUtils.EMPTY;
    }

}
