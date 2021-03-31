package com.thang.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class [] {ApplicationCofig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class [0] ;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
	    filter.setForceEncoding(true);
	    return new Filter[] { filter };
	}

}
