package hibernate.converter;

import net.sf.json.JSONArray;

public interface IArrayBaseConverter {
	
	/*
	 * ����̳�Java����תJSON����ӿ�
	 * @param Object Java����
	 * @return JSONArray JSON����
	 */
	public JSONArray toJSONArray(Object object);
}
