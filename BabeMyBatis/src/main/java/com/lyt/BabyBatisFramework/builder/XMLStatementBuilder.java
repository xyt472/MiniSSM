package com.lyt.BabyBatisFramework.builder;

import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import com.lyt.BabyBatisFramework.Utils.ReflectUtils;
import com.lyt.BabyBatisFramework.config.Configuration;
import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.Collection;
import com.lyt.BabyBatisFramework.mapping.ColumnResult;
import com.lyt.BabyBatisFramework.mapping.ResultMap;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 专门解析select /insert 等statement标签  这里只写了 select
 */
public class XMLStatementBuilder {

    private Configuration configuration;
    public XMLStatementBuilder(Configuration configuration) {
        this.configuration=configuration;
    }


    //这个是做什么用的？
//    public void parseStatementElement(Element selectElement,String namespace)throws Exception {
//        //解析标签的属性
//        //获取id属性
//        String statementId=selectElement.attributeValue("id");
//        //如果是空的就不解析了
//        if(statementId==null||statementId.equals("")){
//            System.out.println("statementId是空的");
//            return;  //这里可以抛异常
//
//        }
//        //一个CRUD标签对应一个MapperStatement对象
//        //一个MapperStatement对象由一个 statementId来表示，所以保证唯一性
//        //statementId=namespace+"."+CRUD标签的id属性
//        statementId=namespace+"."+statementId;  //开始拼接
//
//        System.out.println("statementId :"+statementId);
//
//        //注意parameterType参数可以不设置也可以不解析
//        // 这是为什么呢？因为我们可以直接根据对象就可以获得参数  这是入参类型  #{}？
//        /*
//         String parameterType= selectElement.attributeValue("parameterType");
//        Class<?> parameterClass=resolveType(parameterType);
//         */
//
//        String resultType= selectElement.attributeValue("resultType"); //这是要映射的类型
//        //resolve用不了是因为缺少一个反射工具类
//         Class<?> resultClass= ReflectUtils.resolveType(resultType); //转化为一个class对象
//       // Class<?> resultClass=resultType.getClass(); //转化为一个class对象  上面这种方式是原视频的  这条是我自己写的 可能会报错
//
//        String statementType=selectElement.attributeValue("statementType");
//        //?????
//        statementType =statementType==null||statementType ==""?"prepared":statementType;
//        //TODO SqlSource和 SqlNode 的封装过程   SqlSource是定义好的一个接口
//        SqlSource sqlSource=createSqlSource(selectElement);
//        //ToDO 建议使用 构建这模式去优化
//        //还没完成  应该要有一个构造参数才行
//        MappedStatement mappedStatement=new MappedStatement(statementId,resultClass,statementType,sqlSource);
//        configuration.addMappedStatement(statementId,mappedStatement);
//    }
    //这个是做什么用的？
    public void parseStatementElement(Element selectElement,String namespace )throws Exception {

        //解析标签的属性
        //获取id属性
        String statementId=selectElement.attributeValue("id");
        String resultMap=selectElement.attributeValue("resultMap");


        //如果是空的就不解析了
        if(statementId==null||statementId.equals("")){
            System.out.println("statementId是空的");
            return;  //这里可以抛异常
        }
        //一个CRUD标签对应一个MapperStatement对象
        //一个MapperStatement对象由一个 statementId来表示，所以保证唯一性
        //statementId=namespace+"."+CRUD标签的id属性
        statementId=namespace+"."+statementId;  //开始拼接

        System.out.println("statementId :"+statementId);

        //注意parameterType参数可以不设置也可以不解析
        // 这是为什么呢？因为我们可以直接根据对象就可以获得参数  这是入参类型  #{}？
        String resultType= selectElement.attributeValue("resultType"); //这是要映射的类型
        Class<?> resultClass=null;
        //resolve用不了是因为缺少一个反射工具类
        if(resultType!=null){
            resultClass = ReflectUtils.resolveType(resultType); //转化为一个class对象
           // System.out.println("resultType :"+resultType);
        }
        // Class<?> resultClass=resultType.getClass(); //转化为一个class对象  上面这种方式是原视频的  这条是我自己写的 可能会报错

        String statementType=selectElement.attributeValue("statementType");
        //?????
        statementType =statementType==null||statementType ==""?"prepared":statementType;
        //TODO SqlSource和 SqlNode 的封装过程   SqlSource是定义好的一个接口
        SqlSource sqlSource=createSqlSource(selectElement);
        //ToDO 建议使用 构建这模式去优化
        //还没完成  应该要有一个构造参数才行
        MappedStatement mappedStatement=new MappedStatement(statementId,resultClass,statementType,sqlSource);
        if(resultMap!=null){
            mappedStatement.setResultMap(resultMap);
         //   System.out.println("已经为mappedStatement设置 resultMap属性该值为："+resultMap+"  该值应与resultMap标签中的id对应");
        }
        else {
        //    System.out.println("该select没有resultMap属性");
        }
        configuration.addMappedStatement(statementId,mappedStatement);
    }
//    //todo 这里极为可能出错
//    public void parseResultMap(Element resultMapElement )throws Exception{
//        ResultMap resultMap=new ResultMap();
//
//
//        //设置resultMapElement节点的属性
//        String ResultMapId= resultMapElement.attributeValue("id");
//        String type= resultMapElement.attributeValue("type");//这是要映射的类型
//        if(type==null){
//            System.out.println("type属性为空");
//        }
//
//        System.out.println("type的类型为"+type);
//        Class<?> typeClass= ReflectUtils.resolveType(type); //转化为一个class对象
//        //先设置属性
//        resultMap.setId(ResultMapId);
//        resultMap.setType(typeClass);
//
//        //可以使用这种方式去获取子节点
//        Element id=resultMapElement.element("id");//获取id子节点元素
//        String idColumn =id.attributeValue("column");
//        String idProperty=id.attributeValue("property");
//        resultMap.setIdColumn(idColumn);
//        resultMap.setIdProperty(idProperty);
//
//        //获取并设置result元素
//        List<Element> results=resultMapElement.elements("result");
//        List<ColumnResult> columnResultsInResultMap =new ArrayList<>();
//
//        for (Element result : results) {
//            //设置并获取所有的result行的属性
//            ColumnResult columnResult =new ColumnResult();
//            columnResult.setColumn(result.attributeValue("column"));
//            columnResult.setProperty(result.attributeValue("property"));
//            columnResultsInResultMap.add(columnResult);
//        }
//        //把resultMap中的result放进去
//        resultMap.setColumnResults(columnResultsInResultMap);
//
//        Element collectionElement =resultMapElement.element("collection");
//        String ofType =collectionElement.attributeValue("ofType");
//        Class<?> ofTypeClass= ReflectUtils.resolveType(ofType); //转化为一个class对象
//        String resultProperty=collectionElement.attributeValue("property");
//
//        List<Element> collectionResult=collectionElement.elements("result"); //里面有很多的result 节点有column 和property属性
//        List<ColumnResult> columnResultsInCollection =new ArrayList<>();
//
//
//        for (Element element : collectionResult) {
//            ColumnResult columnResult2 =new ColumnResult();
//            columnResult2.setColumn(element.attributeValue("column"));
//            columnResult2.setProperty(element.attributeValue("property"));
//            columnResultsInCollection.add(columnResult2);
//        }
//        //把columnResultsInCollection集合放入collection中
//        Collection collection=new Collection(ofType, ofTypeClass,resultProperty,columnResultsInCollection);
//        //再把collection放入resultMap中
//        resultMap.setCollection(collection);
//        System.out.println("打印一下resultMap"+resultMap);
//        //最终是要把生成的对象放入list当中
//
//        configuration.addResultMap(ResultMapId, resultMap);
//    }
    //todo 这里极为可能出错
    public void parseResultMap(Element resultMapElement )throws Exception{
        ResultMap resultMap=new ResultMap();

        //设置resultMapElement节点的属性
        String ResultMapId= resultMapElement.attributeValue("id");
        String type= resultMapElement.attributeValue("type");//这是要映射的类型
        if(type==null){
            System.out.println("type属性为空");
        }
        else{
            System.out.println("type属性bu为空");
        }
        System.out.println("type的类型为"+type);
        Class<?> typeClass= ReflectUtils.resolveType(type); //转化为一个class对象
        //先设置属性
        resultMap.setId(ResultMapId);
        resultMap.setType(typeClass);

        //可以使用这种方式去获取子节点
        Element id=resultMapElement.element("id");//获取id子节点元素
        String idColumn =id.attributeValue("column");
        String idProperty=id.attributeValue("property");
        resultMap.setIdColumn(idColumn);
        resultMap.setIdProperty(idProperty);

        //获取并设置 主对象的  result元素
        List<Element> results=resultMapElement.elements("result");

        resultMap.setIdSize(1+results.size());
        List<ColumnResult> columnResultsInResultMap =new ArrayList<>();

        for (Element result : results) {
            //设置并获取所有的result行的属性
            ColumnResult columnResult =new ColumnResult();
            columnResult.setColumn(result.attributeValue("column"));
            columnResult.setProperty(result.attributeValue("property"));
            columnResultsInResultMap.add(columnResult);
        }
        //把resultMap中的result放进去
        resultMap.setColumnResults(columnResultsInResultMap);

        Element collectionElement =resultMapElement.element("collection");

        // resultMap.setColumnResultName();

        String ofType =collectionElement.attributeValue("ofType");
        Class<?> ofTypeClass= ReflectUtils.resolveType(ofType); //转化为一个class对象
        String resultProperty=collectionElement.attributeValue("property");
        resultMap.setCollectionName(resultProperty);

        List<Element> collectionResult=collectionElement.elements("result"); //里面有很多的result 节点有column 和property属性
        List<ColumnResult> columnResultsInCollection =new ArrayList<>();

        //设置 嵌套类的行数
        resultMap.setColumnResultSize(collectionResult.size());

        for (Element element : collectionResult) {
            ColumnResult columnResult2 =new ColumnResult();
            columnResult2.setColumn(element.attributeValue("column"));
            columnResult2.setProperty(element.attributeValue("property"));
            columnResultsInCollection.add(columnResult2);
        }
        //把columnResultsInCollection集合放入collection中
        Collection collection=new Collection(ofType, ofTypeClass,resultProperty,columnResultsInCollection);
        //再把collection放入resultMap中
        resultMap.setCollection(collection);
        System.out.println("<><><><><><>打印一下resultMap"+resultMap);
        //最终是要把生成的对象放入list当中

        System.out.println("<><>><><><>addResultMap");
        configuration.addResultMap(ResultMapId, resultMap);
    }

