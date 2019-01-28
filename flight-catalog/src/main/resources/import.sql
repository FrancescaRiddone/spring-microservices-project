INSERT INTO company (company_id, company) values (1, 'Ryanair')
INSERT INTO company (company_id, company) values (2, 'easyJet')
INSERT INTO company (company_id, company) values (3, 'Air France')
INSERT INTO company (company_id, company) values (4, 'Alitalia')
INSERT INTO company (company_id, company) values (5, 'Iberia')
INSERT INTO company (company_id, company) values (6, 'British Airways')
INSERT INTO company (company_id, company) values (7, 'Vueling Airlines')
INSERT INTO company (company_id, company) values (8, 'Austrian Airlines')
INSERT INTO company (company_id, company) values (9, 'Lufthansa')
INSERT INTO company (company_id, company) values (10, 'Norlandair')
INSERT INTO company (company_id, company) values (11, 'Air Canada')
INSERT INTO company (company_id, company) values (12, 'Alaska Airlines')
INSERT INTO company (company_id, company) values (13, 'American Airlines')
INSERT INTO company (company_id, company) values (14, 'Atlas Air')
INSERT INTO company (company_id, company) values (15, 'Delta Air Lines')
INSERT INTO company (company_id, company) values (16, 'United Continental Holdings')
INSERT INTO company (company_id, company) values (17, 'International Airlines Group')
INSERT INTO company (company_id, company) values (18, 'Southwest Airlines')
INSERT INTO company (company_id, company) values (19, 'China Southern Airlines')
INSERT INTO company (company_id, company) values (20, 'All Nippon Airways')
INSERT INTO company (company_id, company) values (21, 'China Eastern Airlines')
INSERT INTO company (company_id, company) values (22, 'Turkish Airlines')
INSERT INTO company (company_id, company) values (23, 'Emirates')
INSERT INTO company (company_id, company) values (24, 'Air China')


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


INSERT INTO airport (airport_id, airport, city_id, code) values (1, 'Hartsfield–Jackson Atlanta International Airport', 1, 'ATL')
INSERT INTO airport (airport_id, airport, city_id, code) values (2, 'Beijing Capital International Airport', 2, 'PEK')
INSERT INTO airport (airport_id, airport, city_id, code) values (3, 'Dubai International Airport', 3, 'DXB')
INSERT INTO airport (airport_id, airport, city_id, code) values (4, 'Los Angeles International Airport', 4, 'LAX')
INSERT INTO airport (airport_id, airport, city_id, code) values (5, 'Tokyo Haneda Airport', 5, 'HND')
INSERT INTO airport (airport_id, airport, city_id, code) values (6, 'O Hare International Airport', 6, 'ORD')
INSERT INTO airport (airport_id, airport, city_id, code) values (7, 'London Heathrow Airport', 7, 'LHR')
INSERT INTO airport (airport_id, airport, city_id, code) values (8, 'London Gatwich Airport', 7, 'LGW')
INSERT INTO airport (airport_id, airport, city_id, code) values (9, 'London Luton Airport', 7, 'LTN')
INSERT INTO airport (airport_id, airport, city_id, code) values (10, 'Stansted Airport', 7, 'STN')
INSERT INTO airport (airport_id, airport, city_id, code) values (11, 'Barcelona El Prat Airport', 8, 'BCN')
INSERT INTO airport (airport_id, airport, city_id, code) values (12, 'Orio al Serio Airport', 9, 'BGY')
INSERT INTO airport (airport_id, airport, city_id, code) values (13, 'Malpensa Airport', 9, 'MPX')
INSERT INTO airport (airport_id, airport, city_id, code) values (14, 'Hong Kong International Airport', 10, 'HKG')
INSERT INTO airport (airport_id, airport, city_id, code) values (15, 'Shanghai Pudong International Airport', 11, 'PVG')
INSERT INTO airport (airport_id, airport, city_id, code) values (16, 'Indira Gandhi International Airport', 12, 'DEL')
INSERT INTO airport (airport_id, airport, city_id, code) values (17, 'Munich Airport', 13, 'MUC')
INSERT INTO airport (airport_id, airport, city_id, code) values (18, 'Sydney Kingsford-Smith Airport', 14, 'SYD')
INSERT INTO airport (airport_id, airport, city_id, code) values (19, 'Fiumicino Airport', 15, 'FCO')
INSERT INTO airport (airport_id, airport, city_id, code) values (20, 'Turin Airport', 16, 'TRN')
INSERT INTO airport (airport_id, airport, city_id, code) values (21, 'Charles de Gaulle Airport', 17, 'CDG')


INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (1, 1, 13, 9, 7, 9, 7, 5, 100, 60, 35, 97, 58, 35, 17.99, 52.88, 72.96, '2019-05-13 07:10:00', '2019-05-13 08:20:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (2, 1, 13, 9, 7, 9, 7, 5, 100, 60, 35, 100, 60, 35, 17.99, 52.88, 72.96, '2019-05-13 21:35:00', '2019-05-13 22:30:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (3, 2, 12, 9, 7, 8, 7, 5, 100, 56, 0, 100, 56, 0, 27.00, 40.00, 0, '2019-05-13 07:25:00', '2019-05-13 08:25:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (4, 2, 12, 9, 7, 8, 7, 5, 100, 56, 0, 99, 56, 0, 29.18, 48.67, 0, '2019-05-13 16:25:00', '2019-05-13 17:20:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (5, 2, 13, 9, 7, 8, 7, 5, 100, 56, 0, 100, 56, 0, 29.18, 48.67, 0, '2019-05-13 18:10:00', '2019-05-13 19:05:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (6, 2, 13, 9, 7, 8, 7, 5, 100, 56, 0, 100, 56, 0, 32.00, 60.00, 0, '2019-05-13 20:55:00', '2019-05-13 21:50:00')
INSERT INTO flight (flight_id, company_id, source_airport_id, source_city_id, source_country_id, destination_airport_id, destination_city_id, destination_country_id, total_economy_seats, total_business_seats, total_first_seats, available_economy_seats, available_business_seats, available_first_seats, economy_seat_price, business_seat_price, first_seat_price, departure_time, arrival_time) values (7, 2, 12, 9, 7, 7, 7, 5, 100, 56, 0, 100, 56, 0, 28.10, 46.50, 0, '2019-05-13 20:55:00', '2019-05-13 21:55:00')



INSERT INTO reservation (reservation_id, flight_id, user_name, user_surname, price, seats_type, seats_number, confirmed) values (1, 1, 'Mario', 'Rossi', 105.76, 'business', 2, 1)
INSERT INTO reservation (reservation_id, flight_id, user_name, user_surname, price, seats_type, seats_number, confirmed) values (2, 1, 'Elisa', 'Bianchi', 53.97, 'economy', 3, 1)
INSERT INTO reservation (reservation_id, flight_id, user_name, user_surname, price, seats_type, seats_number, confirmed) values (3, 4, 'Elisa', 'Bianchi', 29.18, 'economy', 1, 1)















