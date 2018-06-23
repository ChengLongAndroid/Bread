package bases_cl.user;

import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.UserChestProto;
import com.google.protobuf.GeneratedMessage;

import flex.messaging.io.ArrayList;

//用户id  宝箱数   宝箱类型集合  宝箱获取时间
public class UserChest implements ProtobufSerializable{

	private int id;
	private int count;
	private int type;
	private long date;
	
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		// TODO Auto-generated method stub
		UserChestProto userChestProto=(UserChestProto) message; 
		this.id=(int) userChestProto.getId();
		this.count=userChestProto.getCount();
		this.type=userChestProto.getType();
		this.date=userChestProto.getDate();
	}
	@Override
	public GeneratedMessage copyTo() {
		// TODO Auto-generated method stub
		UserChestProto.Builder usBuilder=UserChestProto.newBuilder();
		usBuilder.setId(id);
		usBuilder.setCount(count);
		usBuilder.setType(type);
		usBuilder.setDate(date);
		
		return usBuilder.build();
	}
	@Override
	public void parseFrom(byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			UserChestProto userChestProto=UserChestProto.parseFrom(bytes);
			copyFrom(userChestProto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return copyTo().toByteArray();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "UserChest [id=" + id + ", count=" + count + ", type=" + type
				+ ", date=" + date + "]";
	}
	public UserChest(int id, int count,int type, long date) {
		super();
		this.id = id;
		this.count = count;
		this.type = type;
		this.date = date;
	}
	public UserChest() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}