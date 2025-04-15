package com.example.hr.domain;

import java.util.List;

// Ubiquitous Language {TCkimlikNo, FullName, Salary, ...} -> Bounded Context -> Core Sub-domain
public class Employee {
    private TcKimlikNo identity;
    private FullName fullName;
    private Salary salary;
    private Iban iban;
    private BirthYear birthYear;
    private List<Department> departments;
    private JobStyle jobStyle;
}
