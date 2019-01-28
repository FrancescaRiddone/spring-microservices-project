INSERT INTO country (country_id, country) values (1, 'United States')
INSERT INTO country (country_id, country) values (2, 'China')
INSERT INTO country (country_id, country) values (3, 'United Arab Emirates')
INSERT INTO country (country_id, country) values (4, 'Japan')
INSERT INTO country (country_id, country) values (5, 'United Kingdom')
INSERT INTO country (country_id, country) values (6, 'Spain')
INSERT INTO country (country_id, country) values (7, 'Italy')
INSERT INTO country (country_id, country) values (8, 'India')
INSERT INTO country (country_id, country) values (9, 'Germany')
INSERT INTO country (country_id, country) values (10, 'Australia')
INSERT INTO country (country_id, country) values (11, 'France')


INSERT INTO city (city_id, city, country_id) values (1, 'Atlanta', 1)
INSERT INTO city (city_id, city, country_id) values (2, 'Pechino', 2)
INSERT INTO city (city_id, city, country_id) values (3, 'Dubai', 3)
INSERT INTO city (city_id, city, country_id) values (4, 'Los Angeles', 1)
INSERT INTO city (city_id, city, country_id) values (5, 'Tokyo', 4)
INSERT INTO city (city_id, city, country_id) values (6, 'Chicago', 1)
INSERT INTO city (city_id, city, country_id) values (7, 'London', 5)
INSERT INTO city (city_id, city, country_id) values (8, 'Barcelona', 6)
INSERT INTO city (city_id, city, country_id) values (9, 'Milan', 7)
INSERT INTO city (city_id, city, country_id) values (10, 'Hong Kong', 2)
INSERT INTO city (city_id, city, country_id) values (11, 'Shanghai', 2)
INSERT INTO city (city_id, city, country_id) values (12, 'New Delhi', 8)
INSERT INTO city (city_id, city, country_id) values (13, 'Munich', 9)
INSERT INTO city (city_id, city, country_id) values (14, 'Sydney', 10)
INSERT INTO city (city_id, city, country_id) values (15, 'Rome', 7)
INSERT INTO city (city_id, city, country_id) values (16, 'Turin', 7)
INSERT INTO city (city_id, city, country_id) values (17, 'Paris', 11)


INSERT INTO hotel (hotel_id, hotel, city_id, country_id, address, stars, wifi, parking, restaurant, for_disabled_people, gym, spa, swimming_pool, breakfast_available, half_board_available, full_board_available) values (1, 'St Giles London � A St Giles Hotel', 7, 5, 'Bedford Avenue, Camden, Londra, WC1B 3GH, Regno Unito', 3, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0)
INSERT INTO hotel (hotel_id, hotel, city_id, country_id, address, stars, wifi, parking, restaurant, for_disabled_people, gym, spa, swimming_pool, breakfast_available, half_board_available, full_board_available) values (2, 'Cheshire Hotel', 7, 5, '110 Great Russell Street, Camden, Londra, WC1B 3NA, Regno Unito', 3, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0)
INSERT INTO hotel (hotel_id, hotel, city_id, country_id, address, stars, wifi, parking, restaurant, for_disabled_people, gym, spa, swimming_pool, breakfast_available, half_board_available, full_board_available) values (3, 'DoubleTree by Hilton Hotel London-Tower of London', 7, 5, '7 Pepys Street, City di Londra, Londra, EC3N 4AF, Regno Unito', 4, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0)
INSERT INTO hotel (hotel_id, hotel, city_id, country_id, address, stars, wifi, parking, restaurant, for_disabled_people, gym, spa, swimming_pool, breakfast_available, half_board_available, full_board_available) values (4, 'Henry VIII', 7, 5, '23 Leinster Gardens, Quartiere di Westminster, Londra, W2 3AN, Regno Unito', 4, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0)
INSERT INTO hotel (hotel_id, hotel, city_id, country_id, address, stars, wifi, parking, restaurant, for_disabled_people, gym, spa, swimming_pool, breakfast_available, half_board_available, full_board_available) values (5, 'Hilton London Bankside', 7, 5, '2-8 Great Suffolk Street   , Southwark, Londra, SE1 0UG, Regno Unito', 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)


INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (1, 1, 2, 139.00, 160.00, 0.0, 0.0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (2, 1, 2, 139.00, 160.00, 0.0, 0.0, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (3, 1, 2, 168.00, 183.00, 0.0, 0.0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (4, 2, 3, 251.00, 264.00, 0.0, 0.0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (5, 2, 2, 161.00, 176.00, 0.0, 0.0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (6, 2, 2, 134.00, 141.00, 0.0, 0.0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (7, 3, 2, 230.00, 260.00, 300.0, 0.0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (8, 3, 2, 240.00, 271.00, 310.0, 0.0, 2, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (9, 3, 3, 145.00, 161.00, 0.0, 0.0, 3, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (10, 3, 2, 206.00, 229.00, 0.0, 0.0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (11, 3, 1, 119.00, 132.00, 0.0, 0.0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (12, 4, 2, 280.00, 350.00, 380.0, 420.0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1)
INSERT INTO room (room_id, hotel_id, hosts_number, standard_daily_price, with_breakfast_daily_price, half_board_daily_price, full_board_daily_price, single_beds, double_beds, air_conditioner, heat, TV, telephone, vault, bathtub, swimming_pool, soundproofing, with_view, bathroom, balcony, available) values (13, 4, 2, 280.00, 350.00, 380.0, 420.0, 2, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1)


INSERT INTO reservation (reservation_id, room_id, check_in, check_out, user_name, user_surname, price, hosts_number, reservation_type, confirmed) values (1, 1, '2019-03-13', '2019-03-15', 'Mario', 'Rossi', 320.00, 2, 'breakfast', 1)
INSERT INTO reservation (reservation_id, room_id, check_in, check_out, user_name, user_surname, price, hosts_number, reservation_type, confirmed) values (2, 4, '2019-03-13', '2019-03-17', 'Elisa', 'Bianchi', 753.00, 3, 'standard', 1)



