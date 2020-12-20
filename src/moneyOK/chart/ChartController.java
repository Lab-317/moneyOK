package moneyOK.chart;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import moneyOK.user.User;
import moneyOK.user.UserService;

import org.blackbear.util.velocity.VelocityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ChartController extends MultiActionController{
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
	public ModelAndView getAccount(HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String,Object> graphInfo = new HashMap<String,Object>();
		String xml = new VelocityUtil().merge("/moneyOK/velocity/Hello.vm",graphInfo);
		//String xml = VelocityUtil.merge("velocity/Hello.vm",graphInfo);
		res.setContentType( "text/xml; charset=UTF-8" );
		OutputStream outs = res.getOutputStream();
		outs.write( new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF} );
		outs.flush();
		
		PrintWriter out = res.getWriter();
		out.print(xml);
		return null;
		//return new ModelAndView("velocity/data"); 
	}
	public ModelAndView getMonthlyAnalysis(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		res.setContentType( "text/xml; charset=UTF-8" );
		String result = this.chartService.getMonthlyAnalysis(user);
		PrintWriter out = res.getWriter();
		out.print(result);
		return null;
	}
	public ModelAndView getMonthlyAnalysisDetail(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		System.out.println(uid);
		User user = this.userService.getUserByUid(uid);
		res.setContentType( "text/xml; charset=UTF-8" );
		int month = Integer.valueOf(req.getParameter("month"));
		int catgory = Integer.valueOf(req.getParameter("catgory"));
		String result = this.chartService.getMonthlyAnalysisDetail(user, month, catgory);
		PrintWriter out = res.getWriter();
		System.out.println(result);
		out.print(result);
		return null;
	}
}
