package com.experiment.core.service.factory.factoryfour.instance;

import com.experiment.core.enums.EnumEvent;
import com.experiment.core.service.factory.factoryfour.EventListener;
import com.experiment.core.service.factory.factoryfour.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 11:54 上午
 **/

@Slf4j
@Component
public class TwoEventListener implements EventListener<Integer> {
    @Override
    public EnumEvent target() {
        return EnumEvent.BRAND_UPDATE;
    }

    @Override
    public void onEvent(Event<Integer> event) {

    }
}