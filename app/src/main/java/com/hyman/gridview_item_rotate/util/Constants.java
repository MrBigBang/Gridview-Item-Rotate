package com.hyman.gridview_item_rotate.util;

import java.util.Arrays;
import java.util.List;

import android.util.SparseArray;

public class Constants {

	private static List<String> imageUrlLists1;
	
	private static List<String> imageUrlLists2;
	
	private static SparseArray<List<String>> urlArray;
	
	static {
		String[] urls1 = new String[] {
				"http://car0.autoimg.cn/upload/2014/10/3/s_20141003015514509-110.jpg",
				"http://car0.autoimg.cn/upload/2014/9/25/s_2014092522261885926411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522262753326411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522264830126410.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522234807726411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522241219626411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522223742126411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522222242926411.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/25/s_2014092522212610426410.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090500294461210.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090453812461211.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090447744461211.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090442556461210.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090430177461211.jpg",
	            "http://car0.autoimg.cn/upload/2014/9/19/s_20140919090253967461211.jpg",
		};
		
		imageUrlLists1 = Arrays.asList(urls1);
		
		String[] urls2 = new String[] {
				"http://f10.topitme.com/m/201010/20/12875383427269.jpg",
				"http://f8.topitme.com/8/ae/41/110494565166041ae8m.jpg",
	            "http://fb.topitme.com/b/36/80/11727484881258036bm.jpg",
	            "http://fe.topitme.com/e/27/65/11502215676f56527em.jpg",
	            "http://f10.topitme.com/m/201010/07/12863837919922.jpg",
	            "http://f5.topitme.com/5/9b/8d/11756832009d78d9b5m.jpg",
	            "http://f9.topitme.com/9/01/62/11698464377d962019m.jpg",
	            "http://f1.topitme.com/1/26/af/1130430963221af261m.jpg",
	            "http://f10.topitme.com/m/201008/23/12825461566349.jpg",
	            "http://f9.topitme.com/9/1f/cf/1171107828ce5cf1f9m.jpg",
	            "http://f2.topitme.com/2/4a/76/1137186683324764a2m.jpg",
	            "http://f7.topitme.com/7/17/56/1150220342ed956177m.jpg",
	            "http://f8.topitme.com/8/9f/61/113030786634c619f8m.jpg",
	            "http://f10.topitme.com/m008/10008803025c58948f.jpg",
	            "http://f10.topitme.com/m/201003/18/12688849975256.jpg",
		};
		
		imageUrlLists2 = Arrays.asList(urls2);
		
		urlArray = new SparseArray<>();
		urlArray.put(1, imageUrlLists1);
		urlArray.put(2, imageUrlLists2);
	}
	
	
	public static List<String> getUrls(int index) {
		
		return urlArray.get(index);
	}
	
}
