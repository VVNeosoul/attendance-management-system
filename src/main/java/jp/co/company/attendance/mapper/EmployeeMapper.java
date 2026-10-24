package jp.co.company.attendance.mapper;

import jp.co.company.attendance.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 社員情報 データアクセス層 (DAO / Mapper)
 */
@Mapper
public interface EmployeeMapper {

    // 1. 条件検索（多个参数时，使用 @Param 注解对应 XML 里的名字）
    List<Employee> selectByCondition(@Param("empName") String empName,
                                     @Param("deptName") String deptName);

    // 2. 新規登録（返回受影响的行数）
    int insertEmployee(Employee employee);

    // 3. 情報更新
    int updateEmployee(Employee employee);

    // 4. 論理削除
    int logicalDeleteById(@Param("empId") Long empId);
}
