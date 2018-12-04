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
  category_id       UUID         NOT NULL,
  part_of_speech_id UUID         NOT NULL,
  image_id          UUID         NOT NULL,
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
