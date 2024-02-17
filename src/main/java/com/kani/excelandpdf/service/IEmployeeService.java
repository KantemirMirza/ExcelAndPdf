package com.kani.excelandpdf.service;

import com.kani.excelandpdf.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAllEmployees();
    Page<Employee> findAllEmployees(Pageable pageable);
    void saveEmployee(Employee employee);
    Employee findEmployeeById(Long employeeId);
    void deleteEmployeeById(Long employeeId);
}
