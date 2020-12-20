package moneyOK.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import moneyOK.chart.ChartService;
import moneyOK.util.MD5;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserController extends MultiActionController{
	private UserService userService; 
	private ChartService chartService;
	
	public ChartService getChartService() {
		return chartService;
	}

	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public ModelAndView register(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String email=req.getParameter("email");
		String password=req.getParameter("password");		
		if(email==null&&password ==null){
			result.put("success", false);
			JSONObject jsonObject = JSONObject.fromObject( result );  
			System.out.println( "test:"+jsonObject);
			PrintWriter out = res.getWriter();
			out.print(jsonObject);
			return new ModelAndView("register");
		}
		else{
			User user=new User();
			user.setEmail(email);
			String md5password=MD5.encrypt(password);
			user.setPassword(md5password);	
			this.userService.register(user);
			this.userService.login(email, md5password);
						
			res.setContentType("text/html;charset=UTF-8");  
			result.put("success", true);
			JSONObject jsonObject = JSONObject.fromObject( result );  
			System.out.println( "test:"+jsonObject);
			PrintWriter out = res.getWriter();
			out.print(jsonObject);
		}
		return null;
	}
	
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = req.getSession();  //新增一session
		String email=req.getParameter("email");
		System.out.println("Email = "+email);
		String password=req.getParameter("password");
		System.out.println(password+"123");
		if(session.getAttribute("uid")!=null){
			result.put("success", false);
			User defaultUser = (User)session.getAttribute("user");
			String accountXml = this.chartService.getAccountXML(defaultUser);
			result.put("user",defaultUser);
			result.put("accountXml", accountXml);
			System.out.print("toMain");
			return new ModelAndView("main",result);
		}
		if(email==null&&password ==null){
			session.removeAttribute("uid");
			
			System.out.println("Null");
			return new ModelAndView("login");
		}

		String md5password=MD5.encrypt(password);
		User user = this.userService.login(email, md5password);
		if(user!=null){
			session.setAttribute("user", user);
			session.setAttribute("uid",Integer.toString(user.getId())); //session中給定uid
			res.setContentType("text/html;charset=UTF-8");  
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		JSONObject jsonObject = JSONObject.fromObject( result );  
		System.out.println( jsonObject);
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession();  //新增一session
		session.removeAttribute("uid");
		res.sendRedirect("user.do?action=login");
		//return new ModelAndView("login");
		return null;
	}
	
}
