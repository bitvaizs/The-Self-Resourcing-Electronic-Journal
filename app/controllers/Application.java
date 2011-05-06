package controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import models.JournalNumber;
import models.JournalVolume;
import play.mvc.Before;
import play.mvc.Controller;


public class Application extends Controller
{

	@Before
    public static void init(String userRole)
    {
    	boolean conn = Security.isConnected();
    	renderArgs.put("loggedin", conn);
        if (conn)
        {
        	renderArgs.put("user", Security.connected());   
        }
        
        if(userRole != null){
        	if(!conn || !Security.check(userRole)){
        		renderArgs.put("err", "You do not authorization to view this page");
        	}
        }
    }

    public static void index()
    {
    	 List<JournalVolume> volumes = JournalVolume.findAll();
    	 render(volumes);
    }
    
    public static String getJournalNumbers(int volume_id)
    {
    	List<JournalNumber> numbers = JournalNumber.getJournalNumbeByVolume(volume_id);
    	Iterator it = numbers.iterator();
    	String html = "<div class=\"browseHeader\">";
		html +="Journal Numbers";
		html +="</div>";
    	html += "<ul>";
    	while(it.hasNext())
    	{
    		JournalNumber journalNumber = (JournalNumber) it.next();
    		Long journalNumberId = journalNumber.id;
    		Date publishDate = journalNumber.publishDate;
    		html += "<li>";
    		html += "<div class=\"title\">";
    		html += journalNumberId.toString();
    		html += "</div>";
    		html += "<div class=\"year\">";
    		html += publishDate.toString();
    		html += "</div>";
    		html += "</li>";
    	}
    	html += "</ul>";
    	return html;
    }
}