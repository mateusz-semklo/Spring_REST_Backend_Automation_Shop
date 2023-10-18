DROP TABLE If EXISTS orders_products;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders;
DROP TABLE If EXISTS categories;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS MYSEQ;

CREATE SEQUENCE MYSEQ  AS INTEGER
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1000;


create table users(
                      username varchar(50) not null primary key,
                      password varchar(500) not null,
                      enabled boolean not null,
                      user_email varchar(50),
                      user_firstname varchar(50) not null,
                      user_lastname varchar(50) not null,
                      user_street varchar(50) not null,
                      user_city varchar(50) not null,
                      user_country varchar(50) not null,
                      user_post_code varchar(50) not null
);

create table authorities (
                             username varchar(50) not null,
                             authority varchar(100) not null,
                             constraint pk_authorities primary key (authority,username),
                             constraint fk_authorities_users foreign key(username) references users(username)
                                 ON DELETE CASCADE ON UPDATE CASCADE
);

create table categories (
                            category_id int not null primary key,
                            category_name varchar(50) not null
);

create table products (
                          product_id int not null primary key,
                          product_name varchar(100) not null,
                          product_description varchar(1000) not null,
                          product_image_url varchar(100) not null,
                          product_price int not null,
                          category_id int not null,
                          constraint fk_products_categories foreign key(category_id) references categories(category_id)
                              ON UPDATE CASCADE
);

create table orders (
                        order_id int not null primary key,
                        order_date date not null,
                        order_street varchar(50) not null,
                        order_city varchar(50) not null,
                        order_country varchar(50) not null,
                        order_post_code varchar(50) not null,
                        username varchar(50) not null,
                        constraint fk_orders_users foreign key(username) references users(username)
                            ON DELETE CASCADE ON UPDATE CASCADE
);

create table orders_products (
                                 order_id int not null,
                                 product_id int not null,
                                 constraint fk_ordersProducts_orders foreign key(order_id) references orders(order_id)
                                     ON DELETE CASCADE ON UPDATE CASCADE,
                                 constraint fk_ordersProducts_products foreign key(product_id) references products(product_id)
                                     ON DELETE CASCADE ON UPDATE CASCADE,
                                 constraint pk_orders_products PRIMARY KEY (order_id,product_id)
);

INSERT INTO users VALUES(
                            'admin',
                            '$2a$12$bv2/qngWql10qsYlB1jc3e9gIgNe6AZFfcLc/Debt5tXfePkZxkV6',
                            true,
                            'admin@wp.pl',
                            'mateusz',
                            'admin',
                            'Gowarzewo 2a/2',
                            'Swarzedz',
                            'Polska',
                            '60-002'
                        );

INSERT INTO users VALUES(
                            'mateusz2606',
                            '$2a$12$UaNun7I5F3ircUKB/d1Nz.saEKEn8.YuDVwQZMRJWCrr0k6LQgFX6',
                            true,
                            'mat.sem@wp.pl',
                            'mateusz',
                            'semklo',
                            'Gowarzewo 2a/2',
                            'Swarzedz',
                            'Polska',
                            '60-002'
                        );

INSERT INTO users VALUES(
                            'jankowalski',
                            '$2a$12$PCZ4Q3sWP21NOpnPRREEK.PunYhkydilvKQRZvqr/Qbcb9rroWKku',
                            true,
                            'kowal23@onet.pl',
                            'jan',
                            'kowalski',
                            'Obornicka 2a/2',
                            'Poznan',
                            'Polska',
                            '61-122'
                        );

INSERT INTO users VALUES(
                            'krzysztof1223',
                            '$2a$12$JuTvzKAUGkk7OQTkaBiwTel2nNvtmvOmMN9IJ2Sl9e.gG9PB8dHvm',
                            true,
                            'krzysztof345@wp.pl',
                            'krzysztof',
                            'kowal',
                            'Słoneczna 2',
                            'Międzychód',
                            'Polska',
                            '64-400'
                        );

INSERT INTO users VALUES(
                            'adrian1234',
                            '$2a$12$Hd6RtU5UYYs4x.K2FqjMMu5kzDWcR9r4W1SMw0f4JSa4/jlv6kyuO',
                            true,
                            'adrian1234@gmail.com',
                            'adrian',
                            'knych',
                            'Letnia 34',
                            'Międzychód',
                            'Polska',
                            '64-400'
                        );

INSERT INTO authorities (authority,username) VALUES (
                                                        'ROLE_ADMIN',
                                                        'admin'
                                                    );
INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_USER',
                                                        'admin'
                                                    );

INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_ADMIN',
                                                        'mateusz2606'
                                                    );
INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_USER',
                                                        'mateusz2606'
                                                    );

INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_USER',
                                                        'jankowalski'
                                                    );
INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_USER',
                                                        'krzysztof1223'
                                                    );
INSERT INTO authorities (authority,username) VALUES (

                                                        'ROLE_USER',
                                                        'adrian1234'
                                                    );

INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Sterowniki PLC'
                              );
INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Mikrokontrolery'
                              );
INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Czujniki'
                              );
INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Sieci przemysłowe'
                              );
INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Technika napędowa'
                              );
INSERT INTO categories VALUES (
                                  NEXT VALUE FOR MYSEQ,
                                  'Panele HMI'
                              );

select count(CATEGORY_ID) from categories;
set @TAB= ARRAY(SELECT CATEGORY_ID from categories);

INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik indukcyjny M.D. Micro Detectors AM6/AP-2H',
                                                                                                                          'Czujnik indukcyjny M12x1, Zasięg działania: 4 mm [czoło wysunięte], Napięcie zasilania: 10-30 V DC, Funkcja wyjścia: NO PNP, Stopień ochrony: IP67, Częstotliwość przełączania: 2 000 Hz, Połączenie: Złącze męskie M12',
                                                                                                                          '\Products\Czujniki\105947.png',
                                                                                                                          64.80,
                                                                                                                          @TAB[1]
                                                                                                                      );

INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik indukcyjny ifm electronic IM5135 - IMC4040-CPKG/K1/US',
                                                                                                                          'Czujnik indukcyjny prostopadłościenny, Zasięg działania: 40 mm [czoło wysunięte], Wyjście komplementarne, DC PNP, Współczynnik korekcji 1 dla wszystkich metali, Stopień ochrony: IP67/IP69K, Powierzchnia aktywna ustawialna w 5 pozycjach, Złącze M12.',
                                                                                                                          '\Products\Czujniki\110420.png',
                                                                                                                          206.10,
                                                                                                                          @TAB[1]
                                                                                                                      );

INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik indukcyjny M.D. Micro Detectors AE6/AP-3A',
                                                                                                                          'Czujnik indukcyjny M8x1, Zasięg działania: 2 mm [czoło zabudowane], Napięcie robocze: 10-30 V DC, NO PNP, Stopień ochrony: IP67, Częstotliwość przełączania: 5 000 Hz, Połączenie: kabel PVC 2 m',
                                                                                                                          '\Products\Czujniki\110545.png',
                                                                                                                          87.30,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik dyfuzyjny M.D. Micro Detectors FQI7/BP-0E',
                                                                                                                          'Czujnik dyfuzyjny, 1x NC/NO, DC PNP, Zasięg działania: 400 mm, Potencjometr, Stopień ochrony: IP67, Złącze M12',
                                                                                                                          '\Products\Czujniki\110703.png',
                                                                                                                          140.85,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik dyfuzyjny ifm electronic O6H202 - O6H-FPKG/AS/3P',
                                                                                                                          'Czujnik dyfuzyjny z tłumieniem tła, Tryby światło-włącz / ciemno-włącz, 3-przewodowy DC, PNP, Zasięg: 2-200 mm, Stopień ochrony: IP65/IP67, Złącze M8 3-biegunowe',
                                                                                                                          '\Products\Czujniki\110748.png',
                                                                                                                          356.40,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik dyfuzyjny ifm electronic O4H500 - O4H-FPKG/US',
                                                                                                                          'Czujnik dyfuzyjny z tłumieniem tła, Tryby światło-włącz / ciemno-włącz programowane, DC PNP, Zasięg: 0,1-2,6 m, Stopień ochrony: IP67, Złącze M12',
                                                                                                                          '/products/czujniki/110856.png',
                                                                                                                          711.00,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Dalmierz laserowy ifm electronic O1D100 - O1DLF3KG',
                                                                                                                          'Dalmierz laserowy, Wyjście1: NO/NC programowane, Wyjście2: NO/NC programowane lub analogowe (4-20 mA/0-10 V; skalowane), IO-Link, DC PNP, Zasięg: 0,2-10 m, 4-znakowy wyświetlacz alfanumeryczny, Stopień ochrony: IP67, Złącze M12',
                                                                                                                          '/products/czujniki/110927.png',
                                                                                                                          1371.60,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Elektroniczny czujnik ciśnienia z funkcją przełączania ifm electronic PK6522',
                                                                                                                          'Elektroniczny czujnik ciśnienia z funkcją przełączania, Zakres ciśnienia, 0-100 bar, NO/NC komplementarne, DC PNP, Przyłącze procesowe: G 1/4, Stopień ochrony: IP67, Złącze M12',
                                                                                                                          '/products/czujniki/111034.png',
                                                                                                                          670.90,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Elektroniczny czujnik ciśnienia ifm electronic PN7094',
                                                                                                                          'Elektroniczny czujnik ciśnienia z wyświetlaczem, -1-10 bar, 2x NC/NO programowane, DC PNP/NPN, IO-Link, Przyłącze procesowe: G 1/4 żeńskie, Stopień ochrony: IP67, Złącze M12',
                                                                                                                          '/products/czujniki/111100.png',
                                                                                                                          1281.60,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Sygnalizator przepływu ifm electronic SI5000',
                                                                                                                          'Sygnalizator przepływu, NC/NO programowane, DC PNP, Do cieczy i gazów, Zakres działania 3-300/200-3000 cm/s, Przyłącze procesowe: gwint wewnętrzny M18 x 1,5 do adaptera, Ciśnienie dopuszczalne: 30 bar, Złącze M12',
                                                                                                                          '/products/czujniki/111217.png',
                                                                                                                          1140.20,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Czujnik przepływu ifm electronic SA5000',
                                                                                                                          'Czujnik przepływu, Wyjście 1: NO/NC programowane lub częstotliwość lub IO-Link, Wyjście 2: NO/NC programowane lub częstotliwość lub analogowe (4-20 mA/ skalowane) DC PNP/NPN, Zakres regulacji: 0-6 m/s (media ciekłe) lub 0-200 m/s (media gazowe), Przyłącze procesowe: gwint wewnętrzny M18 x 1,5 do adaptera, Ciśnienie dopuszczalne: 100 bar, Stopień ochrony: IP65/IP67, Złącze M12',
                                                                                                                          '/products/czujniki/111244.png',
                                                                                                                          1140.20,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Enkoder inkrementalny ifm electronic RVP510',
                                                                                                                          'Enkoder inkrementalny, Wał Ø10 mm, System detekcji magnetycznej, Rozdzielczość: 1-10000 (ustawienie fabryczne 1024), Wyjście HTL, TTL 50 mA, IO-Link 1.1, Wyświetlacz, Opcje programowania: rozdzielczość/kierunek obrotów/HTL/TTL, Monitorowanie prędkości, Licznik, Stopień ochrony: IP65/IP67 (od strony obudowy)/IP64 (od strony wału), Złącze M12 (8-biegunowe)',
                                                                                                                          '/products/czujniki/111404.png',
                                                                                                                          987.90,
                                                                                                                          @TAB[1]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens CPU 1215C - 6ES7215-1AG40-0XB0',
                                                                                                                          'SIMATIC S7-1200 1215C, Kompaktowy CPU, DC/DC/DC, 2 porty PROFINET, Wejścia: 14 wejść cyfrowych 24 V DC; 2 wejścia analogowe 0-10 V, Wyjścia: 10 wyjść cyfrowych 24 V DC; 0,5 A; 2 wyjścia analogowe 0-20 mA, Napięcie zasilania: 20,4-28,8 V DC, Pamięć programu/danych: 125 kB',
                                                                                                                          '/products/sterowniki_PLC/120500.png',
                                                                                                                          2592.00,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens CPU 1214C - 6ES7214-1AG40-0XB0',
                                                                                                                          'SIMATIC S7-1200 1214C, Kompaktowy CPU, DC/DC/DC, Port PROFINET, Wejścia: 14 wejść cyfrowych 24 V DC; 2 wejścia analogowe 0-10 V, Wyjścia: 10 wyjść cyfrowych 24 V DC; 0,5 A; Napięcie zasilania: 20,4-28,8 V DC, Pamięć programu/danych: 100 kB',
                                                                                                                          '/products/sterowniki_PLC/120540.png',
                                                                                                                          1721.25,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SM 1226 - 6ES7226-6BA32-0XB0',
                                                                                                                          'SIMATIC S7-1200F, Moduł wejść cyfrowych SM 1226 F-DI 16X DC 24V, 16 wejść cyfrowych, Napięcie zasilania: 24 V DC, Napięcie wejściowe: -30-5 V DC (Sygnał 0)/15-30 V DC (Sygnał 1), Natężenie na wejściu: 0,5 mA, Stopień ochrony: IP20, Kategoris 3 PL d (1-kanałowe); Kategoria 3 lub 4 PL e (2-kanałowe), SIL 2 (1-kanałowe); SIL 3 (2-kanałowe)',
                                                                                                                          '/products/sterowniki_PLC/120614.png',
                                                                                                                          845.55,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SM 1223 DC/RLY - 6ES7223-1PH32-0XB0',
                                                                                                                          'SIMATIC S7-1200, Moduł wejść/wyjść SM 1223, 8 wejść cyfrowych 24 V DC, 8 wyjść cyfrowych, Przekaźnikowe 2 A',
                                                                                                                          '/products/sterowniki_PLC/120648.png',
                                                                                                                          789.75,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SM 1223 DC/DC - 6ES7223-1BL32-0XB0',
                                                                                                                          'SIMATIC S7-1200, Moduł wejść/wyjść SM 1223, 16 wejść cyfrowych 24 V DC, 16 wyjść cyfrowych, Tranzystorowe 0,5 A',
                                                                                                                          '/products/sterowniki_PLC/120749.png',
                                                                                                                          1240.40,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Zasilacz Siemens POWER MODUL PM1207 - 6EP1332-1SH71',
                                                                                                                          'SIMATIC S7-1200 moduł zasilacza PM1207, Zasilacz stabilizowany, Wejście: 120/230 V AC, Wyjście: 24 V DC / 2,5 A',
                                                                                                                          '/products/sterowniki_PLC/120750.png',
                                                                                                                          372.40,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SIMATIC DP CPU 1510SP F-1 PN - 6ES7510-1SJ01-0AB0',
                                                                                                                          'SIMATIC DP, CPU 1510SP F-1 PN dla ET 200SP, Jednostka centralna, PL/kategoria zgodna z ISO13849-1 : PL e/kat. 4, SIL zgodnie z IEC 61508: SIL 3, Interfejs: PROFINET IRT z 3-portowym przełącznikiem, Zasilanie: 24 V DC (19,2-28,8 V DC), Prąd przełączania maks.: 4,7 A, Pamięć robocza: 150 KB dla programu; 750 KB dla danych, Wydajność bitowa 72 NS, Wymagana karta pamięci SIMATIC i adapter magistrali dla portu 1 i 2',
                                                                                                                          '/products/sterowniki_PLC/120830.png',
                                                                                                                          3690.10,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SIMATIC ET 200SP DI 8x 24VDC ST - 6ES7131-6BF01-0BA0',
                                                                                                                          'SIMATIC ET 200SP, DI 8x 24V DC ST, Moduł wejść cyfrowych, 8 wejść cyfrowych, 24 V DC, Typ 3 (IEC 61131), Logika Sink (PNP, odczyt P), Wbudowana diagnostyka: Zwarcie/Zasilanie urządzeń/Rozwarcie obwodu/Napięcie zasilania, Opóźnienie wejścia 0,05-20 ms, Kod koloru: CC01, Do podstawek typu A0',
                                                                                                                          '/products/sterowniki_PLC/120906.png',
                                                                                                                          219.10,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SIMATIC ET 200SP AQ 2xI ST - 6ES7135-6GB00-0BA1',
                                                                                                                          'SIMATIC ET 200SP, AQ 2xI ST, Moduł wyjść analogowych, Wyjścia analogowe: 2 (Prądowe), 24 V DC, Diagnostyka modułu, 16 Bit, Kod koloru: CC00, Do podstawek typu A0 oraz A1',
                                                                                                                          '/products/sterowniki_PLC/120943.png',
                                                                                                                          714.15,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SIMATIC ET 200SP PS 24V/10A - 6EP7133-6AE00-0BN0',
                                                                                                                          'SIMATIC ET 200SP PS 24V/10A, Zasilacz regulowany, Wejście: 120/230 V AC, Wyjście: 24 V DC/10 A',
                                                                                                                          '/products/sterowniki_PLC/121014.png',
                                                                                                                          944.15,
                                                                                                                          @TAB[2]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Niezarządzalny Switch Ethernet TERZ NITE-RF5-1100 - 111400',
                                                                                                                          'Niezarządzalny switch Industrial Ethernet, Fast Ethernet 10/100 Mbit/s, Porty: 5 x RJ45, Napięcie wejściowe: 8-28 V AC/9-36 V DC, Temperatura pracy: -40-70 °C, Stopień ochrony: IP30, Materiał obudowy: Aluminium anodowane/Stal nierdzewna',
                                                                                                                          '/products/sterowniki_PLC/124450.png',
                                                                                                                          309.50,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Niezarządzalny Switch Ethernet TERZ NITE-RS5-1100 - 211400',
                                                                                                                          'Niezarządzalny switch Industrial Ethernet, Fast Ethernet 10/100 Mbit/s, Porty: 5 x RJ45, Napięcie wejściowe: 8-28 V AC/9-36 V DC, Temperatura pracy: -40-70 °C, Stopień ochrony: IP30, Materiał obudowy: Aluminium anodowane/Stal nierdzewna',
                                                                                                                          '/products/sterowniki_PLC/124523.png',
                                                                                                                          349.50,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Niezarządzalny Switch Ethernet TERZ NITE-RS16-1100 - 211800',
                                                                                                                          'Niezarządzalny switch Industrial Ethernet, Fast Ethernet 10/100 Mbit/s, Porty: 16 x RJ45, Konstrukcja: wąska, Zakres napięcia: 8-28 V AC/9-36 V DC, Temperatura pracy: -40-70 °C, Stopień ochrony: IP30, Materiał obudowy: Aluminium anodowane/Stal nierdzewna',
                                                                                                                          '/products/sieci_przemyslowe/124551.png',
                                                                                                                          895.90,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Zarządzalny Switch PROFINET 4-portowy Helmholz 700-850-4PS01',
                                                                                                                          'Zarządzalny Switch PROFINET, Protokół: PROFINET IO zgodny z IEC 61158-6-10, Fast Ethernet 10/100 Mbit/s, 4 porty RJ45, Napięcie zasilania: 24 V DC (18-30 V DC), Temperatura pracy: -40-65 °C, Stopień ochrony: IP20, W zestawie Instrukcja Quick Start Guide oraz CD z danymi GSDML',
                                                                                                                          '/products/sieci_przemyslowe/124622.png',
                                                                                                                          1395.90,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Zarządzalny Switch PROFINET 8-portowy Helmholz 700-850-8PS01',
                                                                                                                          'Zarządzalny Switch PROFINET, Protokół:PROFINET IO zgodny z IEC 61158-6-10, Fast Ethernet 10/100 Mbit/s, 8 portów RJ45, Napięcie zasilania: 24 V DC (18-30 V DC), Temperatura pracy: -40-65 °C, Stopień ochrony: IP20, W zestawie Instrukcja Quick Start Guide oraz CD z danymi GSDML',
                                                                                                                          '/products/sieci_przemyslowe/124712.png',
                                                                                                                          1895.90,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'IP65/IP67 Niezarządzalny Switch Ethernet TERZ SPARK-XS41Q-31010 - 431410210',
                                                                                                                          'Niezarządzalny switch Industrial Ethernet, 4 porty M12 kodowanie D (PoE) Fast Ethernet 10/100 Mbit/s , 1 port M12 kodowanie X Full Gigabit Ethernet 10/100/1000 Mbit/s, Napięcie wejściowe: 16,8-30 V DC, Temperatura pracy: -40-70 °C, Stopień ochrony: IP65/IP67, Materiał obudowy: Niklowane aluminium/stal nierdzewna, Otwory M6 do montazu na ścianie',
                                                                                                                          '/products/sieci_przemyslowe/124752.png',
                                                                                                                          2795.30,
                                                                                                                          @TAB[3]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przemiennik częstotliwości Siemens SINAMICS V20 - 6SL3210-5BB15-5BV1',
                                                                                                                          'SINAMICS V20 -1AC, 200-240 V AC (-15% +10%), Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 0,55 kW, Interfejs I/O: 4 DI/2 DO/2 AI/1 AO, Stopień ochrony: IP20, Wielkość: FSAB, Komunikacja: USS/MODBUS RTU, Wbudowany panel BOP, Wbudowany filtr',
                                                                                                                          '/products/technika_napedowa/125753.png',
                                                                                                                          795.30,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przemiennik częstotliwości Siemens SINAMICS V20 - 6SL3210-5BE27-5CV0',
                                                                                                                          'SINAMICS V20 - 3 AC, 380-480 V AC (-15% +10%), Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 7,5 kW, Interfejs I/O: 4 DI/2 DO/2 AI/1 AO, Stopień ochrony: IP20, Wielkość: FSD, Komunikacja: USS/MODBUS RTU, Wbudowany panel BOP, Wbudowany filtr',
                                                                                                                          '/products/technika_napedowa/125823.png',
                                                                                                                          3195.30,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przekształtnik częstotliwości Siemens SINAMICS G120C - 6SL3210-1KE15-8AF2',
                                                                                                                          'SINAMICS G120C, 3 AC, Napięcie sieci: 380-480 V AC (+10/-20 %), Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 2,2 kW, Przeciązalność 150% przez 3 s, 6 x DI/2 x DO, 1 x AI/1 x AO, Rozmiar: FSAA, Komunikacja: PROFINET-PN/Ethernet IP, Safe Torque Off (STO), Zintegrowany filtr EMC kategorii A, Stopień ochrony: IP20',
                                                                                                                          '/products/technika_napedowa/125903.png',
                                                                                                                          2995.10,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przekształtnik częstotliwości Siemens SINAMICS G120C - 6SL3210-1KE22-6AF1',
                                                                                                                          'SINAMICS G120C, 3 AC, Napięcie sieci: 380-480 V AC (+10/-20 %), Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 11 kW, Przeciązalność 150% przez 3 s, 6 x DI/2 x DO, 1 x AI/1 x AO, Rozmiar: FSC, Komunikacja: PROFINET-PN/Ethernet IP, Safe Torque Off (STO), Zintegrowany filtr EMC kategorii A, Stopień ochrony: IP20',
                                                                                                                          '/products/technika_napedowa/125933.png',
                                                                                                                          6998.40,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przekształtnik częstotliwości Siemens SINAMICS G120X - 6SL3220-3YE18-0AF0',
                                                                                                                          'SINAMICS G120X, 3 AC, 380-480 V AC (+10/-20 %) Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 3 kW (110 % 60 s; 100 % 240 s), 6 x DI/2 x DO (Przekaźnikowe), 2 x AI (0-10 V; 0/4-20 mA)/1 x AO (0-10 V; 0/4-20 mA), Rozmiar: FSA, Komunikacja: PROFINET-PN/Ethernet IP, Panel operatorski IOP-2, Kategoria EMC: C2, Stopień ochrony IP20',
                                                                                                                          '/products/technika_napedowa/130001.png',
                                                                                                                          4289.40,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Przekształtnik częstotliwości Siemens SINAMICS G120X - 6SL3220-3YE26-0AF0',
                                                                                                                          'SINAMICS G120X, 3 AC, 380-480 V AC (+10/-20 %) Częstotliwość sieci: 47-63 Hz, Moc znamionowa: 11 kW (110 % 60 s; 100 % 240 s), 6 x DI/2 x DO (Przekaźnikowe), 2 x AI (0-10 V; 0/4-20 mA)/1 x AO (0-10 V; 0/4-20 mA), Rozmiar: FSC, Komunikacja: PROFINET-PN/Ethernet IP, Panel operatorski IOP-2, Kategoria EMC: C2, Stopień ochrony IP20',
                                                                                                                          '/products/technika_napedowa/130029.png',
                                                                                                                          7389.40,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Softstart Siemens SIRIUS 3RW3027-1BB04',
                                                                                                                          'Softstart, Układ łagodnego rozruchu, Wielkość: S0, 32 A, 15 kW/ 400 V, 200-480 V AC, Napięcie sterujące: 24 V AC/DC, Zaciski śrubowe',
                                                                                                                          '/products/technika_napedowa/130107.png',
                                                                                                                          1534.40,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Rozrusznik silnikowy z regulacją prędkości Eaton 174335 - DE1-343D6FN-N20N',
                                                                                                                          'Rozrusznik silnikowy z regulacją prędkości, Połączenie zasilania 3-fazowe, Połączenie silnika 3-fazowe 400 V AC, 3,6 A/ 1,5kW, Zintegrowany filtr EMC, Zaciski śrubowe',
                                                                                                                          '/products/technika_napedowa/130200.png',
                                                                                                                          1244.90,
                                                                                                                          @TAB[4]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Panel podstawowy SIMATIC Siemens KP300 Basic mono PN - 6AV6647-0AH11-3AX1',
                                                                                                                          'SIMATIC HMI KP300 BASIC MONO PN, Panbel Basic, Obsługa przyciskami, Wyświetlacz LCD 3" FSTN, monochromatyczny, Interfejs PROFINET, Możliwość konfiguracji w WinCC BASIC V11/ STEP7 BASIC V11, Zawiera darmowe oprogramowanie open source',
                                                                                                                          '/products/panele_HMI/131638.png',
                                                                                                                          982.90,
                                                                                                                          @TAB[5]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Panel operatorski SIMATIC Siemens KTP700 Basic PN - 6AV2123-2GB03-0AX0',
                                                                                                                          'SIMATIC HMI KTP700 BASIC, Panel operatorski, Ekran dotykowy/ przyciski funkcyjne, Wyświetlacz 7" TFT, 65536 kolorów, Interfejs: PROFINET, Konfiguracja przez WinCC BASIC V13/ STEP7 BASIC V13, Zawiera darmowe oprogramowanie Open Source, W zestawie dysk CD',
                                                                                                                          '/products/panele_HMI/131708.png',
                                                                                                                          2882.90,
                                                                                                                          @TAB[5]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Panel SIMATIC Comfort Siemens TP900 Comfort - 6AV2124-0JC01-0AX0',
                                                                                                                          'SIMATIC HMI TP900 Comfort, Panel Comfort, Dotykowy, Wyświetlacz 9" TFT (Widescreen), 16 mln kolorów, Interfejs PROFINET, Interfejs MPI/PROFIBUS-DP, Pamięć na projekt: 12 MB, Windows CE 6.0 (Wsparcie Microsoft z aktualizacją zabezpieczeń), Konfiguracja za pomocą WinCC Comfort V11',
                                                                                                                          '/products/panele_HMI/131739.png',
                                                                                                                          29252.90,
                                                                                                                          @TAB[5]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Siemens SIMATIC HMI MTP700 Unified Comfort Panel - 6AV2128-3GB06-0AX1',
                                                                                                                          'Panel SIMATIC HMI MTP700 Unified Comfort, Dotykowy wyświetlacz, Wyświetlacz TFT 7" (panoramiczny), 16 mln kolorów, Interfejsy: 4 x USB 3.1/1 x RS-485 (możliwy RS-422)/PROFINET, Konfigurowalny z WinCC Unified Comfort V16, Zawiera oprogramowanie Open Source (DVD), W zestawie materiał montażowy',
                                                                                                                          '/products/panele_HMI/131807.png',
                                                                                                                          5567.60,
                                                                                                                          @TAB[5]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'Panel HMI Siemens SIMATIC KTP900F Mobile - 6AV2125-2JB23-0AX0',
                                                                                                                          'SIMATIC HMI KTP900F Mobile z wbudowanym wyłącznikiem awaryjnym i przełącznikiem zezwalającym, Panel HMI, PL zgodny z ISO13849-1: PL e, SIL zgodny z IEC 61508: SIL 3, Wyświetlacz: 9" TFT, Rozmiar wyświetlacza: 195 x 117 mm, Kolory: 16 777 216, Rozdzielczość: 800 x 480 px, Obsługa: ekran dotykowy/przyciski, Z wyłącznikiem awaryjnym i przełącznikiem zezwalającym, Interfejsy: PROFINET / PROFIsafe / Ethernet / USB 2.0, Możliwość ściemniania podświetlenia LED, Możliwość konfiguracji od WinCC Comfort V13',
                                                                                                                          '/products/panele_HMI/131833.png',
                                                                                                                          15609.60,
                                                                                                                          @TAB[5]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32F429I-DISC1 - Discovery - STM32F429IDISCOVERY + ekran dotykowy 2,4',
                                                                                                                          'Zestaw uruchomieniowy oparty na mikrokontrolerze STM32F429ZIT6 (180 MHz, 2 MB Flash, 256 kB RAM, rdzeń Cortex-M4F ARM). Na płytce także 3-osiowy żyroskop , ekran dotykowy LCD 2.4" QVGA TFT oraz programator / debugger ST-LINK/V2-B.',
                                                                                                                          '/products/mikrokontrolery/132945.png',
                                                                                                                          215.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32F769I-Disco Discovery STM32F769NIH6 - Cortex M7',
                                                                                                                          'Zestaw uruchomieniowy STM32F769I Discovery z mikrokontrolerem STM32F769NIH6 pozwala zapoznać się z układami wyposażonymi w potężny rdzeń Cortex M7 firmy ARM. Układ posiada m. in. wbudowany wyświetlacz dotykowy LCD 4", złącze USB, port Ethernet oraz gniazdo na kartę microSD. Dodatkową funkcją jest możliwość zasilania z sieci Ethernet poprzez układ PoE 3 W 48 V na 5 V.',
                                                                                                                          '/products/mikrokontrolery/133108.png',
                                                                                                                          579.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32 NUCLEO-L476RG - z MCU STM32L476RGT6 ARM Cortex M4',
                                                                                                                          'Moduł Nucleo z 32-bitowym mikrokontrolerem STM32L476RG, który wyposażony jest w rdzeń ARM Cortex M4 o taktowaniu 80 MHz, pamięci Flash 1 MB, 128 kB pamięci SRAM. Nucleo jest kompatybilny z platformą mbed, posiada złącza umożliwiające podłączenie nakładek Arduino Shield. Współpracuje z większością popularnych platform programistycznych, w tym Keil, IAR oraz środowiskami opartymi na kompilatorze GCC. Urządzenie wyposażone zostalo w debugger / programator ST-Link.',
                                                                                                                          '/products/mikrokontrolery/133214.png',
                                                                                                                          99.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32 NUCLEO-F413ZH - STM32F413ZHT6 ARM Cortex M4',
                                                                                                                          'Moduł z 32-bitowym mikrokontrolerem wyposażonym w rdzeń ARM Cortex M4, 100 MHz, 1,5 MB Flash, 320 kB SRAM. Nucleo jest kompatybilny z platformą mbed, posiada złącza umożliwiające podłączenie nakładek Arduino tzw. Arduino Shield.',
                                                                                                                          '/products/mikrokontrolery/133315.png',
                                                                                                                          199.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32F411E-Disco - Discovery - STM32F411E Discovery',
                                                                                                                          'Zestaw uruchomieniowy oparty na mikrokontrolerze STM32F411VET6 pozwala zapoznać się z układami wyposażonymi w najnowszy rdzeń Cortex-M4F firmy ARM. Na płytce także 3-osiowy akcelerometr i żyroskop, narzędzia audio: mikrofon i DAC oraz programator/debugger ST-LINK/V2',
                                                                                                                          '/products/mikrokontrolery/133405.png',
                                                                                                                          156.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32L0 - Discovery - LoRa B-L072Z-LRWAN1',
                                                                                                                          'Zestaw uruchomieniowy z modułem LoRa CMWX1ZZABZ-091 (Murata) oparty na mikrokontrolerze STM32L072CZ pozwala zapoznać się z układami wyposażonymi w rdzeń Cortex-M0+ firmy ARM. Na płytce także programator/debuger ST-LINK/V2, diody LED, przyciski, antena, złącze Arduino UNO i złącze USB OTG w formacie micro USB - B.',
                                                                                                                          '/products/mikrokontrolery/133450.png',
                                                                                                                          356.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32MP157F-DK2 Discovery - STM32MP157FAC1 + ekran dotykowy 4',
                                                                                                                          'Zestaw uruchomieniowy oparty na mikrokontrolerze STM32MP157FAC1 (Cortex-A7 800 MHz 32-bit + Cortex-M4 209 MHz 32-bit). Płytka posiada między innymi wbudowany wyświetlacz dotykowy o rozdzielczości 4", 4 porty USB, port Ethernet oraz slot na kartę microSD..',
                                                                                                                          '/products/mikrokontrolery/133526.png',
                                                                                                                          768.00,
                                                                                                                          @TAB[6]
                                                                                                                      );
