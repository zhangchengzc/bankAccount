package com.cqupt.pub.util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * JSON工具类
 * 
 * @author jacob
 * @version v0.1 2011-12-06
 * 
 */
public class JsonUtil {

	/**
	 * 将结果集Map转换成JSON数据格式的字符串
	 * 
	 * 
	 * @param result
	 *            result为Map类型，并且包含两个数据键resultCount:int表示总数据条数
	 *            resultList:List<Map<column, value>>表示获取的结果集map类型链表
	 * @return JSON格式的字符串 例如:
	 *         {Rows:[{"Phone":"030-0074321","Fax":"030-0076545"},{"Phone":
	 *         "(5) 555-4729"
	 *         ,"Fax":"(5) 555-3745"},{"Phone":"(5) 555-3932","Fax"
	 *         :null},{"Phone":"(95) 555 82 82","Fax":null}],Total:3}
	 */
	public static String map2json(Map<String, Object> result) {
		int resultCount = (Integer) result.get("resultCount");	
		List resultList = (List) result.get("resultList");
//System.out.println(" JsonUtil.map2json:"+resultList);
		JSONArray jsonObject = JSONArray.fromObject(resultList);
		return "{Rows:" + jsonObject + ",Total:" + resultCount + "}";
	}
	

	public static String list2json(List result) {
		int resultCount = (Integer) result.size();
	
		JSONArray jsonObject = JSONArray.fromObject(result);
		return "{Rows:" + jsonObject + ",Total:" + resultCount + "}";
	}
	

}
