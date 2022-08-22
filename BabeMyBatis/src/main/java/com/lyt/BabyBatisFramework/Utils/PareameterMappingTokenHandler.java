package com.lyt.BabyBatisFramework.Utils;



import com.lyt.BabyBatisFramework.config.ParameterMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//todo 有关入参2
public class PareameterMappingTokenHandler  implements TokenHandler{

    //参数映射记号处理器，静态内部类

        private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
    public List<ParameterMapping> getParameterMappings() {
            return parameterMappings;
        }
    public void setParameterMappings(List<ParameterMapping> parameterMappings){
        this.parameterMappings=parameterMappings;
    }
    private Map<String, String> parseParameterMapping(String content) {
            return null;
    }
        @Override
        public String handleToken(String content) {
            //先构建参数映射
            parameterMappings.add(buildParameterMapping(content));
            //如何替换很简单，永远是一个问号，但是参数的信息要记录在parameterMappings里面供后续使用
            return "?";
        }

        //构建参数映射
       private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping=new ParameterMapping(content);
        return parameterMapping;
       }



}



