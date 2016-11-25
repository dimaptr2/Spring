CREATE TABLE IF NOT EXISTS employee (id bigint auto_increment, first_name varchar(50), last_name varchar(50),
middle_name VARCHAR(50), PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS address (id bigint auto_increment, building varchar(50), street varchar(128),
city varchar(128), region varchar(128), country varchar(128), postbox varchar(10), emplid bigint not null,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS phones(id bigint auto_increment, phone_number varchar(15), emplid bigint not null,
PRIMARY KEY(id));

ALTER TABLE phones ADD FOREIGN KEY (emplid) REFERENCES employee(id);
ALTER TABLE address ADD FOREIGN KEY (emplid) REFERENCES employee(id);
