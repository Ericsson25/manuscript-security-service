package manuscript.system.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import manuscript.system.security.writter.GsonWritter;
import manuscript.system.seucurity.reply.ReplyObject;

/**
 * 
 * @author Balazs Kovacs
 *
 */
public class CustomSimpleUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		ReplyObject reply = new ReplyObject();

		reply.setSuccess(true);

		response.setContentLength(GsonWritter.length(reply));
		response.getOutputStream().write(GsonWritter.write(reply).getBytes());
	}
}
