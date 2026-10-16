INSERT IGNORE INTO periodo (id, periodo_enum) VALUES (1, 'manha');
INSERT IGNORE INTO periodo (id, periodo_enum) VALUES (2, 'tarde');
INSERT IGNORE INTO periodo (id, periodo_enum) VALUES (3, 'noite');

INSERT IGNORE INTO funcionario (funcao, matricula, nome, periodo_id, cpf, senha) 
VALUES ('gerente', 'FUNC-1234', 'João Silva', 1, '11111111111', 'senha123');

INSERT IGNORE INTO funcionario (funcao, matricula, nome, periodo_id, cpf, senha) 
VALUES ('asg', 'FUNC-1235', 'Nicolas Pereira', 2, '11111111112', 'senha123');

INSERT IGNORE INTO funcionario (funcao, matricula, nome, periodo_id, cpf, senha) 
VALUES ('banheirista', 'FUNC-1236', 'Maria Oliveira', 1, '11111111114', 'senha123');

INSERT IGNORE INTO funcionario (funcao, matricula, nome, periodo_id, cpf, senha) 
VALUES ('asg', 'FUNC-1237', 'Carlos Souza', 2, '11111111115', 'senha123');

INSERT IGNORE INTO funcionario (funcao, matricula, nome, periodo_id, cpf, senha) 
VALUES ('banheirista', 'FUNC-1238', 'Ana Santos', 3, '11111111116', 'senha123');

INSERT IGNORE INTO professor (matricula, nome, periodo_id, cpf, senha) 
VALUES ('PROF-1232', 'Vinicius Lira', 3, '11111111113', 'senha123');

INSERT IGNORE INTO professor (matricula, nome, periodo_id, cpf, senha) 
VALUES ('PROF-1233', 'Mariana Costa', 1, '11111111117', 'senha123');

INSERT IGNORE INTO professor (matricula, nome, periodo_id, cpf, senha) 
VALUES ('PROF-1234', 'Roberto Alves', 2, '11111111118', 'senha123');

INSERT IGNORE INTO ambiente (nome, localizacao, tipo, status_ambiente, ocupado, professor_ocupante_id) 
VALUES ('Sala 101', 'Bloco A - 1º Andar', 'sala', 'limpo', false, NULL);

INSERT IGNORE INTO ambiente (nome, localizacao, tipo, status_ambiente, ocupado, professor_ocupante_id) 
VALUES ('Banheiro Masculino Térreo', 'Bloco A - Térreo', 'banheiro', 'sujo', false, NULL);

INSERT IGNORE INTO ambiente (nome, localizacao, tipo, status_ambiente, ocupado, professor_ocupante_id) 
VALUES ('Refeitório Principal', 'Bloco Central', 'refeitorio', 'pendente', false, NULL);

INSERT IGNORE INTO ambiente (nome, localizacao, tipo, status_ambiente, ocupado, professor_ocupante_id) 
VALUES ('Sala 202', 'Bloco B - 2º Andar', 'sala', 'limpo', true, 1);

INSERT IGNORE INTO ambiente (nome, localizacao, tipo, status_ambiente, ocupado, professor_ocupante_id) 
VALUES ('Banheiro Feminino Térreo', 'Bloco A - Térreo', 'banheiro', 'limpo', false, NULL);

INSERT IGNORE INTO tarefa (descricao, tipo_limpeza_enum, periodo_id, horario_conclusao) 
VALUES ('Varredura completa, limpeza de mesas e desinfecção do chão', 'intensiva', 1, NULL);

INSERT IGNORE INTO tarefa (descricao, tipo_limpeza_enum, periodo_id, horario_conclusao) 
VALUES ('Reposição de papel toalha, sabonete líquido e recolhimento de lixo', 'manutencao', 1, '2026-10-02 10:30:00');

INSERT IGNORE INTO tarefa (descricao, tipo_limpeza_enum, periodo_id, horario_conclusao) 
VALUES ('Higienização completa dos vasos sanitários e lavagem do piso', 'intensiva', 2, NULL);

INSERT IGNORE INTO tarefa (descricao, tipo_limpeza_enum, periodo_id, horario_conclusao) 
VALUES ('Limpeza das mesas do refeitório e organização das cadeiras', 'moderada', 3, NULL);

INSERT IGNORE INTO tarefa (descricao, tipo_limpeza_enum, periodo_id, horario_conclusao) 
VALUES ('Passar pano nas superfícies e esvaziar lixeiras recicláveis', 'manutencao', 2, '2026-10-02 14:15:00');
