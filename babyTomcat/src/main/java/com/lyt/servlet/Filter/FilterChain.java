package com.lyt.servlet.Filter;


import com.lyt.servlet.ServletRequest;
import com.lyt.servlet.ServletResponse;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter{


    List<Filter> list = new ArrayList<Filter>();//存放过滤器的集合
    int index = 0;//计数器
    public int layer = 0;//责任链进入的层数
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {

        if (index == list.size()) return;//如果已经冲到list的结尾了就返回
        Filter filter = list.get(index);//没有到结尾就拿一个filter;
        index++;//自增
        filter.doFilter(req, res, chain);//往里执行
    }

    //用于添加过滤器，返回值是本对象，方便链式添加
    public FilterChain addFilter(Filter filter) {
        if (filter instanceof FilterChain) {//添加的时候判断添加的是否是责任链
            List<Filter> temlist=((FilterChain) filter).list;//获取子链中的Filter放到主链容器中
            for (Filter filter2 : temlist) {
                this.list.add(filter2);
            }
        }else    this.list.add(filter);//普通过滤器添加
        return this;
    }




    @Override
    public void destroy() {

    }
}
