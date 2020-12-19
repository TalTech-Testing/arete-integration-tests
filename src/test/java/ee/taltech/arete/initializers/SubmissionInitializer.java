package ee.taltech.arete.initializers;

import ee.taltech.arete.java.request.AreteRequestDTO;
import ee.taltech.arete.java.request.SourceFileDTO;
import ee.taltech.arete.java.response.arete.AreteResponseDTO;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class SubmissionInitializer {
	private final static String UNIID_GIT = "envomp";

	private static final String TESTER_REPO_PYTHON_2019 = "git@gitlab.cs.ttu.ee:iti0102-2019/ex.git";

	private static final String STUDENT_REPO_PYTHON_SINGLE_2020 = "git@gitlab.cs.ttu.ee:envomp/iti0102-2020-janguru.git";
	private static final String STUDENT_REPO_PYTHON_2020 = "git@gitlab.cs.ttu.ee:envomp/iti0102-2020.git";
	private static final String TESTER_REPO_PYTHON_2020 = "git@gitlab.cs.ttu.ee:iti0102-2020/ex.git";

	private static final String STUDENT_REPO_PYTHON_EXAM_2018 = "git@gitlab.cs.ttu.ee:iti0102-2018/exams/exam2-envomp.git";
	private static final String TESTER_REPO_PYTHON_EXAM_2018 = "git@gitlab.cs.ttu.ee:iti0102-2018/ex.git";

	private static final String STUDENT_REPO_JAVA_2019 = "git@gitlab.cs.ttu.ee:envomp/iti0202-2019.git";
	private static final String TESTER_REPO_JAVA_2019 = "git@gitlab.cs.ttu.ee:iti0202-2019/ex.git";

	private static final String STUDENT_REPO_PROLOG_2019 = "https://gitlab.cs.ttu.ee/envomp/iti0211-2019.git";
	private static final String TESTER_REPO_PROLOG_2019 = "https://gitlab.cs.ttu.ee/iti0211-2019/tests.git";

	private final static String TESTING_PLATFORM_JAVA = "java";
	private final static String TESTING_PLATFORM_PYTHON = "python";
	private final static String TESTING_PLATFORM_PROLOG = "prolog";

	private final static String EXTRA = "stylecheck";

	public static AreteRequestDTO getSubmissionUva() {
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitStudentRepo(STUDENT_REPO_PYTHON_2020)
				.testingPlatform("uva")
				.systemExtra((new HashSet<>(Arrays.asList("overrideContentRoot", "skipCopyingTests", "noMail", "integration_tests"))))
				.dockerTestRoot("567")
				.build();
	}

	public static AreteRequestDTO getSubmissionJava() {
		String hash = "f951d41763c6b0b6d0def92722241e5746bb0b3c";
		return AreteRequestDTO.builder()
				.gitStudentRepo(STUDENT_REPO_JAVA_2019)
				.gitTestRepo(TESTER_REPO_JAVA_2019)
				.hash(hash)
				.testingPlatform(TESTING_PLATFORM_JAVA)
				.systemExtra((new HashSet<>(Arrays.asList("noMail", "integration_tests"))))
				.build();
	}

	public static AreteRequestDTO getSubmissionPython() {
		String hash = "d90bc5ff9b0e0b87928a35796cb4022fb77eed8a";
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitStudentRepo(STUDENT_REPO_PYTHON_2020)
				.systemExtra((new HashSet<>(Arrays.asList("noMail", "integration_tests"))))
				.gitTestRepo(TESTER_REPO_PYTHON_2020)
				.testingPlatform(TESTING_PLATFORM_PYTHON)
				.hash(hash)
				.priority(10)
				.build();
	}

	public static AreteRequestDTO getSubmissionPythonExam() {
		String hash = "b9a7e15b14d31b8b6af47a5b53af73051e32e38b";
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitStudentRepo(STUDENT_REPO_PYTHON_EXAM_2018)
				.gitTestRepo(TESTER_REPO_PYTHON_EXAM_2018)
				.testingPlatform(TESTING_PLATFORM_PYTHON)
				.systemExtra((new HashSet<>(Arrays.asList("noStd", "noFeedback", "noMail", "integration_tests"))))
				.hash(hash)
				.dockerExtra(EXTRA)
				.build();
	}

	public static AreteRequestDTO getSubmissionProlog() {
		String hash = "79f0f49766593c95c051e1dbf8d2af1c7c383b01";
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitStudentRepo(STUDENT_REPO_PROLOG_2019)
				.gitTestRepo(TESTER_REPO_PROLOG_2019)
				.testingPlatform(TESTING_PLATFORM_PROLOG)
				.systemExtra((new HashSet<>(Arrays.asList("noMail", "integration_tests"))))
				.hash(hash)
				.build();
	}

	@SneakyThrows
	public static AreteRequestDTO getSubmissionJavaFiles() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitTestRepo(TESTER_REPO_JAVA_2019)
				.testingPlatform(TESTING_PLATFORM_JAVA)
				.dockerExtra("-r ~CHECKSTYLE")
				.systemExtra(new HashSet<>(Arrays.asList("noMail", "integration_tests")))
				.source(new ArrayList<>(Collections.singletonList(
						SourceFileDTO.builder()
								.path("EX01IdCode/src/ee/taltech/iti0202/idcode/IDCode.java")
								.contents(IOUtils.toString(Objects.requireNonNull(classloader.getResourceAsStream("IDCode.java")), StandardCharsets.UTF_8))
								.build())))
				.build();
	}

	@SneakyThrows
	public static AreteRequestDTO getSubmissionPythonFiles() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitTestRepo(TESTER_REPO_PYTHON_2019)
				.testingPlatform(TESTING_PLATFORM_PYTHON)
				.dockerExtra(EXTRA)
				.systemExtra((new HashSet<>(Arrays.asList("integration_tests", "noMail"))))
				.source(new ArrayList<>(Collections.singletonList(
						SourceFileDTO.builder()
								.path("ex04_cipher/cipher.py")
								.contents(IOUtils.toString(Objects.requireNonNull(classloader.getResourceAsStream("cipher.py")), StandardCharsets.UTF_8))
								.build())))
				.build();
	}

	public static AreteRequestDTO getSubmissionPythonFirstPush() {
		String hash = "3a576489c3b3db981dcc93badbf2665bb5c1be1f";
		return AreteRequestDTO.builder()
				.uniid(UNIID_GIT)
				.gitStudentRepo(STUDENT_REPO_PYTHON_SINGLE_2020)
				.gitTestRepo(TESTER_REPO_PYTHON_2020)
				.hash(hash)
				.testingPlatform(TESTING_PLATFORM_PYTHON)
				.systemExtra((new HashSet<>(Arrays.asList("noMail", "integration_tests"))))
				.dockerExtra(EXTRA)
				.build();
	}

	public static void assertFullSubmission(AreteResponseDTO submission) {
		assert submission.getUniid() != null;
		assert submission.getHash() != null;
		assert submission.getTimestamp() != null;
		assert !submission.getFailed();
		assert submission.getTestSuites().size() > 0;
		assert submission.getTestSuites().get(0).getUnitTests().size() != 0;
		assert submission.getTestSuites().get(0).getUnitTests().get(0).getName() != null;
		assert submission.getTotalGrade() != null;
		assert submission.getType() != null;
		assert submission.getVersion() != null;
		assert submission.getFiles().size() > 0;
		assert submission.getTestFiles().size() > 0;
	}
}
