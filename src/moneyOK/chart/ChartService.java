package moneyOK.chart;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import moneyOK.account.Account;
import moneyOK.account.AccountService;
import moneyOK.accountBook.AccountBook;
import moneyOK.accountBook.AccountBookService;
import moneyOK.item.ItemService;
import moneyOK.user.User;
import moneyOK.user.UserService;

import org.blackbear.util.velocity.VelocityUtil;

public class ChartService {
	private UserService userService;
	private AccountService accountService;
	private AccountBookService accountBookService;
	private ItemService itemService;
	private ChartDAO chartDAO;
	
	public ChartDAO getChartDAO() {
		return chartDAO;
	}

	public void setChartDAO(ChartDAO chartDAO) {
		this.chartDAO = chartDAO;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
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
	public String getAccountXML(User user) throws Exception{
		AccountBook accountBook = this.accountBookService.getDefaultAccountBookByUser(user);
		TreeSet<Account>accounts = new TreeSet<Account>(accountBook.getAccounts());
		
		Map<String,Object> xmlContent = new HashMap<String,Object>();
		xmlContent.put("accounts", accounts);
		String xml = new VelocityUtil().merge("/moneyOK/velocity/Hello.vm",xmlContent);
		xml = xml.replaceAll("\r\n","");
		System.out.println(xml);
		return xml;
	}
	public String getTransactionXML(User user,String dayNum) throws Exception{
		AccountBook accountBook=this.accountBookService.getDefaultAccountBookByUser(user);
		Map<String,Object> parentCategoryTotalMap = new HashMap<String,Object>();		
		try{
			for(int i=1;i<7;i++){//食衣住行育樂的transaction
				int parentCategoryTotal=itemService.findParentCategoryTotalByUserAndIid(accountBook, i,dayNum);		
				parentCategoryTotalMap.put(Integer.toString(i), parentCategoryTotal);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("parentCategoryTotalMap:"+parentCategoryTotalMap.size());
		accountBook.setParentCategoryTotalMap(parentCategoryTotalMap);
		this.accountBookService.updateAccountBook(accountBook);
		Map<String,Object> xmlContent = new HashMap<String,Object>();
		
		xmlContent.put("accountBook", accountBook);
		
		String xml = new VelocityUtil().merge("/moneyOK/velocity/ItemPieChart.vm",xmlContent);
		xml = xml.replaceAll("\r\n","");
		System.out.println(xml);
		return xml;
	}
	
	public String getBudgetXML(User user) throws Exception{
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		TreeSet<Account>accounts = new TreeSet<Account>(accountbook.getAccounts());
		for(Account account:accountbook.getAccounts()){
			accounts.add(account);
			//for(Budget budget:account.getBudgets()){
				//Budgets.add(budget);
			//}
		}
		Map<String,Object> xmlContent = new HashMap<String,Object>();
		xmlContent.put("accounts", accounts);
		String xml = new VelocityUtil().merge("/moneyOK/velocity/BudgetBarChart.vm",xmlContent);
		xml = xml.replaceAll("\r\n","");
		System.out.println(xml);
		return xml;
	}
	public String getMonthlyAnalysis(User user) throws Exception{
		Map totalMap = new HashMap();
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		System.out.println(accountbook.getId());
		for(int i = 1 ; i<=12;i++){
			Map<Object,Map<String,Object>>  map = this.chartDAO.findMonthSumary(accountbook.getId(),i);
			Map<Object,Map<String,Object>> catogoryMap = new HashMap<Object,Map<String,Object>>();
			for(int j = 1 ; j <= 6 ; j++){
				Map<String,Object> subMap = map.get(j);
				if(subMap == null){
					subMap = new HashMap<String,Object>();
					subMap.put("parentCatID",j);
					subMap.put("total", 0);
				}
				catogoryMap.put(String.valueOf(j), subMap);
			}
			totalMap.put(String.valueOf(i), catogoryMap);
		}
		Map result = totalMap;
		Map catgoryMapping = new HashMap();
		Map colorMapping = new HashMap();
		catgoryMapping.put("1", "食");
		catgoryMapping.put("2", "衣");
		catgoryMapping.put("3", "住");
		catgoryMapping.put("4", "行");
		catgoryMapping.put("5", "育");
		catgoryMapping.put("6", "樂");
		
		colorMapping.put("1", "A0009E");
		colorMapping.put("2", "A0004E");
		colorMapping.put("3", "A00200");
		colorMapping.put("4", "A05200");
		colorMapping.put("5", "9EA000");
		colorMapping.put("6", "4EA000");
		
		Map content = new HashMap();
		content.put("result",result);
		content.put("catgoryName",catgoryMapping );
		content.put("colorName",colorMapping);
		String xml = new VelocityUtil().merge("/moneyOK/velocity/analysis.vm",content);
		xml = xml.replaceAll("\r\n","");
		System.out.println(xml);
		return xml;
	}
	public String getMonthlyAnalysisDetail(User user,int month,int catgoryID) throws Exception{
		Map totalMap = new HashMap();
		AccountBook accountbook = this.accountBookService.getDefaultAccountBookByUser(user);
		System.out.println(accountbook.getId());
		Map detail = this.chartDAO.findMonthTransactionDetail(accountbook.getId(), month, catgoryID);
		Map<String,Object> xmlContent = new HashMap<String,Object>();
		xmlContent.put("detail", detail);
		String xml = new VelocityUtil().merge("/moneyOK/velocity/Detail.vm",xmlContent);
		return xml;
	}
}
