package synchronicity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;


import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

	  
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	  
	  
	// Load the service account key JSON file
	  FileInputStream serviceAccount = new FileInputStream("WEB-INF/synchronicity-a1795-firebase-adminsdk-4r21q-cfaf47bd65.json");

	  // Authenticate a Google credential with the service account
	  GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);

	  // Add the required scopes to the Google credential
	  GoogleCredential scoped = googleCred.createScoped(
	      Arrays.asList(
	        "https://www.googleapis.com/auth/firebase.database",
	        "https://www.googleapis.com/auth/userinfo.email"
	      )
	  );

	  // Use the Google credential to generate an access token
	  scoped.refreshToken();
	  String token = scoped.getAccessToken();

	  // See the "Using the access token" section below for information
	  // on how to use the access token to send authenticated requests to the
	  // Realtime Database REST API.
	  
	  
	 String db = "https://synchronicity-a1795.firebaseio.com/users/B5bOrHmThxR0nA1Cph11ZEIO8WJ3/records.json?access_token="+token;
	  
	 
	 URL url = new URL(db);
	 HttpURLConnection con = (HttpURLConnection) url.openConnection();
	 con.setRequestMethod("GET");
	
	 
	 BufferedReader in = new BufferedReader(
			  new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
	 
	 
//	 System.out.println(content.toString());
	 
			JSONObject obj = new JSONObject(content.toString());

	 
			for(int x = 0; x < obj.length(); x++) {
				
				String clicked = 
				
			    response.getWriter().print(obj.length());
				
				
			}
	 




  }
}