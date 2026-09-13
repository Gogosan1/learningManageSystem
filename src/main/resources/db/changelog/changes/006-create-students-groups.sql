CREATE TABLE IF NOT EXISTS student_groups (
    student_id UUID NOT NULL REFERENCES lms.student(id) ON DELETE CASCADE,
    group_id UUID NOT NULL REFERENCES lms.groups(id) ON DELETE CASCADE,
    PRIMARY KEY (student_id, group_id)
);