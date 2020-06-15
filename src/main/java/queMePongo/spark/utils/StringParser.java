package queMePongo.spark.utils;

import java.util.HashMap;
import java.util.Map;

public class StringParser {
	
	private static Map<String, String> commonErrors = getCommonErrors();
	
	public static Map<String,String> parseString(String separator1, String separator2, String body) {
		Map<String,String> map = new HashMap<>();
		for (String pairedBody : body.split(separator2)) {
			try{
				String[] splittedPair = pairedBody.split(separator1);
				map.put(splittedPair[0],splittedPair[1]);
			} catch ( Exception e ) {}
		}
		return map;
	}
	
	public static Map<String,String> parseBody(String body) {
		return parseString("=", "&", body);
	}
	
	public static String replaceCommonErrors(String base){
		for (String key : commonErrors.keySet()){
			base = base.replaceAll(key, commonErrors.get(key));
		}
		return base;
	}
	
	private static Map<String,String> getCommonErrors() {
		Map<String, String> map = new HashMap<>();
		map.put("%3A", ":");
		map.put("%2F", "/");
		map.put("%3D", "=");
		map.put("%3F", "?");
		https://www.distritomoda.com.ar/sites/default/files/styles/producto_interior/public/imagenes/img-20190819-wa0260_1568254050.jpg?itok=mwpxY7vx
		return map;
	}
}
