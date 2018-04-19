package actions.master;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
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
		//��ȡsession���󣬵�sessionΪ��ʱ��Ҫ�����µ�session
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		if(session == null){
			this.setMessage("�Ự���ڣ������µ�½��");
			return ERROR;
		}
		
		RoleDAO roleDao = new RoleDAO();
		Role masterRole = roleDao.findById(1);
		Master master = null;
		MasterDAO masterDao = new MasterDAO();
		//���session�д���name����˵���ѵ�¼
		if(session.getAttribute("name") != null){
			//��session��ȡ��master�ĵ�½��Ϣ
			String name = (String)session.getAttribute("name");
			String password = (String)session.getAttribute("password");
			master = new Master(name, masterRole, password);
			
		}else{//���δ��½������Ҫ��ȡ��ҳ�洫��ĵ�½��Ϣ
			master = new Master(this.getName(), masterRole, this.getPassword());
		}
		
		@SuppressWarnings("unchecked")
		List<Master> masters = masterDao.findByExample(master);
		if(masters.size() == 0){
			this.setMessage("�û������������");
			return ERROR;
		}else{
			//����½��Ϣ�洢��session
			session.setAttribute("name", masters.get(0).getName());
			session.setAttribute("password", masters.get(0).getPassword());
			return SUCCESS;
		}
	}
}
