package translate.utils;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * 网易有道智云翻译服务api调用demo
 * api接口: https://openapi.youdao.com/api
 */
public class TranslateDemo {

    private static final String APP_KEY = "1c662a20dcd3d9b6";     // 您的应用ID
    private static final String APP_SECRET = "RloQf3aswE7Etr4GfK1lkaqQZqYqL1uc";  // 您的应用密钥

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String aa = "{\"query\":\"my name is helong\",\"translation\":[\"我的名字叫贺龙\"]}";
        JSONObject jsonObject1 = JSONObject.parseObject(aa);
        System.out.println(jsonObject1);
        String engString1 = (String) jsonObject1.getString("query");
        // JSONArray chnArray1 = jsonObject1.get
        
        // System.out.println(engString1+":"+chnArray1);

        // 添加请求参数
        Map<String, String[]> params = createRequestParams();
        // 添加鉴权相关参数
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            String resultStr = new String(result, StandardCharsets.UTF_8);

            resultStr = "{\"query\":\"my name is helong\",\"translation\":[\"我的名字叫贺龙\"]}";
            
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            System.out.println(jsonObject);

            String code = (String) jsonObject.get("errorCode");
            String engString = (String) jsonObject.getString("query");
            JSONArray chnArray = jsonObject.getObject("translation", JSONArray.class);
            
            System.out.println(engString+":"+chnArray);

        }
        System.exit(1);
    }

    private static Map<String, String[]> createRequestParams() {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/%E8%87%AA%E7%84%B6%E8%AF%AD%E8%A8%80%E7%BF%BB%E8%AF%91/API%E6%96%87%E6%A1%A3/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
         */
        // String q = "Before my current role as Head of Engineering, I led our Platforms Team. Companies like Google have understood the power of Platforms for a long time, however outside of the Tech Giant World the idea of a whole team dedicated to Platforms is still quite new. Taking the plunge paid off, our Platforms Team catapulted us at a rate of change and maturity that we had never seen before.";
        // String q = "At the time there was (and still is) a lot of noise in the industry around Platform Teams. We keep a close eye on the ThoughtWorks Tech Radar for understanding new trends and fads; Platform Teams popped up on there around the time as a concept to ‘Adopt’.";
        String q = "my name is helong";
        String from = "auto";
        String to = "zh-CHS";
        // String vocabId = "您的用户词表ID";

        return new HashMap<String, String[]>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
            // put("vocabId", new String[]{vocabId});
        }};
    }
}
