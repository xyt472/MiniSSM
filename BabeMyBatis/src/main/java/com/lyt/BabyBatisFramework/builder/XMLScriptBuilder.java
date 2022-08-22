package com.lyt.BabyBatisFramework.builder;

import com.lyt.BabyBatisFramework.SqlSource.DynamicSqlSource;
import com.lyt.BabyBatisFramework.SqlSource.RawSqlSource;
import com.lyt.BabyBatisFramework.SqlSource.SqlSource;
import com.lyt.BabyBatisFramework.sqlNode.*;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 专门解析sql脚本的  sqlNode的信息
 *
 */
public class XMLScriptBuilder {
    private   boolean isDynamic=false;
    public XMLScriptBuilder( ) {
    }

    public SqlSource parseScriptNode(Element selectElement) throws  Exception{
        //解析所有sql节点，最终封装到MixedSqlNode当中
        SqlNode mixedSqlNode=parseDynamicTags(selectElement);
        SqlSource sqlSource;
        //根据有无标签判断是静态还是动态 如果带有 $#或者动态sql标签
        //isDynamic全局变量 默认是false  应该还有一个复制操作

        if(isDynamic){   //为什么要这么做？是因为处理的逻辑是不一样的
            //Dynamic处理${}和动态标签的工作都是交给了SqlNode本身去处理 它本身只是处理了#{}(没有#{}则不做处理)
            //如果既有$ 又有#就交由dynamic去处理
            sqlSource=new DynamicSqlSource(mixedSqlNode);
        }else {
            //Raw处理#{ }   是交给  去处理的TokenParser TokenHandler
            sqlSource=new RawSqlSource(mixedSqlNode);
        }
        return sqlSource;
    }

    private SqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> sqlNodes =new ArrayList<SqlNode>();
        //获取select标签的子元素 ：文本类型或者Element类型
        int nodeCount =selectElement.nodeCount();
        for (int i = 0; i <nodeCount ; i++) {
            //设置化节点的值
            Node node =selectElement.node(i);
            //如果不是文本节点的话
            if(node instanceof Text){
                String text=node.getText();
                if(text==null){
                    continue;
                }

                if("".equals(text.trim())){
                    continue;
                }
                //将sql文本封装到TextSqlNode中
                TextSqlNode textSqlNode=new TextSqlNode(text.trim());
                if(textSqlNode.isDynamic()){  //如果有$符号设置成动态的
                    sqlNodes.add(textSqlNode);
                    isDynamic=true;
                }else{
                    //那么就是静态的  放入静态节点当中
                    sqlNodes.add(new StaticTextSqlNode((text.trim())));
                }

            }
            //如果都不是  而是元素节点的话  那就获取节点的名称
            else if(node instanceof  Element){
                isDynamic=true;   //设置为true是因为肯定会有参数？？
                //转换
                Element element =(Element)node;
                String name =element.getName();
                if("if".equals(name)){
                    //这有什么用呢？为什么是test  因为test是命名空间
                    String test=element.attributeValue("test");
                    //递归去解析子元素  为什么要递归？因为节点里面还会有节点
                    SqlNode sqlNode=parseDynamicTags(element);
                    IfSqlNode ifSqlNode=new IfSqlNode(test,sqlNode);  // 这里会设置mixedSqlNode
                    sqlNodes.add(ifSqlNode);
                }else {
                    //Todo
                }
            }else{
                //TODO
            }
        }
        //最后返回复合节点
        return new MixedtSqlNode(sqlNodes);  //最终必然会返回一个混合节点  他可能只有文本
        //调用构造函数 仅仅初始化sqlNodeList成员，而不初始化 sqlText
    }

    // todo  DML指的是数据库操作语言  insert update   使用java来传递参数比较好？
    private SqlNode parseDynamicTagsV2(Element DMLElement){
        List<SqlNode> sqlNodes =new ArrayList<SqlNode>();
        //获取select标签的子元素 ：文本类型或者Element类型
        int nodeCount =DMLElement.nodeCount();
        for (int i = 0; i <nodeCount ; i++) {
            //设置化节点的值
            Node node =DMLElement.node(i);
            //如果不是文本节点的话
            if(node instanceof Text){
                String text=node.getText();
                if(text==null){
                    continue;
                }
                //这有什么用呢？
                if("".equals(text.trim())){
                    continue;
                }
                //将sql文本封装到TextSqlNode中
                TextSqlNode textSqlNode=new TextSqlNode(text.trim());
                if(textSqlNode.isDynamic()){  //如果有$符号设置成动态的
                    sqlNodes.add(textSqlNode);
                    isDynamic=true;
                }else{
                    //那么就是静态的  放入静态节点当中
                    sqlNodes.add(new StaticTextSqlNode((text.trim())));
                }
            }
        }
        //最后返回复合节点
        return new MixedtSqlNode(sqlNodes);  //最终必然会返回一个混合节点  他可能只有文本
        //调用构造函数 仅仅初始化sqlNodeList成员，而不初始化 sqlText

    }

}
