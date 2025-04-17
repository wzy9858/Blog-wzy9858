package com.wzy.ai;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DeepSeekService {
    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    public Flowable<GenerationResult> callAI(String prompt) throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(prompt).build();
        GenerationParam param = buildGenerationParam(userMsg);
        return gen.streamCall(param);
    }


    private GenerationParam buildGenerationParam(Message userMsg) {

//        应该把数据库中文章的内容传送过去 文章标题
//        下面设置记住对话历史  注意: 由于token有限，所以要尽可能的少携带文本
//        Message admin1 = Message.builder().role("admin").content("").build();//这里管理设定
//        Message data1 = Message.builder().role("admin").content("").build(); //这里传送数据库


        return GenerationParam.builder()
//                .apiKey("sk-da54820ab9c145e7b51703e9868a2644")  //原先的apikey
                .apiKey("sk-da54820ab9c145e7b51703e9868a2644")  // 应用智能体有设定的apikey
                .model("deepseek-r1")
                .messages(Arrays.asList(userMsg))  //原本的
//                .messages(Arrays.asList(admin1,data1,userMsg))  // 采用多轮对话
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();
    }

    public void handleGenerationResult(GenerationResult message, SseEmitter emitter) {
        String reasoning = message.getOutput().getChoices().get(0).getMessage().getReasoningContent();
        String content = message.getOutput().getChoices().get(0).getMessage().getContent();
        if (!reasoning.isEmpty()) {
            try {
                emitter.send(SseEmitter.event()
                        .data("思考过程: " + reasoning, MediaType.TEXT_EVENT_STREAM));
                logger.info("发送思考过程: " + reasoning);
            } catch (IOException e) {
                logger.error("发送思考过程失败", e);
            }
        }

        if (!content.isEmpty()) {
            try {
                emitter.send(SseEmitter.event()
                        .data("回复内容: " + content, MediaType.TEXT_EVENT_STREAM));
                logger.info("发送回复内容: " + content);
            } catch (IOException e) {
                logger.error("发送回复内容失败", e);
            }
        }
    }
}