package jp.co.company.attendance.service;

import jp.co.company.attendance.entity.Employee;
import jp.co.company.attendance.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 社員情報 ビジネスロジック層 (Service)
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 社員検索（条件付き）
     */
    public List<Employee> searchEmployees(String empName, String deptName) {
        return employeeMapper.selectByCondition(empName, deptName);
    }

    /**
     * 社員新規登録
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerEmployee(Employee employee) {
        int rows = employeeMapper.insertEmployee(employee);
        return rows > 0 ? "登録成功（社員ID: " + employee.getEmpId() + "）" : "登録失敗";
    }

    /**
     * 社員情報更新
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateEmployee(Employee employee) {
        int rows = employeeMapper.updateEmployee(employee);
        return rows > 0 ? "更新成功" : "対象データが存在しません";
    }

    /**
     * 社員論理削除
     */
    @Transactional(rollbackFor = Exception.class)
    public String deleteEmployee(Long empId) {
        int rows = employeeMapper.logicalDeleteById(empId);
        return rows > 0 ? "論理削除成功" : "対象データが存在しません";
    }
}
