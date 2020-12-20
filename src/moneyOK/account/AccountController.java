package moneyOK.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import moneyOK.accountBook.AccountBook;
import moneyOK.accountBook.AccountBookService;
import moneyOK.chart.ChartService;
import moneyOK.user.User;
import moneyOK.user.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.json.simple.JSONValue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AccountController extends MultiActionController{
	private AccountService accountService;
	private UserService userService;
	private AccountBookService accountBookService;
	private ChartService chartService;
	
	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}
	public AccountBookService getAccountBookService() {
		return accountBookService;
	}
	public void setAccountBookService(AccountBookService accountBookService) {
		this.accountBookService = accountBookService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public ModelAndView add(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session  = req.getSession();
		if(req.getParameter("account")==null || req.getParameter("amount")==null)
			return new ModelAndView("addAccount");
		String accountName = req.getParameter("account");
		String description = req.getParameter("description");
		String amount      = req.getParameter("amount");
		String category    = req.getParameter("category");
		Account account = new Account(accountName,Integer.valueOf(amount));
		account.setType(category);
		account.setDescription(description);
		User user = this.userService.getUserByUid((String) session.getAttribute("uid"));
		AccountBook accountBook  = this.accountBookService.getDefaultAccountBookByUser(user);
		this.accountService.addAccount(accountBook, account);
		user = this.userService.getUserByUid((String) session.getAttribute("uid"));
		res.sendRedirect("http://localhost:8080/moneyOK/account.do");
		return list(req,res);
	}
		
	public ModelAndView updateAccount(HttpServletRequest req, HttpServletResponse res) throws Exception{
		if(req.getParameter("accountId")==null){
			return list(req,res);
		}
		String selectAccountId[] = req.getParameterValues("accountId");
		try{
			for (int i = 0; i < selectAccountId.length; i++) {
				Account account = this.accountService.findAccountById(selectAccountId[i]);
				this.accountService.updateAccount(account);
			}
		}catch(Exception e){
		}
		return list(req,res);
	}
	public ModelAndView updateAccountJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String accountStr = req.getParameter("datas");//JSON String
		System.out.println(accountStr);
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		Object obj = JSONValue.parse(accountStr);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
		for(int i = 0 ; i < array.size();i++){
			org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
			String accountID   =String.valueOf(obj2.get("id"));
			String accountName =String.valueOf(obj2.get("name"));
			String total       =String.valueOf(obj2.get("total"));
			String type        =String.valueOf(obj2.get("type"));
			String description =String.valueOf(obj2.get("description"));
			if(accountID.equals("-1")){
				Account account = new Account();
				account.setName(accountName);
				account.setTotal(Integer.parseInt(total));
				account.setDescription(description);
				account.setType(type);
				this.accountService.addAccount(accountbook, account);
			}else{
				Account account = this.accountService.findAccountById(accountID);
				account.setName(accountName);
				account.setDescription(description);
				account.setType(type);
				this.accountService.updateAccount(account);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	public ModelAndView removeAccountJSON(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String accountIDs = req.getParameter("id");//JSON String
		System.out.print(accountIDs);
		
		Object obj = JSONValue.parse(accountIDs);
		System.out.println(obj);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
		for(int i = 0 ; i < array.size();i++){
			String id = String.valueOf(array.get(i));
			Account account = this.accountService.findAccountById(id);
			this.accountService.removeAccount(account);
		}
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	public ModelAndView removeAccount(HttpServletRequest req, HttpServletResponse res) throws Exception{
		if(req.getParameter("select")==null){
			return list(req,res);
		}
		String selectAccountId[] = req.getParameterValues("select");
		try{
			for (int i = 0; i < selectAccountId.length; i++) {
				Account account = this.accountService.findAccountById(selectAccountId[i]);
				this.accountService.removeAccount(account);
			}
		}catch(Exception e){
		}
		return list(req,res);
	}
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		String accountXml = this.chartService.getAccountXML(user);
		result.put("user", user);
		result.put("accountXml", accountXml);
		return new ModelAndView("main", result);
	}
	public ModelAndView getAccountChartXML(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		res.setContentType("text/html;charset=UTF-8");
		String accountXml = this.chartService.getAccountXML(user);
		PrintWriter out = res.getWriter();
		out.print(accountXml);
		return null;
	}
	public ModelAndView getAccountTypeJSON(HttpServletRequest req, HttpServletResponse res) throws IOException{
		List list = new ArrayList();
		Map<String,Object> cash = new HashMap<String,Object>();
		Map<String,Object> account = new HashMap<String,Object>();
		Map<String,Object> card = new HashMap<String,Object>();
		res.setContentType("text/html;charset=UTF-8");
		cash.put("id", 0);
		cash.put("name", "現金");
		account.put("id", 1);
		account.put("name","銀行帳戶/郵局");
		card.put("id",2);
		card.put("name","儲值卡");
		list.add(cash);
		list.add(account);
		list.add(card);
		JSONArray jsonObject = JSONArray.fromObject(list);  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	public ModelAndView getAccountsJson(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		Set<Account> accounts=accountbook.getAccounts();
		res.setContentType("text/html;charset=UTF-8");
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("fix_transaction") ||name.equals("users") || name.equals("var_transaction")||name.equals("budgets")
					||name.equals("budgets")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
		JSONArray jsonArray = JSONArray.fromObject(accounts,config); 
		String resultStr = jsonArray.toString();
		PrintWriter out = res.getWriter();
		out.print(resultStr);
		System.out.println("accJSON:"+resultStr);
		return null;
	}
	
	public ModelAndView getAccountTotalJSON(HttpServletRequest req, HttpServletResponse res) throws IOException{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		int total=this.userService.getUserTotalMoney(user);
		//total JSON
		Map<String, Object> accountTotalMap = new HashMap<String, Object>();
		accountTotalMap.put("accountTotal", total); //使用者總資產	
		JSONArray jsonArray = JSONArray.fromObject(accountTotalMap); 
		String resultStr = jsonArray.toString();
		PrintWriter out = res.getWriter();
		out.print(resultStr);
		System.out.println("getAccountTotalJSON:"+resultStr);
		return null;
	}
}
