package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Transactional
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Transactional
    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Transactional
    public RuleName getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
    }

    @Transactional
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
