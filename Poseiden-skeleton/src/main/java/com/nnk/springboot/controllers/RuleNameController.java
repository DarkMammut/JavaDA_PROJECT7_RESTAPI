package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class RuleNameController {
    private final RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        model.addAttribute("currentUser", currentUser);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(@AuthenticationPrincipal UserDetails currentUser, RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@AuthenticationPrincipal UserDetails currentUser, @Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.saveRuleName(ruleName);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        ruleNameService.saveRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
