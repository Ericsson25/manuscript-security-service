package manuscript.system.security.writter;

import com.google.gson.Gson;

/**
 * 
 * @author Balázs Kovács
 *
 */
public final class GsonWritter {

	public static String write(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	public static int length(Object object) {
		return write(object).getBytes().length;
	}
}
