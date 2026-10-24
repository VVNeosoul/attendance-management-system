# 社内勤怠・休暇管理システム（バックエンドAPI）

## 概要 (Overview)
Java 17 および Spring Boot を用いて開発した、社内向けの勤怠打刻・休暇申請管理システムのバックエンドRESTful APIです。

## 使用技術 (Tech Stack)
- **言語:** Java 17
- **フレームワーク:** Spring Boot 4.0.1, MyBatis
- **データベース:** MySQL 8.x
- **ビルドツール:** Maven
- **テスト:** JUnit 5

## 主な機能一覧 (Features)
1. **社員情報管理機能 (`/api/employees`)**
    - 動的SQL（MyBatis XML）を用いた複数条件検索（氏名部分一致・部署名一致）
    - 社員の新規登録・情報更新
    - 削除フラグ（`delete_flg`）を用いた**論理削除（Logical Delete）**
2. **勤怠打刻・照会機能 (`/api/attendances`)**
    - 出勤打刻（定時09:00以降の打刻は自動的に「遅刻」ステータスと判定）および退勤打刻
    - 社員マスタと勤怠テーブルの **INNER JOIN（内部結合）** による月次勤怠データの抽出（DTOマッピング）
    - `java.time.Duration` を用いた実労働時間の自動計算（休憩1時間を控除）
3. **休暇申請・承認機能 (`/api/leaves`)**
    - 休暇申請の登録および開始日・終了日の相関チェック（バリデーション）
    - 承認・却下ステータスの遷移管理および `@Transactional` によるトランザクション制御
4. **単体テスト (`AttendanceApplicationTests`)**
    - JUnit 5 を用いたService層の正常系・異常系テスト（`@Transactional` によるテストデータの自動ロールバック）
