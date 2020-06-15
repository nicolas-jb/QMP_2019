package queMePongo.model.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Connector {
	
	public static String get(String url) {
		try {
			CloseableHttpClient cliente = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse respuesta = cliente.execute(get);
			HttpEntity entity = respuesta.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new IllegalArgumentException("Error al conectarse");
		}
	}
}
