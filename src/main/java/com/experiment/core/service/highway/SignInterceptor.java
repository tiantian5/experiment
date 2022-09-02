package com.experiment.core.service.highway;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
public class SignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean withToken = isWithToken(request.url().url());
        String value = HighWayUtils.getValue();
        String[] split = value.split(",");
        String ak = split[0];
        String sk = split[1];
        if (withToken) {
            long now = System.currentTimeMillis();
            //获取token接口
            Headers.Builder map = new Headers.Builder();
            map.add("client_id", ak);
            map.add("t", String.valueOf(now));
            map.add("sign_method", "HMAC-SHA256");
            map.add("lang", "zh");
//            map.add("envtag", "pirnt-sn-third");
            String accessToken = getAccessToken(ak, sk);
            map.add("access_token", accessToken);
            String body = bodyToString(request.body());
            String str = ak + accessToken + now + this.stringToSign(request, request.headers(), body);
            log.info(JSONObject.toJSONString(str));
            map.add("sign", this.sign(str, sk));
            Request build = request.newBuilder().headers(map.build()).build();
            log.info(JSONObject.toJSONString(build));
            Response proceed = chain.proceed(build);
            log.info(JSONObject.toJSONString(proceed));
            return proceed;
        } else {
            long now = System.currentTimeMillis();
            log.info("now:{}", now);
            //获取token接口
            Headers.Builder map = new Headers.Builder();
            map.add("client_id", ak);
            map.add("t", String.valueOf(now));
            map.add("sign_method", "HMAC-SHA256");
            map.add("lang", "zh");
            String str = ak + now + this.stringToSign(request, request.headers(), "");
            log.info("str: {}", JSONObject.toJSONString(str));
            log.info(JSONObject.toJSONString(str));
            map.add("sign", this.sign(str, sk));
            log.info("sign: {}", this.sign(str, sk));
            Response proceed = chain.proceed(request.newBuilder().headers(map.build()).build());
            log.info(JSONObject.toJSONString(proceed));
            return proceed;
        }

    }

    private boolean isWithToken(URL url) {
        String path = url.getPath();
        return !path.contains("/v1.0/token");
    }

    private String getAccessToken(String ak, String sk) {
        return HighWayUtils.getAccessToken(ak, sk, false).getAccessToken();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @SneakyThrows
    private String stringToSign(Request request, Headers headers, String body) {
        List<String> lines = new ArrayList(16);
        lines.add(request.method().toUpperCase());
        String bodyHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        if (request.body() != null && Objects.requireNonNull(request.body()).contentLength() > 0) {
            bodyHash = Sha256Util.encryption(body);
        }

        String signHeaders = headers.get("Signature-Headers");
        String headerLine = "";
        if (signHeaders != null) {
            String[] sighHeaderNames = signHeaders.split("\\s*:\\s*");
            headerLine = Arrays.stream(sighHeaderNames).map(String::trim).filter((it) -> it.length() > 0)
                    .map((it) -> it + ":" + headers.get(it)).collect(Collectors.joining("\n"));
        }

        lines.add(bodyHash);
        lines.add(headerLine);
        URL url = request.url().url();
        String paramSortedPath = this.getPathAndSortParam(url);
        lines.add(paramSortedPath);
        return String.join("\n", lines);

    }

    public String sign(String content, String sk) {
        return Sha256Util.sign(content, sk);
    }

    public static void main(String[] args) {
        System.out.println(Sha256Util.sign("jhsdkajsdakj", "c0682ecb42b24e9395cad063e65447fd"));
    }

    private String getPathAndSortParam(URL url) {
        String query = url.getQuery();
        String path = url.getPath();
        if (!StringUtils.hasText(query)) {
            return path;
        } else {
            Map<String, String> kvMap = new TreeMap();
            String[] kvs = query.split("&");
            String[] var6 = kvs;
            int var7 = kvs.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                String kv = var6[var8];
                String[] kvArr = kv.split("=");
                if (kvArr.length > 1) {
                    kvMap.put(kvArr[0], this.decode(kvArr[1]));
                } else {
                    kvMap.put(kvArr[0], "");
                }
            }

            return path + "?" + kvMap.entrySet().stream().map((it) -> it.getKey() + "=" +
                    this.encode(it.getValue())).collect(Collectors.joining("&"));
        }
    }

    @SneakyThrows
    private String decode(String data) {
        return URLDecoder.decode(data, StandardCharsets.UTF_8);
    }

    private String encode(String data) {
        return data;
    }

}
