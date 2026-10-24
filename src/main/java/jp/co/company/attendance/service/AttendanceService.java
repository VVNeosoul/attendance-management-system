package jp.co.company.attendance.service;

import jp.co.company.attendance.dto.AttendanceRecordDTO;
import jp.co.company.attendance.entity.Attendance;
import jp.co.company.attendance.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 勤怠管理 ビジネスロジック層
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    // 规定早上 09:00:00 为标准上班时间（定時）
    private static final LocalTime STANDARD_START_TIME = LocalTime.of(9, 0, 0);

    /**
     * 1. 勤怠リスト取得 ＆ 実労働時間計算（查询考勤列表并计算工作时长）
     */
    public List<AttendanceRecordDTO> getAttendanceList(Long empId, String yearMonth) {
        List<AttendanceRecordDTO> list = attendanceMapper.selectAttendanceList(empId, yearMonth);

        // 遍历查询结果，如果上下班时间都有，计算工作小时数（保留1位小数）
        for (AttendanceRecordDTO dto : list) {
            if (dto.getClockInTime() != null && dto.getClockOutTime() != null) {
                long minutes = Duration.between(dto.getClockInTime(), dto.getClockOutTime()).toMinutes();
                // 扣除中午1小时(60分钟)休息时间（如果工作超过6小时）
                if (minutes > 360) {
                    minutes -= 60;
                }
                double hours = Math.round((minutes / 60.0) * 10.0) / 10.0;
                dto.setWorkingHours(hours);
            }
        }
        return list;
    }

    /**
     * 2. 出勤打刻処理（上班打卡：自动判断是否超过9点迟到）
     */
    @Transactional(rollbackFor = Exception.class)
    public String clockIn(Long empId) {
        LocalDateTime now = LocalDateTime.now();

        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setWorkDate(now.toLocalDate());
        attendance.setClockInTime(now);

        // 如果打卡时间晚于 09:00，状态设为 2(遅刻)，否则设为 1(通常出勤)
        if (now.toLocalTime().isAfter(STANDARD_START_TIME)) {
            attendance.setWorkStatus(2); // 2: 遅刻
        } else {
            attendance.setWorkStatus(1); // 1: 通常出勤
        }

        attendanceMapper.insertClockIn(attendance);
        return attendance.getWorkStatus() == 2 ? "出勤打刻完了（遅刻）" : "出勤打刻完了（通常出勤）";
    }

    /**
     * 3. 退勤打刻処理（下班打卡）
     */
    @Transactional(rollbackFor = Exception.class)
    public String clockOut(Long empId) {
        LocalDateTime now = LocalDateTime.now();
        int rows = attendanceMapper.updateClockOut(empId, now.toLocalDate(), now);
        return rows > 0 ? "退勤打刻完了" : "本日の出勤記録が見つかりません（未打上班卡）";
    }
}
