CREATE DATABASE IF NOT EXISTS mvcplantas
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mvcplantas;

-- =========================================
-- TABELA 1: PERFIS
-- =========================================
CREATE TABLE perfis (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

-- =========================================
-- TABELA 2: USUÁRIOS
-- =========================================
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_usuario_login UNIQUE (login),
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);

-- =========================================
-- TABELA 3: AMBIENTES (Locais da casa)
-- =========================================
CREATE TABLE ambientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),

    PRIMARY KEY (id)
);

-- =========================================
-- TABELA 4: CUIDADOS (Catálogo de cuidados)
-- =========================================
CREATE TABLE cuidados (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    dias_intervalo INT DEFAULT 1,

    PRIMARY KEY (id)
);

-- =========================================
-- TABELA 5: PLANTAS
-- =========================================
CREATE TABLE plantas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome_popular VARCHAR(150) NOT NULL,
    nome_cientifico VARCHAR(150),
    data_aquisicao DATE,
    observacoes TEXT,
    usuario_id BIGINT NOT NULL,
    ambiente_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_planta_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_planta_ambiente FOREIGN KEY (ambiente_id) REFERENCES ambientes(id)
);

-- =========================================
-- TABELA 6: LEMBRETES (Lembretes e histórico de cuidados)
-- =========================================
CREATE TABLE lembretes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    planta_id BIGINT NOT NULL,
    cuidado_id BIGINT NOT NULL,
    data_agendada DATE NOT NULL,
    data_realizada DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE', -- PENDENTE, CONCLUIDO, CANCELADO
    observacao VARCHAR(255),

    PRIMARY KEY (id),
    CONSTRAINT fk_lembrete_planta FOREIGN KEY (planta_id) REFERENCES plantas(id) ON DELETE CASCADE,
    CONSTRAINT fk_lembrete_cuidado FOREIGN KEY (cuidado_id) REFERENCES cuidados(id)
);

-- =========================================
-- DADOS PARA TESTE (SEED)
-- =========================================

INSERT INTO perfis (nome) VALUES
    ('Administrador'),
    ('Jardineiro');

-- Senha padrao inicial dos usuarios de seed: 123456 (criptografada com BCrypt)
INSERT INTO usuarios (nome, login, senha, perfil_id) VALUES
    ('Pedro Henrique', 'pedro', '$2a$12$VlW/RXxONLPaguKIAdkZU.TOoDvQVoBid9vkTnWe.oZQ/0.4u55Cm', 1),
    ('Miguel Garcia', 'miguel', '$2a$12$VlW/RXxONLPaguKIAdkZU.TOoDvQVoBid9vkTnWe.oZQ/0.4u55Cm', 1),
    ('Usuario Teste', 'teste', '$2a$12$VlW/RXxONLPaguKIAdkZU.TOoDvQVoBid9vkTnWe.oZQ/0.4u55Cm', 2);

INSERT INTO ambientes (nome, descricao) VALUES
    ('Sala de Estar', 'Ambiente interno com luz indireta'),
    ('Varanda', 'Ambiente com bastante incidência de sol pela manhã'),
    ('Jardim de Inverno', 'Ambiente com boa umidade e ventilação'),
    ('Quarto', 'Local com luminosidade filtrada');

INSERT INTO cuidados (nome, descricao, dias_intervalo) VALUES
    ('Rega Moderada', 'Regar a terra sem encharcar as raízes', 3),
    ('Adubação Orgânica', 'Adicionar húmus de minhoca ou NPK', 30),
    ('Poda de Limpeza', 'Remover folhas secas ou amareladas', 15),
    ('Banho de Sol', 'Expor a planta diretamente à luz solar matinal', 1),
    ('Troca de Substrato / Vaso', 'Mudar o substrato ou replantar em vaso maior', 180);

INSERT INTO plantas (nome_popular, nome_cientifico, data_aquisicao, observacoes, usuario_id, ambiente_id) VALUES
    ('Costela-de-Adão', 'Monstera deliciosa', '2024-01-15', 'Gosta de umidade e luz difusa', 1, 1),
    ('Espada-de-São-Jorge', 'Sansevieria trifasciata', '2024-02-10', 'Muito resistente, rega bem espaçada', 1, 2),
    ('Jiboia', 'Epipremnum aureum', '2024-03-01', 'Planta pendente na prateleira alta', 2, 1);

INSERT INTO lembretes (planta_id, cuidado_id, data_agendada, data_realizada, status, observacao) VALUES
    (1, 1, '2026-09-10', NULL, 'PENDENTE', 'Checar se o substrato está seco antes de regar'),
    (1, 3, '2026-09-05', '2026-09-05', 'CONCLUIDO', 'Folhas velhas retiradas'),
    (2, 1, '2026-09-15', NULL, 'PENDENTE', 'Rega quinzenal'),
    (3, 2, '2026-09-12', NULL, 'PENDENTE', 'Aplicar adubo diluído na água');

-- =========================================
-- CONSULTAS DE EXEMPLO
-- =========================================

-- Listar plantas com seus respectivos usuários e ambientes
SELECT 
    p.id,
    p.nome_popular,
    p.nome_cientifico,
    u.nome AS dono,
    a.nome AS ambiente
FROM plantas p
INNER JOIN usuarios u ON u.id = p.usuario_id
INNER JOIN ambientes a ON a.id = p.ambiente_id;

-- Listar lembretes pendentes com detalhes da planta e do cuidado
SELECT 
    l.id,
    p.nome_popular AS planta,
    c.nome AS cuidado,
    l.data_agendada,
    l.status
FROM lembretes l
INNER JOIN plantas p ON p.id = l.planta_id
INNER JOIN cuidados c ON c.id = l.cuidado_id
WHERE l.status = 'PENDENTE'
ORDER BY l.data_agendada ASC;