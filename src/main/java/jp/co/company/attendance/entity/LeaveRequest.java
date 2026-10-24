package jp.co.company.attendance.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 休暇申請テーブル エンティティ
 */
@Data
public class LeaveRequest {
    private Long requestId;             // 申請ID
    private Long empId;                 // 社員ID
    private Integer leaveType;          // 休暇種別 (1:有給休暇, 2:病欠, 3:特別休暇)
    private LocalDate startDate;        // 開始日
    private LocalDate endDate;          // 終了日
    private String reason;              // 申請理由
    private Integer approvalStatus;     // 承認ステータス (0:申請中, 1:承認済, 2:却下)
    private LocalDateTime createdAt;    // 登録日時
    private LocalDateTime updatedAt;    // 更新日時
}
