package com.ft.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Path("/courseinfo")
@Produces(MediaType.APPLICATION_JSON)
public class CourseInfoResource {
	
	private String template;
	private String defaultName;
	
	public CourseInfoResource(String template, String defaultName) 
	{
		this.template = template;
        this.defaultName = defaultName;
	}

	@GET
	public Course courseInfo(@QueryParam("id") String id) throws IOException, ParseException {
		String accessCode = "Access_Token access_token="+getAccessCode();
		
		Course course = getCourseInfo(accessCode, id);
		
		return course;
		
	}
	
private Course getCourseInfo(String accessCode, String id) throws ClientProtocolException, IOException, ParseException {
		
	    String courseUrl = "https://m-api.ecollege.com/courses/"+id;
	    
	    System.out.println("CourseURL - inside" + courseUrl);
	    
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(courseUrl);
		// add request header
		request.addHeader("X-Authorization", accessCode);
		HttpResponse response = client.execute(request);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject finalResult = new JSONObject(tokener);
		
		System.out.println(finalResult.toString());
		
		JSONArray jsonArray = finalResult.getJSONArray("courses");
		JSONObject objectInArray1 = jsonArray.getJSONObject(0);
		
		Course course = new Course();
		course.setId(objectInArray1.getString("id"));
		course.setTitle(objectInArray1.getString("title"));
		course.setRatings(5);
		
		String syllabusUrl = "http://m-api.ecollege.com/courses/"+course.getId()+"/syllabus";
		
		StringBuilder builder1 = new StringBuilder();
		
		HttpClient client1 = new DefaultHttpClient();
		HttpGet request1 = new HttpGet(syllabusUrl);
		// add request header
		request1.addHeader("X-Authorization", accessCode);
		HttpResponse response1 = client.execute(request1);
		HttpEntity responseEntity = response1.getEntity();
		if(responseEntity!=null) {
			builder1.append(EntityUtils.toString(responseEntity));
		    
		}
		
		 System.out.println(builder1.toString());
		 
		course.setDescription(builder1.toString());
		
		List<Modules> modules = getModules();
		
		course.setModules(modules);
		
		return course;
	}
	
	private List<Modules> getModules() throws UnsupportedEncodingException, IllegalStateException, IOException {
	
		List<Modules> modules = new ArrayList<Modules>();
		
		Modules module1 = new Modules();
		module1.setId("14079159");
		module1.setTitle("Business Environment");
		module1.setDescription("Discover the many factors affecting and determining the success of any business.");
		module1.setRatings(5);
		
		
		module1.setResources(getResources1(module1.getTitle()));
		
		Modules module2 = new Modules();
		module2.setId("14079160");
		module2.setTitle("Managing Financial Resources and Decisions");
		module2.setDescription("Learn how and where to find sources of finance for a business, and the skills to use financial information for decision-making.");
		module2.setRatings(5);
		
		module2.setResources(getResources2(module2.getTitle()));
		
		Modules module3 = new Modules();
		module3.setId("14079162");
		module3.setTitle("Organisations and Behaviour");
		module3.setDescription("Develop an understanding of the current theories surrounding organisational behaviour, using organisations’ own experiences where relevant.");
		module3.setRatings(5);
		
		module3.setResources(getResources3(module3.getTitle()));
		
		Modules module4 = new Modules();
		module4.setId("14079163");
		module4.setTitle("Marketing Principles");
		module4.setDescription("Explore the theories, concepts and frameworks used in marketing.");
		module4.setRatings(5);
		
		module4.setResources(getResources1(module4.getTitle()));
		
		Modules module5 = new Modules();
		module5.setId("14079164");
		module5.setTitle("Aspects of Contract and Negligence for Business");
		module5.setDescription("Delve into the fundamental principles of these two key areas of civil law, known collectively as the Law of Obligations, and how they affect business in the UK.");
		module5.setRatings(5);
		
		module5.setResources(getResources2(module5.getTitle()));
		
		
		modules.add(module1);
		modules.add(module2);
		modules.add(module3);
		modules.add(module4);
		modules.add(module5);
		
	return modules;
}

