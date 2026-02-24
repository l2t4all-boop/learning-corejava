package com.careerit.cj.empstats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpMgrDeptDto {
    private Integer empno;
    private String name;
    private String mgrName;
    private double salary;
    private String dname;
    private String loc;
}
