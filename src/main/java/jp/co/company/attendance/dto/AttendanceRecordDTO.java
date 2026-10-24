package jp.co.company.attendance.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 勤怠照会画面返却用 DTO（包含员工表+考勤表关联字段，以及计算出的工时）
 */
@Data
public class AttendanceRecordDTO {
    private Long attendanceId;          // 勤怠ID
    private Long empId;                 // 社員ID
    private String empCode;             // 社員番号 (来自 m_employee 表)
    private String empName;             // 氏名 (来自 m_employee 表)
    private String deptName;            // 部署名 (来自 m_employee 表)
    private LocalDate workDate;         // 勤務日
    private LocalDateTime clockInTime;  // 出勤時間
    private LocalDateTime clockOutTime; // 退勤時間
    private Integer workStatus;         // 勤務状態 (1:通常, 2:遅刻)
    private Double workingHours;        // 実労働時間（Java在Service层算出来的工作小时数）
}
