package net.javaguides.ems.service;

import net.javaguides.ems.dto.request.EmployeeRequest;
import net.javaguides.ems.dto.response.EmployeeResponse;
import net.javaguides.ems.dto.response.Response;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.EmployeeAlreadyExistException;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void tearDown() {
        employeeRepository.deleteAll();
    }
    @Test
    public void createEmployee_returnEmployeeTest() {
        EmployeeRequest request = new EmployeeRequest("Tijani", "Olayinka", "salaldj@gmail.com");
        EmployeeResponse response = employeeService.createEmployee(request);

        assertEquals(1,employeeRepository.count());

        assertEquals("Employee Created Successfully",response.getResponse());
    }

    @Test
    public void createEmployee_throwsExceptionWhenEmployeeAlreadyExists() {
        EmployeeRequest request = new EmployeeRequest("Tijani", "Olayinka", "salaldj@gmail.com");
        employeeService.createEmployee(request);
        assertThrows(EmployeeAlreadyExistException.class,()-> employeeService.createEmployee(request));
    }

    @Test
    public void createEmployee_saveEmployeeAndGetEmployeeById_returnEmployeeTest() {
        EmployeeRequest request = new EmployeeRequest("Tijani", "Olayinka", "salaldj@gmail.com");
        EmployeeResponse response = employeeService.createEmployee(request);

        assertEquals(1,employeeRepository.count());
        assertEquals("Employee Created Successfully",response.getResponse());

        EmployeeRequest savedEmployee = employeeService.getEmployeeById(response.getEmployee().getId());

        assertEquals(savedEmployee.getFirstName(),request.getFirstName());
        assertEquals(savedEmployee.getLastName(),request.getLastName());
        assertEquals(savedEmployee.getEmail(),request.getEmail());
    }

    @Test
    public void createThreeEmployees_saveEmployeesAndGetAllEmployees_returnAllEmployeeTest(){
        EmployeeRequest firstRequest = new EmployeeRequest("Tobiloba", "Benson", "benson@gmail.com");
        EmployeeRequest secondRequest = new EmployeeRequest("Agbajelola", "Salami", "salami@gmail.com");
        EmployeeRequest thirdRequest = new EmployeeRequest("Tijani", "Olayinka", "olayink@gmail.com");

        employeeService.createEmployee(firstRequest);
        employeeService.createEmployee(secondRequest);
        employeeService.createEmployee(thirdRequest);

        assertEquals(3,employeeRepository.count());

        List<EmployeeRequest> employees = employeeService.getAllEmployees();

        assertEquals(employees.get(0).getEmail(),firstRequest.getEmail());
        assertEquals(employees.get(1).getEmail(),secondRequest.getEmail());
        assertEquals(employees.get(2).getEmail(),thirdRequest.getEmail());

    }

    @Test
    public void createThreeEmployees_updateFirstEmployeeEmail_firstEmployeeInfoUpdatedTest() {
        EmployeeRequest firstRequest = new EmployeeRequest("Tobiloba", "Benson", "benson@gmail.com");
        EmployeeRequest secondRequest = new EmployeeRequest("Agbajelola", "Salami", "salami@gmail.com");
        EmployeeRequest thirdRequest = new EmployeeRequest("Tijani", "Olayinka", "olayink@gmail.com");

        EmployeeResponse employeeResponse = employeeService.createEmployee(firstRequest);
        employeeService.createEmployee(secondRequest);
        employeeService.createEmployee(thirdRequest);

        assertEquals(3,employeeRepository.count());

        Employee createdEmployee = employeeResponse.getEmployee();
        EmployeeRequest savedEmployee = employeeService.getEmployeeById(createdEmployee.getId());
        savedEmployee.setEmail("bensontobi@gmail.com");

        Response response =  employeeService.updateEmployee(createdEmployee.getId(),savedEmployee);
        assertEquals("Employee information Updated successfully", response.getResponse());

        EmployeeRequest foundEmployee = employeeService.getEmployeeById(createdEmployee.getId());
        assertEquals("bensontobi@gmail.com", foundEmployee.getEmail());

        assertEquals(3,employeeRepository.count());

    }

    @Test
    public void createThreeEmployees_deleteFirstEmployee_employeeCountIsTwo(){
        EmployeeRequest firstRequest = new EmployeeRequest("Tobiloba", "Benson", "benson@gmail.com");
        EmployeeRequest secondRequest = new EmployeeRequest("Agbajelola", "Salami", "salami@gmail.com");
        EmployeeRequest thirdRequest = new EmployeeRequest("Tijani", "Olayinka", "olayink@gmail.com");

        EmployeeResponse employeeResponse = employeeService.createEmployee(firstRequest);
        employeeService.createEmployee(secondRequest);
        employeeService.createEmployee(thirdRequest);

        assertEquals(3,employeeRepository.count());

        Employee createdEmployee = employeeResponse.getEmployee();
        employeeService.deleteEmployee(createdEmployee.getId());

        assertEquals(2,employeeRepository.count());
    }

    @Test
    public void createThreeEmployees_deleteAllEmployees_employeeCountIsZero(){
        EmployeeRequest firstRequest = new EmployeeRequest("Tobiloba", "Benson", "benson@gmail.com");
        EmployeeRequest secondRequest = new EmployeeRequest("Agbajelola", "Salami", "salami@gmail.com");
        EmployeeRequest thirdRequest = new EmployeeRequest("Tijani", "Olayinka", "olayink@gmail.com");

        EmployeeResponse employeeResponse = employeeService.createEmployee(firstRequest);
        employeeService.createEmployee(secondRequest);
        employeeService.createEmployee(thirdRequest);

        assertEquals(3,employeeRepository.count());

        employeeService.deleteAllEmployees();

        assertEquals(0,employeeRepository.count());
    }

}