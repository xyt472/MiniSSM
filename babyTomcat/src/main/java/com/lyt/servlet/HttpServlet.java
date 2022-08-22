package com.lyt.servlet;


import java.io.IOException;
import java.io.PrintWriter;

public abstract class HttpServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";

	private volatile String cachedAllowHeaderValue = null;

	public HttpServlet() {
		// NOOP
	}

	@Override
	public void init(ServletConfig config) throws Exception {

		super.init(config);

	}

	public void init(){

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {}

	/** 从request中取出method，判断是get还是post，调用doGet，doPost
	 * @param request 请求
	 * @param response 响应
	 */

	protected void service(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("调用的是httpServlet");
		String method = request.getMethod();
		if("GET".equals(method)){
			System.out.println("调用get");
			doGet(request,response);
		}else if("POST".equals(method)){
			System.out.println("调用post");
			doPost(request,response);
		}else {
			//跨域请求 在发送正式请求前 会发送一个 预请求  所以需要在这里直接返回一个 跨域验证的response
			try {

				//String res=response.genProtocol();
				System.out.println("返回跨域请求报文");
				PrintWriter printWriter =response.getWriter();
				printWriter.write(response.genProtocol());
				printWriter.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//还有 HEAD  TRACE DELETE PUT等方法
	}
	@Override
	public void service(ServletRequest req, ServletResponse res) {

		HttpServletRequest  request;
		HttpServletResponse response;
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) res;

		service(request, response);
	}
}

