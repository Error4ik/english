CREATE DATABASE english;

CREATE EXTENSION pgcrypto;

CREATE TABLE roles (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  role VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE users (
  id       UUID PRIMARY KEY,
  email    VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255)        NOT NULL
);

CREATE TABLE user_roles (
  id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  role_id UUID NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);

CREATE TABLE categories (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL
);

CREATE TABLE part_of_speech (
  id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  part_of_speech VARCHAR(50) NOT NULL
);

CREATE TABLE words (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  word              VARCHAR(50)  NOT NULL,
  transcription     VARCHAR(50)  NOT NULL,
  category_id       UUID,
  part_of_speech_id UUID         NOT NULL,
  image_id          UUID,
  description       VARCHAR(255) NOT NULL,

  FOREIGN KEY (category_id) REFERENCES categories (id),
  FOREIGN KEY (part_of_speech_id) REFERENCES part_of_speech (id),
  FOREIGN KEY (image_id) REFERENCES images (id)
);

CREATE TABLE phrases (
  id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  phrase    VARCHAR(255) NOT NULL,
  translate VARCHAR(255) NOT NULL,
  word_id   UUID         NOT NULL,

  FOREIGN KEY (word_id) REFERENCES words (id)
);

CREATE TABLE translations (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  translation VARCHAR(50) NOT NULL,
  word_id     UUID        NOT NULL,

  FOREIGN KEY (word_id) REFERENCES words (id)
);

CREATE TABLE images (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  url  VARCHAR(255) NOT NULL
);

-- new

ALTER TABLE public.categories
  ADD description VARCHAR(255) DEFAULT 'some text' NOT NULL;
ALTER TABLE public.categories
  ADD image_id UUID NULL;
ALTER TABLE public.categories
  ADD CONSTRAINT categories_image_id_fkey FOREIGN KEY (image_id) REFERENCES images (id);

ALTER TABLE public.words
  ADD date_added TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.users
  ADD create_date TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.users
  ADD last_visit TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.categories
  ADD words_count INT DEFAULT 0 NOT NULL;

CREATE TABLE exams (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL
);

CREATE TABLE questions (
  id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  word_id UUID NOT NULL,
  exam_id UUID NOT NULL,

  FOREIGN KEY (word_id) REFERENCES words (id),
  FOREIGN KEY (exam_id) REFERENCES exams (id)
);

CREATE TABLE question_words (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  question_id UUID NOT NULL,
  word_id     UUID NOT NULL,

  FOREIGN KEY (question_id) REFERENCES questions (id),
  FOREIGN KEY (word_id) REFERENCES words (id)
);

ALTER TABLE public.exams
  ADD category_id UUID NULL;
ALTER TABLE public.exams
  ADD CONSTRAINT category_id
FOREIGN KEY (category_id) REFERENCES categories (id);

CREATE TABLE user_exams_stats (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id         UUID    NOT NULL,
  exam_id         UUID    NOT NULL,
  total_questions INTEGER NOT NULL,
  correct_answer  INTEGER NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (exam_id) REFERENCES exams (id)
);

ALTER TABLE public.user_exams_stats
  ADD date_of_the_exam TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.exams ADD type INT DEFAULT 0;

ALTER TABLE public.part_of_speech ADD number_of_words INT DEFAULT 0;
