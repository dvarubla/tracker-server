package ru.aborisov.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aborisov.testtask.config.PrivilegeAlias;
import ru.aborisov.testtask.exception.AppSecurityException;
import ru.aborisov.testtask.exception.ExpenseNotFoundException;
import ru.aborisov.testtask.exception.ValidationException;
import ru.aborisov.testtask.model.ExpenseManager;
import ru.aborisov.testtask.resource.DatePeriod;
import ru.aborisov.testtask.resource.ExpenseCreateData;
import ru.aborisov.testtask.resource.ExpenseData;
import ru.aborisov.testtask.resource.ExpenseUpdateData;
import ru.aborisov.testtask.resource.Id;
import ru.aborisov.testtask.resource.MoneyValue;
import ru.aborisov.testtask.resource.OutputList;
import ru.aborisov.testtask.resource.ResponseStatusBody;
import ru.aborisov.testtask.resource.SearchQuery;

@RestController
public class ExpenseController {
    private final ExpenseManager manager;

    @Autowired
    public ExpenseController(ExpenseManager manager) {
        this.manager = manager;
    }

    @PostMapping("/expense")
    public Id createExpense(@RequestBody ExpenseCreateData data, Authentication authentication)
            throws ValidationException, AppSecurityException {
        return new Id(
                manager.createExpense(data, getLogin(authentication), getCanManageOther(authentication))
        );
    }

    @GetMapping(
            path = "/expenses"
    )
    @ResponseStatus(HttpStatus.OK)
    public OutputList<ExpenseData> searchExpenses(SearchQuery query, Authentication authentication) {
        return manager.findExpenseData(query, getLogin(authentication), getCanManageOther(authentication));
    }

    @PutMapping(
            path = "/expense"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseStatusBody updateExpense(@RequestBody ExpenseUpdateData data, Authentication authentication)
            throws AppSecurityException, ExpenseNotFoundException, ValidationException {
        manager.updateExpense(data, getLogin(authentication), getCanManageOther(authentication));
        return new ResponseStatusBody("Запись изменена");
    }

    @DeleteMapping(
            path = "/expense"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseStatusBody deleteUser(Id id, Authentication authentication)
            throws ExpenseNotFoundException, AppSecurityException {
        manager.deleteExpense(id, getLogin(authentication), getCanManageOther(authentication));
        return new ResponseStatusBody("Запись удалёна");
    }

    @GetMapping(
            path = "/expenses/average"
    )
    @ResponseStatus(HttpStatus.OK)
    public MoneyValue getAverageByPeriod(DatePeriod period, Authentication authentication) {
        return manager.getAverageByPeriod(period, getLogin(authentication), getCanManageOther(authentication));
    }

    @GetMapping(
            path = "/expenses/total"
    )
    @ResponseStatus(HttpStatus.OK)
    public MoneyValue getTotalByPeriod(DatePeriod period, Authentication authentication) {
        return manager.getTotalByPeriod(period, getLogin(authentication), getCanManageOther(authentication));
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
