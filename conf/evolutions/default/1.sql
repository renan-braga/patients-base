# --- !Ups

CREATE TABLE patients (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  age INT,
  diagnosis VARCHAR(255)
);

# --- !Downs

DROP TABLE patients;