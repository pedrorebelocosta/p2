package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.UserRoleEnum;

import java.time.LocalDate;

public class Employee extends User {
    private LocalDate hireDate;
    private LocalDate terminationDate;

    public Employee() {
    }

    public Employee(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, Boolean active, UserRoleEnum role, LocalDate hireDate, LocalDate terminationDate) {
        super(username, password, firstName, lastName, dateOfBirth, active, role);
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
    }

    public Employee(Integer id, String username, String password, String firstName, String lastName, LocalDate dateOfBirth, Boolean active, UserRoleEnum role, LocalDate hireDate, LocalDate terminationDate) {
        super(id, username, password, firstName, lastName, dateOfBirth, active, role);
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }
}
