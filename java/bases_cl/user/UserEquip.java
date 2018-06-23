package bases_cl.user;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.UserChestProto;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.UserEquipProto;
import com.google.protobuf.GeneratedMessage;

import bases_cl.props.Prop;

//用户装备表 ： 用户ID，道具ID，道具个数

public class UserEquip  implements ProtobufSerializable{

	private int userid;
	private int propsid;
	private int count;
	
	
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		// TODO Auto-generated method stub
		UserEquipProto userEquipProto=(UserEquipProto) message;
		this.userid=(int) userEquipProto.getUserid();
		this.propsid=(int) userEquipProto.getPropsid();
		this.count=userEquipProto.getCount();
	}
	@Override
	public GeneratedMessage copyTo() {
		// TODO Auto-generated method stub
		UserEquipProto.Builder userEquip=UserEquipProto.newBuilder();
		userEquip.setUserid(userid);
		userEquip.setPropsid(propsid);
		userEquip.setCount(count);
		return userEquip.build();
	}
	@Override
	public void parseFrom(byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			UserEquipProto userEquipProto=UserEquipProto.parseFrom(bytes);
			copyFrom(userEquipProto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return copyTo().toByteArray();
	}
	
	
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getProps() {
		return propsid;
	}
	public void setProps(int props) {
		this.propsid = props;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

	
	
	
	@Override
	public String toString() {
		return "UserEquip [userid=" + userid + ", props=" + propsid + ", count="
				+ count + "]";
	}
	public UserEquip(int userid, int props, int count) {
		super();
		this.userid = userid;
		this.propsid = props;
		this.count = count;
	}
	public UserEquip() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	

	
}
