package bases_cl.server;


import java.io.IOException;
import java.util.ArrayList;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.chest.ChestSlot;
import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

import bases_cl.chest.Chest;
import bases_cl.data.ChestData;
import bases_cl.data.PropsData;
import bases_cl.props.Prop;
import bases_cl.redis.ChestServ;
import bases_cl.redis.UserServ;
import bases_cl.user.User;
import bases_cl.user.UserChest;
import bases_cl.user.UserEquip;




//@Service("activeService")
public class ChestsService {

	public static ChestData cdChestData; //key type
	public static PropsData propsData;  //key  id 
	public static final long time = 24*60*60*1000; 
	public static  User user;
	public  List<Integer> types =new ArrayList<Integer>();
	public  List<Integer> Equip =new ArrayList<Integer>();
	
	public static UserChest userChest;
	public static UserEquip userEquip;
	
	
	public void initialize() throws IOException{

		int date=(int) System.currentTimeMillis();
		
		 user =new User(1, "cheng", "long", 4, 2000, 2000);
		 
		 UserServ us=new UserServ();
		 us.adduser(user);
		 System.out.println(" ----"+ "存储完毕进行提取");
		 System.out.println("User信息"+us.getUserInfo(user.getUserid()).toString());;
		 
		 System.out.println("-----"+"xml文件提取宝箱"+"---------");
		 cdChestData.init();
		 
		 Map<Integer, Chest> map =   cdChestData.getchestInfoMap();
		 for(Entry<Integer, Chest> entry : map.entrySet()){  
		     System.out.print("Key = "+entry.getKey()+",value="+entry.getValue());  
		 }  
		 
		 
		 System.out.println("-----"+"xml文件提取道具"+"---------");
		 
		 propsData.init();
		 Map<Integer, Prop> map1 =   propsData.getchestInfoMap();
		 for(Entry<Integer, Prop> entry : map1.entrySet()){  
		     System.out.print("Key = "+entry.getKey()+",value="+entry.getValue());  
		 } 
		 
		 //System.out.println( cdChestData.getChestProperty(101).toString());
		 
		 
		// userChest=new UserChest(1, 1, 1,date );
		 
//		 Equip.add(2);//添加道具 
//		 Equip.add(3);
//		 userEquip =new UserEquip(1, Equip, 2);

		 
	}
	
	
//	
//	
//	
//	private void getRoleNetInfo(int userid) {
//		// TODO Auto-generated method stub
//		
//	}



	//打开箱子    MapChese字串切割  定义id：date 
	//         MapEquip字串切割  定义Propsid 
	public String  openChestOnSlot(int index, User user){  
		
		UserChest userChest=new ChestServ().getUserInfo(user.getUserid());
		if (userChest.getId()!=index) {
			throw new BusinessException(ErrorCode.chest_slot_no_chest);
		}
		 Chest chest=cdChestData.getChestProperty(index);
		 float time=userChest.getDate()+chest.getCountdown()*10000;
		 int con=0;
		if (time>System.currentTimeMillis()) {
			
			//con=costGemOpenChest(chest,user.getUserid());
		}else {
			//
			return "时间未到无法获取";
		}
		if (con>0) {
			return "领取成功";
		}else {
			return "领取失败";
		}
			
	}
	
	
	
	
//	public int costGemOpenChest(Chest chest ,long roleId){//宝石购买宝箱返回 道具   
//		
//
//	}
	
	public static void main(String[] args) {
		String test = "1234";
		int testInt = (Integer)(Object)test;
		System.out.print(testInt);;
	}
	
	
//	public void OpenChest(long roleId, int index, int type){ 
//
//	}
	
}
