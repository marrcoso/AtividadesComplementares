-- Limpa o banco de dados se ele já existir (para testes)
DROP TABLE IF EXISTS validacao_atividade, parecer, atividade_realizada, requerimento, aluno, atividade_complementar, modalidade, documentacao_comprobatoria, horas_por_atividade CASCADE;

-- Tabela de Alunos
CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Tabela de Modalidades de Atividades Complementares
CREATE TABLE modalidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela que descreve os tipos de atividades complementares
CREATE TABLE atividade_complementar (
    id SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    limite_maximo_horas INT, -- Limite de horas por ocorrência da atividade
    modalidade_id INT NOT NULL,
    FOREIGN KEY (modalidade_id) REFERENCES modalidade(id)
);

-- Tabela de Requerimentos (agrupa um conjunto de atividades para validação)
CREATE TABLE requerimento (
    id SERIAL PRIMARY KEY,
    aluno_id INT NOT NULL,
    data_requerimento DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(50) NOT NULL, -- Ex: "Em análise", "Validado", "Reprovado"
    data_validacao DATE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

-- Tabela de Atividades que o aluno efetivamente realizou
CREATE TABLE atividade_realizada (
    id SERIAL PRIMARY KEY,
    requerimento_id INT, -- Pode ser nulo se a atividade for registrada sem um requerimento formal ainda
    aluno_id INT NOT NULL, -- Redundância denormalizada para facilitar a consulta por aluno
    atividade_complementar_id INT NOT NULL,
    horas_apresentadas INT NOT NULL,
    documento TEXT, -- Ex: URL para o certificado
    data_realizacao DATE NOT NULL,
    horas_validadas INT, -- Horas efetivamente validadas pelo NDE
    FOREIGN KEY (requerimento_id) REFERENCES requerimento(id),
    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    FOREIGN KEY (atividade_complementar_id) REFERENCES atividade_complementar(id)
);

-- Populando dados iniciais para teste
INSERT INTO aluno (nome) VALUES ('João da Silva'), ('Maria Oliveira');

INSERT INTO modalidade (nome) VALUES ('Ensino'), ('Pesquisa e Inovação'), ('Extensão'), ('Outras');

INSERT INTO atividade_complementar (descricao, limite_maximo_horas, modalidade_id) VALUES
('Curso extracurricular na área do curso (por certificado)', 40, (SELECT id FROM modalidade WHERE nome = 'Ensino')),
('Monitoria (por semestre)', 60, (SELECT id FROM modalidade WHERE nome = 'Ensino')),
('Participação em projeto de Iniciação Científica (por semestre)', 80, (SELECT id FROM modalidade WHERE nome = 'Pesquisa e Inovação')),
('Publicação de artigo em evento científico', 20, (SELECT id FROM modalidade WHERE nome = 'Pesquisa e Inovação')),
('Estágio não obrigatório (por semestre)', 100, (SELECT id FROM modalidade WHERE nome = 'Extensão')),
('Trabalho voluntário (por projeto)', 30, (SELECT id FROM modalidade WHERE nome = 'Outras')),
('Representação estudantil (por ano)', 25, (SELECT id FROM modalidade WHERE nome = 'Outras'));

-- Exemplo de atividades realizadas para o Aluno 1 (João da Silva)
-- Para este exemplo, vamos considerar horas_validadas = horas_apresentadas
INSERT INTO atividade_realizada (aluno_id, atividade_complementar_id, horas_apresentadas, horas_validadas, documento, data_realizacao) VALUES
(1, 1, 30, 30, 'certificado_curso_java.pdf', '2024-05-10'),  -- Ensino
(1, 2, 60, 60, 'declaracao_monitoria.pdf', '2023-12-20'), -- Ensino (Total Ensino: 90h)
(1, 3, 80, 80, 'declaracao_ic.pdf', '2024-06-01'),        -- Pesquisa (Total Pesquisa: 80h)
(1, 5, 120, 100, 'contrato_estagio.pdf', '2024-01-15'),  -- Extensão (Apresentou 120h, mas o limite da atividade era 100h)
(1, 6, 30, 30, 'declaracao_voluntariado.pdf', '2023-11-05'); -- Outras (Total Outras: 30h)