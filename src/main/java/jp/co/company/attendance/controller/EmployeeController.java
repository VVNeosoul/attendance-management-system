package jp.co.company.attendance.controller;

import jp.co.company.attendance.entity.Employee;
import jp.co.company.attendance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 社員情報管理 コントローラー
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 1. 社員検索API (GET)
     * テストURL: http://localhost:8080/api/employees?empName=田中
     */
    @GetMapping
    public List<Employee> searchEmployees(
            @RequestParam(required = false) String empName,
            @RequestParam(required = false) String deptName) {
        return employeeService.searchEmployees(empName, deptName);
    }

    /**
     * 2. 社員新規登録API (POST)
     */
    @PostMapping
    public String registerEmployee(@RequestBody Employee employee) {
        return employeeService.registerEmployee(employee);
    }

    /**
     * 3. 社員情報更新API (PUT)
     */
    @PutMapping("/{empId}")
    public String updateEmployee(@PathVariable Long empId, @RequestBody Employee employee) {
        employee.setEmpId(empId);
        return employeeService.updateEmployee(employee);
    }

    /**
     * 4. 社員論理削除API (DELETE)
     */
    @DeleteMapping("/{empId}")
    public String deleteEmployee(@PathVariable Long empId) {
        return employeeService.deleteEmployee(empId);
    }
}
