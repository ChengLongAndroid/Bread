package bases_cl.chest;

import java.util.List;

import com.funcy.g01.base.bo.chest.ChestType;

import flex.messaging.io.ArrayList;

//宝箱表  ： 宝箱ID ，宝箱name ，宝箱类型，宝箱倒计时，宝箱解锁等级，出装备价值随机范围
public class Chest {

	private int  id;
	private String name;
	private int type;
	private int countdown;
	private int unlockLevel=0;
	private List<Integer>  scope=new ArrayList();
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCountdown() {
		return countdown;
	}
	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}
	public int getUnlockLevel() {
		return unlockLevel;
	}
	public void setUnlockLevel(int unlockLevel) {
		this.unlockLevel = unlockLevel;
	}
	public List<Integer> getScope() {
		return scope;
	}
	public void setScope(List<Integer> scope) {
		this.scope = scope;
	}
	
	
	@Override
	public String toString() {
		return "Chest [id=" + id + ", name=" + name + ", type=" + type
				+ ", countdown=" + countdown + ", unlockLevel=" + unlockLevel
				+ ", scope=" + scope + "]";
	}
	
	
	
	public Chest(int id, String name, int type, int countdown,
			List<Integer> scope) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.countdown = countdown;
	
		this.scope = scope;
	}

	
	

	
	
	
	
	
	
}

