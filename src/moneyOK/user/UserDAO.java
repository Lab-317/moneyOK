package moneyOK.user;

import java.util.List;

import moneyOK.util.CommonDAO;

public class UserDAO extends CommonDAO {
	public void add(User user){
		this.hibernateTemplate.merge(user);
	}
	
	@SuppressWarnings("unchecked")
	public User findUserByUid(int id){
		List<User> user = this.hibernateTemplate.find("From User user where user.id = ?",id);
		return user.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUserByEmailAndPassword(String email,String password){
		List<User> user = this.hibernateTemplate.findByExample(new User(email,password));
		return user;
	}
	
	public void update(User user){
		this.hibernateTemplate.merge(user);
	}
}
