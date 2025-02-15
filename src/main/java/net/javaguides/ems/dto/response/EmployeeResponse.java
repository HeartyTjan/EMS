package net.javaguides.ems.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.ems.entity.Employee;
@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponse {
    private String response;
    private Employee employee;
}
