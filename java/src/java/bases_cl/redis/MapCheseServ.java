package bases_cl.redis;

import org.springframework.beans.factory.annotation.Autowired;

import bases_cl.map.MapChese;
import bases_cl.user.UserChest;
import bases_cl.user.UserEquip;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;

public class MapCheseServ {
	@Autowired
	private JRedis jRedis;
	
	private byte[] getUserKey(long roleId) {
		return  RedisStorageKey.getByteArray(roleId, (short)4);  //规则4
		
	}
	
	//添加
		public void adduser(MapChese userChest) {
			 
			byte[] key = getUserKey(userChest.getUserid());
			byte[] bytes = userChest.toByteArray();
			this.jRedis.set(key, bytes);
			
			
		}

		
		//提取
		public MapChese getUserInfo(long userId) {
			byte[] key = getUserKey(userId);
			byte[] value = jRedis.get(key);
			if(value == null) {
				MapChese mapChese = new MapChese();
				
				return mapChese;
			} else {
				MapChese mapChese = new MapChese();
				mapChese.parseFrom(value);
				return mapChese;
			}
		}
}
