package it.petshop.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonResponseHelper {
	private static final Gson gson = new Gson();
	private Map<String, Object> data;

	public JsonResponseHelper() {
		data = new HashMap<>();
	}

	public void add(String key, Object value) {
		data.put(key, value);
	}

	public void send(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(data));
	}

}
