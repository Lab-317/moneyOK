package moneyOK.item;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import moneyOK.user.User;
import moneyOK.user.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ItemController extends MultiActionController {
	private ItemService itemService;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public ModelAndView add(HttpServletRequest req, HttpServletResponse res) {
		String id=req.getParameter("parentCategory");
		String userItem=req.getParameter("userCategory");
		if(id==null && userItem==null){
			return new ModelAndView("addItem");
		}
		int parentId=Integer.parseInt(id);
		Item parentCategory=itemService.getParentItem(parentId);
		Item userCategory=new Item();
		userCategory.setName(userItem);
		userCategory.setParentCategory(parentCategory);
		userCategory.setChildCategory(null);
		
		Set childSet=parentCategory.getChildCategory(); //取得parentCategory的childSet
		childSet.add(userCategory); //將使用者自定的項目加入parent的childSet
		parentCategory.setChildCategory(childSet);
		
		HttpSession session=req.getSession();
		User user=userService.getUserByUid((String)session.getAttribute("uid"));
		user=itemService.addItemObject(userCategory, user);
		this.userService.update(user);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", user);
		return new ModelAndView("main",result);
	}
	public ModelAndView getItem(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String pid = req.getParameter("Pid");
		Item item = this.itemService.findItemById(pid);
		HttpSession session = req.getSession(true);
		String uid = (String)session.getAttribute("uid");
		User user = this.userService.getUserByUid(uid);
		List<Item> itemList = this.itemService.findItemByParentIDAndUser(user, item);

		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply(Object source, String name, Object value) {
			if(name.equals("users") || name.equals("childCategory")||name.equals("parentCategory")) {
				return true;
			} else {
				return false;
			}
		}
		});
		res.setContentType("text/html;charset=UTF-8");   
		JSONArray jsonArray = JSONArray.fromObject( itemList,config ); 
		String result = jsonArray.toString();
		PrintWriter out = res.getWriter();
		out.print(result);
		return null;
	}
}