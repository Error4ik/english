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

ALTER TABLE public.users
  ADD create_date TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.users
  ADD last_visit TIMESTAMP DEFAULT now() NOT NULL;

ALTER TABLE public.users
  ADD Key VARCHAR(255) NULL;

ALTER TABLE public.users
  ADD active BOOLEAN DEFAULT FALSE  NOT NULL;


