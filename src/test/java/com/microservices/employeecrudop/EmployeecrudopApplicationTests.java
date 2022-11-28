package com.microservices.employeecrudop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
class EmployeecrudopApplicationTests {

	@Test
	void contextLoads() {
	}
	// we need to write here the junit test cases to test api anyone you can test
	// whatever method we have used at controller the same method we can use here but just append it with TEST word

		@Test
		public void empIdListTest() throws URISyntaxException
		{
			System.out.println("test started");
			RestTemplate restTemplate = new RestTemplate();
			String url ="http://localhost:8080/findAllEmployee"; // this can be created dynamically
			URI uri = new URI (url);
			ResponseEntity<String> response =restTemplate.getForEntity(uri,String.class);
			// http status code --> success/fail
			Assertions.assertEquals(200,response.getStatusCodeValue());
			System.out.println(response.getStatusCodeValue());
			System.out.println("Test Ended");
		}
}