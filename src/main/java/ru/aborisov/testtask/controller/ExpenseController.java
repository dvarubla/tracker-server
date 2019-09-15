package ru.aborisov.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.aborisov.testtask.config.PrivilegeAlias;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.ExpenseManager;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.Id;

@RestController
public class ExpenseController {
    private final ExpenseManager manager;

    @Autowired
    public ExpenseController(ExpenseManager manager) {
        this.manager = manager;
    }

    @PostMapping("/expenses")
    public Id createExpense(@RequestBody ExpenseCreateData data, Authentication authentication)
            throws ValidationException, AppSecurityException {
        return new Id(
                manager.createExpense(data, getLogin(authentication), getCanManageOther(authentication))
        );
    }

    private boolean getCanManageOther(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        return details.getAuthorities().contains(
                new SimpleGrantedAuthority(PrivilegeAlias.MANAGE_OTHER_RECORDS.getAlias())
        );
    }

    private String getLogin(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        return details.getUsername();
    }
}