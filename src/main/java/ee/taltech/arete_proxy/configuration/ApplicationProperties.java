package ee.taltech.arete_proxy.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.arete.java.AuthenticationServiceClient;
import ee.taltech.arete.java.LoadBalancerClient;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ApplicationProperties {
	private String authenticationServiceUrl = System.getProperty("ARETE_URL", "https://cs.ttu.ee/services/arete/api/v2");
	//	private String authenticationServiceUrl = System.getProperty("ARETE_URL", "http://127.0.0.1:8001/services/arete/api/v2");
	private String loadBalancerUrl = "http://127.0.0.1:8098";
	private String token = "arete-integration-test " + System.getenv("ARETE_TOKEN");

	@Bean
	@Scope("prototype")
	public Logger produceLogger(InjectionPoint injectionPoint) {
		Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
		return LoggerFactory.getLogger(classOnWired);
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}

	@Bean
	public LoadBalancerClient loadBalancerClient() {
		return new LoadBalancerClient(loadBalancerUrl);
	}

	@Bean
	public AuthenticationServiceClient authenticationServiceClient() {
		return new AuthenticationServiceClient(authenticationServiceUrl, token);
	}

}

