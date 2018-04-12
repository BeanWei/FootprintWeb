package actions;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.MasterDAO;

public class InitSystem extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		MasterDAO dao = new MasterDAO();
		
		//������ݿ��в����ڳ�������Ա�û�������Ҫ�´���һ��
		if(dao.findAll().size() == 0)
			return "masterRegister";
		else
			return "userLogin";
	}
}
