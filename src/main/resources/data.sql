INSERT INTO menu (name_of_dish, price_per_plate) VALUES
('Tempura Rolls', 130.00),
('Salmon Rolls', 145.00),
('California Rolls', 155.00),
('Miso Soup', 120.00);

INSERT INTO room (room_name, room_price, max_guests) VALUES
('Sakura', 4000.00, 40),
('Osaka', 3000.00, 30),
('Dragon Room', 2000.00,20),
('Bamboo Hall', 1000.00, 10);


INSERT INTO role (role_name) VALUES
('ADMIN'),
('USER');

INSERT INTO users (user_name, password, email ,role_id) VALUES
('Hugo','hugo', 'Hugo@Ransvi.se',(SELECT id FROM role WHERE role_name = 'ADMIN')),
('Jocelyn', 'jocelyn', 'Jocelyn@Campos.se', (SELECT id FROM role WHERE role_name = 'USER')),
('Erik','erik','Erik@Edman.se', (SELECT id FROM role WHERE role_name = 'USER')),
('Mohamed','mohamed', 'Mohamed@Sharshar.se', (SELECT id FROM role WHERE role_name = 'USER'));
 