package com.example.hr.domain;

import java.util.List;
// Analysis --> Design
// Analysis: Domain/Sub-Domain/Core Sub-Domain -> Analysis Model
// Design:   Bounded-Context -> Design Model -> Ubiquitous Language -> OO Design -> Class -> Design Class 
// Ubiquitous Language {TCkimlikNo, FullName, Salary, ...} -> Bounded Context -> Core Sub-domain
// Entity Class -- 1 --> identity -> identity
//              -- 2 --> persistent
//              -- 3 --> behavior -> business method
public class Employee {
    private TcKimlikNo identity;
    private FullName fullName;
    private Salary salary;
    private Iban iban;
    private BirthYear birthYear;
    private List<Department> departments;
    private JobStyle jobStyle;
    private Photo photo;
}
