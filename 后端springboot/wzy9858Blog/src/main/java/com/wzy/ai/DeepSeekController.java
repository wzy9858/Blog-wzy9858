package com.wzy.ai;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class DeepSeekController {

    private static final Logger logger = Logger.getLogger(DeepSeekController.class.getName());
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private DeepSeekService deepSeekService;

    @GetMapping("/ai-stream")
    public SseEmitter streamAIResponse(@RequestParam String prompt) {



        SseEmitter emitter = new SseEmitter(300000L); // 超时时间为5分钟

        emitter.onTimeout(() -> {
            logger.info("客户端连接超时");
            emitter.complete();
        });

        emitter.onCompletion(() -> {
            logger.info("客户端主动断开连接");
        });

        executorService.execute(() -> {
            try {
                Flowable<GenerationResult> result = deepSeekService.callAI(prompt);
                result.blockingForEach(message -> deepSeekService.handleGenerationResult(message, emitter));
                emitter.complete();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "AI调用失败", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}