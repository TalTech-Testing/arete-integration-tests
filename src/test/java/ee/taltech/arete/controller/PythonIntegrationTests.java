package ee.taltech.arete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ee.taltech.arete.java.request.AreteRequestDTO;
import ee.taltech.arete.java.response.arete.AreteResponseDTO;
import ee.taltech.arete_proxy.AreteProxyApplication;
import ee.taltech.arete_proxy.service.AreteService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static ee.taltech.arete.initializers.SubmissionInitializer.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AreteProxyApplication.class)
public class PythonIntegrationTests {

	@Autowired
	private AreteService areteService;

	@BeforeEach
	@SneakyThrows
	public void beforeEach() {
		TimeUnit.SECONDS.sleep(30);
	}

	@Test
	public void testNoFiles() {
		AreteRequestDTO payload = getSubmissionPythonFiles();
		payload.getSystemExtra().add("noFiles");

		AreteResponseDTO response = areteService.makeRequestSync(payload);

		assertEquals(0, response.getTestFiles().size());
		assertEquals(0, response.getFiles().size());
	}

	@Test
	public void testNoStd() {
		AreteRequestDTO payload = getSubmissionPythonFiles();
		payload.getSystemExtra().add("noStd");

		AreteResponseDTO response = areteService.makeRequestSync(payload);

		assertEquals("", response.getConsoleOutputs());
		assertFullSubmission(response);
		assertEquals(23, response.getTotalCount());
		assertEquals(16, response.getTotalPassedCount());

	}

	@Test
	public void testStyle() {
		AreteRequestDTO payload = getSubmissionPythonFiles();

		AreteResponseDTO response = areteService.makeRequestSync(payload);

		assertEquals(0, response.getStyle());
		assertTrue(response.getErrors().size() > 0);
		assertFullSubmission(response);
		assertEquals(23, response.getTotalCount());
		assertEquals(16, response.getTotalPassedCount());
	}

	@Test
	public void testReturnExtra() {
		ObjectNode root = new ObjectMapper().createObjectNode();
		root.put("some", "stuff");
		AreteRequestDTO submission = getSubmissionPythonExam();
		submission.setReturnExtra(root);

		AreteResponseDTO response = areteService.makeRequestSync(submission);

		assertNotNull(response.getOutput());
		assertNotNull(response.getReturnExtra());
	}

	@Test
	public void testMultipleTestSuites() {
		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionPythonExam());

		assertFullSubmission(response);
		assertEquals(55, response.getTotalCount());
		assertEquals(46, response.getTotalPassedCount());
		assertEquals("envomp@ttu.ee", response.getEmail());
	}

	@Test
	public void testLotsOfTests() {
		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionPython());

		assertFullSubmission(response);
		assertEquals(101, response.getTotalCount());
		assertTrue(99 < response.getTotalPassedCount());
		assertEquals("envomp@ttu.ee", response.getEmail());
	}


	@Test
	public void testFirstGroupedSubmission() {
		AreteResponseDTO response = areteService.makeRequestSync(getSubmissionPythonFirstPush());

		assertFullSubmission(response);
		assertEquals(20, response.getTotalCount());
		assertEquals(20, response.getTotalPassedCount());
	}

}
