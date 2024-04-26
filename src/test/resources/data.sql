-- Criar a tabela 'game'
CREATE TABLE game (
                      id INT PRIMARY KEY,
                      name VARCHAR(255),
                      banner_url VARCHAR(255)
);

-- Inserir dados de teste na tabela 'game'
INSERT INTO game (id, name, banner_url) VALUES (1, 'Teste 1', 'url1');
INSERT INTO game (id, name, banner_url) VALUES (2, 'Teste 2', 'url2');
INSERT INTO game (id, name, banner_url) VALUES (3, 'Teste 3', 'url3');

SELECT * FROM game;