	private List<Resources> getResources1(String title)  {
		
		List<Resources> resourcesList = new ArrayList<Resources>();
		
	    Resources resources = new Resources();
	    resources.setId("d91dddea-3d7d-11e3-9928-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/d91dddea-3d7d-11e3-9928-00144feab7de.html");
		resources.setDescription("Social media: Headhunters’ traditional skills survive in world of web-based tools");
		
		
		Resources resources1 = new Resources();
	    resources.setId("1f715c6c-2f3d-11e3-ae87-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/1f715c6c-2f3d-11e3-ae87-00144feab7de.html");
		resources.setDescription("Sentiment analysis: Machine and man unite to assess brand value");
		
		
		Resources resources2 = new Resources();
	    resources.setId("6f9cd8f4-4565-11e3-997c-00144feabdc0");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/6f9cd8f4-4565-11e3-997c-00144feabdc0.html");
		resources.setDescription("Urgent business for a new global statistics agency");
		
		Resources resources3 = new Resources();
	    resources.setId("fc29e598-367c-11e3-8ae3-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/fc29e598-367c-11e3-8ae3-00144feab7de.html");
		resources.setDescription("Former London gin distillery becomes winery");
		
		Resources resources4 = new Resources();
	    resources.setId("b3c16434-4617-11e3-b495-00144feabdc0");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/b3c16434-4617-11e3-b495-00144feabdc0.html");
		resources.setDescription("Miliband attacks Britain’s ‘Wonga economy’");
		
		resourcesList.add(resources);
		resourcesList.add(resources1);
		resourcesList.add(resources2);
		resourcesList.add(resources3);
		resourcesList.add(resources4);
		
		return resourcesList;
		
	}
	
private List<Resources> getResources2(String title)  {
		
		List<Resources> resourcesList = new ArrayList<Resources>();
		
	    Resources resources = new Resources();
	    resources.setId("e72a5724-4197-11e3-9073-00144feabdc0");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/e72a5724-4197-11e3-9073-00144feabdc0.html");
		resources.setDescription("Middle-aged Britons are so downbeat about money");
		
		
		Resources resources1 = new Resources();
	    resources.setId("95f0e3ce-31c9-11e3-a16d-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/95f0e3ce-31c9-11e3-a16d-00144feab7de.html");
		resources.setDescription("Extracts from the 2013 shortlist");
		
		
		Resources resources2 = new Resources();
	    resources.setId("a0a64a56-31e8-11e3-a16d-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/a0a64a56-31e8-11e3-a16d-00144feab7de.html");
		resources.setDescription("New York Fed sued over Goldman oversight");
		
		Resources resources3 = new Resources();
	    resources.setId("5f3ac424-2ece-11e3-be22-00144feab7de");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/5f3ac424-2ece-11e3-be22-00144feab7de.html");
		resources.setDescription("Transcript: Peter Voser talks to the FT");
		
		Resources resources4 = new Resources();
	    resources.setId("e5a4d194-f558-11e2-94e9-00144feabdc0");
	    resources.setType("Article");
	    resources.setUrl("http://www.ft.com/cms/s/0/e5a4d194-f558-11e2-94e9-00144feabdc0.html");
		resources.setDescription("Pension plans: Flying solo");
		
		resourcesList.add(resources);
		resourcesList.add(resources1);
		resourcesList.add(resources2);
		resourcesList.add(resources3);
		resourcesList.add(resources4);
		
		return resourcesList;
		
	}

private List<Resources> getResources3(String title)  {
	
	List<Resources> resourcesList = new ArrayList<Resources>();
	
    Resources resources = new Resources();
    resources.setId("525d21b0-3d0c-11e3-a8c4-00144feab7de");
    resources.setType("Article");
    resources.setUrl("http://www.ft.com/cms/s/0/525d21b0-3d0c-11e3-a8c4-00144feab7de.html");
	resources.setDescription("Merkel and Hollande to change intelligence ties with US");
	
	
	Resources resources1 = new Resources();
    resources.setId("6ff46438-42eb-11e3-8350-00144feabdc0");
    resources.setType("Article");
    resources.setUrl("http://www.ft.com/cms/s/0/6ff46438-42eb-11e3-8350-00144feabdc0.html");
	resources.setDescription("Being ethical in business is not as simple as ‘doing the right thing’");
	
	
	Resources resources2 = new Resources();
    resources.setId("dacd1f84-41bf-11e3-b064-00144feabdc0");
    resources.setType("Article");
    resources.setUrl("http://www.ft.com/cms/s/2/dacd1f84-41bf-11e3-b064-00144feabdc0.html");
	resources.setDescription("The internet is not going to save the world, says the Microsoft co-founder, whatever Mark Zuckerberg and Silicon Valley’s tech billionaires believe. But eradicating disease just might.");
	
	Resources resources3 = new Resources();
    resources.setId("525d21b0-3d0c-11e3-a8c4-00144feab7de");
    resources.setType("Article");
    resources.setUrl("http://www.ft.com/cms/s/0/525d21b0-3d0c-11e3-a8c4-00144feab7de.html");
	resources.setDescription("Merkel and Hollande to change intelligence ties with US");
	
	Resources resources4 = new Resources();
    resources.setId("94f74c94-2b69-11e3-a1b7-00144feab7de");
    resources.setType("Article");
    resources.setUrl("http://www.ft.com/cms/s/0/94f74c94-2b69-11e3-a1b7-00144feab7de.html");
	resources.setDescription("Analytics: Fragments of information come together to give the big picture");
	
	resourcesList.add(resources);
	resourcesList.add(resources1);
	resourcesList.add(resources2);
	resourcesList.add(resources3);
	resourcesList.add(resources4);
	
	return resourcesList;
	
}

