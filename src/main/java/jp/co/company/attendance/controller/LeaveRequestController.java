package jp.co.company.attendance.controller;

import jp.co.company.attendance.entity.LeaveRequest;
import jp.co.company.attendance.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    // 1. 查询请假列表: GET http://localhost:8080/api/leaves
    @GetMapping
    public List<LeaveRequest> getLeaveRequests(
            @RequestParam(required = false) Long empId,
            @RequestParam(required = false) Integer approvalStatus) {
        return leaveRequestService.getLeaveRequests(empId, approvalStatus);
    }

    // 2. 提交请假申请: POST http://localhost:8080/api/leaves
    @PostMapping
    public String submitLeaveRequest(@RequestBody LeaveRequest request) {
        return leaveRequestService.submitLeaveRequest(request);
    }

    // 3. 审批请假申请: PUT http://localhost:8080/api/leaves/{requestId}/status?approvalStatus=1
    @PutMapping("/{requestId}/status")
    public String approveOrReject(@PathVariable Long requestId,
                                  @RequestParam Integer approvalStatus) {
        return leaveRequestService.approveOrReject(requestId, approvalStatus);
    }
}
