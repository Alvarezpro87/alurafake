CREATE TABLE options (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         option_text VARCHAR(80) NOT NULL,
                         is_correct BOOLEAN NOT NULL,
                         task_id BIGINT NOT NULL,
                         CONSTRAINT fk_option_task FOREIGN KEY (task_id)
                             REFERENCES tasks(id)
                             ON DELETE CASCADE
);
