package jp.co.company.attendance.controller;

import jp.co.company.attendance.dto.AttendanceRecordDTO;
import jp.co.company.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 勤怠打刻・照会 コントローラー
 */
@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 1. 勤怠一覧照会API (GET)
     * テストURL: http://localhost:8080/api/attendances?empId=1&yearMonth=2026-10
     */
    @GetMapping
    public List<AttendanceRecordDTO> getAttendanceList(
            @RequestParam(required = false) Long empId,
            @RequestParam(required = false) String yearMonth) {
        return attendanceService.getAttendanceList(empId, yearMonth);
    }

    /**
     * 2. 出勤打刻API (POST)
     */
    @PostMapping("/clock-in/{empId}")
    public String clockIn(@PathVariable Long empId) {
        return attendanceService.clockIn(empId);
    }

    /**
     * 3. 退勤打刻API (PUT)
     */
    @PutMapping("/clock-out/{empId}")
    public String clockOut(@PathVariable Long empId) {
        return attendanceService.clockOut(empId);
    }
}
