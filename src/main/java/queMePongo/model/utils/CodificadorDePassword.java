package queMePongo.model.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class CodificadorDePassword {
	
	public String codificarPass(String pass) {
		return Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
	}
}
