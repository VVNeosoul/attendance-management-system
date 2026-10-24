package jp.co.company.attendance.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 勤怠テーブル エンティティ
 */
@Data
public class Attendance {
    private Long attendanceId;          // 勤怠ID
    private Long empId;                 // 社員ID
    private LocalDate workDate;         // 勤務日
    private LocalDateTime clockInTime;  // 出勤打刻時間
    private LocalDateTime clockOutTime; // 退勤打刻時間
    private Integer workStatus;         // 勤務状態 (1:通常出勤, 2:遅刻, 3:早退, 4:欠勤)
    private LocalDateTime createdAt;    // 登録日時
    private LocalDateTime updatedAt;    // 更新日時
}
