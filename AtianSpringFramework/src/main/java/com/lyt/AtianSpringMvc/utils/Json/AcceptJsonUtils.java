package com.lyt.AtianSpringMvc.utils.Json;

import com.alibaba.fastjson.JSON;
import com.lyt.servlet.HttpServletRequest;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AcceptJsonUtils {
    public static <T> Object getJson(Class<T> obj, HttpServletRequest req)throws Exception{
        //为什么要这样子？ 因为这样才知道你传的是什么类啊
        if(req.getInputStream()!=null){
            System.out.println("成功获取到 inputstream 正在处理");
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            // Object yourObj= JsonToPojoUtils.ToPojo(sb.toString(),obj);
            T yourObj= JSON.parseObject(sb.toString(),obj);
            System.out.println("这是生成的json字符串"+sb.toString());
            return yourObj;
        }
        System.out.println("没有获取到inPutStream数据");
        return null;
    }


}
