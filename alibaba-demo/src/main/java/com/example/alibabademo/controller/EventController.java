package com.example.alibabademo.controller;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {

    @Value("${api.key}")
    private String apiKey;

    @GetMapping(value = "/streamAsk", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamAsk(String q) throws Exception {

        Generation gen = new Generation();

        // system 和 user
        MessageManager msgManager = new MessageManager(2);
        Message systemMsg0 = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("嗨，我是短视频剧本创作家，我可以为您提供一个短视频剧本的创作预输入。" +
                        "首先，我会分析您想要的主题和情感，例如浪漫、幽默、悬疑等等。" +
                        "然后，我会根据这些信息生成一些可能的情节和角色，包括主角、配角、反派等等。" +
                        "接着，我会为这些角色设计一些独特的特点和个性，以使他们更加生动和有趣。" +
                        "最后，我会根据您的需要为这个短视频剧本提供一些配乐和对白，以增强情感共鸣和视听效果。" +
                        "希望这个预输入能够为您的短视频剧本创作提供一些灵感和帮助。")
//                .content("我是一个短视频剧本的创作者，我的核心功能就是创作短视频剧本，我会根据您的要求进行完整的创作。并按照以下格式输出：" +
//                        "【剧本名称】" +
//                        "【角色介绍】" +
//                        "【剧情描述】" +
//                        "【分镜】" )
//                .content("我要你担任编剧。为能够吸引观众的短视频您将开发引人入胜且富有创意的剧本。从想出有趣的角色、故事的背景、角色之间的对话等开始。您要记住要创造一个充满曲折的激动人心的故事情节，让观众一直悬念到最后。")
//                .content("你现在是一个画家,你的任务就是根据我提出的要求画画")
//                .content("你是一个文本ai。")
//                .content("你擅长写短视频剧本：1.剧情紧凑；2.视觉冲击力强 现在你需要根据以下需求写\n")
                .build();
        Message userMsg1 = Message
                .builder()
                .role(Role.USER.getValue())
                .content(q)
                .build();
        msgManager.add(systemMsg0);
        msgManager.add(userMsg1);

        // 创建用户消息对象
//        Message userMsg = Message
//                .builder()
//                .role(Role.USER.getValue())
//                .content(q)
//                .build();

        // 创建QwenParam对象，设置参数
        QwenParam param = QwenParam.builder()
                .model(Generation.Models.QWEN_MAX)
//                .messages(Arrays.asList(msgManager))
                .messages(msgManager.get())
                .seed(1234)
                .topP(0.8)
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .enableSearch(true)
                .apiKey(apiKey)
                .temperature((float)0.85)
                .repetitionPenalty((float)1.0)
                // get streaming output incrementally
                .incrementalOutput(true)
                .build();

        // 调用生成接口，获取Flowable对象
        Flowable<GenerationResult> result = gen.streamCall(param);

        // 将Flowable转换成Flux<ServerSentEvent<String>>并进行处理
        return Flux.from(result)
                // add delay between each event
                .delayElements(Duration.ofMillis(100))
                .map(message -> {
                    String output = message.getOutput().getChoices().get(0).getMessage().getContent();
                    System.out.println(output); // print the output
                    return ServerSentEvent.<String>builder()
                            .data(output)
                            .build();
                })
                .concatWith(Flux.just(ServerSentEvent.<String>builder().comment("").build()))
                .doOnError(e -> {
                    if (e instanceof NoApiKeyException) {
                        // 处理 NoApiKeyException
                    } else if (e instanceof InputRequiredException) {
                        // 处理 InputRequiredException
                    } else if (e instanceof ApiException) {
                        // 处理其他 ApiException
                    } else {
                        // 处理其他异常
                    }
                });
    }

    @GetMapping(value = "test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testEventStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence + " at " + LocalTime.now());
    }
}