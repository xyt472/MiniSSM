package com.lyt.BabyBatisFramework.handler;

import com.lyt.BabyBatisFramework.config.MappedStatement;
import com.lyt.BabyBatisFramework.mapping.ColumnResult;
import com.lyt.BabyBatisFramework.mapping.ResultMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class DeafaultResultSetHandler  implements ResultSetHandler {


    @Override
    public List<Object> handleResultSet(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement)throws  Exception {
        //遍历查询结果集  clazz是一个映射后的类型
        Class clazz = mappedStatement.getResultTypeClass();  //
        //通过构造器去创建对象
        Constructor<?> constructor=null;
        if(clazz!=null){
            constructor= clazz.getDeclaredConstructor();  //获得被映射对象的构造函数
        }
        List<Object> results=new ArrayList<>();
        //实例化呢？ 还差v1的代码
        Object result = null;
        while (rs.next()) {
            //当返回的是  String  、 Inteager 、等等 单个数据的时候
            if(clazz==null){
                ResultSetMetaData metaData = rs.getMetaData();//有什么作用？
                result=rs.getObject(metaData.getColumnName(1));
                results.add( result);    //放入list中
            }else {
                result = constructor.newInstance();   //
                //获取结果集中列的信息
                ResultSetMetaData metaData = rs.getMetaData();//有什么作用？
                int columnCount = metaData.getColumnCount();
                //开始赋值
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1); //获取列名
                    //通过反射给指定的列对象的属性名称赋值
                    //列名和属性名要一致
                    Field field = clazz.getDeclaredField(columnName);  //根据列名 来
                    //暴力破解，破坏封装 。可以访问私有成员
                /*
                如果该构造方法的权限为 private，默认为不允许通过反射利用 netlnstance()
                方法创建对象。如果先执行该方法，并将入口参数设置为 true，则允许创建对
                象
                 */
                    field.setAccessible(true);
                    //设置属性值
                    field.set(result, rs.getObject(columnName));
                }
                results.add( result);    //放入list中
            }
        }
        System.out.println("results :"+results);
        return results;
    }
    //  最终的使用可以通过判断 每一个select中有无 resultMap来决定使用哪一个结果集处理器

