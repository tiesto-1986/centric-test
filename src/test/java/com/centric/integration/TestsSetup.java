package com.centric.integration;

import com.centric.CentricTestApplication;
import com.centric.integration.TestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {CentricTestApplication.class, TestConfiguration.class})
@Ignore
public class TestsSetup {

	protected MockMvc mvc;

	protected static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	@Rule
	public PreparedDbRule db =
	    EmbeddedPostgresRules.preparedDatabase(
	        FlywayPreparer.forClasspathLocation("db/migration"));


	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	public <T> List<T> getListFromResponse(MvcResult result, Class<T> returnType) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = getDataFromResult(result);
		return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, returnType));
	}

	public static <T> T getObjectFromResponse(MvcResult result, Class<T> returnType) throws Exception {
		String json = getDataFromResult(result);
		return objectMapper.readValue(json, returnType);
	}

	public static String getDataFromResult(MvcResult result) throws UnsupportedEncodingException {
		return result.getResponse().getContentAsString();
	}
}
