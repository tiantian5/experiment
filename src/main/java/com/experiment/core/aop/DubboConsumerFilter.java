//package com.experiment.core.aop;
//
//import com.alibaba.druid.filter.Filter;
//import com.alibaba.dubbo.common.Constants;
//import com.alibaba.dubbo.common.extension.Activate;
//import com.alibaba.dubbo.rpc.Invocation;
//import com.alibaba.dubbo.rpc.Invoker;
//import com.alibaba.dubbo.rpc.Result;
//import com.alibaba.dubbo.rpc.RpcException;
//import com.alibaba.fastjson.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URL;
//
//@Activate(group = Constants.CONSUMER)
//public class DubboConsumerFilter implements Filter {
//
//    private Logger logger = LoggerFactory.getLogger("dubbo_consumer_logger");
//
//    @Override
//    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        return logElapsed(invoker, invocation);
//    }
//
//
//    protected Result logElapsed(Invoker<?> invoker, Invocation invocation) {
//        long start = System.currentTimeMillis();
//        Result result = null;
//        try {
//            result = invoker.invoke(invocation);
//            return result;
//        } finally {
//            long elapsed = System.currentTimeMillis() - start;
//            logger.info(getProtocolAndHost(invoker.getUrl()) + "/" + invoker.getInterface().getName() + "." + invocation.getMethodName() + ", elapsed:" + elapsed + "(ms),  arguments: " + JSON.toJSONString(invocation.getArguments()) +
//                    ", result:" + JSON.toJSONString(result));
//        }
//    }
//
//    private String getProtocolAndHost(URL url) {
//        StringBuilder buf = new StringBuilder();
//        buf.append(url.getProtocol());
//        buf.append("://");
//        buf.append(url.getHost());
//        buf.append(":");
//        buf.append(url.getPort());
//        return buf.toString();
//    }
//
//}
