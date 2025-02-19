package net.javaguides.ems.service;


import net.javaguides.ems.dto.request.EmployeeRequest;
import net.javaguides.ems.dto.request.EmployeeUpdateRequest;
import net.javaguides.ems.dto.response.EmployeeResponse;
import net.javaguides.ems.dto.response.Response;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.EmployeeAlreadyExistException;
import net.javaguides.ems.exception.ResourceNotFoundException;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        if (employeeExists(employeeRequest.getEmail())){
            throw new EmployeeAlreadyExistException("Email already exists");
        }
        Employee newEmployee = EmployeeMapper.mapToEmployee(employeeRequest);
        employeeRepository.save(newEmployee);
        return EmployeeMapper.mapEmployeeToResponse("Employee Created Successfully",newEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));
           return EmployeeMapper.mapEmployeeToResponse("successful", employee);
    }

    @Override
    public List<EmployeeRequest> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeRequest)
                .collect(Collectors.toList());
    }

    @Override
    public Response updateEmployee(Long employeeId, EmployeeUpdateRequest updateEmployeeRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));

        employee.setFirstName(updateEmployeeRequest.getFirstName());
        employee.setLastName(updateEmployeeRequest.getLastName());
        employee.setEmail(updateEmployeeRequest.getEmail());
        employeeRepository.save(employee);
//        employeeRepository.delete(employee);
//        Employee updatedEmployee = EmployeeMapper.mapToEmployee(updatedEmployeeRequest);
//        employeeRepository.save(updatedEmployee);
        return EmployeeMapper.mapToResponse("Employee information Updated successfully");

    }

    private boolean employeeExists(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public Long deleteAllEmployees() {
        long count = employeeRepository.count();
        employeeRepository.deleteAll();
        return count;
    }
}
