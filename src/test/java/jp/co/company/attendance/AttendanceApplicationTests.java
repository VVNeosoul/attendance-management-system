package jp.co.company.attendance;

import jp.co.company.attendance.entity.LeaveRequest;
import jp.co.company.attendance.service.LeaveRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional // 重点！测试类加上 @Transactional 后，测试插入的数据会自动回滚，不会污染数据库！
class AttendanceApplicationTests {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Test
    @DisplayName("正常系テスト：休暇申請が正常に登録できること")
    void testSubmitLeaveRequest_Success() {
        // 1. 準備テストデータ (准备测试数据)
        LeaveRequest request = new LeaveRequest();
        request.setEmpId(1L);
        request.setLeaveType(1); // 1: 有給休暇（年假）
        request.setStartDate(LocalDate.of(2026, 11, 1));
        request.setEndDate(LocalDate.of(2026, 11, 3));
        request.setReason("私用のため（因私事请假）");

        // 2. 実行 (执行被测试的方法)
        String result = leaveRequestService.submitLeaveRequest(request);

        // 3. 検証 (Assert断言：验证返回结果包含"休暇申請完了"，且成功生成了主键ID)
        Assertions.assertTrue(result.contains("休暇申請完了"));
        Assertions.assertNotNull(request.getRequestId());
        System.out.println("★正常系テスト成功！返却メッセージ: " + result);
    }

    @Test
    @DisplayName("異常系テスト：終了日が開始日より前の場合、例外がスローされること")
    void testSubmitLeaveRequest_DateError() {
        // 1. 準備異常データ（结束日期 11月1日 早于 开始日期 11月5日）
        LeaveRequest request = new LeaveRequest();
        request.setEmpId(1L);
        request.setLeaveType(1);
        request.setStartDate(LocalDate.of(2026, 11, 5));
        request.setEndDate(LocalDate.of(2026, 11, 1));
        request.setReason("日付逆転テスト");

        // 2 & 3. 実行＆検証 (断言一定会抛出 IllegalArgumentException 异常)
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> leaveRequestService.submitLeaveRequest(request)
        );

        System.out.println("★異常系テスト成功！キャッチしたエラー: " + exception.getMessage());
    }
}
