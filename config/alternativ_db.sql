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







CREATE TABLE public.linc
(
    id           serial NOT NULL,
    company_name varchar primary key,
    website      varchar UNIQUE
);


CREATE TABLE public.company_section_experience
(
    id                serial  NOT NULL,
    resume_uuid       varchar NOT NULL,
    data_start        DATE,
    data_end          DATE,
    linc_company_name varchar REFERENCES linc (company_name) ON DELETE CASCADE,
    title             TEXT,
    description       TEXT
);
create unique index company_section_experience_uuid_start_name_title
    on company_section_experience (resume_uuid, data_start, linc_company_name);


CREATE TABLE public.company_section_education
(
    id                serial  NOT NULL,
    resume_uuid       varchar NOT NULL,
    data_start        DATE,
    data_end          DATE,
    linc_company_name varchar REFERENCES linc (company_name) ON DELETE CASCADE,
    title             TEXT
);

create unique index company_section_education_uuid_start_name_index
    on company_section_education (resume_uuid, data_start, linc_company_name);





