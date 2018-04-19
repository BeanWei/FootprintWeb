package actions.user;

import hibernate.Role;
import hibernate.RoleDAO;
import hibernate.User;
import hibernate.UserDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Transaction;

import actions.DaoActionSupport;

public class UserInfo extends DaoActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�û���
	private String name; 
	//����
	private String password;
	//�Ա� true:����, false:Ů��
	private Boolean sex; 
	//��ӳ����ļ��ֶ�
	private File portrait;
	//struts2�ļ��ϴ�ʱ�ṩ������
	private String portraitFileName;
	//�ļ��ϴ���MIME����
	private String portraitContentType;
	
	//��ɫ��ʶ��Ĭ��Ϊ��ͨ�û�3
	private int roleid = 3;
	
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
	
	public void setSex(Boolean sex){
		this.sex = sex;
	}
	
	public Boolean getSex(){
		return this.sex;
	}
	
	public File getPortrait(){
		return this.portrait;
	}
	
	public void setPortrait(File portrait){
		this.portrait = portrait;
	}
	
	public String getPortraitFileName(){
		return this.portraitFileName;
	}
	
	public void setPortraitFileName(String portraitFileName){
		this.portraitFileName = portraitFileName;
	}
	
	public String getPortraitContentType(){
		return this.portraitContentType;
	}
	
	public void setPortraitContentType(String portraitContentType){
		this.portraitContentType = portraitContentType;
	}
	
	public int getRoleid(){
		return this.getRoleid();
	}
	
	public void setRoleid(int roleid){
		this.roleid = roleid;
	}
	
	/*
	 * �û�ע�����
	 */
	public String Register() throws Exception{
		//�������û���ɫ����
		setRoleid(3);
		return saveUser();
	}
	
	protected String saveUser() throws Exception{
		//��ѯ��ͨ�û���ɫ
		RoleDAO roleDao = new RoleDAO();
		Role role = roleDao.findById(this.roleid);
		
		//����user
		User user = new User(role, this.name, this.password);
		user.setSex(this.sex);
		UserDAO userDao = new UserDAO();
		
		//��������û������������Ϣ�������ش���ҳ��
		if(userDao.findByName(this.name).size() > 0){
			this.setMessage("�Ѵ����û���" + this.name);
			return ERROR;
		}
		
		try{
			//���ļ���תΪblob�洢��user��portrait�ֶ���
			InputStream iStream = new FileInputStream(this.portrait);
			Blob portrait = Hibernate.getLobCreator(userDao.getSession()).createBlob(iStream, iStream.available());
			user.setPortrait(portrait);
			
			Transaction tx = userDao.getSession().beginTransaction();
			userDao.save(user);
			tx.commit();
			
		}catch(Exception e){
			throw e;
		}
		
		return SUCCESS;
	}
	
	//�û���¼����
	public String login(){
		UserDAO userDao = new UserDAO();
		List<User> result = userDao.findByName(this.name);
		//�����ѯ���Ϊ�գ���˵���û�������
		if(result.size() == 0){
			this.setMessage("�������û���" + this.name);
			return ERROR;
		}else{
			//�ڲ�ѯ����жԱ�����
			for(int i=0; i<result.size(); i++){
				if(result.get(i).getPassword().equals(this.password)){
					return SUCCESS;
				}
			}
			this.setMessage("�û���Ϊ" + this.name + "���������벻��ȷ!");
			return ERROR;
		}
	}
	
}
