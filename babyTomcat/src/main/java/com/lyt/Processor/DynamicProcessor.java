package com.lyt.Processor;

//import com.lyt.AtianSpringMvc.servlet.DispatcherServlet;
import com.lyt.servlet.HttpServlet;
import com.lyt.servlet.HttpServletRequest;
import com.lyt.servlet.HttpServletResponse;
import com.lyt.servlet.ServletContext;
import org.junit.Test;

public class DynamicProcessor implements Processor{

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response)throws Exception {

        //1、假设请求的地址为  /hello.action  ->对应的是hello 这个servlet 类
        //从request中取出getRequestURI
//        String uri = request.getRequestURI();
//        System.out.println("uri "+uri);
        //2、去掉 / 及action 两个部分 ，得到hello  ，就是servlet类名
      //  String servletName = uri.substring(1,uri.indexOf("."));
        //单实例实现  ->到Map中判断是否已经存在了这个servlet实例，如果有，则取出这个实例

        HttpServlet servlet=null;

        //利用反射来加载这个类的字节码  URLClassLoader 来加载class
        //todo 这里应该改为 xml 来加载
        if(!ServletContext.servlets.containsKey("DispatcherServlet")) {

            System.out.println("使用反射生成 servlet");
            Class clazz = Class.forName("com.lyt.servlet.DispatcherServlet");
//            Class clazz2 = Class.forName("com.lyt.servlet.DispatcherServlet");
           System.out.println("<>======================<><><><><><>获取包名"+clazz.getPackageName());
             servlet = (HttpServlet) clazz.getConstructor().newInstance();

            ServletContext.servlets.put("DispatcherServlet", servlet);
            servlet.init();   //这样就实现了 int()只被调用一次
            // HttpServlet httpServlet=
        }else {
            System.out.println("从容器当中取出servelet");
            servlet = (HttpServlet) ServletContext.servlets.get("DispatcherServlet");
        }

        //这里也是执行过滤器的入口？

        servlet.service(request,response);

       //======================================================
        //没有servlet实例
//        if(!ServletContext.servlets.containsKey(servletName)){
//            try {
//                Class c = Class.forName(servletName);
//                //2、利用class实例化newInstance()->  servlet初始化
//                Object obj = c.getConstructor().newInstance();
//                //将servlet放入ServletContext中
//                ServletContext.servlets.put(servletName, obj);
//                if(obj instanceof HttpServlet){
//                    servlet = (HttpServlet)obj;
//                    servlet.init();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            servlet = (HttpServlet) ServletContext.servlets.get(servletName);
//        }
//        servlet.service(request, response);

    }

    @Test
    public void test()throws  Exception{
        Class clazz=Class.forName("com.lyt.AtianSpringMvc.servlet.DispatcherServlet");
        Object obj = clazz.getConstructor().newInstance();
        if(obj!=null){
            System.out.println(obj.getClass().getName());
        }else {
            System.out.println("komh");
        }
    }
}
