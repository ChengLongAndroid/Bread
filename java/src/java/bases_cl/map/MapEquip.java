package bases_cl.map;

import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.MapEquipProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class MapEquip implements ProtobufSerializable{
	private int userid;
	private String mappropid;
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		// TODO Auto-generated method stub
		MapEquipProto mapEquipProto=(MapEquipProto) message;
	    this.userid=mapEquipProto.getUserid();
	    this.mappropid=mapEquipProto.getProps();
	    
	    		
	}
	@Override
	public GeneratedMessage copyTo() {
		// TODO Auto-generated method stub
		MapEquipProto.Builder mapEquipProto=MapEquipProto.newBuilder();
		mapEquipProto.setUserid(userid);
		mapEquipProto.setProps(mappropid);
		return mapEquipProto.build();
	}
	@Override
	public void parseFrom(byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			MapEquipProto mapEquipProto=MapEquipProto.parseFrom(bytes);
			copyFrom(mapEquipProto);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return copyTo().toByteArray();
	}
	
	
	public MapEquip(String mappropid) {
		super();
		this.mappropid = mappropid;
	}
	public MapEquip(int userid) {
		super();
		this.userid = userid;
	}
	public MapEquip(int userid, String mappropid) {
		super();
		this.userid = userid;
		this.mappropid = mappropid;
	}
	public MapEquip() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MapEquip [userid=" + userid + ", mappropid=" + mappropid + "]";
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getMappropid() {
		return mappropid;
	}
	public void setMappropid(String mappropid) {
		this.mappropid = mappropid;
	}
	
	

	
}
