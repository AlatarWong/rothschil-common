package io.github.rothschil;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FaceAITest {

    public static void main(String[] args) throws IOException {
        //1. 创建Java代码和百度云交互的Client对象
        AipFace client = new AipFace("27384863","iRHFhrYopThQ8wqlzB3dlTGy","IWnDOHPZlw0mjjrLMyf54xBfgTCKjzvU");
        sample(client);
    }

    public static void sample(AipFace client) throws IOException {
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<>();
//        options.put("face_field", "age");
//        options.put("max_face_num", "2");
//        options.put("face_type", "LIVE");
        options.put("quality_control","NORMAL");//图片质量
//        options.put("liveness_control", "LOW");

        // 取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串
        String path = "D:\\20220827_235754206_iOS.jpg";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        String imageType = "BASE64";

        // 人脸检测
        JSONObject res = client.detect(image, imageType, options);
        System.out.println(res);
    }
}
