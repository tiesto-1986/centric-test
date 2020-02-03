CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(36) primary key,
    name VARCHAR(64),
    description text,
    category VARCHAR(64),
    brand VARCHAR(64),
    tags jsonb,
    created_date timestamp without time zone default (now() at time zone 'utc')
);

CREATE INDEX idx_category ON products USING btree (category);
