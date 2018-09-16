import java.io.*;
import java.util.*;
public class pinterestQuestion2{
 
	//@author : amishra95, BJClarke
	
  /**
   * implement your solution here.
   * @param log_path string with path of Nginx access logfile
   * @return A list of integers
   */
  public static int STATIC_CACHE_LIMIT = 210000;
  public static int DYNAMIC_CACHE_LIMIT = 1500000;
  public static int currentStaticCacheSize = 0;
  public static int currentDynamicCacheSize = 0;
  
  static List<Integer> list = new ArrayList<Integer>();    
  
  
  public static int[] idenitfy_cache_hits(String log_path){
    	BufferedReader br = getLogContents(log_path); // BufferedReader containing contents of the log file stated in the JSON intput file
    
    	List<Request> cache = new ArrayList<Request>();
    	
    	try{
    	String line = br.readLine();

    	while (line != null) {
        	handleRequest(line, cache);
        	line = br.readLine();
    	}
      } catch (Exception e) {
        	e.printStackTrace();
        	return null;
      }
    
   int[] ints = new int[list.size()];
    
  for(int i = 0; i < list.size(); i++){
    ints[i] = list.get(i);
  }
  return ints;
    // return an array of the ids of the requests that were cache hits
		
  }
  
  public static class Request {
    	int id;
    	int size;
    	boolean isStatic;
    	String request;
    	String ip;
    
    	public Request(int id, int size, boolean isStatic, String request, String ip) {
        	this.id = id;
        	this.size = size;
        	this.isStatic = isStatic;
      		this.request = request;
        	this.ip = ip;
  	}
  
  public int getSize(){
    return this.size;
  }
    
  public int getId(){
    return this.id;
  }
    
  public boolean getIsStatic(){
    return this.isStatic;
  }
    
  public String getRequest(){
    return this.request;
  }
    
  public String getIP(){
		return this.ip;
  }
  
  
  }
  
  // list contains list of request that will : each request will always be a get and be a .html
  // line: represents the next request to consider, a line in the .log file
  // cache: represents a list of Request objects for requests that are currently in the cache, may be static or dynamic 
  // 		and cache.get(x) happens chronologically before cache.get(x+1)
  // currentStaticCacheSize: represents the total number of bytes of static requests stored in the cache
  // currentDynamicCacheSize: represents the total number of bytes of dynamic requests stored in the cache
  // this function should add GET .html requests in the cache and remove least recently used requests if necesssary
  public static void handleRequest(String line, List<Request> cache) {
    if (isValidRequestToCache(line)) {
      
      for(int i = 0; i < cache.size(); i++){
				if(cache.get(i).getRequest().equals(getRequest(line))){
          if (!isStatic(line)) {
            if (cache.get(i).getIP().equals(getIP(line))) {
              	cache.add(cache.remove(i));
          			list.add(getID(line));
              //	System.out.println("RETURN 1");
          			return;
            }
          } else {
            	cache.add(cache.remove(i));
          		list.add(getID(line));
            //	System.out.println("RETURN 2");
          		return;
          }
      	}        
      }

      // create a new variable
      // add it to the cache
      Request r = new Request(getID(line), getSize(line), isStatic(line), getRequest(line), getIP(line));
      
      if(r.getIsStatic()){
        if(STATIC_CACHE_LIMIT - currentStaticCacheSize - r.getSize()< 0){
          
          if (r.getSize() > STATIC_CACHE_LIMIT) {
            System.out.println("RETURN 3");
            return;
          }
          
          while(STATIC_CACHE_LIMIT - currentStaticCacheSize - r.getSize() < 0) {
						for(int i = 0; i < cache.size(); i++){
              // if cache.get(i) is static
              // 		remove cache.get(i) from cache and decrease currentStaticCacheSize by the size of the request you just removed
              if(cache.get(i).getIsStatic()){
                Request removed = cache.remove(i);
                currentStaticCacheSize = currentStaticCacheSize - removed.getSize();
              	break;
              }
                
              }
            } 
          
          }
          currentStaticCacheSize += r.getSize();
          
          
         }
      else { // is dynamic
         if(DYNAMIC_CACHE_LIMIT - currentDynamicCacheSize - r.getSize()< 0){
          
          if (r.getSize() > DYNAMIC_CACHE_LIMIT) {
            System.out.println("RETURN 4");
            return;
          }
          
          while(DYNAMIC_CACHE_LIMIT - currentDynamicCacheSize - r.getSize() < 0) {
						for(int i = 0; i < cache.size(); i++){
              if(!cache.get(i).getIsStatic() && !cache.get(i).getIP().equals(getIP(line))){
                Request removed = cache.remove(i);
                currentDynamicCacheSize = currentDynamicCacheSize - removed.getSize();
              	break;
              }
                
              }
            } 
          
         
          }
       currentDynamicCacheSize += r.getSize();
      }
      
     // System.out.println(r.getId());
      //System.out.println(r.getRequest());
      cache.add(r);
      
      
      
    }
      
  }
	    

  
  
  
  // request: a line in the .log file
  // returns true if the request is a GET .html request
  public static boolean isValidRequestToCache(String request) {
    String str = getRequest(request);
    String[] splited = str.split("\\s+");
    if(splited[0].startsWith("\"GET") && (splited[1].endsWith(".html"))) {
      return true;
    }
    
    return false;
  }
           
  public static String getRequest(String request){
    String str = request.substring(request.indexOf("\"request\":")+10, request.indexOf("\"code\":"));
   	str = str.trim();
    return str;
  } 
  
  public static String getIP(String request){
    String str = request.substring(request.indexOf("\"ip\":")+5, request.indexOf("\"datestring\":"));
   str = str.trim();
    return str;
  } 
      
  
	public static int getID(String request){
	String[] splited = request.split("\\s+");
   String str = splited[1];
   str = str.replaceAll(",", "");
	int id = Integer.parseInt(str);
    return id;
  }
  
  
  public static int getSize(String request)
  {
    String[] splited = request.split("\\s+");
	  String str = splited[splited.length-1];
    str = str.replaceAll("}", "");
  	int size = Integer.parseInt(str);
    return size;
  }  
  
  // return true if the request URL begins with "/static/"
  public static boolean isStatic(String request)
  {
    String str = getRequest(request);
    String[] splited = str.split("\\s+");
    if(splited[1].startsWith("/static/")){
      return true;
    }
  else
    return false;
    
  }
  
  public static BufferedReader getLogContents(String log_path) {
   
    
    try {
      // String path = "";
    BufferedReader br = new BufferedReader(new FileReader(log_path));
//     String line = br.readLine();
    
//     while (line != null) {
//       	System.out.println(line);
//         if (line.contains("log_path")) {
//           	System.out.println("FOUND LINE");
//           	String[] split = line.split("\"log_path\":");
//           	path = split[split.length-1];
//           	path = path.trim();
//           	path = path.replaceAll("\"", "");
//           	break;
//         }
//         line = br.readLine();
//     }
     
//     System.out.print("PATH: ");
//     System.out.println(path);
//     BufferedReader br1 = new BufferedReader(new FileReader(path));
    
    return br;
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
    
  }
}

