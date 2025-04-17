package com.wzy.controller;


import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.wzy.ai.DeepSeekController;
import com.wzy.ai.DeepSeekService;
import com.wzy.mapper.ArticlesMapper;
import com.wzy.mapper.CustomAiInfoMapper;
import com.wzy.pojo.Articles;
import com.wzy.pojo.CustomAiInfo;
import com.wzy.util.R;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("ai")
public class SuperAIController {

    @Autowired
    CustomAiInfoMapper customAiInfoMapper;

//    拿到文章的所有信息
    @Autowired
    ArticlesMapper articlesMapper;
    @GetMapping("getArticles")
    public List<Articles> getAllArticles(){
        List<Articles> articles = articlesMapper.selectList(null);
        return articles;
    }

    //    拿到相关信息
    @GetMapping("getinfo")
    public List<CustomAiInfo> getInfo() {
        List<CustomAiInfo> customAiInfos = customAiInfoMapper.selectList(null);
        return customAiInfos;
    }

    @PutMapping("save")
    public void save(@RequestBody CustomAiInfo customAiInfo) {//传过来应该是一个实体类


        System.out.println(customAiInfo);


//        System.out.println("11--------");
//        System.out.println(customAiInfo.getReferenceData().equals("true"));//是否选择引用数据库 引用为真
        System.out.println("22------");
        System.out.println(customAiInfoMapper.selectList(null).get(0).getReferenceData().equals("true"));//是否选择引用数据库 引用为真

//        保存的时候把那个引用的数据库的字段给插入到备用的字段里面

        customAiInfoMapper.updateById(customAiInfo);



    }

    //    专属ai回复的controller
    private static final Logger logger = Logger.getLogger(DeepSeekController.class.getName());
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private DeepSeekService deepSeekService;
    @GetMapping("/stream")
    public SseEmitter streamAIResponse(@RequestParam String prompt) {

        CustomAiInfo  empty1 = customAiInfoMapper.selectList(null).get(0);
//        接下来是对这个ai的定制，包括ai的设定，是否引用数据库等
        if(empty1.getReferenceData().equals("true")){//如果引用了数据库
            String customAiStatement = empty1.getCustomAiStatement() + "\n";//ai设定

            String referenceData = "以下是你可以引用的数据\n"+empty1.getSpareData();//引用的数据

            String q = "以下是你要回答的问题\n" + prompt;

            prompt = customAiStatement + referenceData + q;
        }
        

        SseEmitter emitter = new SseEmitter(300000L); // 超时时间为5分钟

        emitter.onTimeout(() -> {
            logger.info("客户端连接超时");
            emitter.complete();
        });

        emitter.onCompletion(() -> {
            logger.info("客户端主动断开连接");
        });

        String finalPrompt = prompt;

        System.out.println("--------");
        System.out.println(prompt);

        executorService.execute(() -> {
            try {
                Flowable<GenerationResult> result = deepSeekService.callAI(finalPrompt);
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
