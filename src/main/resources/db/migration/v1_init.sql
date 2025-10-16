CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'client_type') THEN
    CREATE TYPE client_type AS ENUM ('PERSON', 'COMPANY');
  END IF;
END$$;

CREATE TABLE IF NOT EXISTS client (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  type client_type NOT NULL,
  name TEXT NOT NULL,
  phone TEXT,
  email TEXT,
  birth_date DATE,
  company_identifier TEXT,
  deleted BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS contract (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  client_id UUID NOT NULL REFERENCES client(id) ON DELETE CASCADE,
  start_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  end_date TIMESTAMP WITH TIME ZONE,
  cost_amount NUMERIC(19,4) NOT NULL,
  last_modified_date TIMESTAMP WITH TIME ZONE DEFAULT now(),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_contract_client_enddate ON contract (client_id, end_date);
