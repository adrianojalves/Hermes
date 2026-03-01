ALTER TABLE IF EXISTS public.table_despesas DROP CONSTRAINT IF EXISTS fk6mbvrbkvq1eh03pkb4xdsua9v;
ALTER TABLE IF EXISTS public.table_conta DROP CONSTRAINT IF EXISTS table_conta_id_conta_check;
ALTER TABLE public.table_conta
    ALTER COLUMN id_conta TYPE varchar(20),
    ADD COLUMN status BOOLEAN;
    
UPDATE table_conta SET status='true';