INSERT INTO products (product_id,product_name,product_description,product_image_url,product_price,category_id) VALUES (
                                                                                                                          NEXT VALUE FOR MYSEQ,
                                                                                                                          'STM32 NUCLEO-32 L031K6 - z MCU STM32L031K6 - kompatybilny z Arduino Nano',
                                                                                                                          'Płytka Nucleo z 32-bitowym rdzeniem ARM Cortex M0+ o taktowaniu 32 MHz. Nucleo współpracuje z większością popularnych platform programistycznych, do których należą m.in. Keil, IAR, oraz ze środowiskami opartymi na kompilatorze GCC. Rozkład złącz jest kompatybilny z Arduino Nano, dzięki czemu funkcjonalność platformy zostaje rozszerzona. Nucleo-32 nie wymaga żadnej oddzielnej sondy, ponieważ integruje debugger / programator ST-LINK.',
                                                                                                                          '/products/mikrokontrolery/133641.png',
                                                                                                                          89.00,
                                                                                                                          @TAB[6]
                                                                                                                      );

INSERT INTO orders VALUES (
                              NEXT VALUE FOR MYSEQ,
                              current_date,
                              'letnia 34',
                              'Poznan',
                              'POLAND',
                              '64-100',
                              'mateusz2606'
                          );

INSERT INTO orders VALUES (
                              NEXT VALUE FOR MYSEQ,
                              current_date,
                              'letnia 34',
                              'Poznan',
                              'POLAND',
                              '64-100',
                              'mateusz2606'
                          );

INSERT INTO orders VALUES (
                              NEXT VALUE FOR MYSEQ,
                              current_date,
                              'zimowa 34',
                              'Szczecin',
                              'POLAND',
                              '63-200',
                              'jankowalski'
                          );

SET @ORD=SELECT TOP 1ORDER_ID FROM ORDERS;
SET @PRD=SELECT TOP 1 PRODUCT_ID FROM PRODUCTS;
SELECT @ORD;
SELECT @PRD;
INSERT INTO ORDERS_PRODUCTS(
    SELECT @ORD,
    SELECT @PRD
);













