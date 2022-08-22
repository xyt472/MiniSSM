package com.lyt.filter;



import com.lyt.servlet.annotation.WebFilter;


@WebFilter(filterName = "Filter", urlPatterns = {"/*"} )
public class Filter  {
//    public void destroy() {
//    }
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//       req.getParameter("sds");
//
//        /**
//         * 可以对拦截的请求进行增强处理
//         * 对拦截的请求进行合法性判
//          */
//        HttpServletResponse httpServletResponse=(HttpServletResponse)resp;
//        req.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html; charset=UTF-8");
//        doOptions(httpServletResponse); //解决跨域问题
//        chain.doFilter(req, resp); //放行请求
//    }
//    public void init(FilterConfig config) throws ServletException {
//
//    }
//    public void doOptions(HttpServletResponse resp) throws ServletException, IOException {
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        resp.setHeader("Access-Control-Allow-Methods", "*");
//        resp.setHeader("Access-Control-Max-Age", "3600");
//        resp.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
//                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
//        resp.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
//        resp.setHeader("Access-Control-Expose-Headers", "*");
//    }
}
