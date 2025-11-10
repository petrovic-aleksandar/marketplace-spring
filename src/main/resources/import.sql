insert into appuser (id, username, password, name, email, phone, balance, role, active) values (1, 'admin', '$2a$10$BGDNy/UW5ORUfHGZlTEw.ew0d4lZAUgGwEMUg75db2vxtApznQzYa', 'The Admin', 'mm@gmail.com', '67 123 123', 0.0, 'Admin', true);
insert into appuser (id, username, password, name, email, phone, balance, role, active) values (2, 'john', '$2a$10$BGDNy/UW5ORUfHGZlTEw.ew0d4lZAUgGwEMUg75db2vxtApznQzYa', 'John Doe', 'pp@gmail.com', '67 100 100', 0.0, 'User', true);
insert into appuser (id, username, password, name, email, phone, balance, role, active) values (3, 'rich', '$2a$10$BGDNy/UW5ORUfHGZlTEw.ew0d4lZAUgGwEMUg75db2vxtApznQzYa', 'Guy Rich', 'jj@gmail.com', '67 111 222', 100000.0, 'User', true);
select setval('user_seq', 3);

insert into item_type (id, name, description, image_path) values (1, 'Cars&Vehicles', 'Cars and other transportation. Scooters, bikes, trains, boats, yachts, airships..', 'Categories/1.jpg');
insert into item_type (id, name, description, image_path) values (2, 'Home&Kitchen', 'Everything for your home, small home and kitchen electronics.', 'Categories/2.jpg');
insert into item_type (id, name, description, image_path) values (3, 'Electronics', 'All kinds of devices. Gaming equipment, laptops, home appliances etc.', 'Categories/3.jpg');
insert into item_type (id, name, description, image_path) values (4, 'Sports&Outdoors', 'Sports clothing and sport requisits.', 'Categories/4.jpg');
insert into item_type (id, name, description, image_path) values (5, 'Womens clothing', 'Everything for women.', 'Categories/5.jpg');
insert into item_type (id, name, description, image_path) values (6, 'Jewelry&Accesories', 'All kinds of jewelry and decorative items.', 'Categories/6.jpg');
insert into item_type (id, name, description, image_path) values (7, 'Mens clothing', 'Clothing items for men', 'Categories/7.jpg');
insert into item_type (id, name, description, image_path) values (8, 'Books&Media', 'Books and stuff.', 'Categories/8.jpg');
insert into item_type (id, name, description, image_path) values (9, 'Services', 'For various intelectual or physical services.', 'Categories/9.jpg');
insert into item_type (id, name, description, image_path) values (10, 'Other', 'Whatever else.', 'Categories/10.jpg');
select setval('item_type_seq', 10);

insert into item (id, name, description, price, type_id, active, deleted, created_at, seller_id) values (1, 'Audi A4', 'Red color, 2021., 35 TFSI', 30000, 1, true, false, '2025-10-19 17:43:38.8328', 2);
insert into item (id, name, description, price, type_id, active, deleted, created_at, seller_id) values (2, 'Audi A5', 'Pure luxury, 2021., 40 TFSI', 40000, 1, true, false, '2025-10-19 17:43:38.8328', 2);
insert into item (id, name, description, price, type_id, active, deleted, created_at, seller_id) values (3, 'Audi Q5', 'SUV version of classic Audi, 2021., 45 TDI', 65000, 1, true, false, '2025-10-19 17:43:38.8328', 2);
insert into item (id, name, description, price, type_id, active, deleted, created_at, seller_id) values (4, 'Audi TT', 'Old school 2016., 3.0 engine', 15000, 1, true, false, '2025-10-19 17:43:38.8328', 2);
insert into item (id, name, description, price, type_id, active, deleted, created_at, seller_id) values (5, 'Lotus elise', 'Cult classic from 2014., 4.0 gasoline', 60000, 1, true, false, '2025-10-19 17:43:38.8328', 2);
select setval ('item_seq', 5);

insert into image (id, path, item_id, front) values (1, 'audi_a4.jpg', 1, true);
insert into image (id, path, item_id, front) values (2, 'audi_a4_2.jpg', 1, false);
insert into image (id, path, item_id, front) values (3, 'audi_a4_3.jpg', 1, false);
insert into image (id, path, item_id, front) values (4, 'audi_a5.jpg', 2, true);
insert into image (id, path, item_id, front) values (5, 'audi_q5.jpg', 3, true);
insert into image (id, path, item_id, front) values (6, 'audi_tt.jpg', 4, true);
insert into image (id, path, item_id, front) values (7, 'lotuselise.jpg', 5, true);
select setval ('image_seq', 7);

