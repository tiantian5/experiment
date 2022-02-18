package com.experiment.core.service.talk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/20/2:00 下午
 * @Description:
 */
public class DingDingTalkSendService {

    private static Logger logger = LoggerFactory.getLogger(DingDingTalkSendService.class);
    /**
     * 发送POST请求，参数是Map, contentType=x-www-form-urlencoded
     *
     * @param url 请求路由
     * @param mapParam 请求参数
     * @return 返回结果
     */
    public static String sendPostByMap(String url, Map<String, Object> mapParam) {
        Map<String, String> headParam = new HashMap<>(8);
        headParam.put("Content-type", "application/json;charset=UTF-8");
        return httpPostMethod(url, headParam, JSONObject.toJSONString(mapParam));
    }
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
            logger.error("请求外部接口异常 e", e);
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args){

        // 钉钉的webhook
        String dingDingToken="https://oapi.dingtalk.com/robot/send?access_token=c933bfe8645885a1f12ae112ae5c1e1ae219a488221de5417be4369c8ed21993"
                +"&timestamp=" + System.currentTimeMillis() + "&sign=e48ynJ281R4mfUhSkRCJh8D4%2FxfdCycUNKnrCsRfRdk%3D";
        // 请求的JSON数据，这里我用map在工具类里转成json格式
        Map<String,Object> json=new HashMap<>();
        Map<String,Object> text=new HashMap<>();
        json.put("msgtype","text");
        text.put("content","测试文本");
        json.put("text",text);
        // 发送post请求
        String response = DingDingTalkSendService.sendPostByMap(dingDingToken, json);
        System.out.println("相应结果："+response + dingDingToken);

    }

}
