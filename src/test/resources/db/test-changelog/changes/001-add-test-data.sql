--liquibase formatted sql

--changeset test:schedule-test-data context:@schedule_test
INSERT INTO lms.teacher (first_name, last_name) VALUES
('Pavel', 'Ivanov'),
('Anna', 'Petrova');

INSERT INTO lms.groups (name) VALUES
('DPO-1'),
('DPO-2');

INSERT INTO lms.course (title, teacher_id) VALUES
('JAVA',        (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' AND last_name = 'Ivanov' LIMIT 1)),
('Базы данных', (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' AND last_name = 'Ivanov' LIMIT 1)),
('Python',      (SELECT id FROM lms.teacher WHERE first_name = 'Anna'  AND last_name = 'Petrova' LIMIT 1));


INSERT INTO lms.schedule (group_id, teacher_id, course_id, start_time, end_time) VALUES
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-1' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Базы данных' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-1' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Базы данных' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 hour',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '2 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-1' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'JAVA' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year'+ INTERVAL '1 day',
    CURRENT_TIMESTAMP + INTERVAL '1 year'+ INTERVAL '1 day' + INTERVAL '1 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-2' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Pavel' LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Базы данных' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 day' + INTERVAL '1 hour',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 day' + INTERVAL '2 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-2' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Pavel'  LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'JAVA' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year' +  INTERVAL '1 day' + INTERVAL '2 hour',
    CURRENT_TIMESTAMP + INTERVAL '1 year' +  INTERVAL '1 day' + INTERVAL '3 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-1' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Anna'  LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Python' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-2' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Anna'  LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Python' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '1 hour',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '2 hour'
),
(
    (SELECT id FROM lms.groups WHERE name = 'DPO-1' LIMIT 1),
    (SELECT id FROM lms.teacher WHERE first_name = 'Anna'  LIMIT 1),
    (SELECT id FROM lms.course WHERE title = 'Python' LIMIT 1),
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '2 hour',
    CURRENT_TIMESTAMP + INTERVAL '1 year' + INTERVAL '3 hour'
);