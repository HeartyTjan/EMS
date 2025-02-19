package net.javaguides.ems.service;

import net.javaguides.ems.dto.request.EmployeeRequest;
import net.javaguides.ems.dto.request.EmployeeUpdateRequest;
import net.javaguides.ems.dto.response.EmployeeResponse;
import net.javaguides.ems.dto.response.Response;
import net.javaguides.ems.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse getEmployeeById(Long employeeId);
    List<EmployeeRequest> getAllEmployees();
    Response updateEmployee(Long employeeId, EmployeeUpdateRequest updatedRequest);
    void deleteEmployee(Long employeeId);
    Long deleteAllEmployees();
}
