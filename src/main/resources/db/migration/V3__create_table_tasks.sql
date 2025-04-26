CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       statement VARCHAR(255) NOT NULL,
                       `order` INT NOT NULL,
                       course_id BIGINT NOT NULL,
                       type VARCHAR(20) NOT NULL,
                       dtype VARCHAR(31) NOT NULL,
                       CONSTRAINT fk_task_course FOREIGN KEY (course_id) REFERENCES Course(id),
                       CONSTRAINT uk_task_statement UNIQUE (course_id, statement)
);
