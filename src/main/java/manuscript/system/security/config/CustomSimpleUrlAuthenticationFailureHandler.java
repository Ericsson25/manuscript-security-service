package manuscript.system.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import manuscript.system.security.writter.GsonWritter;
import manuscript.system.seucurity.reply.ReplyObject;

/**
 * 
 * @author Balazs Kovacs
 *
 */
public class CustomSimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomSimpleUrlAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LOGGER.debug("Exception occured during authentication!", exception);

		response.setContentType("application/json");
		response.setStatus(400);

		ReplyObject reply = new ReplyObject();

		reply.setSuccess(false);
		reply.setErrorMessage("Wrong username or password. Please try again.");

		response.setContentLength(GsonWritter.length(reply));
		response.getOutputStream().write(GsonWritter.write(reply).getBytes());

	}

}
