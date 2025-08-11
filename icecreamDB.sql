		-- phpMyAdmin SQL Dump
		-- version 5.2.1
		-- https://www.phpmyadmin.net/
		--
		-- Host: 127.0.0.1
		-- Generation Time: May 20, 2025 at 11:11 AM
		-- Server version: 10.4.32-MariaDB
		-- PHP Version: 8.0.30

		SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
		START TRANSACTION;
		SET time_zone = "+07:00";

		--
		--
		-- Tạo database
		DROP DATABASE IF EXISTS icecream_website_db;
		CREATE DATABASE icecream_website_db;
		USE icecream_website_db;

		-- --------------------------------------------------------

		--
		-- Table structure for table `authorities`
		--

		CREATE TABLE `authorities` (
		  `username` varchar(50) NOT NULL,
		  `authority` varchar(50) NOT NULL,
		  `id` bigint(20) NOT NULL
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

		--
		-- Dumping data for table `authorities`
		--

		INSERT INTO `authorities` (`username`, `authority`, `id`) VALUES
		('admin', 'ROLE_ADMIN', 1),
		('admin', 'ROLE_USER', 2),
		('system', 'ROLE_ADMIN', 3),
		('system', 'ROLE_SYSTEM', 4),
		('system', 'ROLE_USER', 5),
		('customer', 'ROLE_USER', 6);

ALTER TABLE authorities
MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;

		-- --------------------------------------------------------

		-- Table structure for table `customer`
		--

		CREATE TABLE `customer` (
		  `id` int(11) NOT NULL,
		  `user_id` int(11) NOT NULL,
		  `first_name` varchar(100) NOT NULL,
		  `last_name` varchar(100) NOT NULL
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

		--
		-- Dumping data for table `customer`
		--

		INSERT INTO `customer` (`id`, `user_id`, `first_name`, `last_name`) VALUES
		(1, 4, 'Minh', 'Trần'),
		(2, 5, 'Hoàng', 'Nguyễn'),
		(3, 6, 'Thoại', 'Phạn'),
		(4, 7, 'Sâm', 'Ngô');


ALTER TABLE customer MODIFY COLUMN id INT AUTO_INCREMENT PRIMARY KEY;

		-- --------------------------------------------------------

		--
		-- Table structure for table `product`
		--

	   CREATE TABLE `product` (
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `image_url` varchar(255) DEFAULT NULL,
		  `name` varchar(100) NOT NULL,
		  `category` varchar(50) NOT NULL,
		  `description` varchar(255) DEFAULT NULL,
		  `price` decimal(10,2) NOT NULL,
		  `content` text,  -- Bỏ DEFAULT NULL ở đây
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
		--
		-- Dumping data for table `product`
		--
		
		DELETE FROM product;

		
	INSERT INTO `product` (`id`, `image_url`, `name`, `category`, `description`, `price`, `content`) VALUES
	-- Menu Kem
	(1, '/images/Super-Sundae-Socola-Mixue.jpg', 'Kem Super Sundae Socola', 'Kem', 'Kem socola mát lạnh', 25000.00, 'Kem socola đậm đà, ngọt ngào'),
	(2, '/images/Super-Sundae-Xoai-Mixue.jpg', 'Kem Super Sundae Xoài', 'Kem', 'Kem xoài tươi mát', 25000.00, 'Hương vị xoài thơm ngon, mịn màng'),
	(3, '/images/Super-Sundae-Dau-Mixue.jpg', 'Kem Super Sundae Dâu', 'Kem', 'Kem dâu tây tươi mát', 25000.00, 'Hương vị dâu tây tự nhiên, ngọt dịu'),
	(4, '/images/Super-Sundae-Vi-Chocolate-Den-Mixue.jpg', 'Super Sundae Vị Chocolate Đen Mixue', 'Kem', 'Kem sô-cô-la đen đậm vị', 25000.00, 'Sô-cô-la đen nguyên chất, mịn màng'),
	(5, '/images/Oc-Que-Mixue.jpg', 'Kem Ốc Quế Mixue', 'Kem', 'Kem ốc quế truyền thống', 10000.00, 'Kem vani mềm mịn, ốc quế giòn thơm'),
	(6, '/images/Sua-Kem-Lac-Mixue.jpg', 'Kem Sữa Tươi Lắc', 'Kem', 'Kem sữa tươi lắc truyền thống', 25000.00, 'Kem sữa tươi béo ngậy, thơm ngon'),
	(7, '/images/Sua-Kem-Lac-Socola-Mixue.jpg', 'Kem Sữa Tươi Lắc Socola', 'Kem', 'Kem sữa tươi lắc vị socola', 25000.00, 'Kem sữa tươi kết hợp socola đậm đà'),
	(8, '/images/Sua-Kem-Lac-Xoai-Mixue.jpg', 'Kem Sữa Tươi Lắc Xoài', 'Kem', 'Kem sữa tươi lắc vị xoài', 25000.00, 'Kem sữa tươi kết hợp xoài tươi mát'),
	(9, '/images/Sua-Kem-Lac-Dau-Mixue.jpg', 'Kem Sữa Tươi Lắc Dâu', 'Kem', 'Kem sữa tươi lắc vị dâu', 25000.00, 'Kem sữa tươi kết hợp dâu tây ngọt dịu'),
	(10, '/images/Sua-Kem-Lac-Dao-Mixue.jpg', 'Kem Sữa Tươi Lắc Đào', 'Kem', 'Kem sữa tươi lắc vị đào', 25000.00, 'Kem sữa tươi kết hợp đào thơm mát'),
	(11, '/images/Sua-Kem-Lac-Viet-Quat-Mixue.jpg', 'Kem Sữa Tươi Lắc Việt Quất', 'Kem', 'Kem sữa tươi lắc vị việt quất', 25000.00, 'Kem sữa tươi kết hợp việt quất chua ngọt'),
	(12, '/images/Hong-Tra-Kem-Mixue.jpg', 'Hồng Trà Kem Mixue', 'Kem', 'Hồng trà kết hợp kem mịn', 25000.00, 'Hồng trà thơm, kem béo ngậy'),

	-- Menu Trà Sữa
	(13, '/images/Tra-Sua-Dao-Cam-Sa-Mixue.jpg', 'Trà Sữa Đào Cam Sả', 'Trà Sữa', 'Trà sữa đào cam sả thơm mát', 25000.00, 'Trà sữa kết hợp đào, cam, sả tươi mát'),
	(14, '/images/Tra-Sua-Dau-Tay-Mixue.jpg', 'Trà Sữa Dâu Tây', 'Trà Sữa', 'Trà sữa dâu tây ngọt dịu', 25000.00, 'Trà sữa kết hợp dâu tây tươi ngon'),
	(15, '/images/Tra-Sua-Xoai-Mixue.jpg', 'Trà Sữa Xoài', 'Trà Sữa', 'Trà sữa xoài thơm ngon', 22000.00, 'Trà sữa kết hợp xoài tươi mát'),
	(16, '/images/Tra-Sua-Socola-Mixue.jpg', 'Trà Sữa Socola', 'Trà Sữa', 'Trà sữa socola đậm vị', 25000.00, 'Trà sữa kết hợp socola nguyên chất'),
	(17, '/images/Tra-Sua-Viet-Quat-Mixue.jpg', 'Trà Sữa Việt Quất', 'Trà Sữa', 'Trà sữa việt quất chua ngọt', 25000.00, 'Trà sữa kết hợp việt quất tươi mát'),
	(18, '/images/Tra-Sua-Khoai-Mon-Mixue.jpg', 'Trà Sữa Khoai Môn', 'Trà Sữa', 'Trà sữa vị khoai môn thơm béo', 25000.00, 'Trà sữa kết hợp khoai môn thơm ngon'),
	(19, '/images/Tra-Sua-Duong-Chi-Cam-Lo-Mixue.jpg', 'Trà Sữa Dương Chi Cam Lộ Mixue', 'Trà Sữa', 'Trà sữa Dương Chi Cam Lộ Mixue', 25000.00, 'Hương vị trà đào với cam sả thơm lừng'),
	(20, '/images/Tra-Sua-Tran-Chau-Pearl-Mixue.jpg', 'Trà Sữa Trân Châu Pearl Mixue', 'Trà Sữa', 'Trà sữa truyền thống với trân châu', 25000.00, 'Trà sữa thơm ngon với trân châu dai giòn'),
	(21, '/images/Tra-Sua-Thach-Dua-Mixue.jpg', 'Trà Sữa Thạch Dừa Mixue', 'Trà Sữa', 'Trà với trân châu và thạch dừa', 25000.00, 'Hương vị trà thơm, trân châu dừa béo'),
	(22, '/images/Tra-Sua-3Q-Mixue.jpg', 'Trà Sữa 3Q Mixue', 'Trà Sữa', 'Trà sữa với hương vị ngọt ngào', 25000.00, 'Trà sữa thơm, vị dâu tự nhiên'),
	(23, '/images/Tra-Sua-Ba-Vuong-Mixue.jpg', 'Trà Sữa Bá Vương Mixue', 'Trà Sữa', 'Trà sữa cao cấp với vị đặc biệt', 30000.00, 'Trà sữa đậm đà, hương vị tinh tế'),

	(24, '/images/Tra-Dao-Cam-Sa-Mixue.jpg', 'Trà Đào Cam Sả', 'Trà Sữa', 'Trà đào cam sả mát lạnh', 20000.00, 'Trà đào cam sả tươi mát, thanh nhiệt cơ thể'),
	(25, '/images/Tra-Sua-Tran-Chau-Duong-Den-Mixue.jpg', 'Trà Đen Sữa Mixue', 'Trà Sữa', 'Trà đen sữa đậm vị', 20000.00, 'Trà đen kết hợp sữa béo ngậy, thơm ngon'),
	(26, '/images/Tra-Xanh-Sua-Mixue.jpg', 'Trà Xanh Sữa Mixue', 'Trà Sữa', 'Trà xanh sữa tươi mát', 20000.00, 'Trà xanh kết hợp sữa tươi ngọt dịu'),
	(27, '/images/Tra-Den-Macchiato-Mixue.jpg', 'Trà Đen Macchiato', 'Trà Sữa', 'Trà đen phủ kem macchiato', 20000.00, 'Trà đen nguyên chất kết hợp lớp kem mặn ngọt hấp dẫn'),
	(28, '/images/Tra-Xanh-Macchiato-Mixue.jpg', 'Trà Xanh Macchiato', 'Trà Sữa', 'Trà xanh phủ kem macchiato', 20000.00, 'Trà xanh thơm mát với lớp kem sánh mịn');


	-- Dư liệu cũ 
	-- INSERT INTO `product` (`id`, `image_url`, `name`, `category`, `description`, `price`, `content`) VALUES
	-- (1, '/images/Kem-Chocolate-Sundae-Mixue.jpg', 'Kem Chocolate Sundae Mixue', 'Kem', 'Kem sô-cô-la sundae thơm ngon', 25000.00, 'Hương vị sô-cô-la đậm đà, béo ngậy'),
	-- (2, '/images/Sua-Kem-Lac-Dau-Tay-Mixue.jpg', 'Sữa Kem Lắc Dâu Tây Mixue', 'Kem', 'Kem dâu tươi mát', 25000.00, 'Hương vị dâu ngọt ngào, creamy'),
	-- (3, '/images/Super-Sundae-Vi-Chocolate-Den-Mixue.jpg', 'Super Sundae Vị Chocolate Đen Mixue', 'Kem', 'Kem sô-cô-la đen đậm vị', 25000.00, 'Sô-cô-la đen nguyên chất, mịn màng'),
	-- (4, '/images/Hong-Tra-Kem-Mixue.jpg', 'Hồng Trà Kem Mixue', 'Kem', 'Hồng trà kết hợp kem mịn', 25000.00, 'Hồng trà thơm, kem béo ngậy'),
	-- (5, '/images/Super-Sundae-Socolate-Mixue.jpg', 'Super Sundae Socolate Mixue', 'Kem', 'Kem sô-cô-la sundae đa tầng', 25000.00, 'Kem sô-cô-la với topping đa dạng'),
	-- (6, '/images/Tra-Sua-Duong-Chi-Cam-Lo-Mixue.jpg', 'Trà Sữa Dương Chi Cam Lộ Mixue', 'Trà Sữa', 'Trà sữa Dương Chi Cam Lộ Mixue', 25000.00, 'Hương vị trà đào với cam sả thơm lừng'),
	-- (7, '/images/Tra-Sua-Tran-Chau-Pearl-Mixue.jpg', 'Trà Sữa Trân Châu Pearl Mixue', 'Trà Sữa', 'Trà sữa truyền thống với trân châu', 25000.00, 'Trà sữa thơm ngon với trân châu dai giòn'),
	-- (8, '/images/Tra-Sua-Thach-Dua-Mixue.jpg', 'Trà Sữa Thạch Dừa Mixue', 'Trà Sữa', 'Trà với trân châu và thạch dừa', 25000.00, 'Hương vị trà thơm, trân châu dừa béo'),
	-- (9, '/images/Tra-Sua-Tran-Chau-Duong-Den-Mixue.jpg', 'Trà Sữa Trân Châu Đường Đen Mixue', 'Trà Sữa', 'Trà sữa với trân châu đường đen', 25000.00, 'Trà sữa ngọt ngào, trân châu dâu thơm'),
	-- (10, '/images/Tra-Sua-3Q-Mixue.jpg', 'Trà Sữa 3Q Mixue', 'Trà Sữa', 'Trà sữa với hương vị ngọt ngào', 25000.00, 'Trà sữa thơm, vị dâu tự nhiên'),
	-- (11, '/images/Tra-Sua-Ba-Vuong-Mixue.jpg', 'Trà Sữa Bá Vương Mixue', 'Trà Sữa', 'Trà sữa cao cấp với vị đặc biệt', 30000.00, 'Trà sữa đậm đà, hương vị tinh tế');



UPDATE locations
SET image = CONCAT('/uploads/locations/', image)
WHERE image IS NOT NULL AND image NOT LIKE '/uploads/locations/%';





	SELECT * FROM authorities WHERE username = 'admin';
		-- --------------------------------------------------------
		
		--
		-- Table structure for table `users`
		--

	-- 	CREATE TABLE `users` (
	-- 	  `id` int(11) NOT NULL,
	-- 	  `username` varchar(50) NOT NULL,
	-- 	  `password` varchar(100) NOT NULL,
	-- 	  `enabled` tinyint(4) NOT NULL,
	-- 	  `email` varchar(100) NOT NULL
	-- 	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

	CREATE TABLE `users` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `username` varchar(50) NOT NULL,
	  `password` varchar(100) NOT NULL,
	  `enabled` tinyint(4) NOT NULL,
	  `email` varchar(100) NOT NULL,
	  PRIMARY KEY (`id`),
	  UNIQUE KEY `username` (`username`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

		--
		-- Dumping data for table `users`
		--

		INSERT INTO `users` (`id`, `username`, `password`, `enabled`, `email`) VALUES
		(1, 'admin', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'admin@milkteawebsite.com'),
		(2, 'customer', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'customer@milkteawebsite.com'),
		(3, 'system', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'system@milkteawebsite.com'),
		(4, 'minh', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'minhduyen1@example.com'),
		(5, 'hoang', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'hn6033233@gmail.com'),
		(6, 'thoai', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'thoaibanmai1gmail.com'),
		(7, 'sam', '{bcrypt}$2a$12$OgXdlMRgVIIY8UhiQunJGeBs592lbMWNNR19rPmbJ.BhwYsdKB4QG', 1, 'samtam1@gmail.com');

		-- --------------------------------------------------------

		--
		-- Table structure for table `orders`
		--
	CREATE TABLE `orders` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `user_id` int(11) NOT NULL,
	  `receiver_name` varchar(100) NOT NULL,
	  `receiver_phone` varchar(20) NOT NULL,
	  `delivery_instructions` varchar(255),
	  `total_price` decimal(10,2) NOT NULL,
	  `status` varchar(50) NOT NULL DEFAULT 'Pending',
	  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  PRIMARY KEY (`id`),
	  KEY `fk_orders_users_user_id` (`user_id`),
	  CONSTRAINT `fk_orders_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


	-- begin
	-- Thêm cột user_role vào bảng orders
	ALTER TABLE `orders` ADD COLUMN `user_role` VARCHAR(50) AFTER `user_id`;

	-- Thêm cột address vào bảng orders
	ALTER TABLE `orders` ADD COLUMN `address` VARCHAR(255) AFTER `delivery_instructions`;
	-- end

	INSERT INTO orders (user_id, receiver_name, receiver_phone, delivery_instructions, total_price, status, order_date)
	VALUES (3, 'Nguyễn Văn A', '0909123456', 'Giao giờ hành chính', 120000, 'Pending', NOW());

	CREATE TABLE `order_items` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `order_id` int(11) NOT NULL,
	  `product_id` int(11) NOT NULL,
	  `quantity` int(11) NOT NULL,
	  `unit_price` decimal(10,2) NOT NULL,
	  PRIMARY KEY (`id`),
	  KEY `fk_order_items_order_id` (`order_id`),
	  KEY `fk_order_items_product_id` (`product_id`),
	  CONSTRAINT `fk_order_items_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
	  CONSTRAINT `fk_order_items_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


		-- --------------------------------------------------------
	DROP TABLE IF EXISTS cart;

	CREATE TABLE `cart` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `user_id` int(11) NOT NULL,
	  `product_id` int(11) NOT NULL,
	  `quantity` int(11) NOT NULL,
	  `total_price` decimal(10,2) NOT NULL,
	  PRIMARY KEY (`id`),
	  KEY `fk_cart_users_user_id` (`user_id`),
	  KEY `fk_cart_product_id` (`product_id`),
	  CONSTRAINT `fk_cart_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
	  CONSTRAINT `fk_cart_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

	INSERT INTO cart (user_id, product_id, quantity, total_price) VALUES
	(3, 1, 2, 50000.00),
	(3, 6, 1, 25000.00),
	(3, 11, 3, 90000.00);


		-- --------------------------------------------------------
		-- Tạo bảng locations
		CREATE TABLE locations (
			id BIGINT AUTO_INCREMENT PRIMARY KEY,
			name VARCHAR(255) NOT NULL,
			address VARCHAR(255) NOT NULL,
			description TEXT,
			image VARCHAR(255),
			opening_hours VARCHAR(100),
			phone_number VARCHAR(20)
		);

		-- Thêm dữ liệu mẫu
		INSERT INTO locations (name, address, description, image, opening_hours, phone_number) VALUES
		('MIXUE 53 TRIỆU KHÚC', 'Số 53 Triệu Khúc, Thanh Xuân, Hà Nội', 'MIXUE 53 TRIỆU KHÚC - TUYỂN DỤNG 03 PARTTIME. Môi trường làm việc thân thiện, chế độ lương thưởng hấp dẫn.', 'store1.jpg', '08:00-22:00', '0123456789'),
		('MIXUE 86 NGUYỄN GIA TRÍ', '86 Nguyễn Gia Trí, Hồ Chí Minh', 'MIXUE 86 NGUYỄN GIA TRÍ - HCM - TUYỂN DỤNG 04 FULLTIME Ca sáng và Ca chiều. Môi trường làm việc năng động.', 'store2.jpg', '07:30-23:00', '0987654321'),
		('MIXUE 37 PHẠM ĐẠI LÃNG', '37 Phạm Đại Lãng, Hà Nội', 'Thông tin Mixue 37 Phạm Đại Lãng Hà Nội Tuyển Dụng -Pv Bì Lăm Ngay. SỐ LƯỢNG 3 NHÂN VIÊN PARTTIME Ca sáng.', 'store3.jpg', '08:00-22:00', '0369852147'),
		('MIXUE AN BÌNH CITY', 'An Bình City, Cổ Nhuế', 'Thông tin Mixue An Bình City Cổ Nhuế Tuyển Dụng. SỐ LƯỢNG: 10 Ca parttime. Thời gian: 8h-13h,13h-18h,18h-23h.', 'store4.jpg', '08:00-23:00', '0741852963'),
		('MIXUE COOPMART HÀ ĐÔNG', 'Coopmart Hà Đông', 'MÔ TẢ CÔNG VIỆC: Order, pha chế, thu ngân và các công việc khác được giao. YÊU CẦU: - KHÔNG yêu cầu bằng cấp, kinh nghiệm.', 'store5.jpg', '09:00-22:00', '0258963147'),
		('MIXUE 92 BẠCH MAI', '92 Bạch Mai, Hà Nội', 'MIXUE 92 BẠCH MAI TUYỂN NHÂN VIÊN PARTTIME. Đăng ký Tại đây! - Vị trí công việc: Thu ngân/ Pha chế - Ca làm việc.', 'store6.jpg', '07:00-23:00', '0147852369');
		--
		-- Table structure for table `cart`
		--
		
		
	-- 	-- Bỏ comment và thêm vào file SQL
	CREATE TABLE job_applications (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	full_name VARCHAR(255) NOT NULL,
	age INT NOT NULL,
	birth_year INT NOT NULL,
	gender VARCHAR(10) NOT NULL,
	email VARCHAR(255) NOT NULL,
	citizen_id_front VARCHAR(255),
	citizen_id_back VARCHAR(255),
	created_at DATETIME,
	location_id BIGINT,
	is_read BOOLEAN DEFAULT FALSE,
	is_approved BOOLEAN DEFAULT FALSE,
	FOREIGN KEY (location_id) REFERENCES locations(id)
	);
	INSERT INTO job_applications (full_name, age, birth_year, gender, email, created_at, location_id, is_read, is_approved)
	VALUES ('Nguyễn Văn A', 25, 1998, 'Nam', 'nguyenvana@example.com', NOW(), 1, false, false);



	-- Thêm bảng staff
	CREATE TABLE staff (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	full_name VARCHAR(255) NOT NULL,
	age INT NOT NULL,
	birth_year INT NOT NULL,
	gender VARCHAR(10) NOT NULL,
	email VARCHAR(255) NOT NULL,
	citizen_id_front VARCHAR(255),
	citizen_id_back VARCHAR(255),
	hire_date DATETIME,
	location_id BIGINT,
	FOREIGN KEY (location_id) REFERENCES locations(id)
	);


	CREATE TABLE `feedback` (
	  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	  `name` VARCHAR(100) NOT NULL,
	  `email` VARCHAR(100) NOT NULL,
	  `message` TEXT NOT NULL,
	  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	  `is_read` BOOLEAN DEFAULT FALSE
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


	CREATE TABLE `feedback_read` (
	  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	  `name` VARCHAR(100) NOT NULL,
	  `email` VARCHAR(100) NOT NULL,
	  `message` TEXT NOT NULL,
	  `created_at` DATETIME,
	  `read_at` DATETIME DEFAULT CURRENT_TIMESTAMP
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


		select * from locations 


		-- Dumping data for table `cart`

		--
		-- Indexes for dumped tables
		--

		--
		-- Indexes for table `authorities`
		--
		ALTER TABLE `authorities`
		  ADD PRIMARY KEY (`id`),
		  ADD UNIQUE KEY `authorities_idx_1` (`username`,`authority`);

		--
		-- Indexes for table `customer`
		--
		ALTER TABLE `customer`
		  ADD PRIMARY KEY (`id`),
		  ADD KEY `fk_customer_users_user_id` (`user_id`);

		--
		-- Indexes for table `product`
		--
		ALTER TABLE `product`
		  ADD PRIMARY KEY (`id`);

		--
		-- Indexes for table `users`
		--
		ALTER TABLE `users`
		  ADD PRIMARY KEY (`id`),
		  ADD UNIQUE KEY `username` (`username`);

		--
		-- Indexes for table `orders`
		--
		ALTER TABLE `orders`
		  ADD PRIMARY KEY (`id`),
		  ADD KEY `fk_orders_users_user_id` (`user_id`);

		--
		-- Indexes for table `cart`
		--
		ALTER TABLE `cart`
		  ADD PRIMARY KEY (`id`),
		  ADD KEY `fk_cart_users_user_id` (`user_id`),
		  ADD KEY `fk_cart_product_id` (`product_id`);

		--
		-- AUTO_INCREMENT for dumped tables
		--

		--
		-- AUTO_INCREMENT for table `authorities`
		--
		ALTER TABLE `authorities`
		  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

		--
		-- AUTO_INCREMENT for table `customer`
		--
		ALTER TABLE `customer`
		  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

		--
		-- AUTO_INCREMENT for table `product`
		--
		ALTER TABLE `product`
		  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

		--
		-- AUTO_INCREMENT for table `users`
		--
		ALTER TABLE `users`
		  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

		--
		-- AUTO_INCREMENT for table `orders`
		--
		ALTER TABLE `orders`
		  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

		--
		-- AUTO_INCREMENT for table `cart`
		--
		ALTER TABLE `cart`
		  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

		--
		-- Constraints for dumped tables
		--

		--
		-- Constraints for table `authorities`
		--
		ALTER TABLE `authorities`
		  ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

		--
		-- Constraints for table `customer`
		--
		ALTER TABLE `customer`
		  ADD CONSTRAINT `fk_customer_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

		--
		-- Constraints for table `orders`
		--
		ALTER TABLE `orders`
		  ADD CONSTRAINT `fk_orders_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

		--
		-- Constraints for table `cart`
		--
		ALTER TABLE `cart`
		  ADD CONSTRAINT `fk_cart_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
		  ADD CONSTRAINT `fk_cart_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

		COMMIT;