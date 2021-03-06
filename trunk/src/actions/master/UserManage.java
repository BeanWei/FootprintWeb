package actions.master;

import hibernate.User;
import hibernate.UserDAO;

import java.util.List;

import actions.user.UserInfo;
import hibernate.converter.ESCTransformer;
import hibernate.converter.UserListConverter;

public class UserManage extends UserInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<User> users;
	String usersJsonString;
	
	public void setUsers(List<User> users){
		this.users = users;
	}
	
	public List<User> getUsers(){
		return this.users;
	}
	
	public void setUsersJsonString(String usersJsonString){
		this.usersJsonString = usersJsonString;
	}
	
	public String getUsersJsonString(){
		return this.usersJsonString;
	}
	
	public String users(){
		UserDAO userDao = new UserDAO();
		users = userDao.findAll();
		
		//将user列表转换成JSON字符串，便于前台取值
		UserListConverter converter = new UserListConverter();
		usersJsonString = converter.toJSONArray(users).toString();
		
		//需要使用transformer转换器进行字符转换，否则有些转义字符JSON.parse()无法解析
		usersJsonString = ESCTransformer.transformESC(usersJsonString);
		
		return SUCCESS;
	}
	
	/*
	 * 添加管理员用户
	 */
	public String addAdmin() throws Exception{
		//设置角色类型为管理员
		setRoleid(2);
		return this.saveUser();
	}
}
