
INSERT IGNORE INTO `User` (id, createdAt, name, email, role, password)
VALUES (100, CURRENT_TIMESTAMP, 'inst', 'inst@f.com', 'INSTRUCTOR', 'pwd');


INSERT IGNORE INTO `Course` (id, createdAt, title, description, instructor_id, status)
VALUES (1, CURRENT_TIMESTAMP, 'Course 1', 'Test', 100, 'BUILDING');
