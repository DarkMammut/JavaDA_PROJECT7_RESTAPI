package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRuleNames_shouldReturnRuleNames() {
        RuleName ruleName = RuleName.builder().id(1).name("TestRule").build();
        when(ruleNameRepository.findAll()).thenReturn(Collections.singletonList(ruleName));

        List<RuleName> result = ruleNameService.getAllRuleNames();

        assertEquals(1, result.size());
        assertEquals(ruleName, result.get(0));
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void saveRuleName_shouldReturnSavedRuleName() {
        RuleName ruleName = RuleName.builder().id(1).name("TestRule").build();
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName result = ruleNameService.saveRuleName(ruleName);

        assertEquals(ruleName, result);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void getRuleNameById_shouldReturnRuleName() {
        RuleName ruleName = RuleName.builder().id(1).name("TestRule").build();
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getRuleNameById(1);

        assertEquals(ruleName, result);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void getRuleNameById_shouldThrowExceptionWhenNotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ruleNameService.getRuleNameById(1));
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void deleteRuleName_shouldCallDeleteById() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
