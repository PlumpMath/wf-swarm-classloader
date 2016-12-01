package issue.classloader;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.config.ConfigurationManager;
import com.netflix.ribbon.Ribbon;

@Path("")
public class ApplicationREST {

	@GET
	@Path("proxiedtime")
	@Produces(MediaType.TEXT_PLAIN)
	public String proxiedtime() {
		//ConfigurationManager.getConfigInstance().setProperty("TimeService.ribbon." + CommonClientConfigKey.NFLoadBalancerClassName, "issue.classloader.MyLoadBalancer");
		//System.setProperty("TimeService.ribbon.NFLoadBalancerClassName", "issue.classloader.MyLoadBalancer");
		//System.setProperty("ribbon.NFLoadBalancerClassName", "issue.classloader.MyLoadBalancer");
		TimeService timeService = Ribbon.from(TimeService.class);
		return timeService.getTime().execute().toString();
	}
	
	@GET
	@Path("time")
	@Produces(MediaType.TEXT_PLAIN)
	public String time() {
		Calendar calendar = GregorianCalendar.getInstance();
		DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	    fmt.setCalendar(calendar);
	    return fmt.format(calendar.getTime());
	}

}
