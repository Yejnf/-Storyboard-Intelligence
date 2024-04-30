package com.example.alibabademo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


/**
 * 弃用,使用GetPromptController
 */


//@RestController
//@RequestMapping("/base64")
//@CrossOrigin("*")
public class GetSdController {
    @GetMapping(value = "/base64")
    public String generateImage(@RequestParam String prompt) throws IOException {
//        try {
//            String decodedPrompt = URLDecoder.decode(prompt, "UTF-8");
//            String[] cmd = {"python", "C:\\Users\\yjnf\\Desktop\\py.py", decodedPrompt};
//            Process process = Runtime.getRuntime().exec(cmd);
//
//            // 可选：等待Python脚本执行完成
//            process.waitFor();
//
//            System.out.println(decodedPrompt);
//            return "success";
//        } catch (IOException | InterruptedException e) {
//            System.err.println(2222);
//
//            e.printStackTrace();
//            return "failed";
//        }

        System.out.println(prompt);


//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时时间为10秒
//                .build();
//        String url = "http://172.25.47.157:7860";
//
//        JSONObject txt2imgData = new JSONObject();
//        txt2imgData.put("prompt", prompt);
//        txt2imgData.put("sampler_name", "DPM++ 2M Karras");
//        txt2imgData.put("batch_size", 1);
//        txt2imgData.put("steps", 20);
//        txt2imgData.put("cfg_scale", 9);
//        txt2imgData.put("width", 512);
//        txt2imgData.put("height", 512);
//        txt2imgData.put("negative_prompt", "nsfw");
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, txt2imgData.toString());
//        Request request = new Request.Builder()
//                .url(url + "/sdapi/v1/txt2img")
//                .post(body)
//                .build();
//        Response response = null;
//        response = client.newCall(request).execute();
//        if (response.body() != null) {
//            String jsonData = null;
//            jsonData = response.body().string();
//            // 获取json第0个
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonData);
//            String base64Data = jsonNode.get("images").get(0).asText();
//            System.out.println("Base64 Encoded Data: " + base64Data);
//
//            return base64Data; // 将base64Data返回给调用方
//        }

        return null;

    }
}
