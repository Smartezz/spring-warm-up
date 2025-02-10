package ch.etmles.payroll.Controllers;

import ch.etmles.payroll.Entities.Employee;
import ch.etmles.payroll.Entities.Department;
import ch.etmles.payroll.Repositories.EmployeeRepository;
import ch.etmles.payroll.Repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    // Constructeur pour injecter les repositories
    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    /* curl sample :
    curl -i localhost:8080/employees
    */
    @GetMapping
    public List<Employee> all() {
        return employeeRepository.findAll();  // Récupère tous les employés
    }

    /* curl sample :
    curl -i -X POST localhost:8080/employees ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"Russel George\", \"role\": \"gardener\", \"departmentName\": \"HR\"}"
    */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee, @RequestParam String departmentName) {
        // Récupérer le département en fonction du nom
        Department department = departmentRepository.findByName(departmentName);
        if (department == null) {
            throw new RuntimeException("Department not found");
        }
        employee.setDepartment(department);  // Associer l'employé au département
        return employeeRepository.save(employee);  // Sauvegarder l'employé
    }

    /* curl sample :
    curl -i localhost:8080/employees/1
    */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    /* curl sample :
    curl -i -X PUT localhost:8080/employees/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"Samwise Bing\", \"role\": \"peer-to-peer\", \"departmentName\": \"IT\"}"
    */
    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id, @RequestParam String departmentName) {
        // Trouver le département
        Department department = departmentRepository.findByName(departmentName);
        if (department == null) {
            throw new RuntimeException("Department not found");
        }

        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setName(employee.getName());
                    existingEmployee.setRole(employee.getRole());
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setDepartment(department);  // Mettre à jour le département
                    return employeeRepository.save(existingEmployee);  // Sauvegarder l'employé mis à jour
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    /* curl sample :
    curl -i -X DELETE localhost:8080/employees/2
    */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id);  // Supprimer l'employé
    }

    /* curl sample :
    curl -i localhost:8080/employees/email/john.doe@example.com
    */
    @GetMapping("/email/{email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        return employeeRepository.findByEmail(email);
    }
}
