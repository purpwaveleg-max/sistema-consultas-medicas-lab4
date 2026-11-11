-- Garante que os acentos sejam inseridos corretamente
SET NAMES utf8mb4;

USE consultas_medicas;

-- endereco
INSERT INTO endereco (id_endereco, logradouro, numero, complemento, bairro, cidade, uf, cep) VALUES
    (1, 'Rua das Flores', '123', 'Apto 101', 'Renascença', 'São Luís', 'MA', '65075-000'),
    (2, 'Av. Litorânea', '450', NULL, 'Ponta d''Areia', 'São Luís', 'MA', '65077-000'),
    (3, 'Rua São Pantaleão', '88', NULL, 'Centro', 'São Luís', 'MA', '65010-000'),
    (4, 'Travessa Boa Vista', '22', 'Bloco B', 'Cohama', 'São Luís', 'MA', '65076-000');

-- plano_saude
INSERT INTO plano_saude (id_plano, nome_plano) VALUES
    (1, 'Unimed'),
    (2, 'Hapvida'),
    (3, 'Bradesco Saúde');

-- medico
INSERT INTO medico (id_medico, nome, crm) VALUES
    (1, 'Dr. Ricardo Vilegas', '1234-MA'),
    (2, 'Dra. Ana Beatriz Costa', '5678-MA');

-- paciente
INSERT INTO paciente (id_paciente, nome_crianca, nome_responsavel, data_nascimento, sexo, id_endereco, id_plano) VALUES
    (1, 'Lucas Andrade Silva', 'Fernanda Andrade', '2022-06-10', 'M', 1, 1),
    (2, 'Maria Eduarda Silva', 'Fernanda Andrade', '2021-07-22', 'F', 1, 1),
    (3, 'Pedro Henrique Souza', 'Camila Souza', '2022-11-05', 'M', 2, 2),
    (4, 'Sofia Ramos Costa', 'Patrícia Ramos', '2023-01-15', 'F', 3, NULL),
    (5, 'Gabriel Lima Ferreira', 'Renata Lima', '2020-05-30', 'M', 4, 3);

-- telefone
INSERT INTO telefone (id_telefone, id_paciente, numero, tipo) VALUES
    (1, 1, '(98) 99111-2222', 'celular'),
    (2, 1, '(98) 3235-1111', 'residencial'),
    (3, 2, '(98) 99111-2222', 'celular'),
    (4, 3, '(98) 99222-3333', 'celular'),
    (5, 4, '(98) 99333-4444', 'celular'),
    (6, 5, '(98) 99444-5555', 'celular');

-- medicamento
INSERT INTO medicamento (id_medicamento, nome) VALUES
    (1, 'Paracetamol Gotas'),
    (2, 'Amoxicilina Suspensão'),
    (3, 'Ibuprofeno Gotas'),
    (4, 'Vitamina D Gotas'),
    (5, 'Sulfato Ferroso Gotas');

-- exame
INSERT INTO exame (id_exame, nome) VALUES
    (1, 'Hemograma Completo'),
    (2, 'Exame de Urina (EAS)'),
    (3, 'Raio-X de Tórax'),
    (4, 'Ultrassonografia Abdominal');

-- consulta
INSERT INTO consulta (id_consulta, id_paciente, id_medico, data_hora, novo_paciente, agendada) VALUES
    (1, 1, 1, '2026-11-10 09:30:00', 1, 1),
    (2, 2, 1, '2026-11-10 10:30:00', 0, 1),
    (3, 3, 1, '2026-11-10 11:30:00', 0, 1);

-- prontuario
INSERT INTO prontuario (id_prontuario, id_consulta, descricao_sintomas, observacao_clinica, peso, altura) VALUES
    (1, 1, 'Febre baixa e tosse leve', 'Ausculta pulmonar sem alterações. Boa hidratação.', 8.0, 68.0),
    (2, 2, 'Consulta de rotina', 'Desenvolvimento adequado para a idade.', 10.8, 79.0);

-- prescricao
INSERT INTO prescricao (id_prescricao, id_prontuario, id_medicamento, dosagem, administracao, tempo_de_uso) VALUES
    (1, 1, 1, '10 gotas', 'oral, a cada 6h se febre', '3 dias'),
    (2, 1, 4, '3 gotas', 'oral, 1x ao dia', '30 dias');

-- prontuario_exame
INSERT INTO prontuario_exame (id_prontuario, id_exame) VALUES
    (1, 1),
    (1, 2);
