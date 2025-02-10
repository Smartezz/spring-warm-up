package ch.etmles.payroll.Controllers;

import ch.etmles.payroll.Entities.Department;
import ch.etmles.payroll.Services.DepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department createDepartment(@RequestParam String name) {
        return departmentService.createDepartment(name);
    }

    @GetMapping("/{name}")
    public Department getDepartment(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }
}
