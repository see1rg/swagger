CREATE TABLE car (
                     id SERIAL PRIMARY KEY,
                     brand_car varchar not null ,
                     model varchar not null ,
                     price money
);

CREATE TABLE human (
                       id SERIAL PRIMARY KEY,
                       age INTEGER check (age > 0),
                       car_id INT NOT NULL,
                       name VARCHAR(20) not null unique,
                       driver_licence BOOLEAN default('true'),
                       CONSTRAINT fk_human_car FOREIGN KEY (car_id)
                           REFERENCES car(id)
                           ON DELETE CASCADE
                           ON UPDATE CASCADE
);