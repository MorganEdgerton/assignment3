package assign.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class EavesdropService{

	static JSoupHandler jsoupHandler;
	
	public EavesdropService() {
		if (jsoupHandler == null) {
			jsoupHandler = new JSoupHandler();
		}
	}
	
	public void setJSoupHandler(JSoupHandler jsoupHandler) {
		this.jsoupHandler = jsoupHandler;
	}
	

    public static ArrayList<String> parseProjects(String source) throws ServletException, IOException
    {
    	ArrayList<String> allProjects = new ArrayList<String>();
    	try {		    
			String sourceURL = source;	
			Elements links = jsoupHandler.getElements(sourceURL);
			

		    if (links != null) {
		    	Boolean start = false;
			    ListIterator<Element> iter = links.listIterator();		    	
			    while(iter.hasNext()) {
		    			Element e = (Element) iter.next();
		    			String s = e.html();
		    			
		    			if ( s != null && start == true) {
		    				allProjects.add(s);
		    			}
		    			if(s.equals("Parent Directory")){
		    				start = true;
		    			}
			    }	    
		    }
		    else {
		    		//w.println("Unknown project " + project);
		    }
		} catch (Exception exp) {
			exp.printStackTrace();
		}	
		
		return allProjects;
    }
    
    
    public static ArrayList<String> parseMeetings(String source, String projectName) throws ServletException, IOException
    {
    	ArrayList<String> allProjects = new ArrayList<String>();
    	try {		    
			String sourceURL = source;	
			Elements links = jsoupHandler.getElements(sourceURL);
			

		    if (links != null) {
		    	Boolean start = false;
			    ListIterator<Element> iter = links.listIterator();		    	
			    while(iter.hasNext()) {
		    			Element e = (Element) iter.next();
		    			String s = e.html();
		    			
		    			if ( s != null && start == true) {
		    				allProjects.add(s);
		    			}
		    			if(s.equals("Parent Directory")){
		    				start = true;
		    			}
			    }	    
		    }
		    else {
		    	//String err = "Project " + projectName + " does not exist";
		    }
		} catch (Exception exp) {
			exp.printStackTrace();
		}	
		
		return allProjects;
    }
    
	
}
