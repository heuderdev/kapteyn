DROP DATABASE IF EXISTS kapteyn;
CREATE DATABASE IF NOT EXISTS kapteyn;

USE kapteyn;
--
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS dependencies;
DROP TABLE IF EXISTS scheduled_schedule_task;
DROP TABLE IF EXISTS scheduled_schedule;
DROP TABLE IF EXISTS operators;
DROP TABLE IF EXISTS checklist_dates;
DROP TABLE IF EXISTS checklists;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS areas;


--

CREATE TABLE users(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `permission` TINYINT(1) NOT NULL DEFAULT '1',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE,
    UNIQUE INDEX `email` (`email`) USING BTREE
);

CREATE TABLE areas(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `area_name` LONGTEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `id` (`id`)
);

CREATE TABLE dependencies(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `dependency` LONGTEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);

-- scheduled_schedule = horario_previstos
CREATE TABLE scheduled_schedule(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `description` TEXT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);

CREATE TABLE tasks(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `dependencies_id` INT(11) NULL,
    `areas_id` INT(11) NULL,
    `instructions` LONGTEXT NULL,
    `sla` VARCHAR(50) NULL,
    `process_name` VARCHAR(100) NOT NULL,
    `monday`  TINYINT(1) NOT NULL DEFAULT '1',
    `tuesday`  TINYINT(1) NOT NULL DEFAULT '1',
    `wednesday`  TINYINT(1) NOT NULL DEFAULT '1',
    `thursday`  TINYINT(1) NOT NULL DEFAULT '1',
    `friday`  TINYINT(1) NOT NULL DEFAULT '1',
    `saturday`  TINYINT(1) NOT NULL DEFAULT '1',
    `sunday`  TINYINT(1) NOT NULL DEFAULT '1',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (dependencies_id) REFERENCES dependencies(id),
    FOREIGN KEY (areas_id) REFERENCES areas(id),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);


CREATE TABLE scheduled_schedule_task(
    `scheduled_schedule_id` INT(11) NOT NULL,
    `task_id` INT(11) NOT NULL,
    FOREIGN KEY (scheduled_schedule_id) REFERENCES scheduled_schedule(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE checklists(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `task_id` INT(11) NOT NULL,
    `spreadsheet_date` TIMESTAMP,
    `execution_date` TIMESTAMP,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);

CREATE TABLE operators(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `checklist_id` INT(11) NOT NULL,
    `json` LONGTEXT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (checklist_id) REFERENCES checklists(id),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);

CREATE TABLE checklist_dates(
    `id` INT(11)  NOT NULL AUTO_INCREMENT,
    `checklist_id` INT(11) NOT NULL,
    `start_time` TIMESTAMP,
    `end_time` TIMESTAMP,
    `status` ENUM('PENDING','IN_PROCESS','SUCCESS','ERROR') DEFAULT('PENDING'),
    `description` LONGTEXT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (checklist_id) REFERENCES checklists(id),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
);