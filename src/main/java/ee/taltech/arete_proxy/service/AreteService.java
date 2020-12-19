package ee.taltech.arete_proxy.service;

import ee.taltech.arete.java.AuthenticationServiceClient;
import ee.taltech.arete.java.request.AreteRequestDTO;
import ee.taltech.arete.java.response.arete.AreteResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AreteService {

	private final Logger logger;
	private final AuthenticationServiceClient areteClient;

	public AreteResponseDTO makeRequestSync(AreteRequestDTO areteRequest) {
		logger.info("Forwarding a sync submission: {} {}", areteRequest.getUniid(), areteRequest.getHash());
		AreteResponseDTO responseDTO = areteClient.requestSync(areteRequest);
		System.out.println(responseDTO.getOutput());
		return responseDTO;
	}

}
