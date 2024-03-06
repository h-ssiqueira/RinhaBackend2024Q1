CREATE TABLE IF NOT EXISTS "client" (
    "id" SERIAL PRIMARY KEY,
    "account_limit" INTEGER NOT NULL,
    "balance" INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS "transaction" (
    "id" SERIAL PRIMARY KEY,
    "client_id" INTEGER NOT NULL,
    "value" INTEGER NOT NULL,
    "type" CHAR(1) NOT NULL,
    "description" CHAR(10) NOT NULL,
    "transaction_date" TIMESTAMP NOT NULL,
    FOREIGN KEY ("client_id") REFERENCES "client"("id")
);

CREATE INDEX IF NOT EXISTS idx_client_id ON "transaction" ("client_id");
CREATE INDEX IF NOT EXISTS idx_data_transaction ON "transaction" ("transaction_date" DESC);

INSERT INTO "client" ("id", "account_limit", "balance") VALUES
    (1,100000,0),
    (2,80000,0),
    (3,1000000,0),
    (4,10000000,0),
    (5,500000,0);