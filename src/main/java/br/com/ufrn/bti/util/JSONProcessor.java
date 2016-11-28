package br.com.ufrn.bti.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Classe responsável por realizar a manipulação de objetos json
 * @author ramonsantos
 *
 */
public class JSONProcessor {
	public static String caminhoJson = "/home/ramon/EspatifadoServer/file.json";
	
	//public static String caminhoJson = "/Users/ramonsantos/workspace/file.json";
	
	public synchronized static <T> T toObject(String jsonText, Class<T> classe) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = gsonBuilder.create();
		T obj = null;
		try {
			// Converte String JSON para objeto Java
			obj = gson.fromJson(jsonText, classe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public synchronized static String toJSON(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = gsonBuilder.create();
		
		String json = gson.toJson(object);
		try {
			json = "'"+ json + "'";
			// Escreve Json convertido em arquivo chamado "file.json"
			FileWriter writer = new FileWriter(caminhoJson);
			writer.write(json);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public synchronized static <T> List<T> toList(String jsonText, Class<T> classe) throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray(jsonText);

		List<T> result = new ArrayList<T>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			T object = toObject(jsonObject.toString(), classe);

			result.add(object);
		}
		return result;
	}
}
