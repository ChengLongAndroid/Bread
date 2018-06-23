package bases_cl.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import bases_cl.chest.Chest;
import bases_cl.props.Prop;

import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.data.ChestProperty;
import com.funcy.g01.base.data.ItemProperty;
import com.funcy.g01.base.data.MysteriousShopPeriodProperty;
import com.funcy.g01.base.util.DocumentBuilder;



public class PropsData {

private Map<Integer, Prop> propsInfoMap = new HashMap<Integer, Prop>();
	
	public void init() {
		this.initProps();
	}
	

//	id	名字                   品质	            出售价格	  货币类型
//	id	name	  quality	  price	     pattern

	public void initProps(){
		String url = "com/funcy/g01/xml/items_cl.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				String quality = e.elementText("quality");
				
				String priceStr = e.elementText("price");
				int price = 0;
				
				if (!priceStr.equals("")) {
					price = Integer.parseInt(priceStr);
				}
				
				String patterne = e.elementText("pattern");
				
				int pattern = 0;
				if (!patterne.equals("")) {
					pattern = Integer.parseInt(patterne);
				}
				
				this.propsInfoMap.put(id, new Prop(id, name, quality, price, pattern));
				

			}
			//logger.info("init " + url + " success");
		} catch (DocumentException e) {
			//logger.error("init " + url + " fail");
			e.printStackTrace();
		}
		}
	
	
	public Prop getChestProperty(int type) {
		return this.propsInfoMap.get(type);
	}
	
	public Map<Integer, Prop> getchestInfoMap() {
		return propsInfoMap;
	}
	
}
