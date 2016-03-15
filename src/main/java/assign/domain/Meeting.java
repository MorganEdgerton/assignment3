package assign.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "year")
public class Meeting {
	
	private String year;
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
}