    public void parseStatementElementV2(Element DMLElement,String namespace)throws Exception{
        //解析标签的属性
        //获取id属性
        String statementId=DMLElement.attributeValue("id");
        //如果是空的就不解析了
        //一个CRUD标签对应一个MapperStatement对象
        //一个MapperStatement对象由一个 statementId来表示，所以保证唯一性
        //statementId=namespace+"."+CRUD标签的id属性
        statementId=namespace+"."+statementId;  //开始拼接

       // System.out.println("statementId :"+statementId);

        //注意parameterType参数可以不设置也可以不解析
        // 这是为什么呢？因为我们可以直接根据对象就可以获得参数  这是入参类型  #{}？
        /*
         String parameterType= selectElement.attributeValue("parameterType");
        Class<?> parameterClass=resolveType(parameterType);
         */

       // String resultType= DMLElement.attributeValue("resultType"); //这是要映射的类型
        //resolve用不了是因为缺少一个反射工具类
       // Class<?> resultClass= ReflectUtils.resolveType(resultType); //转化为一个class对象
        // Class<?> resultClass=resultType.getClass(); //转化为一个class对象  上面这种方式是原视频的  这条是我自己写的 可能会报错

        String statementType=DMLElement.attributeValue("statementType");
        //?????
        statementType =statementType==null||statementType ==""?"prepared":statementType;
        //TODO SqlSource和 SqlNode 的封装过程   SqlSource是定义好的一个接口
        SqlSource sqlSource=createSqlSource(DMLElement);    //调用了parseScriptNode
        //ToDO 建议使用 构建这模式去优化
        //还没完成  应该要有一个构造参数才行
        System.out.println("parseStatementElementV2");
        MappedStatement mappedStatement=new MappedStatement(statementId,null,statementType,sqlSource);
        configuration.addMappedStatement(statementId,mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) throws Exception{
        //TODO 其他子标签的处理
        XMLScriptBuilder scriptBuilder=new XMLScriptBuilder();
        SqlSource sqlSource =scriptBuilder.parseScriptNode(selectElement);
        return  sqlSource;

    }


}
