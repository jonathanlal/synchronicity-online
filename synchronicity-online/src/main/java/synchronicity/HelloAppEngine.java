package synchronicity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.counting;
import static java.util.function.Function.identity;

import org.json.*;


import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {

	  
//	    response.setContentType("text/plain");
//	    response.setCharacterEncoding("UTF-8");
//	    PrintWriter out = response.getWriter();
	
	    
//	  ArrayList<String> strings = getXAxis();
	 
	  
	  String json = getJSON();
	  ArrayList<String> data = getFieldAsArrayList(json, "sync");
	  
	  
	  Map<String, Integer> counts = new HashMap<String, Integer>();
	  
	  for (String str : data) {
		  
		  //fixes records which are like 2:2 instead of 02:02
		  if(str.length() == 3) {
			  String base = str.substring(0,1);
			  str = "0"+base+":"+"0"+base;
		  }
		  
		  str= "\""+str+"\"";
		  
	      if (counts.containsKey(str)) {
	          counts.put(str, counts.get(str) + 1);
	      } else {
	          counts.put(str, 1);
	      }
	      
	  }
	   
//	  for (Map.Entry<String, Integer> entry : counts.entrySet()) {
//	      System.out.println(entry.getKey() + " = " + entry.getValue());
//	  }
	  
	  
//	  Set<String> keyset = counts.keySet();
//	  System.out.println(keyset);
	  
	 
//	  System.out.println( counts.values());
	  
	
	  
//	    ArrayList<Record> records = getAllRecords(restRequest());
	    
	    
	    
//	    request.setAttribute("records", records);
	 

	 

      Map<String, Integer> sortedMapDesc = sortByComparator(counts, DESC);
      
      Map<String, Integer> map = new TreeMap<>(sortedMapDesc);

	  Set<String> keyset = map.keySet();
	  
	  
//      request.setAttribute("categories", keyset);
//	  request.setAttribute("data", counts.values());
	  
	  
	  
	String test =  "var options = {chart: {type: 'bar'},series: [{name: 'records',data: "+map.values()+"}],xaxis: {categories: "+keyset+"}}";
	  
	  request.setAttribute("test", test);
	  
	  
	  
	  
	 
		RequestDispatcher rd = request.getRequestDispatcher("test.jsp");
		rd.forward(request, response);
		



  }
  public static boolean ASC = true;
  public static boolean DESC = false;
  private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
  {

      List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

      // Sorting the list based on values
      Collections.sort(list, new Comparator<Entry<String, Integer>>()
      {
          public int compare(Entry<String, Integer> o1,
                  Entry<String, Integer> o2)
          {
              if (order)
              {
                  return o1.getValue().compareTo(o2.getValue());
              }
              else
              {
                  return o2.getValue().compareTo(o1.getValue());

              }
          }
      });

      // Maintaining insertion order with the help of LinkedList
      Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
      for (Entry<String, Integer> entry : list)
      {
          sortedMap.put(entry.getKey(), entry.getValue());
      }

      return sortedMap;
  }

  public ArrayList<String> getFieldAsArrayList(String json, String field){
	  JSONObject obj = new JSONObject(json);
	  
		ArrayList<String> clicked = new ArrayList<String>();
		  for (String keyStr : obj.keySet()) {
			  JSONObject r = (JSONObject) obj.get(keyStr);
		        r.keySet().forEach(keySt ->
			    {
			        Object keyvalu = r.get(keySt);
			        String value = keyvalu.toString();
			        if(keySt.equals(field)) {
			        	clicked.add(value);
			        }
			    });
		    }
	  return clicked;
  }
  public ArrayList<String> getClicked(String json){
	  
	  JSONObject obj = new JSONObject(json);
	  
		ArrayList<String> clicked = new ArrayList<String>();
		  for (String keyStr : obj.keySet()) {
			  JSONObject r = (JSONObject) obj.get(keyStr);
		        r.keySet().forEach(keySt ->
			    {
			        Object keyvalu = r.get(keySt);
			        String value = keyvalu.toString();
			        if(keySt.equals("clicked")) {
			        	clicked.add(value);
			        }
			    });
		    }
	  return clicked;
  }
  
  public ArrayList<String> getXAxis(){
	  ArrayList<String> x = new ArrayList<String>();
	  
	  x.add("00:00");x.add("01:01");x.add("02:02");x.add("03:03");x.add("04:04");x.add("05:05");
	  x.add("06:06");x.add("07:07");x.add("08:08");x.add("09:09");x.add("10:10");x.add("11:11");
	  x.add("12:12");x.add("13:13");x.add("14:14");x.add("15:15");x.add("16:16");x.add("17:17");
	  x.add("18:18");x.add("19:19");x.add("20:20");x.add("21:21");x.add("22:22");x.add("23:23");
		
//	    Map<String, Long> map = x.stream()
//	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//	    
//	    System.out.println(map.size());
//	    
//	    Map<String, Long> map2 = x
//                .stream()
//                .collect(groupingBy(identity(), counting()));
//	    
//	    System.out.println(map2.size());
	  
	  return x;
  }

//  public void createMap() {
//	  
//	  ArrayList<String> x = getXAxis();
//	  HashMap<String, Integer> map = new HashMap<String, Integer>();
//	  
//	  
//	  for(int i = 0; i < x.size();i++) {
//		  
//		  String value = map.get(x.get(i));
//		  if (value != null) {
//		      // Key might be present...
//		      if (map.containsKey(x.get(i))) {
//		         // Okay, there's a key but the value is null
//		    	  
//		    	  //increment by 1
//		    	  
//		      } else {
//		         // Definitely no such key
//		    	  map.put(x.get(i), 1);
//		      }
//		  }else {
//			  //add key
//		  }
//		  
//	  }
//	  
//	  
//	  
//	  
//  }
  
  public ArrayList<Record> getAllRecords(String json){
	  
		JSONObject obj = new JSONObject(json);
		
		ArrayList<Record> records = new ArrayList<Record>();
	  

		
		
		  for (String keyStr : obj.keySet()) {
			  JSONObject r = (JSONObject) obj.get(keyStr);

			  Record record = new Record();
//			  System.out.println(r.toString());
		        r.keySet().forEach(keySt ->
			    {
			        Object keyvalu = r.get(keySt);
			       
			        String value = keyvalu.toString();

			        if(keySt.equals("clicked")) {
			        	record.setClicked(value);
			        }
			        else if(keySt.equals("diff")) {
			        	record.setDiff(value);
			        }
			        else if(keySt.equals("date")) {
			        	record.setDate(value);
			        }
			        else if(keySt.equals("uid")) {
			        	record.setUid(value);
			        }
			        else if(keySt.equals("sync")) {
			        	record.setSync(value);
			        	System.out.println(value);
			        }
			        
			    
			    });
		            records.add(record);
		    }
		return records;
  }
  
  public String getJSON() throws IOException {
	  
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
		 
	return content.toString();
  }
}