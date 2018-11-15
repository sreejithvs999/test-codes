/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');


insert into manufacturer (id, country, date_created, deleted, logo, name)
values(1, 'Deutschland', now(), false, 'https://www.bmw.com/en/index.html', 'BMW');

insert into manufacturer (id, country, date_created, deleted, logo, name)
values(2, 'Deutschland', now(), false, 'https://www.porsche.com/international/models/macan/', 'Porsche');

insert into manufacturer (id, country, date_created, deleted, logo, name)
values(3, 'Deutschland', now(), false, 'https://www.mercedes-benz.com/en/', 'Mercedes-Benz');

insert into manufacturer (id, country, date_created, deleted, logo, name)
values(4, 'Deutschland', now(), false, 'https://www.audi.com/', 'Audi');


insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(1, false, now(), false, 'GAS', 'MUN 07632', 3.44, 6, 2);

insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(2, true, now(), false, 'ELECTRIC', 'HHK 056295', 2.5, 4, 1);

insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(3, false, now(), false, 'PETROL', 'HB 6892B', 5.0, 5, 4);

insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(4, false, now(), false, 'DIESEL', 'HB 8794C', 4.8, 6, 3);

insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(5, false, now(), false, 'GAS', 'HB 6338D', 2.1, 4, 4);

insert into car(id, convertible, date_created, deleted, engine_type, license_plate, rating, seat_count, manufacturer_id)
values(6, false, now(), false, 'DIESEL', 'HHK 357632', 4.0, 6, 1);


insert into driver_car(driver_id, car_id) values(4, 3);
insert into driver_car(driver_id, car_id) values(2, 1);
insert into driver_car(driver_id, car_id) values(6, 5);

alter sequence hibernate_sequence restart with 100;
commit;
