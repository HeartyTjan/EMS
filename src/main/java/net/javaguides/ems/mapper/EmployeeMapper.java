package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.request.EmployeeRequest;
import net.javaguides.ems.dto.request.EmployeeUpdateRequest;
import net.javaguides.ems.dto.response.EmployeeResponse;
import net.javaguides.ems.dto.response.Response;
import net.javaguides.ems.entity.Employee;

public class EmployeeMapper  {

    public static EmployeeRequest mapToEmployeeRequest(Employee employee) {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName(employee.getFirstName());
        request.setLastName(employee.getLastName());
        request.setEmail(employee.getEmail());
        return request;
    }

    public static Employee mapToEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        return employee;
    }

    public static EmployeeResponse mapEmployeeToResponse(String message, Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setResponse(message);
        response.setEmployee(employee);
        return response;
    }
    public static EmployeeUpdateRequest mapToUpdateRequest(Employee employee) {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setFirstName(employee.getFirstName());
        request.setLastName(employee.getLastName());
        request.setEmail(employee.getEmail());
        return request;
    }
    public static Response mapToResponse(String message) {
        Response response = new Response();
        response.setResponse(message);
        return response;
    }



}
