package bases_cl.redis;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import bases_cl.user.User;
import bases_cl.user.UserChest;
import bases_cl.user.UserEquip;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;

public class PropServ {
	
	@Autowired
	private JRedis jRedis;
	
	private byte[] getUserKey(long roleId) {
		return  RedisStorageKey.getByteArray(roleId, (short)2);  //规则2
		
	}
	
	//添加
		public void adduser(UserEquip userChest) {
			 
			byte[] key = getUserKey(userChest.getUserid());
			byte[] bytes = userChest.toByteArray();
			this.jRedis.set(key, bytes);
			
			
		}

		
		
		
		//提取
		public UserEquip getUserInfo(long userId) {
			byte[] key = getUserKey(userId);
			byte[] value = jRedis.get(key);
			if(value == null) {
				UserEquip userEquip = new UserEquip();
				
				return userEquip;
			} else {
				UserEquip userEquip = new UserEquip();
				userEquip.parseFrom(value);
				return userEquip;
			}
		}
		
}
