package springsecurity001;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringSecurityListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//1. 得到Spring的IOC容器
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
		
		//2. 从IOC容器中取出FilterSecurityInterceptor对象
		FilterSecurityInterceptor filterSecurityInterceptor = ac.getBean(FilterSecurityInterceptor.class);
		
		//3. 从IOC容器中取出securityMetadataSource
		FilterInvocationSecurityMetadataSource source = (FilterInvocationSecurityMetadataSource) ac.getBean("filterInvocationSecurityMetadataSourceBean");
		
		//4. 调用2的setSecurityMetadataSource方法，参数为3
		filterSecurityInterceptor.setSecurityMetadataSource(source);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
