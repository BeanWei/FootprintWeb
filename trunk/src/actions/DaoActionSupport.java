package actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Dao Action��Ļ��࣬���ڶ���һЩ�紫����Ϣ�ı�����Ϣ
 */
public class DaoActionSupport extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
