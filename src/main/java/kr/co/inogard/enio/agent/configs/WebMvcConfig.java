package kr.co.inogard.enio.agent.configs;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import kr.co.inogard.enio.agent.commons.converter.EnioJsonHttpMessageConverter;
import kr.co.inogard.enio.agent.commons.interceptor.HttpInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private HttpInterceptor httpInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(httpInterceptor).addPathPatterns("/**").excludePathPatterns("/public/**");
	}

	@Bean
	public EnioJsonHttpMessageConverter enioJsonHttpMessageConverter() {
		return new EnioJsonHttpMessageConverter();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(enioJsonHttpMessageConverter());
		super.configureMessageConverters(converters);
	}
}
