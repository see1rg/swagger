--liquibase formatted sql

--changeset slyubimov:1
CREATE INDEX students_name_index ON student (name);
CREATE INDEX faculties_namecolor_ind ON faculty (name, color);
