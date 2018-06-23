package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.util.DocumentBuilder;


@Component
public class WordData{

	private static final Logger logger = Logger.getLogger(WordData.class);
	private final Map<Integer, WordProperty> wordPropertyMap = new HashMap<Integer, WordProperty>();

	public void init() {
		initWord();
	}

	public void initWord(){
		try {
			List<Element> elements = DocumentBuilder.build("com/funcy/g01/xml/words.xml");
			for (Element e : elements) {
				int id = Integer.valueOf(e.elementText("id"));
				String word = e.elementText("word");
				WordProperty wordProperty = new WordProperty(id, word);
				wordPropertyMap.put(id,wordProperty);
			}
			logger.info("init com/funcy/g01/xml/words.xml success");
		} catch (DocumentException e) {
			logger.error("init com/funcy/g01/xml/words.xml fail");
			e.printStackTrace();
		}
	}

	public WordProperty getWordProperty(int id) {
		return wordPropertyMap.get(id);
	}
}
