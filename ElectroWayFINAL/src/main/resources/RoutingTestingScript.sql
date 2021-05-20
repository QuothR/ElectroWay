/*
    Inainte de a executa scriptul.
    1) Inregistrati un nou user, folosind body-ul:
{
 "username": "cxdsasasffdsvddsd",
 "password": "ds5safdsas!Aa1",
 "firstName": "vcxdsfasafsdsdda23dsdzx",
 "lastName": "xczdafsasfssdsxc",
 "phoneNumber": "66dssasaffdsdsff232ddssdsasss4335",
 "emailAddress": "electroway@mailinator.com",
 "address1": "xzcdsdsaf324dssxzasdsac",
 "city": "cxzsafcx",
 "country": "Rdsafcxzcxz",
 "zipcode": "73sfad0",
 "roles": ["ROLE_DRIVER","ROLE_OWNER"]
}
    POST URL: https://localhost:443/register

    1.1) # Enable the user.
        UPDATE user SET is_enabled = 1 WHERE user_name = 'cxdsasasffdsvddsd';
    2) Va logati cu noul user.
    2.2) Daca user-ul pe care tocmai l ati inserat nu are rol de driver si owner, atunci executati in Heidi urmatoarele comenzi:

    INSERT INTO user_role VALUES(1, 2, 3);
    INSERT INTO user_role VALUES(2, 2, 2);

    3) Creati o noua masina folosind body-ul.
{
  "model": "Audi Q7 e-tron quattro",
  "year": 2015,
  "batteryCapacity": 17.3,
  "chargingCapacity": 7.2,
  "plugType": "Type 2",
  "vehicleMaxSpeed": 225,
  "auxiliaryKwh": 0.1
}
    Deocamdata ConstantSpeedConsumption-ul este hardocat in cod(in RoutingService -> getConstantSpeedConsumption(Long carId))
    si daca vreti sa-l modificati, modificati in cod.
*/

# De acum poti rula script-ul pentru populare.

INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(1,"Targu Frumos",47.22197,27.06514,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(2,"Vaslui",46.63387, 27.71967,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(3,"Barlad",46.22585, 27.66664,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(4,"Tecuci",45.85389, 27.42921,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(5,"Focsani",45.69681, 27.18202,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(6,"Brasov",45.67795, 25.63295,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(7,"Bacau",46.58017, 26.92384,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(8,"Galati",45.42035, 27.99313,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(9,"Braila",45.26242, 27.96711,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(10,"Tulcea",45.14825, 28.77186,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(11,"Buzau",45.15354, 26.8163,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(12,"Piatra Neamt",46.95267, 26.35487,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(13,"Onesti",46.26588, 26.76136,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(14,"Iasi",47.17005, 27.61742,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(15,"Iasi Tatarasi",47.15718, 27.60624,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(16,"Bucuresti Titan",44.4224, 26.16088,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(17,"Suceava",47.66773, 26.23916,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(18,"Botosani",47.75644, 26.65664,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(19,"Gura Humorului",47.57146, 25.83267,2);
INSERT INTO station(id,address,map_latitude_location,map_longitude_location,owner_id) VALUES(20,"Vatra Dornei",47.39327, 25.31631,2);

INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(1,1,1,"Type 1",0.1,7.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(2,1,2,"Type 1",0.3,8.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(3,1,2,"Type 2",0.4,7.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(4,1,1,"Type 2",0.2,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(5,1,1,"Type 1",0.4,5.8);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(6,1,1,"Type 2",0.5,7.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(7,1,2,"Type 1",0.5,5.4);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(8,1,2,"Type 2",0.2,2.9);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(9,1,2,"Type 2",0.4,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(10,1,2,"Type 1",0.7,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(11,1,3,"Type 1",0.33,6.0);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(12,1,3,"Type 2",0.24,6.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(13,1,3,"Type 1",0.39,4.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(14,1,3,"Type 1",0.80,4.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(15,1,3,"Type 2",0.24,6.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(16,1,3,"Type 1",0.39,4.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(17,1,3,"Type 1",0.37,6.0);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(18,1,3,"Type 2",0.24,6.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(19,1,3,"Type 1",0.39,4.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(20,1,3,"Type 1",0.60,6.0);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(21,1,3,"Type 2",0.24,6.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(22,1,3,"Type 1",0.39,4.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(23,1,2,"Type 2",0.70,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(24,1,2,"Type 1",0.70,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(25,1,3,"Type 1",0.90,6.0);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(26,1,3,"Type 2",0.24,6.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(27,1,3,"Type 1",0.39,4.5);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(28,1,2,"Type 2",0.8,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(29,1,2,"Type 1",0.7,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(30,1,2,"Type 2",0.4,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(31,1,2,"Type 1",0.7,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(32,1,2,"Type 2",0.4,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(33,1,2,"Type 1",0.7,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(34,1,1,"Type 1",0.7,3.8);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(35,1,1,"Type 2",1.47,4.7);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(36,1,2,"Type 2",0.4,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(37,1,2,"Type 1",0.7,5.1);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(38,1,2,"Type 2",0.4,6.2);
INSERT INTO charging_plug(id,status,level,connector_type,price_kw,charging_speed_kw) VALUES(39,1,2,"Type 1",0.7,5.1);

INSERT INTO charging_point(id,station_id) VALUES(1,1);
INSERT INTO charging_point(id,station_id) VALUES(2,2);
INSERT INTO charging_point(id,station_id) VALUES(3,3);
INSERT INTO charging_point(id,station_id) VALUES(4,4);
INSERT INTO charging_point(id,station_id) VALUES(5,5);
INSERT INTO charging_point(id,station_id) VALUES(6,6);
INSERT INTO charging_point(id,station_id) VALUES(7,7);
INSERT INTO charging_point(id,station_id) VALUES(8,8);
INSERT INTO charging_point(id,station_id) VALUES(9,9);
INSERT INTO charging_point(id,station_id) VALUES(10,10);
INSERT INTO charging_point(id,station_id) VALUES(11,11);
INSERT INTO charging_point(id,station_id) VALUES(12,12);
INSERT INTO charging_point(id,station_id) VALUES(13,13);
INSERT INTO charging_point(id,station_id) VALUES(14,14);
INSERT INTO charging_point(id,station_id) VALUES(15,15);
INSERT INTO charging_point(id,station_id) VALUES(16,16);
INSERT INTO charging_point(id,station_id) VALUES(17,17);
INSERT INTO charging_point(id,station_id) VALUES(18,18);
INSERT INTO charging_point(id,station_id) VALUES(19,19);
INSERT INTO charging_point(id,station_id) VALUES(20,20);

UPDATE charging_plug SET charging_point_id = 1 WHERE id = 1;
UPDATE charging_plug SET charging_point_id = 2 WHERE id = 2;
UPDATE charging_plug SET charging_point_id = 2 WHERE id = 3;
UPDATE charging_plug SET charging_point_id = 3 WHERE id = 4;
UPDATE charging_plug SET charging_point_id = 4 WHERE id = 5;
UPDATE charging_plug SET charging_point_id = 5 WHERE id = 6;
UPDATE charging_plug SET charging_point_id = 6 WHERE id = 7;
UPDATE charging_plug SET charging_point_id = 6 WHERE id = 8;
UPDATE charging_plug SET charging_point_id = 7 WHERE id = 9;
UPDATE charging_plug SET charging_point_id = 7 WHERE id = 10;
UPDATE charging_plug SET charging_point_id = 8 WHERE id = 11;
UPDATE charging_plug SET charging_point_id = 8 WHERE id = 12;
UPDATE charging_plug SET charging_point_id = 8 WHERE id = 13;
UPDATE charging_plug SET charging_point_id = 9 WHERE id = 14;
UPDATE charging_plug SET charging_point_id = 9 WHERE id = 15;
UPDATE charging_plug SET charging_point_id = 9 WHERE id = 16;
UPDATE charging_plug SET charging_point_id = 10 WHERE id = 17;
UPDATE charging_plug SET charging_point_id = 10 WHERE id = 18;
UPDATE charging_plug SET charging_point_id = 10 WHERE id = 19;
UPDATE charging_plug SET charging_point_id = 11 WHERE id = 20;
UPDATE charging_plug SET charging_point_id = 11 WHERE id = 21;
UPDATE charging_plug SET charging_point_id = 11 WHERE id = 22;
UPDATE charging_plug SET charging_point_id = 12 WHERE id = 23;
UPDATE charging_plug SET charging_point_id = 12 WHERE id = 24;
UPDATE charging_plug SET charging_point_id = 13 WHERE id = 25;
UPDATE charging_plug SET charging_point_id = 13 WHERE id = 26;
UPDATE charging_plug SET charging_point_id = 13 WHERE id = 27;
UPDATE charging_plug SET charging_point_id = 14 WHERE id = 28;
UPDATE charging_plug SET charging_point_id = 14 WHERE id = 29;
UPDATE charging_plug SET charging_point_id = 15 WHERE id = 30;
UPDATE charging_plug SET charging_point_id = 15 WHERE id = 31;
UPDATE charging_plug SET charging_point_id = 16 WHERE id = 32;
UPDATE charging_plug SET charging_point_id = 16 WHERE id = 33;
UPDATE charging_plug SET charging_point_id = 17 WHERE id = 34;
UPDATE charging_plug SET charging_point_id = 18 WHERE id = 35;
UPDATE charging_plug SET charging_point_id = 19 WHERE id = 36;
UPDATE charging_plug SET charging_point_id = 19 WHERE id = 37;
UPDATE charging_plug SET charging_point_id = 20 WHERE id = 38;
UPDATE charging_plug SET charging_point_id = 20 WHERE id = 39;



