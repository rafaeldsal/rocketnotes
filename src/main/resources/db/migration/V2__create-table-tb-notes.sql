-- Criação da tabela "tb_notes"
CREATE TABLE IF NOT EXISTS tb_notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    description TEXT,
    user_id INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES tb_user(id) ON DELETE CASCADE
)