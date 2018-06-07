package main.java.actions;

import java.util.ArrayList;
import java.util.List;

public  class DynamicValues {
	static List<String> storeDynamicList =new ArrayList<String>();

	public static List<String> getStoreDynamicList() {
		return storeDynamicList;
	}

	public static void setStoreDynamicList(List<String> storeDynamicList1) {
		storeDynamicList = storeDynamicList1;
	}
}
