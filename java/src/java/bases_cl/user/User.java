package bases_cl.user;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto;
import com.funcy.g01.base.proto.service.AggregateServiceReqProtoBuffer.UserProto;
import com.google.protobuf.GeneratedMessage;

//用户表 ：  用户ID ，用户name，用户密码 ，用户等级 ，用户钻石，用户奶酪
public class User implements ProtobufSerializable{

	private int userid;
	private String name;
	private String pwd;
	private int level=0;
	private int diamond;
	private int cheese;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		// TODO Auto-generated method stub
		UserProto userProto=(UserProto) message;
		this.userid=(int) userProto.getUserid();
		this.name=userProto.getName();
		this.pwd=userProto.getPwd();
		this.level=userProto.getLevel();
		this.diamond=userProto.getDiamond();
		this.cheese=userProto.getCheese();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		// TODO Auto-generated method stub
	try {
		UserProto uBuilder=UserProto.parseFrom(bytes);
		copyFrom(uBuilder);
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	
	public GeneratedMessage copyTo() {
		UserProto.Builder builder = UserProto.newBuilder();
		builder.setUserid(userid);
		builder.setName(name);
		builder.setPwd(pwd);
		builder.setLevel(level);
		builder.setDiamond(diamond);
		builder.setCheese(cheese);
		return builder.build();
	}
	
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getDiamond() {
		return diamond;
	}
	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}
	public int getCheese() {
		return cheese;
	}
	public void setCheese(int cheese) {
		this.cheese = cheese;
	}
	
	
	
	@Override
	public String toString() {
		return "User [userid=" + userid + ", name=" + name + ", pwd=" + pwd
				+ ", level=" + level + ", diamond=" + diamond + ", cheese="
				+ cheese + "]";
	}
	
	
	
	public User(int userid, String name, String pwd, int level, int diamond,
			int cheese) {
		super();
		this.userid = userid;
		this.name = name;
		this.pwd = pwd;
		this.level = level;
		this.diamond = diamond;
		this.cheese = cheese;
	}
	
	
	
	
	
	
}
