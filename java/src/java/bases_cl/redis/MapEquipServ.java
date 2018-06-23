package bases_cl.redis;

import org.springframework.beans.factory.annotation.Autowired;

import bases_cl.map.MapEquip;
import bases_cl.user.UserChest;
import bases_cl.user.UserEquip;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;

public class MapEquipServ {
	@Autowired
	private JRedis jRedis;
	
	private byte[] getUserKey(long roleId) {
		return  RedisStorageKey.getByteArray(roleId, (short)5);  //规则5
		
	}
	
	//添加
		public void adduser(MapEquip userChest) {
			 
			byte[] key = getUserKey(userChest.getUserid());
			byte[] bytes = userChest.toByteArray();
			this.jRedis.set(key, bytes);
			
			
		}

		
		
		
		//提取
		public MapEquip getUserInfo(long userId) {
			byte[] key = getUserKey(userId);
			byte[] value = jRedis.get(key);
			if(value == null) {
				MapEquip mpEquip = new MapEquip();
				return mpEquip;
			} else {
				MapEquip mpEquip = new MapEquip();
				mpEquip.parseFrom(value);
				return mpEquip;
			}
		}
}
