package com.dev.tech.skills.contract;

import com.dev.tech.skills.app.TechSkillsApplication;
import com.dev.tech.skills.app.resource.PersonResource;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechSkillsApplication.class)
public class BaseTestClass {

    @Autowired
    private PersonResource personResource;

    @Before
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(personResource);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}