	private String getAccessCode() throws IOException
	{
		String grantType = "password";
	    String applicationID = "f356fb1d-0595-42ab-acff-81549184df4a";
	    String clientString = "gbtestc";
	    String username = "wokuvustud";
	    String password = "wokuvustud";
	    String url = "https://m-api.ecollege.com/token";
	    HttpsURLConnection httpConn = null;
	    BufferedReader in = null;
	 
	    try {
	 
	      // Create the data to send
	      StringBuilder data = new StringBuilder();
	      data.append("grant_type=" + URLEncoder.encode(grantType, "UTF-8"));
	      data.append("&client_id=" + URLEncoder.encode(applicationID, "UTF-8"));
	      data.append("&username=" + URLEncoder.encode(clientString + "\\" + username, "UTF-8"));
	      data.append("&password=" + URLEncoder.encode(password, "UTF-8"));
	 
	      // Create a byte array of the data to be sent
	      byte[] byteArray = data.toString().getBytes("UTF-8");
	 
	      // Setup the Request
	      URL request = new URL(url);
	      httpConn = (HttpsURLConnection)request.openConnection();
	      httpConn.setRequestMethod("POST");
	      httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	      httpConn.setRequestProperty("Content-Length", "" + byteArray.length);
	      httpConn.setDoOutput(true);
	 
	      // Write data
	      OutputStream postStream = httpConn.getOutputStream();
	      postStream.write(byteArray, 0, byteArray.length);
	      postStream.close();
	 
	      // Send Request & Get Response
	      InputStreamReader reader = new InputStreamReader(httpConn.getInputStream());
	      in = new BufferedReader(reader);
	 
	      // Get the Json reponse containing the Access Token
	      String json = in.readLine();
	 
	      // Parse the Json response and retrieve the Access Token
	      Gson gson = new Gson();
	      Type mapType  = new TypeToken<Map<String,String>>(){}.getType();
	      Map<String,String> ser = gson.fromJson(json, mapType);
	      String accessToken = ser.get("access_token");
	      System.out.println("Access Token = " + accessToken);
	 
	      return accessToken;
	      
	    } catch (java.io.IOException e) {
	 
	      // This exception will be raised if the server didn't return 200 - OK
	      // Retrieve more information about the error
	      System.out.println(e.getMessage());
	 
	    } finally {
	 
	      // Be sure to close out any resources or connections
	      if (in != null) in.close();
	      if (httpConn != null) httpConn.disconnect();
	    }
		return null;
	
	}
}
