package com.careerit.cj.empstats;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeService {

    private DeptWrapper deptWrapper;
    private List<Employee> employees;
    private List<Department> departments;

    private Map<Integer, Employee> empMap;
    private Map<Integer, DeptDto> deptMap;
    private Map<Integer, List<Employee>> deptEmpMap;

    public EmployeeService() {
        init();

    }

    private void init() {
        deptWrapper = JsonReaderUtil.getDepartmentDetails();
        departments = deptWrapper.getDepartments();
        deptMap = departments == null ? Map.of() : departments.stream()
                .collect(Collectors.toMap(Department::getDeptno,
                        this::toDeptDto));
        deptEmpMap = departments == null ? Map.of() : departments.stream()
                .collect(Collectors.toMap(Department::getDeptno,
                        Department::getEmployees));
        employees = new ArrayList<>();
        for (Department dept : departments) {
            List<Employee> empList = dept.getEmployees()
                    .stream()
                    .map(e -> {
                        e.setDeptno(dept.getDeptno());
                        return e;
                    }).toList();
            employees.addAll(empList);
        }
        empMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmpno,
                        Function.identity()));

    }

    private DeptDto toDeptDto(Department department) {
        return new DeptDto(department.getDeptno(), department.getDname(), department.getLoc());
    }

    // Get unique job title
    public Collection<String> getUniqueJobTitles() {
        return
                employees.stream()
                        .map(Employee::getJob)
                        .collect(Collectors.toSet());
    }

    public List<EmpMgrDeptDto> getEmpMgrDeptDto() {
        return employees.stream()
                .map(emp -> {
                    String mgrName = emp.getMgr() == null ? "" : empMap.get(emp.getMgr()).getEname();
                    DeptDto deptDto = deptMap.get(emp.getDeptno());
                    String dname = deptDto == null ? "" : deptDto.getDname();
                    String loc = deptDto == null ? "" : deptDto.getLoc();
                    return EmpMgrDeptDto.builder()
                            .empno(emp.getEmpno())
                            .name(emp.getEname())
                            .mgrName(mgrName)
                            .salary(emp.getSal())
                            .dname(dname)
                            .loc(loc)
                            .build();
                })
                .toList();
    }

    public List<String> getEmployeeNames(String dname) {
        Optional<Integer> optDeptno = deptMap.values()
                .stream()
                .filter(deptDto -> deptDto.getDname().equals(dname))
                .map(DeptDto::getDeptno)
                .findFirst();
        if(optDeptno.isEmpty()){
            return List.of();
        }else{
            Integer deptno = optDeptno.get();
            return deptEmpMap.get(deptno)
                    .stream()
                    .map(Employee::getEname)
                    .toList();
        }
    }

    // Get deptno, dname, loc, max salary of all departments.

    // Get max paid employee(s) details

    // Get emp count, max sal, min sal, avg sal, total sal of all employee
}
