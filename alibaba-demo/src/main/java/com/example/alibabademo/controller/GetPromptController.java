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

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/prompt")
@CrossOrigin("*")
public class GetPromptController {

    @Value("${api.key}")
    private String apiKey;
    //1 时代：2 画风；3 分镜
    @GetMapping(value = "/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamAsk(String q,String param3,String param1,String param2) throws Exception {

        Generation gen = new Generation();

        // system 和 user
        MessageManager msgManager = new MessageManager(2);
        Message systemMsg0 = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("嗨，Stable Diffusion是一款利用深度学习的文生图模型，支持通过使用提示词来产生新的图像，描述要包含或省略的元素。" +
                        "我在这里引入Stable Diffusion算法中的 Prompt 概念，又被称为提示符。" +
                        "这里的Prompt通常可以用来描述图像，他由普通常见的单词构成，最好是可以在数据集来源站点找到的著名标签(比如Danbooru)。" +
                        "下面我将说明 Prompt的生成步骤，这里的Prompt主要用于描述人物。" +
                        "在Prompt的生成中，你需要通过提示词来描述人物属性，主题，外表，情绪，衣服，姿势，视角，动作，背景。" +
                        "用英语单词或短语甚至自然语言的标签来描述，并不局限于我给你的单词。" +
                        "然后将你想要的相似的提示词组合在一起，请使用英文半角，做分隔符，并将这些按从最重要到最不重要的顺序排列。" +
                        "另外请注意，永远在每个Prompt的前面加上引号里的内容，、\\\"(best quality),(masterpiece), (monochrome)\\\"这是必要的前缀词。" +
                        "人物属性中，1girl表示你生成了一个女孩，1boy表示你生成了一个男孩，人数可以多人。另外注意，Prompt中不能带有-和_。" +
                        "可以有空格和自然语言，但不要太多，单词不能重复。" +
                        "包含人物性别、主题、外表、情绪、衣服、姿势、视角、动作、背景，将这些按从最重要到最不重要的顺序排列，" +
                        "越详细越好，请尝试根据以下内容生成具有连续性故事分镜的Prompt，细节越多越好，" +
                        "下面我将发送短视频剧本给你"+ "，为我生成" + param3 +"段Prompt。" +
//                        "下面我将发送短视频剧本给你，为我生成你觉得合适数量的Prompt。" +
                        "一个场景只要一个Prompt。"+
                        "生成的格式是这样的\"Prompt 1:<lora:add_detail:0.6>,safe for work,mid_shot, Prompt内容\"。" +
//                        "Prompt 2:<lora:add_detail:0.6>,huge files" +
                        "我需要你把每个Prompt前面加上'<lora:add_detail:0.6>,safe for work,mid_shot,'这句话," +
                        "应避免生成不良内容包括色情等违反法律的内容。" +
                        "生成内容我只要Prompt不要有其他中文解释，")

                //
                .build();
        Message userMsg1 = Message
                .builder()
                .role(Role.USER.getValue())
                .content(q)
                .build();
        msgManager.add(systemMsg0);
        msgManager.add(userMsg1);

        QwenParam param = QwenParam.builder()
                .model(Generation.Models.QWEN_MAX)
                .messages(msgManager.get())
                .seed(1234)
                .topP(0.8)
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .enableSearch(true)
                .apiKey(apiKey)
                .temperature((float)0.85)
                .repetitionPenalty((float)1.0)
                .incrementalOutput(true)
                .build();


        System.out.println(param1+ " "+param2+ " "+param3+ " ");
        // 调用生成接口，获取Flowable对象
        Flowable<GenerationResult> result = gen.streamCall(param);
        final AtomicReference<String> res = new AtomicReference<>("");
        // 将Flowable转换成Flux<ServerSentEvent<String>>并进行处理
        return Flux.from(result)
                // add delay between each event
                .delayElements(Duration.ofMillis(1000))
                .map(message -> {
                    String output = message.getOutput().getChoices().get(0).getMessage().getContent();
                    System.out.println(output); // print the output
//                    output = output.replace("\n", "");
                    res.updateAndGet(value -> value + output); // 更新res的值
                    return ServerSentEvent.<String>builder()
                            .data(output)
                            .build();

                })
                .concatWith(Flux.just(ServerSentEvent.<String>builder().comment("").build()))
                .doOnComplete(() -> {
                    System.out.println("Final result: " + res.get()); // 输出res的值
                    try {
//                        String decodedPrompt = URLDecoder.decode(res.get(), "UTF-8");
                        String[] cmd = {"python", "E:\\视听中国\\alibaba-demo\\py.py", res.get()+"xxxxxxxxxxxxxx"+param1+"xxxxxxxxxxxxxx"+param2};
                        Process process = Runtime.getRuntime().exec(cmd);

                        // 可选：等待Python脚本执行完成
                        process.waitFor();

                        System.out.println(res.get()+"xxxxxxxxxxxxxx"+param1+"xxxxxxxxxxxxxx"+param2);


                    } catch (IOException | InterruptedException e) {
                        System.err.println(2222);
                        e.printStackTrace();
                    }



//                    try {
//                        String decodedPrompt = URLDecoder.decode(res.get(), "UTF-8");
//                        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
//                        writer.write(decodedPrompt);
//                        writer.close();
//                        System.out.println("File written successfully.");
//
//                        // 调用Python脚本并传递文件路径作为参数
//                        String[] command = {"python", "C:\\Users\\yjnf\\Desktop\\py.py", "output.txt"};
//                        Process process = Runtime.getRuntime().exec(command);
//                        process.waitFor();
//                    } catch (IOException | InterruptedException e) {
//                        e.printStackTrace();
//                    }

                })
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
//        return "执行完毕";
    }

    @GetMapping(value = "test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testEventStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Event " + sequence + " at " + LocalTime.now());
    }
}