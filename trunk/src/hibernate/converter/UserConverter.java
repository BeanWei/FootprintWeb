package hibernate.converter;

import hibernate.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import net.sf.json.JSONObject;
import ognl.DefaultTypeConverter;
import sun.misc.BASE64Encoder; 

public class UserConverter extends DefaultTypeConverter implements IObjectBaseConverter{
	
	@Override
	public Object convertValue(Map context, Object value, Class toType){
		//�ӿͻ��˴���������ΪString���飬��Ϊname��ͬ��input�ȿؼ������ж��
		if(User.class == toType){
			User user = new User();
			
			//��ʱ��֧��StringתObject
			return null;
		}
		
		//�ӷ������˷������ݵ��ͻ��ˣ����ͽ��ΪString���ͣ�JSON��ʽ��
		if(String.class == toType){
			return toJSONObject(value).toString();
		}
		return null;
	}

	@Override
	public JSONObject toJSONObject(Object object){
		// TODO Auto-generated method stub
		User user = (User)object;
		
		JSONObject userJSON = new JSONObject();
		userJSON.put("userid", user.getUserid());
		
		JSONObject roleJSON = new JSONObject();
		roleJSON.put("roleid", user.getRole().getRoleid());
		roleJSON.put("name", user.getRole().getName());
		roleJSON.put("des", user.getRole().getDes());
		userJSON.put("role", roleJSON);
		
		userJSON.put("name", user.getName());
		userJSON.put("password", user.getPassword());

		try {
			InputStream iStream = user.getPortrait().getBinaryStream();
			ByteArrayOutputStream oStream = new ByteArrayOutputStream(iStream.available());
			byte[] buffer = new byte[1024]; 
			int n = 0;  
			while (-1 != (n = iStream.read(buffer))) {  
				oStream.write(buffer, 0, n);  
            }  
			byte[] data = oStream.toByteArray(); 
			BASE64Encoder encoder = new BASE64Encoder();
		
			String dataImage = new String(encoder.encode(data));
			
			String json ="data:img/jpg;base64," + dataImage;  
			//StringEscapeUtils.escapeJava(json);
			
			userJSON.put("portrait", json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userJSON.put("sex", user.getSex());
		
		return userJSON;
	}
}
