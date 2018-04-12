package actions.master;

import org.hibernate.Transaction;

import actions.DaoActionSupport;
import hibernate.Master;
import hibernate.MasterDAO;
import hibernate.Role;
import hibernate.RoleDAO;

public class MasterInfo extends DaoActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String password;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}

	/*
	 * ��������Աע��
	 */
	public String Register() throws Exception {
		
		if(!name.isEmpty() && !password.isEmpty()){
			MasterDAO dao = new MasterDAO();
			//�������ͬ�������û����򷵻ش���
			if(dao.findById(name) != null){
				this.setMessage("�Ѵ���:" + name);
				return ERROR;
			}
			
			RoleDAO roleDao = new RoleDAO();
			Role masterRole = roleDao.findById(1);
			
			Master master = new Master(name, masterRole, password);
			
			Transaction tx = dao.getSession().beginTransaction();
			dao.save(master);
			tx.commit();
			
			return SUCCESS;
		}else{
			this.setMessage("�û��������벻����Ϊ��!");
			return ERROR;
		}
			
	}

	/*
	 * ��������Աע��
	 */
	public String Delete() throws Exception{
		
		return SUCCESS;
	}
	
	/*
	 * ��������Ա��¼
	 */
	public String login(){
		RoleDAO roleDao = new RoleDAO();
		Role masterRole = roleDao.findById(1);
		//����master
		Master master = new Master(this.getName(), masterRole, this.getPassword());
		MasterDAO masterDao = new MasterDAO();
		
		if(masterDao.findByExample(master).size() == 0){
			this.setMessage("�û������������");
			return ERROR;
		}else 
			return SUCCESS;
	}
}
