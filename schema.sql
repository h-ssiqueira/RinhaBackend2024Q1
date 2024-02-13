CREATE TABLE IF NOT EXISTS "cliente" (
    "id" SERIAL PRIMARY KEY,
    "limite" INTEGER NOT NULL,
    "saldo" INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS "transacao" (
    "id" SERIAL PRIMARY KEY,
    "cliente_id" INTEGER NOT NULL,
    "valor" INTEGER NOT NULL,
    "tipo" CHAR(1) NOT NULL,
    "descricao" CHAR(10) NOT NULL,
    "data_transacao" TIMESTAMP NOT NULL,
    FOREIGN KEY ("cliente_id") REFERENCES "cliente"("id")
);

CREATE INDEX IF NOT EXISTS idx_cliente_id ON "transacao" ("cliente_id");
CREATE INDEX IF NOT EXISTS idx_data_transacao ON "transacao" ("data_transacao" DESC);

INSERT INTO "cliente" ("id", "limite", "saldo") VALUES
    (1,100000,0),
    (2,80000,0),
    (3,1000000,0),
    (4,10000000,0),
    (5,500000,0);