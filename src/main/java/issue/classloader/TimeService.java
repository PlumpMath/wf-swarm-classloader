package issue.classloader;

import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;
import com.netflix.ribbon.proxy.annotation.ResourceGroup;
import com.netflix.ribbon.proxy.annotation.TemplateName;
import com.netflix.ribbon.proxy.annotation.ClientProperties;
import com.netflix.ribbon.proxy.annotation.ClientProperties.Property;

import io.netty.buffer.ByteBuf;

@ClientProperties(properties = { @Property(name = "ReadTimeout", value = "2000"),
		@Property(name = "ConnectTimeout", value = "1000"),
		@Property(name = "MaxAutoRetriesNextServer", value = "2"),
		@Property(name = "NFLoadBalancerClassName", value = "issue.classloader.MyLoadBalancer")
		}, exportToArchaius = true)
@ResourceGroup(name = "timeservice")
public interface TimeService {

	@TemplateName("time")
	@Http(method = HttpMethod.GET, uri = "/time")
	RibbonRequest<ByteBuf> getTime();
}
