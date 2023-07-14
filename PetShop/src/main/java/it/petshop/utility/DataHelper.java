package it.petshop.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class DataHelper {
	private static final Gson gson = new Gson();
	private Map<String, Object> data;

	public DataHelper() {
		data = new HashMap<>();
	}
	
	public void add(String key, Object value) {
		data.put(key, value);
	}
	
	public void setAsRequestAttribute(HttpServletRequest request) {
		data.forEach(request::setAttribute);
	}
	
	public void setAsSessionAttribute(HttpSession session) {
		data.forEach(session::setAttribute);
	}
	
	public void sendAsJSON (HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.getWriter().write(gson.toJson(data));
	}

}
