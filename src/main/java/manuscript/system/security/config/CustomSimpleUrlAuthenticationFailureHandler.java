package manuscript.system.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import manuscript.system.security.writter.GsonWritter;
import manuscript.system.seucurity.reply.BaseReply;
import manuscript.system.seucurity.reply.Message;

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

		BaseReply reply = new BaseReply();
		Message message = new Message();

		LOGGER.debug("Authentication failed. Exception: {}", exception.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		message.setText("Wrong username or password.");
		reply.getMessages().add(message);
		reply.setSuccess(false);

		response.setContentLength(GsonWritter.length(reply));
		response.getOutputStream().write(GsonWritter.write(reply).getBytes());

	}

}
