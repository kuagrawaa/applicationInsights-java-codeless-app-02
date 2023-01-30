package com.example.appinsighttrainingdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.common.io.CharStreams;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//import com.microsoft.applicationinsights.TelemetryClient;

@RestController
public class AiHelloController {

	private static final Logger logger = LogManager.getLogger();

	//TelemetryClient client = new TelemetryClient();

	
	@GetMapping("/")
	public String landingPageServiceB() {
		//return get("http://localhost:8082/api2/hello");
		//return get("http://localhost:8080/swagger-ui/index.html#/ai-hello-controller");
		//return "Please access https://webappaidemojvcdloptlinuxservice2.azurewebsites.net/swagger-ui/#/ai-hello-controller";
		return "Open Swagger API via http://localhost:8082/swagger-ui/index.html#/ai-hello-controller";
	}
	
	@GetMapping("/hello")
	public String helloFromServiceB() {
		//client.trackEvent("JAVATesting");
		return "Hello from Service B";
	}

	@GetMapping("/hello/ServiceB")
	public String helloWorldFromServiceB() {
		return "Hello World from Service B";
	}

	/*
	 * @RequestMapping("/hello/12a34b/kd/23") public String index3(@PathVariable
	 * String from, @PathVariable String to) { return "Hello Kuldeep A!"; }
	 */

	@GetMapping("/http-dependency/success")
	public String httpDependencyServiceB() throws IOException {
		return get("https://httpstat.us/200");
	}

	@GetMapping("/http-dependency/failure")
	public String httpDependencyFailureServiceB() throws IOException {
		return get("https://httpstat.us/500");
	}

	@GetMapping("/jdbc-dependency/success")
	public String jdbcDependencySuccessServiceB() throws SQLException {
		try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
			try (Statement statement = connection.createStatement()) {
				statement.execute("select 1");
			}
		} catch (SQLException e) {
			return e.getMessage();
		}
		return "SQL Call from ServiceB sent success";
	}

	@GetMapping("/jdbc-dependency/failure")
	public String jdbcDependencyFailureServiceB() throws SQLException {
		try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
			try (Statement statement = connection.createStatement()) {
				statement.execute("select OOPS");
			}
		} catch (SQLException e) {
			return e.getMessage();
		}
		return "SQL Call from ServiceB sent Failure";
	}

	private static String get(String url) throws IOException {
		return getWithApacheHttpClient4(url);
	}

	private static String getWithApacheHttpClient4(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		HttpResponse response = httpClient.execute(httpGet);
		InputStream content = response.getEntity().getContent();
		InputStreamReader reader = new InputStreamReader(content);
		String body = CharStreams.toString(reader);
		// String body = "test";
		content.close();
		httpClient.close();
		return "Response from " + url + " was: " + body;
	}

	@GetMapping("/log/fatal")
	public String logFatalServiceB() throws IOException {
		logger.error("this is a fatal message from service B");
		return "fatal message was logged from service B";
	}

	@GetMapping("/log/error")
	public String logErrorServiceB() throws IOException {
		logger.error("this is an error message from service B");
		return "error message was logged from service B";
	}

	@GetMapping("/log/warn")
	public String logWarnServiceB() throws IOException {
		logger.warn("this is a warn message from service B");
		return "warn message was logged from service B";
	}

	@GetMapping("/log/info")
	public String logInfoServiceB() throws IOException {
		logger.info("this is an info message from service B");
		return "info message was logged from service B";
	}

	@GetMapping("/log/debug")
	public String logDebugServiceB() throws IOException {
		logger.debug("this is a debug message from service B");
		return "debug message was logged from service B";
	}

	@GetMapping("/log/trace")
	public String logTraceServiceB() throws IOException {
		logger.trace("this is a trace message from service B");
		return "trace message was logged from service B";
	}
}
