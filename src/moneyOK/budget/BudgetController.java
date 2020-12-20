package moneyOK.budget;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import moneyOK.account.Account;
import moneyOK.account.AccountService;
import moneyOK.accountBook.AccountBook;
import moneyOK.accountBook.AccountBookService;
import moneyOK.chart.ChartService;
import moneyOK.item.Item;
import moneyOK.item.ItemService;
import moneyOK.user.User;
import moneyOK.user.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.json.simple.JSONValue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BudgetController extends MultiActionController{
	
	private UserService userService;
	private AccountBookService accountBookService;
	private BudgetService budgetService;
	private AccountService accountService;
	private ItemService itemService;
	private ChartService chartService;
	
	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	public void setAccountBookService(AccountBookService accountBookService) {
		this.accountBookService = accountBookService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ModelAndView updateBudgetJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String budgetStr = req.getParameter("datas");//JSON String
		Object obj = JSONValue.parse(budgetStr);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
		
		for(int i = 0 ; i < array.size();i++){//解析JSON String
			org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
			String budgetId      =String.valueOf(obj2.get("id"));
			String name =String.valueOf(obj2.get("name"));
			String description =String.valueOf(obj2.get("description"));
			String amount      =String.valueOf(obj2.get("amount"));  //預算額度
			String type      =String.valueOf(obj2.get("type"));  //分類(食衣住行..)
			String accountId   =String.valueOf(obj2.get("accountId"));
			
			//日期轉換
			String startDateString  =String.valueOf(obj2.get("startDate"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = (Date)formatter.parse(startDateString);
			String endDateString  =String.valueOf(obj2.get("endDate"));
			Date endDate = (Date)formatter.parse(endDateString);
			
			if(budgetId.equals("-1")){ //新增一budget
				Account account = this.accountService.findAccountById(accountId);
				Budget budget=new Budget();
				budget.setName(name);
				budget.setDescription(description);
				budget.setStartDate(startDate);
				budget.setEndDate(endDate);
				budget.setAmount(Integer.parseInt(amount));
				budget.setTotal(Integer.parseInt(amount));
				 //budget的Item
				Item item=this.itemService.findItemById(type);
				budget.setItem(item);
				this.budgetService.add(account, budget);
			}
			else{ //修改一budget
				Budget budget = this.budgetService.findBudgetById(budgetId);
				int oldAmount=budget.getAmount();
				budget.setName(name);
				budget.setDescription(description);
				budget.setAmount(Integer.parseInt(amount));
				budget.setStartDate(startDate);
				budget.setEndDate(endDate);
				//Item item=this.itemService.findItemById(type);
				//budget.setItem(item);
				budget=this.budgetService.updateBudgetAmount(budget,oldAmount); //amount有變時更新total
				this.budgetService.update(budget);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	
	public ModelAndView removeBudgetJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String budgetIds = req.getParameter("id");//JSON String				
		Object obj = JSONValue.parse(budgetIds);
		System.out.println(obj);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
		for(int i = 0 ; i < array.size();i++){
			String budgetId = String.valueOf(array.get(i));//JSON String
			Budget budget = this.budgetService.findBudgetById(budgetId);
			Account account=this.accountService.findAccountByBid(budgetId);
			this.budgetService.remove(account, budget);
		}
				
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	
	public ModelAndView getBudgetJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		List<Map<String, Object>> allBudgetList=new ArrayList<Map<String, Object>>();			
		for(Account account:accountbook.getAccounts()){
			for(Budget budget:account.getBudgets()){
				// 將date進行String化(因為json無法處理Date)
				Map<String, Object> budgetMap = new HashMap<String, Object>();
				budgetMap.put("id", budget.getId());
				budgetMap.put("accountId", account.getId());
				budgetMap.put("name", budget.getName());
				budgetMap.put("amount", budget.getAmount());
				budgetMap.put("total", budget.getTotal());
				budgetMap.put("description", budget.getDescription());
				budgetMap.put("type", budget.getItem().getId());
				budgetMap.put("startDate", budget.getStartDate().toString());
				budgetMap.put("endDate", budget.getEndDate().toString());
				allBudgetList.add(budgetMap);
			}
		}		
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("childCategory")|| name.equals("parentCategory")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
		
		JSONArray jsonArray = JSONArray.fromObject( allBudgetList); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		System.out.println("resultJSON:"+resultJSON);
		return null;
	}
	
	public ModelAndView getBudgetChartXML(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		res.setContentType("text/html;charset=UTF-8");
		String budgetChartXml = this.chartService.getBudgetXML(user);
		PrintWriter out = res.getWriter();
		out.print(budgetChartXml);
		System.out.println("budgetChartXml:"+budgetChartXml);
		return null;
	}
	
	public ModelAndView getBudgetsSumJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		int budgetsSum=this.budgetService.getBudgetsSum(user);//所有預算總合
		//budgetsSum JSON
		Map<String, Object> accountTotalMap = new HashMap<String, Object>();
		accountTotalMap.put("budgetsSum", budgetsSum); 
		JSONArray jsonArray = JSONArray.fromObject(accountTotalMap); 
		String resultStr = jsonArray.toString();
		PrintWriter out = res.getWriter();
		out.print(resultStr);
		System.out.println("getBudgetsSumJSON:"+resultStr);
		return null;
	}
}
