package com.experiment.core.service.factory.factoryfour;

import com.experiment.core.enums.EnumEvent;
import com.experiment.core.service.factory.factoryfour.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author tzw
 * @description 事件处理器
 * @create 2020-11-30 11:40 上午
 **/

@Slf4j
@Component
public class FourFactoryEventDispatcher {

    /**
     * 线程池
     */
    @Autowired
    private Executor executor;

    public FourFactoryEventDispatcher(Executor executor) {
        this.executor = executor;
    }

    private Map<EnumEvent, List<EventListener<?>>> localListenerMap;

    @Resource
    private List<EventListener<?>> listeners;

    private EnumSet<EnumEvent> onlyLocal;
    private EnumSet<EnumEvent> onlyRemote;

    @PostConstruct
    public void init() {
        this.localListenerMap = this.listeners
                .stream()
                .collect(Collectors.groupingBy(EventListener::target));

        // 内部消息
        onlyLocal = EnumSet.of(EnumEvent.BRAND_GET_PITEM, EnumEvent.BRAND_UPDATE);
        // 外部消息
        onlyRemote = EnumSet.of(EnumEvent.BRAND_UPDATE);

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void dispatchEvent(Event<?> event) {

        EnumEvent type = event.getEnumBrandEvent();

        // 只发布到远程
        if (onlyRemote.contains(type)) {
            publish2Remote(event);
            return;
        }
        // 只发布到本地
        if (onlyLocal.contains(type)) {
            invokeLocalListeners(event);
        }

    }

    private <T> void publish2Remote(Event<T> event) {
        // 一般是发送mq
    }

    @SuppressWarnings("unchecked")
    private void invokeLocalListeners(Event event) {
        EnumEvent type = event.getEnumBrandEvent();
        List<EventListener<?>> listeners = this.localListenerMap.get(type);
        if (!CollectionUtils.isEmpty(listeners)) {
            for (EventListener<?> listener : listeners) {
                try {
                    executor.execute(() -> listener.onEvent(event));
                } catch (Exception e) {
                    log.error("发布本地消息异常", e);
                }
            }
        }
    }

}