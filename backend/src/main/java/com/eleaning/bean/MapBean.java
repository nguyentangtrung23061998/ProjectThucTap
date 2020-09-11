package com.eleaning.bean;

import java.util.HashMap;

public class MapBean {
	private HashMap<String,Object> data = new HashMap<String, Object>();

	public MapBean() {
	}
	
	public void put(String key, Object value) {
		this.data.put(key, value);
	}
	
	public HashMap<String, Object> getAll(){
		 return this.data;
	}
}
