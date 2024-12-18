-- Adicionando o campo Role a minha tabela de usuários
ALTER TABLE tb_user
ADD COLUMN role VARCHAR(255) NULL;