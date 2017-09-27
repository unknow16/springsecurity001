package springsecurity001;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String sql = "SELECT\n" +
						"	u.username,\n" +
						"	u.`password`,\n" +
						"	r.`name`\n" +
						"FROM\n" +
						"	user_role ur,\n" +
						"	USER u,\n" +
						"	role r\n" +
						"WHERE\n" +
						"	ur.user_id = u.id\n" +
						"AND ur.role_id = r.id\n" +
						"AND username = ? ";
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, username);
		
		if(results == null || results.size() == 0) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		String password = (String) results.get(0).get("password");
		boolean enabled = true;
		boolean accountNonExpired = true; //帐号不过期
		boolean credentialsNonExpired = true; //凭证不过期
		boolean accountNonLocked = true; //帐号未锁定
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(int i=0; i<results.size(); i++) {
			Map<String, Object> map = results.get(i);
			String roleName = (String) map.get("name");
			authorities.add(new GrantedAuthorityImpl(roleName));
		}
		
		User user = new User(username, password, enabled, accountNonExpired, 
				credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}

}
