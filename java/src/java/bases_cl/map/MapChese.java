package bases_cl.map;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.MapChestProto;
import com.google.protobuf.GeneratedMessage;

public class MapChese implements ProtobufSerializable {
	private long userid;
	private String userchese;
	
	public MapChese(long userid, String userchese) {
		super();
		this.userid = userid;
		this.userchese = userchese;
		
	}
	
	
	
	public MapChese() {
		// TODO Auto-generated constructor stub
	}



	@Override
	public void copyFrom(GeneratedMessage message) {
		// TODO Auto-generated method stub
		//MapChestProto
		MapChestProto proto=(MapChestProto) message;
		this.userid =proto.getUserid();
		this.userchese=proto.getChest();
//		this.userchese = new ArrayList<Long>();
//	//	for (Long long1 : proto.get) {
//			
//		//}
		
	}

	@Override
	public GeneratedMessage copyTo() {
		// TODO Auto-generated method stub
		MapChestProto.Builder mpChestProto=MapChestProto.newBuilder();
		mpChestProto.setUserid(userid);
		mpChestProto.setChest(userchese);
		
		return mpChestProto.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			MapChestProto maoChestProto =MapChestProto.parseFrom(bytes);
		   copyFrom(maoChestProto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	@Override
	public String toString() {
		return "MapChese [userid=" + userid + ", userchese=" + userchese + "]";
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUserchese() {
		return userchese;
	}

	public void setUserchese(String userchese) {
		this.userchese = userchese;
	}



}
