package ee.taltech.arete.controller;

import ee.taltech.arete.java.response.arete.AreteResponseDTO;
import ee.taltech.arete_proxy.AreteProxyApplication;
import ee.taltech.arete_proxy.service.AreteService;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static ee.taltech.arete.initializers.SubmissionInitializer.assertFullSubmission;
import static ee.taltech.arete.initializers.SubmissionInitializer.getSubmissionProlog;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AreteProxyApplication.class)
public class PrologIntegrationTests {

	@Autowired
	private AreteService areteService;

	@BeforeEach
	@SneakyThrows
	public void beforeEach() {
		TimeUnit.SECONDS.sleep(5);
	}

	@Test
	public void addNewSubmissionProlog() {

		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionProlog());

		assertFullSubmission(response);

		assertEquals(9, response.getTotalCount());
		assertEquals(9, response.getTotalPassedCount());
	}
}
