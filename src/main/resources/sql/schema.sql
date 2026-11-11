# ============================================================
-- Sistema de Consultas Médicas - Dr. Vilegas (Pediatra)
-- Etapa 02 - Script de criação do banco de dados (Lab 01/02)
-- ============================================================

SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS consultas_medicas
    CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE consultas_medicas;

CREATE TABLE IF NOT EXISTS endereco (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    logradouro  VARCHAR(120) NOT NULL,
    numero      VARCHAR(10)  NOT NULL,
    complemento VARCHAR(45),
    bairro      VARCHAR(80)  NOT NULL,
    cidade      VARCHAR(80)  NOT NULL,
    uf          CHAR(2)      NOT NULL,
    cep         VARCHAR(11)  NOT NULL
);

CREATE TABLE IF NOT EXISTS plano_saude (
    id_plano   INT AUTO_INCREMENT PRIMARY KEY,
    nome_plano VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS medico (
    id_medico INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(120) NOT NULL,
    crm       VARCHAR(20)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS paciente (
    id_paciente      INT AUTO_INCREMENT PRIMARY KEY,
    nome_crianca     VARCHAR(120) NOT NULL,
    nome_responsavel VARCHAR(120),
    data_nascimento  DATE NOT NULL,
    sexo             CHAR(1) NOT NULL,
    id_endereco      INT NOT NULL,
    id_plano         INT NULL,
    CONSTRAINT fk_paciente_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco),
    CONSTRAINT fk_paciente_plano    FOREIGN KEY (id_plano)    REFERENCES plano_saude(id_plano)
);

CREATE TABLE IF NOT EXISTS telefone (
    id_telefone INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    numero      VARCHAR(20) NOT NULL,
    tipo        VARCHAR(20) NOT NULL,
    CONSTRAINT fk_telefone_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

CREATE TABLE IF NOT EXISTS consulta (
    id_consulta   INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente   INT NOT NULL,
    id_medico     INT NOT NULL,
    data_hora     DATETIME NOT NULL,
    novo_paciente BOOLEAN NOT NULL DEFAULT FALSE,
    agendada      BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    CONSTRAINT fk_consulta_medico   FOREIGN KEY (id_medico)   REFERENCES medico(id_medico)
);

CREATE TABLE IF NOT EXISTS prontuario (
    id_prontuario      INT AUTO_INCREMENT PRIMARY KEY,
    id_consulta        INT NOT NULL UNIQUE,
    descricao_sintomas TEXT,
    observacao_clinica TEXT,
    peso               DECIMAL(5,2) NOT NULL,
    altura             DECIMAL(5,2) NOT NULL,
    CONSTRAINT fk_prontuario_consulta FOREIGN KEY (id_consulta) REFERENCES consulta(id_consulta)
);

CREATE TABLE IF NOT EXISTS medicamento (
    id_medicamento INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS exame (
    id_exame INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS prescricao (
    id_prescricao      INT AUTO_INCREMENT PRIMARY KEY,
    id_prontuario      INT NOT NULL,
    id_medicamento     INT NOT NULL,
    dosagem            VARCHAR(60) NOT NULL,
    administracao      VARCHAR(60) NOT NULL,
    tempo_de_uso       VARCHAR(60) NOT NULL,
    CONSTRAINT fk_prescricao_prontuario  FOREIGN KEY (id_prontuario)  REFERENCES prontuario(id_prontuario),
    CONSTRAINT fk_prescricao_medicamento FOREIGN KEY (id_medicamento) REFERENCES medicamento(id_medicamento)
);

CREATE TABLE IF NOT EXISTS prontuario_exame (
    id_prontuario INT NOT NULL,
    id_exame      INT NOT NULL,
    PRIMARY KEY (id_prontuario, id_exame),
    CONSTRAINT fk_pe_prontuario FOREIGN KEY (id_prontuario) REFERENCES prontuario(id_prontuario),
    CONSTRAINT fk_pe_exame      FOREIGN KEY (id_exame)      REFERENCES exame(id_exame)
);
