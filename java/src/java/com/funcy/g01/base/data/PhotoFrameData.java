package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class PhotoFrameData {
	
	private static final Logger logger  = Logger.getLogger(PhotoFrameData.class);
	
	private Map<Integer, PhotoFrameProperty> photoframeInfoMap = new HashMap<Integer, PhotoFrameProperty>();
	
	public void init() {
		String url = "com/funcy/g01/xml/photoFrames.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				int quality = Integer.parseInt(e.elementText("quality"));
				this.photoframeInfoMap.put(id, new PhotoFrameProperty(id, name, quality));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public List<PhotoFrameProperty> getPhotoFrameList(){
		List<PhotoFrameProperty> list = new ArrayList<PhotoFrameProperty>();
		for(Entry<Integer,PhotoFrameProperty> entry : this.photoframeInfoMap.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}
	
	public PhotoFrameProperty getPhotoFrameProperty(int xmlId) {
		return this.photoframeInfoMap.get(xmlId);
	}

}
