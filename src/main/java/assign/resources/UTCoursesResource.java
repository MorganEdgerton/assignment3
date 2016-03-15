package assign.resources;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jsoup.nodes.Element;

import assign.services.EavesdropService;
import assign.domain.*;

@Path("/")
public class UTCoursesResource {
	
	EavesdropService eavesdropService;
	
	public UTCoursesResource() {
		this.eavesdropService = new EavesdropService();
	}
	
	@GET
	@Path("/helloapp")
	@Produces("text/html")
	public String helloWorld() {
		return "Hello world";		
	}

	
	@GET
	@Path("/projects")
	@Produces("application/xml")
	public StreamingOutput getAllProjects() throws Exception {
		
		//get all projects and add them (in EavesdropService)
		String source = "http://eavesdrop.openstack.org/meetings/";
		
		ArrayList<String> allProjects = EavesdropService.parseProjects(source);	
		final Projects projects = new Projects();		    	
		projects.setProjects(allProjects);		
			    
	    return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	            outputProjects(outputStream, projects);
	         }
	      };	    
	}
	
	@GET
	@Path("projects/{project}/meetings")
	@Produces("application/xml")
	public StreamingOutput getMeetings(@PathParam("project") String project) throws Exception {
		
		//get all meetings of 'project' and add them (in EavesdropService)
		String source = "http://eavesdrop.openstack.org/meetings/" + project;
		System.out.println(source);
		
		ArrayList<String> allMeetings = EavesdropService.parseMeetings(source, project);
		final Meetings meetings = new Meetings();		    	
		meetings.setMeetingList(allMeetings);
		if(allMeetings.isEmpty()){
			final Output err = new Output();
			err.setError("Project " + project + " does not exist");		
			return new StreamingOutput() {
				public void write(OutputStream outputStream) throws IOException, WebApplicationException {
					outputError(outputStream, err);
				}
			};
		}
			    
	    return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	        		 outputMeetings(outputStream, meetings);
	         }
	      };
	    
	}
	
	//MARSHALLING METHODS
	
	protected void outputMeetings(OutputStream os, Meetings meetings) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Meetings.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(meetings, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputProjects(OutputStream os, Projects projects) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Projects.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(projects, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	
	
	protected void outputError(OutputStream os, Output error) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Output.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(error, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
}