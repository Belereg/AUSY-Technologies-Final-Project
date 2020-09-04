package com.ausy_technologies.finalproject;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.ausy_technologies.finalproject.Model.DAO.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")

class FinalprojectApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<Department> departmentList = null;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{methodName}", //unde generam fila
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint()))) //pretty print pt un json frumos
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8083)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(), //adoc files
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .build();

        departmentList = Stream.of(new Department(1,"DEPARTMENTTEST")
                ,new Department(2,"DEPARTMENTTEST22222222"))
                .collect(Collectors.toList());

    }

    @Test
    public void testAddDepartment() throws Exception {
        String departmentsJson=new ObjectMapper().writeValueAsString(departmentList);
        mockMvc.perform(post("/departments/addDepartments")
                .content(departmentsJson)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(departmentsJson));

    }
//
//    @Test
//    public void testGetOrders() throws Exception {
//        mockMvc.perform(get("/departments/getDepartments")
//                .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(departmentList)));
//
//    }

}
