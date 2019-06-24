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

ALTER TABLE public.exams
  ADD type INT DEFAULT 0;

ALTER TABLE public.part_of_speech
  ADD number_of_words INT DEFAULT 0;

CREATE TABLE type_of_phrase (
  id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  type_of_phrase VARCHAR(50) NOT NULL
);

CREATE TABLE time_of_phrase (
  id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  time_of_phrase VARCHAR(50) NOT NULL
);

CREATE TABLE phrase_category (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name              VARCHAR(255) NOT NULL,
  time_of_phrase_id UUID         NOT NULL,
  type_of_phrase_id UUID         NOT NULL,
  description       VARCHAR(500) NOT NULL,
  number_of_phrases INTEGER          DEFAULT 0,

  FOREIGN KEY (time_of_phrase_id) REFERENCES time_of_phrase (id),
  FOREIGN KEY (type_of_phrase_id) REFERENCES type_of_phrase (id)
);

CREATE TABLE phrase_for_training (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  category_id UUID         NOT NULL,
  phrase      VARCHAR(255) NOT NULL,
  translation VARCHAR(255) NOT NULL,

  FOREIGN KEY (category_id) REFERENCES phrase_category (id)
);

ALTER TABLE public.users
  ADD Key VARCHAR(255) NULL;

ALTER TABLE public.users
  ADD active BOOLEAN DEFAULT FALSE  NOT NULL;

ALTER TABLE public.part_of_speech
  ADD image_id UUID NULL;
ALTER TABLE public.part_of_speech
  ADD CONSTRAINT part_of_speech_images__fk
FOREIGN KEY (image_id) REFERENCES images (id);

ALTER TABLE public.part_of_speech
  ADD description VARCHAR(500) DEFAULT 'empty description' NULL;

ALTER TABLE public.words
  ADD BILLING_DETAILS_TYPE CHARACTER NULL;

CREATE TABLE oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

CREATE TABLE oauth_client_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

CREATE TABLE oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
);

CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
);

CREATE TABLE oauth_code (
  code           VARCHAR(256),
  authentication BYTEA
);

CREATE TABLE oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);


ALTER TABLE public.phrases ALTER COLUMN word_id DROP NOT NULL;
ALTER TABLE public.translations ALTER COLUMN word_id DROP NOT NULL;
