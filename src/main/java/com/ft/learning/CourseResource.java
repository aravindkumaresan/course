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
		
		Resources resource1 = new Resources();
		resource1.setId("res001");
		resource1.setTitle("Imagination slips on MediaTek worries");
		resource1.setType("Article");
		resource1.setUrl("http://www.ft.com/cms/s/0/203c2cc4-4579-11e3-997c-00144feabdc0.html");
	
		List<Resources> resourceList1 = new ArrayList<Resources>();
		resourceList1.add(resource1);
		
		Resources resource2 = new Resources();
		resource2.setId("res002");
		resource2.setTitle("M&S shares lifted by food sales");
		resource2.setType("Article");
		resource2.setUrl("http://www.ft.com/cms/s/0/ee223d2a-45fd-11e3-9487-00144feabdc0.html");
	
		List<Resources> resourceList2 = new ArrayList<Resources>();
		resourceList2.add(resource2);
		
		Resources resource3 = new Resources();
		resource3.setId("res003");
		resource3.setTitle("Market gatekeeper to tighten London listing rules");
		resource3.setType("Article");
		resource3.setUrl("http://www.ft.com/cms/s/0/4a0096ca-4566-11e3-b98b-00144feabdc0.html");
	
		List<Resources> resourceList3 = new ArrayList<Resources>();
		resourceList3.add(resource3);
		
		Modules module1 = new Modules();
		module1.setId("moo1");
		module1.setTitle("Business Environment");
		module1.setDescription("Discover the many factors affecting and determining the success of any business.");
		module1.setResources(resourceList1);
		module1.setRatings(5);
		
		Modules module2 = new Modules();
		module2.setId("moo2");
		module2.setTitle("Managing Financial Resources and Decisions");
		module2.setDescription("Learn how and where to find sources of finance for a business, and the skills to use financial information for decision-making.");
		module2.setResources(resourceList2);
		module2.setRatings(5);
		
		Modules module3 = new Modules();
		module3.setId("moo3");
		module3.setTitle("Organisations and Behaviour");
		module3.setDescription("Develop an understanding of the current theories surrounding organisational behaviour, using organisations’ own experiences where relevant.");
		module3.setResources(resourceList3);
		module3.setRatings(5);
		
		List<Modules> modulesList1 = new ArrayList<Modules>();
		modulesList1.add(module1);
		modulesList1.add(module2);
		modulesList1.add(module3);
		
		Course course1 = new Course();
		course1.setId("N190");
		course1.setTitle("BSc (Honours) Business & Enterprise");
		course1.setDescription("Our Business and Enterprise degree aims to give you great, well-rounded academic knowledge, and the chance to put it into practice, testing out what you’ve learnt in real life situations, working closely with leading employers. The course is taught by experienced academics, with the course content shaped by industry, so you’ll be learning business in a very hands-on, up-to-the-minute way, with every aspect fresh and current.");
		course1.setRatings(5);
		course1.setModules(modulesList1);
		
		// make the API call here
		// build the course structure
		courses.add(course1);
		// return it
		return courses;
	}

}
