package com.suo.sdemo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {
   private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);
   
   private static final ObjectMapper mapper=new ObjectMapper();
   static {
       mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   }

   public static ObjectMapper getMapper(){
	   return mapper;
   }
   
   public static String getJson(Object obj) {
	  try {
		return mapper.writeValueAsString(obj);
	  } catch (JsonProcessingException e) {
		log.error(e.getMessage(),e);
	  }
	  return "";
   }
   public static <T> T readJson(String jsonStr,Class<T> tClass){
	   if(jsonStr == null) {
		   return null;
	   }
	   try {
		return mapper.readValue(jsonStr, tClass);
	   } catch (IOException e) {
		log.warn(e.getMessage(),e);
	   }
	   return null;
   }
   public static JsonNode parseJsonStrToNode(String jsonStr){
	   JsonNode jsonNode=null;
	   try {
		jsonNode=mapper.readTree(jsonStr);
	   } catch (IOException e) {
		log.warn(e.getMessage(),e);
	   }
	   return jsonNode;   
   }
   /**
    * json字符串转换为list集合
    * @param jsonString
    * @param tClass
    * @return
    * @throws JsonParseException
    * @throws JsonMappingException
    * @throws IOException
    */
   public static <T> T jsonStrToList(String jsonString,Class<?> tClass) {
	   T t=null;
		try {
			t = mapper.readValue(jsonString, mapper.getTypeFactory().constructParametricType(List.class, tClass));
		} catch (JsonParseException e) {
			log.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return t;
	}
   
   /**
    * json字符串转换为Map集合
    * 
    * @param jsonString
    * @param mapKeyClass keyClass
    * @param mapValueClass valueClass
    * @return
    */
   public static <T> T jsonStrToMap(String jsonString,Class<?> mapKeyClass,Class<?> mapValueClass) {
	   T t=null;
	   try {
		   t = mapper.readValue(jsonString, mapper.getTypeFactory().constructParametricType(Map.class,mapKeyClass, mapValueClass));
	   } catch (JsonParseException e) {
		   log.error(e.getMessage(),e);
	   } catch (JsonMappingException e) {
		   log.error(e.getMessage(),e);
	   } catch (IOException e) {
		   log.error(e.getMessage(),e);
	   }
	   return t;
   }
    /**
    * 解析DataTable参数
    * @param queryJson
    * @return
    */
    public static Map<String,Object> parseQueryJson(String queryJson){
	   Map<String,Object> map=new HashMap<String, Object>();
	   if(StringUtils.isEmpty(queryJson)){
	       return map;
	   }
	   JsonNode jsonNode=parseJsonStrToNode(queryJson);
	   if(jsonNode==null){
		   return map;
	   }
	   if(!jsonNode.isArray()){
		   return map;
	   }
	   Iterator<JsonNode> jsonE= jsonNode.elements();
	   while(jsonE.hasNext()){
		  JsonNode jn=jsonE.next();
		  String name=jn.path("name").asText();
		  String val=jn.path("value").asText();
		  map.put(name, val);
	   }
	   return map;
   }
    
    @SuppressWarnings("unchecked")
	public static void readMap(String key,Map<String, Object> valueMap,Map<String,String> dataMap){
    	Set<String> keySet = valueMap.keySet();
    	for (String k : keySet) {
			Object value = valueMap.get(k);
			if (value instanceof String) {
				dataMap.put(key+"."+k, value.toString());
			}else if(value instanceof Map)
				readMap(key+"."+k, (Map<String, Object>) value, dataMap);
		}
    }

}
