package com.experiment.core.service.talk.controller;

import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.dto.SendMessageResultDTO;
import com.experiment.core.service.talk.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/27/1:30 下午
 * @Description:
 */

@EnableAutoConfiguration
@Controller
public class SendMessageController {

    @Autowired
    private SendMessageService sendMessageService;

    @RequestMapping("/testDingDing")
    @ResponseBody
    public void testDingDing(@RequestBody SendMessageDTO sendMessageDTO){

        SendMessageResultDTO s = sendMessageService.sendMessage(sendMessageDTO);
        System.out.println(s);

    }

}
