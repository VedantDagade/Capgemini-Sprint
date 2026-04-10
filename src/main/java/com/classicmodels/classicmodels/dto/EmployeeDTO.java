package com.classicmodels.classicmodels.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Integer employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String jobTitle;

    // only the office code, not the whole Office object
    private String officeCode;
    private String officeCity;

    // only the manager's ID, not the whole Employee object
    private Integer managerEmployeeNumber;
    private String managerName;
}