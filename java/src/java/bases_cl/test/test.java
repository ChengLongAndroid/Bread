package bases_cl.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import bases_cl.user.User;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;


public class test {
static JRedis jRedis ;


private byte[] getUserKey(long roleId) {
	return  RedisStorageKey.getByteArray(roleId, (short)3);  //规则3
	
}

	
private byte[] getRoleDressInfoKey(long roleId) {
	return RedisStorageKey.getByteArray(roleId, (short)1);
}
	


//@Override
//public GeneratedMessage copyTo() {
//	User.Builder buidler = User.newBuilder();
//	buidler.setRoleId(1);
//	User user=new User();
//	for (User dressVo : user) {
//		buidler.addDresses(dressVo.toString());
//	}
//	return buidler.build();
//}


//保存
	public void saveRole(User user) throws IOException {
		byte[] key = getUserKey(user.getUserid());
		byte[] bytes = toByteArray(user);
		this.jRedis.set(key, bytes);
	}
	
	
	public byte[] toByteArray(Object object) throws IOException {
		  ByteArrayOutputStream bo = new ByteArrayOutputStream();  
	        ObjectOutputStream oo = new ObjectOutputStream(bo);  
	        oo.writeObject(object);  
	        byte[] bytes = bo.toByteArray();  
	        bo.close();  
	        oo.close();  
		return bytes;
	}


	@Test
    public void testString() throws IOException {
		
		User u=new User(1, "fds", "fds", 1, 1, 1);
		//存
		saveRole(u);
		
		
		
       //-----添加数据----------  
//		byte[] key = getRoleDressInfoKey(123);
//		byte[] bytes = roleDressInfo.toByteArray();
//		jedis.set(key, bytes);
        
  }
	
	
	

	
}
