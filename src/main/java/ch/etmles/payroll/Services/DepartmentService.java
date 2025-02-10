package ch.etmles.payroll.Services;

import ch.etmles.payroll.Entities.Department;
import ch.etmles.payroll.Repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return departmentRepository.save(department);
    }

    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }
}
