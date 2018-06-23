package bases_cl.redis;

import org.springframework.beans.factory.annotation.Autowired;

import bases_cl.user.UserChest;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;

public class ChestServ {
	
	@Autowired
	private JRedis jRedis;
	

	private byte[] getUserKey(long roleId) {
		return  RedisStorageKey.getByteArray(roleId, (short)3);  //规则3
		
	}
	
	
	
	//添加
			public void adduser(UserChest userChest) {
				 
				byte[] key = getUserKey(userChest.getId());
				byte[] bytes = userChest.toByteArray();
				this.jRedis.set(key, bytes);
				
				//PersonProtos.Person person = personBuilder.build();
			}

			
			
			
			//提取
			public UserChest getUserInfo(long userId) {
				byte[] key = getUserKey(userId);
				byte[] value = jRedis.get(key);
				if(value == null) {
					UserChest user = new UserChest();
					
					return user;
				} else {
					UserChest user = new UserChest();
					user.parseFrom(value);
					return user;
				}
			}
}
