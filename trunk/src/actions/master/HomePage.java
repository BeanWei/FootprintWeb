package actions.master;

import hibernate.Master;
import hibernate.MasterDAO;
import hibernate.Role;
import hibernate.RoleDAO;

public class HomePage extends MasterInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * ���ʳ�������Ա��¼ҳ��
	 */
	@Override
	public String execute(){		
		//��ѯ�Ƿ��г�����������Ա�����û������볬������Աע��ҳ��
		MasterDAO masterDao = new MasterDAO();
		if(masterDao.findAll().size() == 0){
			return INPUT;
		}else
			return SUCCESS;
	}
}
