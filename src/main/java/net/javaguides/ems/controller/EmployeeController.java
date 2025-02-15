package net.javaguides.ems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.javaguides.ems.dto.request.EmployeeRequest;
import net.javaguides.ems.dto.response.EmployeeResponse;
import net.javaguides.ems.dto.response.Response;
import net.javaguides.ems.exception.EmployeeAlreadyExistException;
import net.javaguides.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Controller", description = "Endpoints for managing employees")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create a new employee", description = "Adds a new employee to the database")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeResponse response = employeeService.createEmployee(employeeRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmployeeAlreadyExistException e) {
            EmployeeResponse response = new EmployeeResponse();
            response.setResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get employee by ID", description = "Fetch an employee by their ID")
    public ResponseEntity<EmployeeRequest> getEmployeeById(@PathVariable("id") Long employeeId) {
        EmployeeRequest employeeRequest = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeRequest);
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Fetches a list of all employees")
    public ResponseEntity<List<EmployeeRequest>> getAllEmployees() {
        List<EmployeeRequest> employeeRequests = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeRequests);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update an employee", description = "Modifies an existing employee's details")
    public ResponseEntity<Response> updateEmployee(@PathVariable("id") Long employeeId,
                                                           @RequestBody EmployeeRequest employeeRequest) {
        Response response = employeeService.updateEmployee(employeeId, employeeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an employee", description = "Removes an employee from the database")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @DeleteMapping
    @Operation(summary = "Delete all employee", description = "Removes an employee from the database")
    public ResponseEntity<String> deleteAllEmployees() {
        Long count = employeeService.deleteAllEmployees();
        return ResponseEntity.ok("All employees deleted successfully\n" + "Number of employees deleted  is " + count );
    }

}
