package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @BeforeEach
    void setUp() {
        // Setup mock data or behavior if needed
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testListRuleNames() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testValidateRuleName() throws Exception {
        RuleName ruleName = new RuleName(); // Initialize with test data
        when(ruleNameService.saveRuleName(ruleName)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("ruleName", ruleName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName(); // Initialize with test data
        when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/update"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateRuleName() throws Exception {
        RuleName ruleName = new RuleName(); // Initialize with test data
        when(ruleNameService.saveRuleName(ruleName)).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("ruleName", ruleName))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testDeleteRuleName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
                .andDo(MockMvcResultHandlers.print());
    }
}
