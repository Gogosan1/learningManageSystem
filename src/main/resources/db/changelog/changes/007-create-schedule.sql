CREATE TABLE IF NOT EXISTS lms.schedule (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id UUID NOT NULL REFERENCES lms.groups(id) ON DELETE CASCADE,
    /*TODO: нужен ли учитель вовсе, если у нас у курса один учитель, тогда мы можем не хранить лишнюю связь
    иначе тут появляется нарушение нормализации*/
    teacher_id UUID REFERENCES lms.teacher(id) ON DELETE SET NULL,
    course_id UUID NOT NULL REFERENCES lms.course(id) ON DELETE RESTRICT,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL
);