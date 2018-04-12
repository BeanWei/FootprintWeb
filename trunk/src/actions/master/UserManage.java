package actions.master;

import hibernate.User;
import hibernate.UserDAO;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import hibernate.converter.ESCTransformer;
import hibernate.converter.UserListConverter;

public class UserManage extends MasterInfo{

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
		
		//��user�б�ת����JSON�ַ���������ǰ̨ȡֵ
		UserListConverter converter = new UserListConverter();
		usersJsonString = converter.toJSONArray(users).toString();
		
		//��Ҫʹ��transformerת���������ַ�ת����������Щת���ַ�JSON.parse()�޷�����
		usersJsonString = ESCTransformer.transformESC(usersJsonString);
		
		return SUCCESS;
	}
	
}
