package jp.co.company.attendance.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 社員マスタ エンティティ（员工表实体类）
 */
@Data // Lombok注解，自动生成Getter/Setter/toString
public class Employee {
    private Long empId;             // 社員ID
    private String empCode;         // 社員番号
    private String empName;         // 氏名
    private String deptName;        // 部署名
    private String email;           // メールアドレス
    private LocalDate hireDate;     // 入社日
    private Integer deleteFlg;      // 削除フラグ (0:未削除, 1:削除済)
    private LocalDateTime createdAt;// 登録日時
    private LocalDateTime updatedAt;// 更新日時
}
