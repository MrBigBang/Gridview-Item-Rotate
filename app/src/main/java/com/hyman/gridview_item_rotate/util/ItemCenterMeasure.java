package com.hyman.gridview_item_rotate.util;


public class ItemCenterMeasure {

	public static int ITEM_WIDTH = 0; 
	
	public static int ITEM_HEIGHT = 0;
	
	public static void measureCenter(int width, int height) {
		
		
		ITEM_WIDTH = (width - DisplayUtil.dp2px(12) * 2) / 3;
		
		ITEM_HEIGHT = (height - DisplayUtil.dp2px(12) * 5) / 5;
	} 
	
}
