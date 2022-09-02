package com.experiment.core.service.highway;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/08/15/3:39 下午
 * @Description:
 */

@Slf4j
public class HighWayUtils {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder().addInterceptor(new SignInterceptor()).build();
    public static String URL = "https://openapi.tuyacn.com";
    public static String getValue() {
        return CONTEXT_HOLDER.get();
    }
    public static void main(String[] args) {

//        System.out.println(JSONObject.toJSONString(getAccessToken("xrheed5xbe5g9w62vwhq", "40e80f3575c04902b6f0a66d7cd01be6", true)));
        String url = "/v1.0/iot-03/si/work-order/sn/print?workOrderCode=sssss&count=3";
        HighwayResultModel<List> t =
                get(url, List.class, "xrheed5xbe5g9w62vwhq", "40e80f3575c04902b6f0a66d7cd01be6");
//        HighwayResultModel<List> t =
//                get(url, List.class, "ae4f5aqybgv23gff8t3m", "c0682ecb42b24e9395cad063e65447fd");
        System.out.println(JSONObject.toJSONString(t));
    }

    private static void removeValue() {
        CONTEXT_HOLDER.remove();
    }

    private static void putValue(String ak, String sk) {
        CONTEXT_HOLDER.set(ak + "," + sk);
    }

    public static EasyAccessToken getAccessToken(String ak, String sk, boolean forceRefresh) {
        String url = "/v1.0/token?grant_type=1";
        HighwayResultModel<EasyAccessToken> t = get(url, EasyAccessToken.class, ak, sk);
        //保留上下文
        putValue(ak, sk);
        return t.getResult();
    }

    @SneakyThrows
    public static <T> HighwayResultModel<T> get(String url, Class<T> clss, String ak, String sk) {
        HighwayResultModel<T> tHighwayResultModel = doGet(url, clss, ak, sk);
        if (Objects.equals(tHighwayResultModel.getCode(), 1010)) {
            getAccessToken(ak, sk, true);
            tHighwayResultModel = doGet(url, clss, ak, sk);
        }

        return tHighwayResultModel;
    }

    private static <T> HighwayResultModel<T> doGet(String url, Class<T> clss, String ak, String sk) throws IOException {
        try {
            putValue(ak, sk);
            Request request = new Request.Builder().url(URL + url).build();
            try (Response response = CLIENT.newCall(request).execute()) {
                String rsp = Objects.requireNonNull(response.body()).string();
                log.info("rsp = {}", rsp);
                JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(HighwayResultModel.class, clss);
                HighwayResultModel<T> o = OBJECT_MAPPER.readValue(rsp, javaType);
                checkResult(o);
                return o;
            }
        } finally {
            removeValue();
        }
    }

    private static void checkResult(HighwayResultModel res) {
        if (Objects.equals(res.getSuccess(), false)) {
            log.error("接口处理失败.... res is {}", JSONObject.toJSONString(res));
        }
    }

}
