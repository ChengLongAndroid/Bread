package bases_cl.redis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Repository;

import bases_cl.user.User;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;
import com.funcy.g01.base.bo.chest.RoleChestInfo;
import com.funcy.g01.base.bo.dress.DressVo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleDressInfoProto;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.UserProto;
import com.google.protobuf.GeneratedMessage;


@Repository
public class UserServ {
	
	@Autowired
	private JRedis jRedis;
	
	private byte[] getUserKey(long roleId) {
		return  RedisStorageKey.getByteArray(roleId, (short)1);  //规则1
		
	}
	//添加
	public void adduser(User user) throws IOException{
		 
		byte[] key = getUserKey(user.getUserid());
		byte[] bytes = user.toByteArray();
		this.jRedis.set(key, bytes);
		
		
	}

	
	
	
	//提取
	public User getUserInfo(long userId) {
		byte[] key = getUserKey(userId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			User user = new User();
			
			return user;
		} else {
			User user = new User();
			user.parseFrom(value);
			return user;
		}
	}
	
	
	
}
