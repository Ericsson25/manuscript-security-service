package manuscript.system.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import manuscript.system.security.bean.AuthenticatedUser;
import manuscript.system.security.core.userdetails.ExtendedUserDetails;
import manuscript.system.security.login.LoginDao;

/**
 * 
 * @author Balazs Kovacs
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	private LoginDao loginDao;

	@Autowired
	public CustomUserDetailsService(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public CustomUserDetailsService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userId = loadUserId(username);
		String password = loadPassword(userId);

		ExtendedUserDetails user = new AuthenticatedUser(username, password, userId, loadAuthorityListByUserId(userId));
		return user;
	}

	private String loadUserId(String username) {
		String userId = loginDao.loadUserIdByUsername(username);

		if (userId == null) {
			throw new UsernameNotFoundException(userId + "not found.");
		}

		return userId;
	}

	private String loadPassword(String userId) {
		return loginDao.loadPasswordByUserId(userId);
	}

	private List<GrantedAuthority> loadAuthorityListByUserId(String userId) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : loginDao.loadAuthorityListByUserId(userId)) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}
}
