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

import static ee.taltech.arete.initializers.SubmissionInitializer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AreteProxyApplication.class)
public class JavaIntegrationTests {

	@Autowired
	private AreteService areteService;

	@BeforeEach
	@SneakyThrows
	public void beforeEach() {
		TimeUnit.SECONDS.sleep(5);
	}

	@Test
	public void addNewSubmissionSyncReturnsOutput() {
		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionJavaFiles());

		assertFullSubmission(response);
		assertEquals(23, response.getTotalCount());
		assertEquals(23, response.getTotalPassedCount());
	}


	@Test
	@Ignore // Git LFS file got removed and now it can't be cloned
	public void addNewSubmissionSyncReturnsFullSubmission() {
		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionJava());

		assertFullSubmission(response);
		assertEquals(23, response.getTotalCount());
		assertEquals(19, response.getTotalPassedCount());
	}
}
