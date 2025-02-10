package ch.etmles.payroll.Repositories;

import ch.etmles.payroll.Entities.Employee;
import ch.etmles.payroll.Entities.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        return args -> {
            // Création ou récupération du département
            Department department = departmentRepository.findByName("IT");  // Exemple de département
            if (department == null) {
                department = new Department("IT");
                departmentRepository.save(department);
            }

            // Création d'employés avec des emails uniques et un département
            log.info("Preloading " + employeeRepository.save(new Employee("Bilbo Baggins", "bilbo@bagginse.com", "burglar", department)));
            log.info("Preloading " + employeeRepository.save(new Employee("Frodo Baggins", "frodo@bagginse.com", "thief", department)));
        };
    }
}