//    public List<Object> handleNestedResultSet0(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement, ResultMap resultMap) throws Exception{
//        //遍历查询结果集  clazz是一个映射后的类型
//        //Class clazz = mappedStatement.getResultTypeClass();  //
//        Class type=resultMap.getType();  //type是映射的主类型
//        Class ofType=resultMap.getCollection().getOfTypeClass();  //这是获取到嵌套类型的对象类型
//        Configuration configuration =new Configuration();
//        //创建一个RowKey来存放 主类型的数据  因为他们都是相同的 只需要放相同类型的即可
//        //key就用 id标签中 id的值即可 只有不同的id才可以放进去
//        String  idColumn=resultMap.getIdColumn();
//        String  idProperty=resultMap.getIdProperty();
//        String nestedObjectProperty=resultMap.getCollection().getResultProperty();//// 被嵌套的对象属性名称
//
//        //实例化呢？ 还差v1的代码
//        Submenu submenu=new Submenu();
//        ResultSetMetaData metaData = rs.getMetaData();//获取元数据对象
//        int columnCount = metaData.getColumnCount(); //获取元数据的行数
//        System.out.println("嵌套查询的列数 ："+columnCount);
//        String  re=resultMap.getColumnResults().get(0).getColumn();
//        Map<Object,Object> cache =new HashMap<>();  //暂存唯一id的地方
//        List<Object> results=new ArrayList<>();
//        List<Object> ofTypeResult=new ArrayList<>();
//        List <Object> key=new LinkedList<>();
//        List <Object> key2=new LinkedList<>();
//        ResultSet rs2=rs;
//        while (rs.next()) {
//            //这是执行的行数
//            Object result = type.getDeclaredConstructor().newInstance();  //
//            Object collectionResult = ofType.getDeclaredConstructor().newInstance();  //
//            MetaObject typeMetaObject=configuration.newMetaObject(result);
//            MetaObject ofTypeMetaObject=configuration.newMetaObject(collectionResult);
//
//
//
//            //获取 并设置 只能 唯一的id
//            if(idProperty!=null){
//                typeMetaObject.setValue(idProperty,rs.getObject(idColumn));//根据列名设置 被映射对象的参数
//            }
//            //尝试一下双指针
//            while (rs2.next()){
//                //对ofType进行分组  每一组都放进一个list中
//                //这算嵌套对象的  列遍历  ofType
//                key2.add(rs.getObject(resultMap.getIdColumn()));
//                if( key2.contains(rs.getObject(resultMap.getIdColumn()))){
//                    for (ColumnResult columnResult : resultMap.getCollection().getColumnResults()) {
//                        ofTypeMetaObject.setValue(columnResult.getProperty(),rs.getObject(columnResult.getColumn()));
//                        //System.out.println("开始设置ofTypeMetaObject对象 的每一列 属性值  值为："+rs.getObject(columnResult.getColumn()));
//                    }
//                    ofTypeResult.add(ofTypeMetaObject.getOriginalObject());
//                }else {
//
//                }
//
//
//
//            }
//
//            //获得 主对象中的 列  并且暂时存储起来
//            //至少要循环好3遍
//            //key是对应的唯一 id名称   根据列名获取到对应的属性值
//            //因为有很多的key是重复的 所以当key相同时会被覆盖掉  相应的对象也会被覆盖掉
//            //主对象的id的值作为key  值为null  复合条件的才可一放入集合当中
//            if(!key.contains(rs.getObject(resultMap.getIdColumn()))){
//                System.out.println("Key不相同 开始设置主对象中嵌套的collection");
//                for (ColumnResult columnResult : resultMap.getColumnResults()) {
//                    //根据定义好的result标签中的列名 设置对应对象中属性的值
//                    typeMetaObject.setValue(columnResult.getProperty(),rs.getObject(columnResult.getColumn()));
//                    //找到resultMap中的属性
//                }
//                typeMetaObject.setValue(nestedObjectProperty,ofTypeResult);
//                System.out.println("打印一下 typeMetaObject的原始对象"+typeMetaObject.getOriginalObject());
//                results.add(typeMetaObject);
//            }else {
//
//            }
//            key.add(rs.getObject(resultMap.getIdColumn()));
//            if(  key.contains(rs.getObject(resultMap.getIdColumn()))){
//                ofTypeResult.clear();
//                typeMetaObject.setValue(nestedObjectProperty,ofTypeResult);
//                ofTypeResult.add(ofTypeMetaObject);
//            }
//            // ofTypeResult.add(ofTypeMetaObject.getOriginalObject());
//            String keyString=rs.getObject(resultMap.getIdColumn()).toString();
//            cache.put(keyString,ofTypeMetaObject.getOriginalObject());
//
//        }
//
//
//        //对resultSet去重
//        System.out.println("不支持嵌套映射！");
//        return null;
//
//    }
//    @Override
//    public List<Object> handleNestedResultSet(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement, ResultMap resultMap) throws Exception{
//        //遍历查询结果集  clazz是一个映射后的类型
//        //Class clazz = mappedStatement.getResultTypeClass();  //
//        Class type=resultMap.getType();  //type是映射的主类型
//        Class ofType=resultMap.getCollection().getOfTypeClass();  //这是获取到嵌套类型的对象类型
//        Configuration configuration =new Configuration();
//        //创建一个RowKey来存放 主类型的数据  因为他们都是相同的 只需要放相同类型的即可
//        //key就用 id标签中 id的值即可 只有不同的id才可以放进去
//        String  idColumn=resultMap.getIdColumn();
//        String  idProperty=resultMap.getIdProperty();
//        String nestedObjectProperty=resultMap.getCollection().getResultProperty();//// 被嵌套的对象属性名称
//
//        //实例化呢？ 还差v1的代码
//        Submenu submenu=new Submenu();
//        ResultSetMetaData metaData = rs.getMetaData();//获取元数据对象
//        int columnCount = metaData.getColumnCount(); //获取元数据的行数
//        System.out.println("嵌套查询的列数 ："+columnCount);
//        String  re=resultMap.getColumnResults().get(0).getColumn();
//        Map<Object,Object> cache =new HashMap<>();  //暂存唯一id的地方
//        List<Object> results=new ArrayList<>();
//        List<Object> ofTypeResult=new ArrayList<>();
//        List <Object> key=new LinkedList<>();
//        List <Object> key2=new LinkedList<>();
//        ResultSet rs2=rs;
//        return  null;
//    }
public List<Object> handleNestedResultSet(PreparedStatement preparedStatement, ResultSet rs, MappedStatement mappedStatement, ResultMap resultMap) throws Exception{
    //遍历查询结果集  clazz是一个映射后的类型
    //Class clazz = mappedStatement.getResultTypeClass();  //
    Class type=resultMap.getType();  //type是映射的主类型
    Class ofType=resultMap.getCollection().getOfTypeClass();  //这是获取到嵌套类型的对象类型

    System.out.println("<>><><><><><><>type 是："+type.getName());
    System.out.println("《》《》《》《》《》of type 是 ："+ofType.getName());
    //创建一个RowKey来存放 主类型的数据  因为他们都是相同的 只需要放相同类型的即可
    //key就用 id标签中 id的值即可 只有不同的id才可以放进去
    String  idColumn=resultMap.getIdColumn();
    String  idProperty=resultMap.getIdProperty();
    //实例化呢？ 还差v1的代码
//        ResultSetMetaData metaData = rs.getMetaData();//获取元数据对象
//        int columnCount = metaData.getColumnCount(); //获取元数据的行数
//        System.out.println("嵌套查询的列数 ："+columnCount);
    Map<String,Object> RowKey =new HashMap<>();  //暂存唯一id的地方
    List<Object> ofTypeResult=null;
    Object result = null;
    while (rs.next()){
        Object collectionResult = ofType.getDeclaredConstructor().newInstance();  //
        //先 给嵌套的对象注入
        //如果key不存在的话就创建主对象 直接填充
        if(!RowKey.containsKey(rs.getString(idProperty))){
            ofTypeResult =new ArrayList<>();
            for (Field declaredField : ofType.getDeclaredFields()) {
                declaredField.setAccessible(true);
                for (ColumnResult columnResult : resultMap.getCollection().getColumnResults()) {
                    if(declaredField.getName().equals(columnResult.getProperty())){
                        declaredField.set(collectionResult,rs.getObject(columnResult.getColumn()));
                    }
                }
            }
            ofTypeResult.add(collectionResult);
            result = type.getDeclaredConstructor().newInstance();  //
            //  MetaObject typeMetaObject=configuration.newMetaObject(result);
            if(idProperty!=null){
                for (Field declaredField : type.getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    if(declaredField.getName().equals(resultMap.getIdProperty())){
                        declaredField.set(result,rs.getObject(resultMap.getIdColumn()));
                    }
                    for (ColumnResult columnResult : resultMap.getColumnResults()) {
                        if(declaredField.getName().equals(columnResult.getProperty())){
                            declaredField.set(result,rs.getObject(columnResult.getColumn()));
                        }
                    }
                    if(declaredField.getName().equals(resultMap.getCollectionName())){
                        if(result!=null){
                            declaredField.set(result,ofTypeResult);
                        }else {
                            System.out.println("result 是空的的");
                        }
                    }
                }
            }
            RowKey.put(rs.getString(idColumn),result);
        }else {
            //如果id存在的话 则从填充复合属性 即只 填充嵌套类型的对象 不填充 主对象
            //根据list的名称   获取嵌套的 list
            for (Field declaredField : ofType.getDeclaredFields()) {
                declaredField.setAccessible(true);
                for (ColumnResult columnResult : resultMap.getCollection().getColumnResults()) {
                    if(declaredField.getName().equals(columnResult.getProperty())){
                        declaredField.set(collectionResult,rs.getObject(columnResult.getColumn()));
                    }
                }
            }
            ofTypeResult.add(collectionResult);
            for (Field declaredField : RowKey.get(rs.getString(idColumn)).getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                //修改 嵌套对象
                if(declaredField.getName().equals(resultMap.getCollectionName())){
                    declaredField.set(result,ofTypeResult);
                }
            }
        }
    }
    List<Object> result2=new ArrayList<>();
    for (Object value : RowKey.values()) {
        result2.add(value);
    }

    System.out.println("嵌套查询实现的结果集 ："+result2);
    return result2;
}

}


