package com.ft.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.apache4.ApacheHttpClient4;


@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
	
	private String template;
	private String defaultName;
	public static final String courseUrl = "https://m-api.ecollege.com/me/courses";
	
	public CourseResource(String template, String defaultName) 
	{
		this.template = template;
        this.defaultName = defaultName;
	}
	
	@GET
	public List<Course> course() throws IOException, ParseException {
		
		List<Course> courses = new ArrayList<Course>();
		
		String accessCode = "Access_Token access_token="+getAccessCode();
		 
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
		JSONArray jsonArray = finalResult.getJSONArray("courses");
		//.out.println("Course Length" + jsonArray.length());
		
		for(int i=0; i< jsonArray.length(); i++)
		{
			JSONObject objectInArray = jsonArray.getJSONObject(i);
			JSONArray jsonArray1 = objectInArray.getJSONArray("links");
			
			JSONObject objectInArray1 = jsonArray1.getJSONObject(0);
			String value = objectInArray1.getString("href");
			
			Course course = getCourseInfo(accessCode, value);
			courses.add(course);
			
			System.out.println("Course URL" + value);
		}
		
		
		return courses;
	}
	
	private Course getCourseInfo(String accessCode, String value) throws ClientProtocolException, IOException, ParseException {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(value);
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
		
		// System.out.println(builder1.toString());
		 
		course.setDescription(builder1.toString());
		
		return course;
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
	     // System.out.println("Access Token = " + accessToken);
	 
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
