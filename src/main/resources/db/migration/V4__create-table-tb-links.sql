-- Criação da tabela "tb_links"
CREATE TABLE IF NOT EXISTS tb_links (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    url TEXT,
    notes_id INTEGER,
    created_at TIMESTAMP,
    FOREIGN KEY(notes_id) REFERENCES tb_notes(id) ON DELETE CASCADE
)