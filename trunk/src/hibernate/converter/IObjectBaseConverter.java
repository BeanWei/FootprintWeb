package hibernate.converter;

import net.sf.json.JSONObject;

public interface IObjectBaseConverter{
	
	/*
	 * ����̳�Java����תJSON����ӿ�
	 * @param Object Java����
	 * @return JSONObject JSON����
	 */
	public JSONObject toJSONObject(Object object);
}
