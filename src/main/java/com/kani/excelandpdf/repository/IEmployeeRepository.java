package com.kani.excelandpdf.repository;

import com.kani.excelandpdf.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    void deleteById(Long employeeId);

    void save(Employee employee);

    List<Employee> findAll();

    Employee findById(Long employeeId);

}
