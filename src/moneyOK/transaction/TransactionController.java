package moneyOK.transaction;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
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

public class TransactionController extends MultiActionController{
	private UserService userService;
	private TransactionService transactionService;
	private AccountService accountService;
	private AccountBookService accountBookService;
	private ItemService itemService;
	private ChartService chartService;
	
	public void setChartService(ChartService chartService) {
		this.chartService = chartService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setAccountBookService(AccountBookService accountBookService) {
		this.accountBookService = accountBookService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ModelAndView add(HttpServletRequest req, HttpServletResponse res) throws Exception
	{	
		Map<String, Object> result = new HashMap<String, Object>();
		String date=req.getParameter("date");
		String type=req.getParameter("type");
		String description=req.getParameter("description");
		String amount=req.getParameter("amount");
		String categoryId=req.getParameter("item");
		String accountId=req.getParameter("account");
		
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		Item foodItem = this.itemService.findItemById("1");
		User user = this.userService.getUserByUid(uid);	
		
		List<Item> itemList = this.itemService.findItemByParentIDAndUser(user,foodItem);
		result.put("subItem", itemList);
		result.put("user", user);
		if(categoryId==null||date==null||amount==null)
			return new ModelAndView("addTransaction",result);
		Account account=this.accountService.findAccountById(accountId);
		Item item=itemService.findItemById(categoryId);	
		VariableTransaction variableTransaction=new VariableTransaction();
		variableTransaction.setAmount(Integer.parseInt(amount));
		variableTransaction.setDescription(description);
		variableTransaction.setType(Boolean.parseBoolean(type));
		variableTransaction.setItem(item);
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date dates=format.parse(date);
			variableTransaction.setDate(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		transactionService.addVarTransaction(account, variableTransaction);
		res.sendRedirect("http://localhost:8080/moneyOK/transaction.do");
		return null;
	}

	public ModelAndView updateVarTransaction(HttpServletRequest req, HttpServletResponse res)throws Exception{
		if(req.getParameter("select")==null && req.getParameter("account")==null){
			return this.list(req, res);
		}	
		String selectTransactionId[] = req.getParameterValues("select");
		//String accountId[] = req.getParameterValues("account");
		for (int i = 0; i < selectTransactionId.length; i++) {
			//Account account = this.accountService.findAccountById(accountId[i]);
			try{
				VariableTransaction variableTransaction = this.transactionService.findVarTransactionById(selectTransactionId[i]);
				this.transactionService.updateVarTransaction(variableTransaction);
			}catch(Exception e){
				
			}
		}
		return this.list(req, res);
	}
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) throws Exception{
//		Map<String, Object> result = new HashMap<String, Object>();
//		HttpSession session = req.getSession(true);
//		String uid = (String) session.getAttribute("uid");
//		User user = this.userService.getUserByUid(uid);
//		AccountBook defaultAccountBook=this.accountBookService.getDefaultAccountBookByUser(user);
//		Item foodItem = this.itemService.findItemById("1");
//		List<Item> itemList = this.itemService.findItemByParentIDAndUser(user,foodItem);
//		result.put("subItem", itemList);
//		this.accountBookService.setTotalIncome(defaultAccountBook);
//		this.accountBookService.setTotalExpense(defaultAccountBook);
//		this.accountBookService.updateAccountBook(defaultAccountBook);
//		//String transactionXml = this.chartService.getTransactionXML(user);
//		result.put("transactionXml", transactionXml);
//		result.put("accountBooks",defaultAccountBook);
//		result.put("user", user);
//		return new ModelAndView("TransactionList", result);
		return null;
	}
	
	public ModelAndView getTransactionChartXML(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String dayNum = req.getParameter("dayNum"); //天數
		System.out.println("dayNum!!!"+dayNum);
		if(dayNum==null)
			dayNum="0";	
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		res.setContentType("text/html;charset=UTF-8");
		String transactionXml = this.chartService.getTransactionXML(user,dayNum);
		PrintWriter out = res.getWriter();
		out.print(transactionXml);
		return null;
	}
	
	public ModelAndView updateVarTransactionJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String tranStr = req.getParameter("datas");//JSON String
		System.out.println("Input:"+tranStr);
		Object obj = JSONValue.parse(tranStr);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		
		for(int i = 0 ; i < array.size();i++){
			org.json.simple.JSONObject obj2=(org.json.simple.JSONObject)array.get(i);
			String tranID      =String.valueOf(obj2.get("id"));
			String description =String.valueOf(obj2.get("description"));
			String amount      =String.valueOf(obj2.get("amount"));
			String type        =String.valueOf(obj2.get("type"));
			String itemId      =String.valueOf(obj2.get("itemId"));
			String itemName      =String.valueOf(obj2.get("itemName"));
			String dateString  =String.valueOf(obj2.get("date"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date)formatter.parse(dateString);
			String accountID   =String.valueOf(obj2.get("accountId"));
			String parentID    =String.valueOf(obj2.get("pId"));
			if(tranID.equals("-1")||tranID.equals("")){ //新增一VarTransaction
				Account account = this.accountService.findAccountById(accountID);
				VariableTransaction variableTransaction=new VariableTransaction();
				variableTransaction.setAmount(Integer.parseInt(amount));
				variableTransaction.setDate(date);
				variableTransaction.setDate(date);
				variableTransaction.setDescription(description);
				boolean typeBool=(type.equals("0")?false:true);
				variableTransaction.setType(typeBool);
				
				if(Integer.parseInt(itemId)<0){ //新增一新的使用者item
					Item newItem=new Item();
					System.out.println("newItem!!!");
					newItem.setName(itemName);
					Item parentCategory=this.itemService.findItemById(parentID);
					newItem.setParentCategory(parentCategory);
					newItem.setChildCategory(null);
					user=this.itemService.addItemObject(newItem, user);
					this.userService.update(user);
					variableTransaction.setItem(newItem);
				}
				else{ //資料庫已有，修改
					Item item=this.itemService.findItemById(itemId);
					Item parentCategory=this.itemService.findItemById(parentID);
					item.setParentCategory(parentCategory);
					variableTransaction.setItem(item);
					this.itemService.updateItem(item);
				}
				this.transactionService.addVarTransaction(account, variableTransaction);
			}
			else{ //修改一VarTransaction
				Account toAccount = this.accountService.findAccountById(accountID);
				Account fromAccount = this.accountService.findAccountByVarTransactionId(tranID);
				VariableTransaction variableTransaction = this.transactionService.findVarTransactionById(tranID);
				int oldAmount=variableTransaction.getAmount();
				boolean oldType=variableTransaction.isType();
				int newAmount=Integer.parseInt(amount);
				variableTransaction.setDescription(description);
				variableTransaction.setAmount(newAmount);
				boolean typeBool=(type.equals("0")?false:true);
				variableTransaction.setType(typeBool);
				variableTransaction.setDate(date);
				if(Integer.parseInt(itemId)<0){ //新增一新的使用者item
					Item newItem=new Item();
					newItem.setName(itemName);
					Item parentCategory=this.itemService.findItemById(parentID);
					newItem.setParentCategory(parentCategory);
					user=this.itemService.addItemObject(newItem, user);
					this.userService.update(user);
					variableTransaction.setItem(newItem);
				}
				else{ //資料庫已有，修改
					Item item=this.itemService.findItemById(itemId);
					Item parentCategory=this.itemService.findItemById(parentID);
					item.setParentCategory(parentCategory);
					variableTransaction.setItem(item);
					this.itemService.updateItem(item);
				}
				
				if(fromAccount.getId()!=toAccount.getId()){  //有變更transaction所屬account
					this.transactionService.changeTransactionAccount(fromAccount, toAccount, variableTransaction);					
				}
				//修改一account下的資訊
				this.accountService.updateAccountTotal(toAccount, variableTransaction, oldAmount,oldType);
				this.transactionService.updateVarTransaction(variableTransaction);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	public ModelAndView removeVarTransactionJSON(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String tranID = req.getParameter("id");//JSON String
		Object obj = JSONValue.parse(tranID);
		System.out.println(obj);
		org.json.simple.JSONArray array=(org.json.simple.JSONArray)obj;
				
		for(int i = 0 ; i < array.size();i++){
			String trid = String.valueOf(array.get(i));
			Account account =this.accountService.findAccountByVarTransactionId(trid);
			VariableTransaction variableTransaction=this.transactionService.findVarTransactionById(trid);
			this.transactionService.removeVarTransaction(account, variableTransaction);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();//true False
		result.put("success", true);
		JSONObject jsonObject = JSONObject.fromObject( result );  
		PrintWriter out = res.getWriter();
		out.print(jsonObject);
		return null;
	}
	
	public ModelAndView removeVarTransaction(HttpServletRequest req, HttpServletResponse res)throws Exception{
		if(req.getParameter("select")==null && req.getParameter("account")==null){
			return new ModelAndView("TransactionList");
		}	
		
		String selectTransactionId[] = req.getParameterValues("select");
		String accountId[] = req.getParameterValues("account");
		
		for (int i = 0; i < selectTransactionId.length; i++) {
			Account account = this.accountService.findAccountById(accountId[i]);
			try{
				VariableTransaction variableTransaction = this.transactionService.findVarTransactionById(selectTransactionId[i]);
				this.transactionService.removeVarTransaction(account,variableTransaction);
			}catch(Exception e){
				
			}
		}
		return this.list(req, res);
	}
		
	public ModelAndView getVarTransactionJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		AccountBook defaultAccountBook=this.accountBookService.getDefaultAccountBookByUser(user);
		this.accountBookService.setTotalIncome(defaultAccountBook);
		this.accountBookService.setTotalExpense(defaultAccountBook);
		this.accountBookService.updateAccountBook(defaultAccountBook);
		
		List<Map<String, Object>> allAccountList=new ArrayList<Map<String, Object>>();
		for(Account account:defaultAccountBook.getAccounts()){
			for(VariableTransaction variableTransaction:account.getVar_transaction()){
				//將date進行String化(因為json無法處理Date)
				Map<String, Object> allAccountMap = new HashMap<String, Object>();
				allAccountMap.put("accountId",account.getId());
				allAccountMap.put("amount",variableTransaction.getAmount());
				allAccountMap.put("description",variableTransaction.getDescription());
				allAccountMap.put("id",variableTransaction.getId());
				allAccountMap.put("itemId",variableTransaction.getItem().getId());
				allAccountMap.put("itemName",variableTransaction.getItem().getName());
				allAccountMap.put("pId",variableTransaction.getItem().getParentCategory().getId());
				allAccountMap.put("date",variableTransaction.getDate().toString());
				allAccountMap.put("type",variableTransaction.isType());
				allAccountList.add(allAccountMap);
			}
		}		
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("users") || name.equals("childCategory")|| name.equals("parentCategory") ||name.equals("total")
					|| name.equals("fix_transaction")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
		JSONArray jsonArray = JSONArray.fromObject( allAccountList,config ); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		System.out.println("VarTrresultJSON:"+resultJSON);
		return null;
	}
	
	public ModelAndView getTotalJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String dayNum = req.getParameter("dayNum"); //天數
		if(dayNum==null)
			dayNum="0";	
		System.out.println("dayNum:"+dayNum);
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		AccountBook defaultAccountBook=this.accountBookService.getDefaultAccountBookByUser(user);
		this.accountBookService.setTotalIncome(defaultAccountBook);
		this.accountBookService.setTotalExpense(defaultAccountBook);
		this.accountBookService.updateAccountBook(defaultAccountBook);
		List<VariableTransaction> allVarList=new ArrayList<VariableTransaction>();
		for(Account account:defaultAccountBook.getAccounts()){
			if(dayNum.equals("0")){
				for(VariableTransaction variableTransaction:account.getVar_transaction()){
					//將date進行String化(因為json無法處理Date)
					allVarList.add(variableTransaction);
				}
			}
			else
			{
				List<VariableTransaction> tempList=new ArrayList<VariableTransaction>();
				tempList=this.transactionService.findVariableTransactionByDay(dayNum, account.getId());
				for(int i=0;i<tempList.size();i++)
					allVarList.add(tempList.get(i));
			}
		}
		Map<String, Object> totalPropertyMap = new HashMap<String, Object>();
		totalPropertyMap.put("totalProperty", allVarList.size());//加入transaction總數以便分頁
		int totalIncome=this.transactionService.getTransationTotalIncome(allVarList);
		int totalExpense=this.transactionService.getTransationTotalExpense(allVarList);
		totalPropertyMap.put("totalIncome", totalIncome);
		totalPropertyMap.put("totalExpense", totalExpense);
		
		
		JSONArray jsonArray = JSONArray.fromObject( totalPropertyMap); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		System.out.println("VarTrresultJSON:"+resultJSON);
		return null;
	}
	
	public ModelAndView getDateVarTransactionJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String dayNum = req.getParameter("dayNum"); //天數
		if(dayNum==null)
			dayNum="0";	
		if(dayNum.equals("0")){
			this.getVarTransactionJSON(req, res);
		}
		else{
			HttpSession session = req.getSession(true);
			String uid = (String) session.getAttribute("uid");
			User user = this.userService.getUserByUid(uid);
			AccountBook defaultAccountBook=this.accountBookService.getDefaultAccountBookByUser(user);
			this.accountBookService.setTotalIncome(defaultAccountBook);
			this.accountBookService.setTotalExpense(defaultAccountBook);
			this.accountBookService.updateAccountBook(defaultAccountBook);
			
			List<Map<String, Object>> allAccountList=new ArrayList<Map<String, Object>>();
			for(Account account:defaultAccountBook.getAccounts()){
				List<VariableTransaction> trList= this.transactionService.findVariableTransactionByDay(dayNum,account.getId());
				for(int i=0;i<trList.size();i++){
					if(trList.size()!=0){
						VariableTransaction vrt=trList.get(i);
						//將date進行String化(因為json無法處理Date)
						Map<String, Object> trMap = new HashMap<String, Object>();
						trMap.put("accountId",account.getId());
						trMap.put("id",vrt.getId());
						trMap.put("amount",vrt.getAmount());
						trMap.put("description",vrt.getDescription());
						trMap.put("itemId",vrt.getItem().getId());
						Item item=this.itemService.findItemById(Integer.toString(vrt.getItem().getId()));
						trMap.put("itemName",item.getName());
						trMap.put("pId",item.getParentCategory().getId());
						trMap.put("date",vrt.getDate().toString());
						trMap.put("type",vrt.isType());
						allAccountList.add(trMap);
					}
				}
			}
			JsonConfig config = new JsonConfig();  
			config.setJsonPropertyFilter(new PropertyFilter(){
			public boolean apply(Object source, String name, Object value) {
				if(name.equals("users") || name.equals("childCategory")|| name.equals("parentCategory") ||name.equals("total")
						|| name.equals("fix_transaction")) {
					return true;
				} 
				else {
					return false;
				}
			}
			});
			JSONArray jsonArray = JSONArray.fromObject( allAccountList,config ); 
			String resultJSON = jsonArray.toString();
			res.setContentType("text/html;charset=UTF-8");  
			PrintWriter out = res.getWriter();
			out.print(resultJSON);
			System.out.println("TrresultJSON:"+resultJSON);
		}
		return null;
	}
	
	public ModelAndView getTypeJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		List<Item> parentList=new ArrayList<Item>();
		Item parent1 =new Item();
		parent1.setId(1);
		parent1.setName("食");
		parentList.add(parent1);
		Item parent2 =new Item();
		parent2.setId(2);
		parent2.setName("衣");
		parentList.add(parent2);
		Item parent3 =new Item();
		parent3.setId(3);
		parent3.setName("住");
		parentList.add(parent3);
		Item parent4 =new Item();
		parent4.setId(4);
		parent4.setName("行");
		parentList.add(parent4);
		Item parent5 =new Item();
		parent5.setId(5);
		parent5.setName("育");
		parentList.add(parent5);
		Item parent6 =new Item();
		parent6.setId(6);
		parent6.setName("樂");
		parentList.add(parent6);
		
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("users") || name.equals("childCategory")||name.equals("parentCategory")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
		JSONArray jsonArray = JSONArray.fromObject( parentList,config ); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		return null;
	}
	
	public ModelAndView getItemJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		
		List<Item> itemList=new ArrayList<Item>();
		for(Item item:user.getItems()){
				itemList.add(item);	
		}
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("users") || name.equals("childCategory")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
		JSONArray jsonArray = JSONArray.fromObject( itemList,config ); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		return null;
	}
	
	public ModelAndView getAccountJSON(HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession(true);
		String uid = (String) session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		List<Account> accountList=new ArrayList<Account>();
		AccountBook defaultAccountBook=this.accountBookService.getDefaultAccountBookByUser(user);
		
		for(Account account:defaultAccountBook.getAccounts()){
			accountList.add(account);
		}
		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("description") ||name.equals("type")||name.equals("total")|| name.equals("var_transaction")||name.equals("fix_transaction")
					||name.equals("amount")||name.equals("budgets")) {
				return true;
			} 
			else {
				return false;
			}
		}
		});
				 
		JSONArray jsonArray = JSONArray.fromObject( accountList,config ); 
		String resultJSON = jsonArray.toString();
		res.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = res.getWriter();
		out.print(resultJSON);
		return null;
	}
}    