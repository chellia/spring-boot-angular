package com.dev.tech.skills.app;


import com.dev.tech.skills.app.model.Address;
import com.dev.tech.skills.app.model.Person;
import com.dev.tech.skills.app.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TechSkillsApplication.class)
@ActiveProfiles("test")
public class PersonResourceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonRepository mockRepository;

    @Before
    public void init() {
        Address address = new Address(1l, "Keetwaltje", "Leeuwarden", "Frisian", "1212UI");
        Person person = new Person(1l, "Gopi", "Chellaiah", null, address);
        when(mockRepository.findById(1L)).thenReturn(Optional.of(person));
    }

    @Test
    public void test_request_with_auth_ok() throws Exception {

        String expected = "{" +
                "  \"id\" : 1," +
                "  \"firstName\" : \"Gopi\"," +
                "  \"lastName\" : \"Chellaiah\"," +
                "  \"dob\" : null," +
                "  \"address\" : {" +
                "    \"id\" : 1," +
                "    \"street\" : \"Keetwaltje\"," +
                "    \"city\" : \"Leeuwarden\"," +
                "    \"state\" : \"Frisian\"," +
                "    \"zipcode\" : \"1212UI\"" +
                "  }" +
                "}";

        ResponseEntity<Person> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/api/v1/persons/1", Person.class);


        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(expected, getJson(response.getBody()), false);

    }

    @Test
    public void test_requst_without_auth_nok_401() {

        ResponseEntity<Person> response = restTemplate
                .getForEntity("/api/v1/persons/1", Person.class);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    private static String getJson(Object object) {
        try {
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
