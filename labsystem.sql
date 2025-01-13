SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `classplan`;
CREATE TABLE `classplan`  (
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lab_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start` int(0) NULL DEFAULT NULL,
  `end` int(0) NULL DEFAULT NULL,
  `start_time` date NULL DEFAULT NULL,
  `last` int(0) NULL DEFAULT NULL,
  `xq` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `lab_id`(`lab_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lab_id` FOREIGN KEY (`lab_id`) REFERENCES `lab` (`lab_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `class_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `people_num` int(0) NULL DEFAULT NULL,
  `status` tinyint(0) NOT NULL,
  `belong_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tool` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `subject` enum('硬件类','软件类') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tea_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `course_semester_sem_id_fk`(`belong_num`) USING BTREE,
  INDEX `course_teacher_tea_id_fk`(`tea_id`) USING BTREE,
  CONSTRAINT `course_semester_sem_id_fk` FOREIGN KEY (`belong_num`) REFERENCES `semester` (`sem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_teacher_tea_id_fk` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `lab`;
CREATE TABLE `lab`  (
  `lab_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lab_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lab_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lab_isfull` tinyint(0) NOT NULL,
  PRIMARY KEY (`lab_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `sem_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sem_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `begindate` date NOT NULL,
  `week` int(0) NOT NULL,
  `isnow` tinyint(0) NOT NULL,
  `dayclass` int(0) NOT NULL,
  PRIMARY KEY (`sem_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tea_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `isroot` tinyint(1) NOT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `teainfo`;
CREATE TABLE `teainfo`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Unknown',
  `tea_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` tinyint(0) NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`tea_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP FUNCTION IF EXISTS `IsMonday`;
delimiter ;;
CREATE FUNCTION `IsMonday`(dateValue DATE)
 RETURNS tinyint(1)
BEGIN
    RETURN WEEKDAY(dateValue) = 0;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
