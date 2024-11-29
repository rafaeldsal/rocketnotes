-- Criação da tabela "tb_tags"
CREATE TABLE IF NOT EXISTS tb_tags (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    user_id INTEGER,
    notes_id INTEGER,
    FOREIGN KEY(user_id) REFERENCES tb_user(id),
    FOREIGN KEY(notes_id) REFERENCES tb_notes(id) ON DELETE CASCADE
)