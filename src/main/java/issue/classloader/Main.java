package issue.classloader;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jgroups.JGroupsFraction;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.topology.Topology;
import org.wildfly.swarm.topology.TopologyArchive;
import org.wildfly.swarm.topology.TopologyListener;

public class Main {
	public static void main(String[] args) throws Exception {

		URL stageConfig = Main.class.getClassLoader().getResource("project-stages.yml");

		Swarm swarm = new Swarm().withStageConfig(stageConfig);

		Fraction<JGroupsFraction> fraction = new JGroupsFraction().applyMulticastDefaults();

		swarm.fraction(fraction);

		JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);

		archive.addPackage(Main.class.getPackage());
		archive.addAllDependencies();

		String serviceName = swarm.stageConfig().resolve("service.service-name").getValue();

		archive.as(TopologyArchive.class).advertise(serviceName);

		swarm.start().deploy(archive);

		Topology lookup = Topology.lookup();
		lookup.addListener(new TopologyListener() {
			@Override
			public void onChange(Topology tplg) {
				System.out.println("Topologie geändert!!");
				printTopology(lookup);
			}
		});

		printTopology(lookup);

	}

	public static void printTopology(Topology lookup) {
		Map<String, List<Topology.Entry>> asMap = lookup.asMap();
		for (String key : asMap.keySet()) {
			System.out.println("Key: " + key + " - Value: " + asMap.get(key));
		}
	}
}
