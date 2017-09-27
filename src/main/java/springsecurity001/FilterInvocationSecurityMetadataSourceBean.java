package springsecurity001;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterInvocationSecurityMetadataSourceBean implements
		FactoryBean<FilterInvocationSecurityMetadataSource> {

	//返回实际对象实例
	public FilterInvocationSecurityMetadataSource getObject() throws Exception {

		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		// 初始化requestMap
		RequestMatcher matcher = new AntPathRequestMatcher("/index.jsp");
		Collection<ConfigAttribute> attributes = new ArrayList<>();
		attributes.add(new SecurityConfig("ROLE_USER"));
		attributes.add(new SecurityConfig("ROLE_ADMIN"));
		requestMap.put(matcher, attributes);

		matcher = new AntPathRequestMatcher("/admin.jsp");
		attributes = new ArrayList<>();
		attributes.add(new SecurityConfig("ROLE_ADMIN"));
		requestMap.put(matcher, attributes);

		FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource = 
				new DefaultFilterInvocationSecurityMetadataSource(requestMap);

		return filterInvocationSecurityMetadataSource;
	}

	//并不是返回bean.xml中配置的类型，而是返回此处配置的
	public Class<?> getObjectType() {
		return FilterInvocationSecurityMetadataSource.class;
	}

	//是否单例
	public boolean isSingleton() {
		return true;
	}

}
