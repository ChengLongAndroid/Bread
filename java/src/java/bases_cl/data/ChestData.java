package bases_cl.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import bases_cl.chest.Chest;

import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.data.ChestProperty;
import com.funcy.g01.base.util.DocumentBuilder;

public class ChestData {

	private Map<Integer, Chest> chestInfoMap = new HashMap<Integer, Chest>();
	
	
	public void init() {
		this.initChest();
	}
	
	public void initChest(){
		String url = "com/funcy/g01/xml/chest_cl.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String dropGroups = e.elementText("dropGroups");//范围
				List<Integer> dropGroupIds = new ArrayList<Integer>();
				for (String dropId : dropGroups.split(",")) {
					dropGroupIds.add(Integer.parseInt(dropId));
				}
				String name = e.elementText("name");
				int openTime = Integer.parseInt(e.elementText("time"));
				int type = Integer.parseInt(e.elementText("type"));
				
				this.chestInfoMap.put(type, new Chest(id, name, type, openTime, dropGroupIds));
			}
			//logger.info("init " + url + " success");
			
			
		} catch (DocumentException e) {
		//	logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	
	public Chest getChestProperty(int type) {
		return this.chestInfoMap.get(type);
	}
	
	public Map<Integer, Chest> getchestInfoMap() {
		return chestInfoMap;
	}
}
