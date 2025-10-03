-- Script để xử lý unique constraint conflict khi sử dụng hibernate.ddl-auto=update

USE SpringBootLoginRole;

-- Kiểm tra và xóa constraint nếu tồn tại
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'UKr43af9ap4edm43mmtq01oddj6') AND type = 'UQ')
BEGIN
    ALTER TABLE users DROP CONSTRAINT UKr43af9ap4edm43mmtq01oddj6;
    PRINT 'Đã xóa constraint UKr43af9ap4edm43mmtq01oddj6';
END
ELSE
BEGIN
    PRINT 'Constraint UKr43af9ap4edm43mmtq01oddj6 không tồn tại';
END

-- Kiểm tra cấu trúc bảng hiện tại
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'users' AND TABLE_SCHEMA = 'dbo'
ORDER BY ORDINAL_POSITION;

PRINT 'Script hoàn thành. Bây giờ bạn có thể chạy ứng dụng Spring Boot với ddl-auto=update';