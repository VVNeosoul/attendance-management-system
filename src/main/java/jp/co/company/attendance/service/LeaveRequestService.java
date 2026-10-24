package jp.co.company.attendance.service;

import jp.co.company.attendance.entity.LeaveRequest;
import jp.co.company.attendance.mapper.LeaveRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    /**
     * 1. 休暇申請一覧検索
     */
    public List<LeaveRequest> getLeaveRequests(Long empId, Integer approvalStatus) {
        return leaveRequestMapper.selectLeaveRequests(empId, approvalStatus);
    }

    /**
     * 2. 休暇申請登録（包含日期合理性校验）
     */
    @Transactional(rollbackFor = Exception.class)
    public String submitLeaveRequest(LeaveRequest request) {
        // 業務チェック：終了日が開始日より前の場合はエラーとする（如果结束日期早于开始日期，报错）
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("エラー：終了日は開始日以降の日付を指定してください。");
        }
        leaveRequestMapper.insertLeaveRequest(request);
        return "休暇申請完了（申請ID: " + request.getRequestId() + "）";
    }

    /**
     * 3. 休暇承認・却下処理 (approvalStatus: 1=承認, 2=却下)
     */
    @Transactional(rollbackFor = Exception.class)
    public String approveOrReject(Long requestId, Integer approvalStatus) {
        if (approvalStatus != 1 && approvalStatus != 2) {
            throw new IllegalArgumentException("エラー：不正な承認ステータスです。");
        }
        int rows = leaveRequestMapper.updateApprovalStatus(requestId, approvalStatus);
        if (rows == 0) {
            return "処理失敗：対象の申請が存在しないか、既に処理済みです。";
        }
        return approvalStatus == 1 ? "承認完了（ステータス：承認済）" : "却下完了（ステータス：却下）";
    }
}
