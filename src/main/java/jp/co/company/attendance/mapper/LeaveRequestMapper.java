package jp.co.company.attendance.mapper;

import jp.co.company.attendance.entity.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface LeaveRequestMapper {

    List<LeaveRequest> selectLeaveRequests(@Param("empId") Long empId,
                                           @Param("approvalStatus") Integer approvalStatus);

    int insertLeaveRequest(LeaveRequest request);

    int updateApprovalStatus(@Param("requestId") Long requestId,
                             @Param("approvalStatus") Integer approvalStatus);
}
