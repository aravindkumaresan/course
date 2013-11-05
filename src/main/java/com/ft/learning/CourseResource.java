package com.ft.learning;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
	
	private String template;
	private String defaultName;
	
	public CourseResource(String template, String defaultName) 
	{
		this.template = template;
        this.defaultName = defaultName;
	}
	
	@GET
	public List<Course> course() {
		
		List<Course> courses = new ArrayList<Course>();
		Course course = new Course();
		course.setId(100);
		course.setName("Strategic Management");
		// make the API call here
		// build the course structure
		courses.add(course);
		// return it
		return courses;
	}

}
