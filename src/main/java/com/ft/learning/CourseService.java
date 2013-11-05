package com.ft.learning;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class CourseService extends Service<DefaultConfiguration> {

	public static void main(String[] args) throws Exception {

		new CourseService().run(args);
	}

	@Override
	public void initialize(Bootstrap<DefaultConfiguration> bootstrap) {
		bootstrap.setName("Course Service");

	}

	@Override
	public void run(DefaultConfiguration configuration, Environment environment)
			throws Exception {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		environment.addResource(new CourseResource(template, defaultName));

	}

}
