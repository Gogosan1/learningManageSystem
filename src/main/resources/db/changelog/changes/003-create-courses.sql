CREATE TABLE IF NOT EXISTS lms.course (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(100) NOT NULL,
    description TEXT,
    teacher_id UUID REFERENCES lms.teacher(id) ON DELETE SET NULL
);