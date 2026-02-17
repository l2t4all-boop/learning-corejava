package com.careerit.cj.empstats;

public class EmployeeStatsManager {

    public static void main(String[] args) {

        EmployeeService employeeService = new EmployeeService();

        employeeService.getEmpMgrDeptDto().forEach(System.out::println);

        employeeService.getEmployeeNames("ACCOUNTING")
                .forEach(System.out::println);
    }
}
