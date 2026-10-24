package jp.co.company.attendance.mapper;

import jp.co.company.attendance.dto.AttendanceRecordDTO;
import jp.co.company.attendance.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    // 1. 勤怠一覧検索（JOIN查询）
    List<AttendanceRecordDTO> selectAttendanceList(@Param("empId") Long empId,
                                                   @Param("yearMonth") String yearMonth);

    // 2. 出勤打刻
    int insertClockIn(Attendance attendance);

    // 3. 退勤打刻
    int updateClockOut(@Param("empId") Long empId,
                       @Param("workDate") LocalDate workDate,
                       @Param("clockOutTime") LocalDateTime clockOutTime);
}
