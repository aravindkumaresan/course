package com.ft.learning;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class DefaultConfiguration extends Configuration {

	 @NotEmpty
	    @JsonProperty
	    private String template;

	    @NotEmpty
	    @JsonProperty
	    private String defaultName = "Default";

	    public String getTemplate() {
	        return template;
	    }

	    public String getDefaultName() {
	        return defaultName;
	    }
}
