-- 1. 创建数据库（文字コード使用 utf8mb4）
CREATE DATABASE IF NOT EXISTS attendance_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE attendance_db;

-- 2. 员工主表：社員マスタ (m_employee)
CREATE TABLE m_employee (
                            emp_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '社員ID（主键）',
                            emp_code VARCHAR(20) NOT NULL UNIQUE COMMENT '社員番号（工号，如 EMP001）',
                            emp_name VARCHAR(50) NOT NULL COMMENT '氏名（姓名）',
                            dept_name VARCHAR(50) NOT NULL COMMENT '部署名（部门名称，如 開発部、営業部）',
                            email VARCHAR(100) COMMENT 'メールアドレス（邮箱）',
                            hire_date DATE NOT NULL COMMENT '入社日（入职日期）',
                            delete_flg TINYINT(1) NOT NULL DEFAULT 0 COMMENT '削除フラグ（0:未削除, 1:削除済 - 逻辑删除用）',
                            created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時（创建时间）',
                            updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時（更新时间）'
) COMMENT='社員マスタ';

-- 3. 考勤打卡流水表：勤怠テーブル (t_attendance)
CREATE TABLE t_attendance (
                              attendance_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '勤怠ID',
                              emp_id BIGINT NOT NULL COMMENT '社員ID（关联 m_employee.emp_id）',
                              work_date DATE NOT NULL COMMENT '勤務日（出勤日期）',
                              clock_in_time DATETIME COMMENT '出勤打刻時間（上班打卡时间）',
                              clock_out_time DATETIME COMMENT '退勤打刻時間（下班打卡时间）',
                              work_status TINYINT NOT NULL DEFAULT 1 COMMENT '勤務状態（1:通常出勤, 2:遅刻, 3:早退, 4:欠勤）',
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
                              updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
                              UNIQUE KEY uk_emp_date (emp_id, work_date)
) COMMENT='勤怠テーブル';

-- 4. 请假申请流水表：休暇申請テーブル (t_leave_request)
CREATE TABLE t_leave_request (
                                 request_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '申請ID',
                                 emp_id BIGINT NOT NULL COMMENT '社員ID（关联 m_employee.emp_id）',
                                 leave_type TINYINT NOT NULL COMMENT '休暇種別（1:有給休暇-年假, 2:病欠-病假, 3:特別休暇）',
                                 start_date DATE NOT NULL COMMENT '開始日',
                                 end_date DATE NOT NULL COMMENT '終了日',
                                 reason VARCHAR(255) COMMENT '申請理由',
                                 approval_status TINYINT NOT NULL DEFAULT 0 COMMENT '承認ステータス（0:申請中, 1:承認済-通过, 2:却下-驳回）',
                                 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
                                 updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) COMMENT='休暇申請テーブル';

-- 5. 插入两条测试数据（テストデータ）
INSERT INTO m_employee (emp_code, emp_name, dept_name, email, hire_date) VALUES
                                                                             ('EMP001', '田中 太郎', '第一開発部', 'tanaka@example.co.jp', '2025-04-01'),
                                                                             ('EMP002', '佐藤 花子', '営業部', 'sato@example.co.jp', '2025-10-01');

INSERT INTO t_attendance (emp_id, work_date, clock_in_time, clock_out_time, work_status) VALUES
                                                                                             (1, '2026-10-20', '2026-10-20 09:00:00', '2026-10-20 18:00:00', 1),
                                                                                             (1, '2026-10-21', '2026-10-21 08:55:00', NULL, 1);