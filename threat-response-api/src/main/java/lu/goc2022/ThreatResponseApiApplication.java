package lu.goc2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThreatResponseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreatResponseApiApplication.class, args);
	}
}
