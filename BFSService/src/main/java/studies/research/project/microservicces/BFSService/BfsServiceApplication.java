package studies.research.project.microservicces.BFSService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BfsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfsServiceApplication.class, args);
	}

}
