CREATE table public.resume
(
    uuid     varchar PRIMARY KEY NOT NULL,
    full_name TEXT                NOT NULL
);

CREATE TABLE public.contact
(
    id          serial  NOT NULL UNIQUE,
    resume_uuid varchar NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT    not null,
    value       TEXT    not null
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);


CREATE TABLE public.text_section
(
    id          serial NOT NULL,
    resume_uuid varchar primary key REFERENCES resume (uuid) ON DELETE CASCADE,
    personal    TEXT,
    objective   TEXT
);



CREATE TABLE public.list_section
(
    id          serial  NOT NULL,
    resume_uuid varchar NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    achievement text,
    qualification text
);
