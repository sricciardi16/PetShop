-- da root
CREATE USER 'pet_shop_users'@'localhost' IDENTIFIED BY 'petshop';
CREATE DATABASE petshop;
GRANT ALL PRIVILEGES ON petshop.* TO 'pet_shop_users'@'localhost';

-- da usercome importare
-- use petshop;
-- source petshop_database_export_20230713_first.sql;