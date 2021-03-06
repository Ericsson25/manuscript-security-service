package manuscript.system.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import manuscript.system.security.writter.GsonWritter;
import manuscript.system.seucurity.reply.ReplyObject;

/**
 * 
 * @author Balazs Kovacs
 *
 */
public class CustomSavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			authentication.setAuthenticated(false);
			throw new SessionAuthenticationException("After authentication process something went wrong!");
		}

		response.setContentType("application/json");

		ReplyObject reply = new ReplyObject();

		reply.setSuccess(true);
		reply.setSession(session.getId());

		response.setContentLength(GsonWritter.length(reply));
		response.getOutputStream().write(GsonWritter.write(reply).getBytes());
	}

}
