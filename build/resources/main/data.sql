INSERT INTO users (active, email, first_name, last_name, password)
VALUES
    (true, 'admin@example.com', 'Admin', 'Adminov', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151');


INSERT INTO brands (name)
VALUES
    ('Toyota'),
    ('Ford');

INSERT INTO models (category, brand_id, name)
VALUES
    ('CAR', 1, 'Camry'),
    ('CAR', 1, 'Corolla'),
    ('CAR', 2, 'Focus'),
    ('CAR', 2, 'Fiesta');