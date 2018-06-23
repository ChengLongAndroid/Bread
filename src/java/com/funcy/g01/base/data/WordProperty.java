package com.funcy.g01.base.data;

public class WordProperty{
	private int id;
	private String word;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}

	public void setWord(String word){
		this.word=word;
	}

	public String getWord(){
		return word;
	}

	public WordProperty(int id, String word) {
		this.id=id;
		this.word=word;
	}
}
