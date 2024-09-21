package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setUp() {
        ruleName = RuleName.builder()
                .id(1)
                .name("Test Rule")
                .description("Test Description")
                .json("{\"key\": \"value\"}")
                .template("Test Template")
                .sqlStr("SELECT * FROM Test")
                .sqlPart("WHERE Test = 1")
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showRuleNameListView() throws Exception {
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);

        when(ruleNameService.getAllRuleNames()).thenReturn(ruleNames);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showAddRuleNameView() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testValidateAddRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("name", "Test Rule")
                        .param("description", "Test Description")
                        .param("json", "{\"key\": \"value\"}")
                        .param("template", "Test Template")
                        .param("sqlStr", "SELECT * FROM Test")
                        .param("sqlPart", "WHERE Test = 1"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showUpdateRuleNameView() throws Exception {
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testUpdateRuleName() throws Exception {
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Updated Rule")
                        .param("description", "Updated Description")
                        .param("json", "{\"key\": \"updatedValue\"}")
                        .param("template", "Updated Template")
                        .param("sqlStr", "SELECT * FROM UpdatedTest")
                        .param("sqlPart", "WHERE UpdatedTest = 1"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testDeleteRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}
