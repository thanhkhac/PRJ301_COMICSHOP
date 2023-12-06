USE [master]

GO
IF EXISTS(SELECT name FROM master.dbo.sysdatabases WHERE name = 'COMICSHOP')
DROP DATABASE [COMICSHOP];

GO
CREATE DATABASE COMICSHOP

GO
USE COMICSHOP;
--============================================TABLE==================================================
GO
--------------------------------USER
CREATE TABLE Users(
	[userName] varchar(50) PRIMARY KEY not null,
	[passWord] varchar(100) not null,
	[fullName] nvarchar(150) not null,	
	[phoneNumber] varchar(20) UNIQUE not null,
	[role] bit DEFAULT 0,
);
--------------------------------BOOK
--Tác giả
CREATE TABLE Authors(
	[authorID] int PRIMARY KEY IDENTITY(1,1),
	[name] nvarchar(255) not null,	
	[thumbnail] nvarchar(max),
	[desc] nvarchar(500)
);

--Thể loại
CREATE TABLE Genres(
	[genreID] int PRIMARY KEY IDENTITY (1,1) ,
	[name] nvarchar(100) UNIQUE not null,
)

CREATE TABLE Books(
	[bookID] int PRIMARY KEY IDENTITY (1,1),
	[title] nvarchar(100) not null,	
	[desc] nvarchar(500),
	[thumbnail] nvarchar(max) not null,
	[price] money,
	[publishedDate] date,
	[authorID] int FOREIGN KEY REFERENCES Authors(authorID) ON DELETE CASCADE,	
	[quantity] int,
	CHECK(quantity >= 0)
);

CREATE TABLE BelongTo(
	[bookID] int FOREIGN KEY REFERENCES Books([bookID])  ON DELETE CASCADE ,
	[genreID] int FOREIGN KEY REFERENCES Genres([genreID])  ON DELETE CASCADE 
	PRIMARY KEY ([bookID], [genreID])
);

--------------------------------SALE
CREATE TABLE Carts(
	[userName] varchar(50) FOREIGN KEY REFERENCES Users([userName])  ON DELETE CASCADE ,
	[bookID] int FOREIGN KEY REFERENCES Books(bookID)  ON DELETE CASCADE not null,
	[quantity] int not null,
	PRIMARY KEY ([userName], bookID),	
	CHECK (quantity >= 0)
);	

CREATE TABLE Orders(
	orderID int PRIMARY KEY IDENTITY (1,1),
	userName varchar(50) FOREIGN KEY REFERENCES Users(username) ON DELETE CASCADE not null,
	recipientName nvarchar(200) not null,
	orderDate date not null,
	phoneNumber varchar(50) not null,
	shipAddress nvarchar(150) not null,	
	[status] int DEFAULT 0
);

CREATE TABLE OrderDetails(
	orderID int  FOREIGN KEY REFERENCES Orders(orderID) ON DELETE CASCADE,
	bookID int FOREIGN KEY REFERENCES Books(bookID) ON DELETE CASCADE not null ,
	quantity int not null,	
	PRIMARY KEY (orderID, bookID),
	CHECK (quantity >= 1)
);

--=============================================DATA=================================================
GO
----------------------------------USER
INSERT INTO Users ([userName], [Password], FullName, [role],[phoneNumber]) VALUES ('thanhcqb2048', '123456', N'Nguyễn Khắc Thành', 1, '0382293847');
INSERT INTO Users ([userName], [Password], FullName, [role],[phoneNumber]) VALUES ('trinh0403', '123456', N'Nguyễn Thùy Trinh', 0, '0123456789');
INSERT INTO Users ([userName], [Password], FullName, [role],[phoneNumber]) VALUES ('duong123', '123456', N'Nguyễn Mạnh Dương', 0, '03456734343');
INSERT INTO Users ([userName], [Password], FullName, [role],[phoneNumber]) VALUES ('robeo123', '123456', N'Rô Béo', 0, '03456732343');

----------------------------------BOOK

--Author:
INSERT INTO Authors([name], [thumbnail], [desc]) VALUES (
N'Gege Akutami', 
N'https://64.media.tumblr.com/b4fbb6bb03f7833a967702fd7ec79a6b/74413778b889ea47-2c/s250x400/d236bb761595debe9ee6b692e183d6b0c2df7c55.png',
N'Gege Akutami là một tác giả manga người Nhật Bản nổi tiếng, nổi danh với tác phẩm nổi tiếng của mình mang tên "Jujutsu Kaisen." Ông sinh ngày 5 tháng 1 năm 1992 tại tỉnh Iwate, Nhật Bản. Trước khi thành công với "Jujutsu Kaisen," Akutami đã từng làm việc với một số tạp chí manga và có một số tác phẩm ngắn khác.'
)
INSERT INTO Authors([name], [thumbnail], [desc]) VALUES (
N'Rover Studio', 
N'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAkFBMVEXdJSfz7BjeLC7hQkT08xfz7hjdIyf08Rf09BfcACjcGyfcFCjjbyPkaiTdHifvzhzvxx3eLCby3BrfPSblfSHtuR7z5Bnz6Rjy4RjokyDrqh7nhiLnjCLw1xrtwh3gOybldiPssx7vzBvpmiDqoCDhViXiXSXw0xvrph/gSyXleiLiYyTgTyXsth7tvh31/RbNa4n2AAAKfklEQVR4nO2b6ZqiuhaGYZ+QkESjiIozzmg59P3f3U5WggJqHdS2dqeffH8sISBvpjVR3j/e3y5HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKEWvtXt6Y883/uqRYj5HYn8rMiPsCrk/f6QxwWTwuVmhY6qXPboNr+PEEdZ+0anA9dn+banj8zGFUTsRUZeoUNkj+Bj82t+iE2XmGbmQ37F+WWRvt/lK6PCe1q1xpCtwqAqgiaAyMeEmEPhuYzIvvodo2wdmw7xeLzrBCFCYb89B242HsgWrShVrQdNgdlSXzQ4U9UB8TS/y3RxoE+PY71ZShfEryoge6F+Pwkuh8IxKxF2rx2Dgi+qbzXxkb5CHmyl8gI+Uc0GUao+UINhPtV9RlqaMLl2KxnxZxFr7jRRP7hBRGsOFAXoNi0TosK5sMnUil6EhTuRZMMkoWwGhOoIEJouWOaE1yvCHX/wjO8RevcGUfanmmJFimXp5w2h7Hv4yGT38zUy42cODmLxiDAIWkVCfYEfpk8OYk3C0nA8SZgkiZqXgX8UYkPUcwZIbl19OEl6/BFh4pfGMEngWrP8fz9h41XCoL9P0yb0fpPxs5oKZLoVlEcrjbihdwnJKEphuDQh6kZpDMefnaafJKRfQIgpFWoZhw0eqcbB1JN7LsYULiWjB4Q7KoSygDwFwgaldPaHEfLRUk2rpHfqZTA3N3yuIXQrzFtq8mYVQtrVe5HcOMnAw2wyS9S39uk0g1k6/HMIaV/vTvJRAXAq+IqoQTWG3NNfEz7WhDEQNul1yQcd2HyLd/HD/ZNW/5OEg5KFQWPOdwppkJs0fS1hMJmT+AjftrxKWNrFyZnefcA/gFAZN76GMfTyMRwqNJ8dYPA2c1ikcU6oDMoNYZBEzzpuH52ll/UEDxxjBhDVdcj36jyaw5wNmPT14LL+YDDIirMUXCHUZfef8L8gFFu9zhoHsAtIOuYpDFPmqafEtEvMXTpgHWZ63/EEhb10LTdPjrE4ztVeSobbLnyeP+TT3CVU+zYvEpJZ6ee5tocyJOibbZ7PAGp5lE9PJ+CloK3gPVieassk0hPMrQXXARPT9nDO6Ug1mz7rmL5BiOas4s6F3YrnDYQppmo6kgX32BamWhBki3Mf9kbZKWbyXnbKi0+jRObcEDK99Q5+jDBAys1me78QT1Uc/ythZgg9mvulxPil/VgiiU6QT3PqeVe/VEVkXz9GGCKCtMJQ/h30F8oxiZed6TTLstayfV40KsHbHcKb2GKrRv0y1dGBXQn1kZ8iFMfdajgZd7/mjWbzcNhujqn6JRypjATnallRXt3l7hDKxx8GJB+xMNvDws2Z5DaC/ytCnYxhWkJJ/8636RM2/iXHO5CEU/n5q2do9iM/hKmQdYUxbmyjWoZBLKDFILzoV1caE/hkfK0aJZ8ifEk6RyUnMysmqgSn++5k0ozoNekhitkt3fyS8ML5XcTddNf/0+uEMHownEIPrc633bbxboYaZnb5UUstKlnLB3epqbqEJqEIy+2SAUvj/f642R6ajflXdzyZDFe7ybMux8dVkxDv1+v1brQ49c6jMVZLQXSTfl8aLF/lh1Auf/NCvu+zqm0tfpFcyB8qRL41WcSiE9B41qf6vF6x+EE4A8Sb3E0w/QNz+6/5NOGI5rFBibD1dDbz83rRa0PKF9H+5F9KCKH21Z/8HOH793uRMAiOwtPZ6o8S4vcRX40tIPzFXhJ8lpBPno7pq3qVUGfMICj9llB5YOJlbMw7HVryhaRX/FPxIWTXRRx+Syho2h1OthRCBloUk+eUsAeBicqHXE+auzDqTQiZeFQPo/JnG935kdOnIF+PgDvqOeiZfEPI4jZEEcmYS0PZJ1eFa9oI1R8xzZB0Is6UdUN9yu+cJxCasbEMRGVXhqQLUeNxoWMSct4+sxhez2KEDWUwDugxoYgTkxMOd/JEsUCHJCGCBJzOcPTopfajioj+GsuNjO50ZUNV8TAbmZSwH5Dw5NV3Dl8n1BkHlhWeu0JIl/kAq8JniZBoQv8Ooe6SltpGIQlEdlRVOdrF9YCytDbiO7k25WWXcm1lQhGb8qEibHFJmPuwcpQeEeZNUFtVG0fKDYb02zkvReoGpFV7Lb5DeFKD6BUy22VCyNbL9donKMhWHjTM2z4aQ+gNaISke8+XaLQgSy77EUaQBNNz5sPECFd1ffw3CINEJR0gU/8NIdkc2+NY5XZjKUVJVmkcp/wuYbiPN+Mp5MgHEY52e0qPuwjjjs48yq80hfxlkEQ1B/ENQh+pfsTxNX6qzNI9MAwaVGcnsDRnilCNDsbsPiEWgos2pIqbzBgYLHRl4wwZBKGNcFjXFXiHUBVO5H7SI/cJParLhqTnmYfBwhBe7nhDqO4o9gGk9Tkb9k6n04rrio5v6mr6tQmduvswoe5HsQ8fEIqNXlIkMW8X1STEDI6dKW8rW7mkOu2f5d4NPavl2qpZZnuLEF6vkEYhuE/o8bnJbKMxf4LQo2qaBjPOoVjTpvlnfltdwPgJQj88CMiHPyD0eHMAUzggB1afUO6hZgxzwjPJu1PpB8dQ7m6lMPHW82aRzuIHLZWhq7sOY18Xny6EUDuGrVv1E/gO8pKfIPQRGIwhuk+IpbNMm+DLQPn9DmF6u5cyCjU46RXmhKZK3AOXG9MdVFTHP7CX+vCuiPzN1DhkFULG9pPlmTbh0bcVQnHQZXva0RujIYzTeJ5pKxPhnBBDDdVHvVT2WLTTrsFP2EO/GiZWLP44k94MWUKdJTxWCDG8XBJMte1bGcIgSYjxaVaqoGp2GOMbkqTdM+9SwUt1v5WwmnS6TFMIE/faHasSwg4E26nuiSKhB4Nn/FA5hyt+KcnkjLwQYjYzZ03dkWS10xt139wrB/OFQZyqtaFXToUQ087lohC6vETIvi52FPU4LscWaKDeubhaCYyzYheTQfybYwuxLydkCgrnYPV19bpi8dMBMvawDaGAIpRxxdCU2Xbm5SeUyQEuv4y6YIqAz+SQEbCDKpLK40N5O69+9qbeG7RRoaJXVabsAG92VG04q8T46SKBt4HXOtbBYtrv95NJ/rbJuIMQCfsjFc+yr6QPGnRaq712X/hZ1UZm5tXb7bmvMwbtarH5dxA2mo8FK4J5W/nntvrKOo8b43EzzcEhvojzXRBz79AdN2J4XhzFWmlE85fCcQoH8leoaCqbd5vxTbH5fUK50r+RqQZDHuz+lYVs2U0h8XL23hv595o/WyJ1/1Fivxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof1yhPbLEdovR2i/HKH9coT2yxHaL0dovxyh/XKE9ssR2i9HaL8cof3639+ufwGR5M7JRxws+QAAAABJRU5ErkJggg==',
N'Rover studio là một nhóm các họa sĩ, biên kịch và designer có đam mê sáng tác truyện tranh và đang mong muốn theo đuổi con đường này một cách chuyên nghiệp. '
)
INSERT INTO Authors([name], [thumbnail], [desc]) VALUES (
N'Aoyama Gosho',
N'https://i.ibb.co/WBxv58J/Conan-Author-Gosho-Aoyama.jpg',
N'Aoyama Gosho là tên thật của một tác giả manga nổi tiếng người Nhật, sinh ngày 21/6/1963. Ông nổi danh với bộ manga "Detective Conan," còn được biết đến với tên "Case Closed" ở phương Tây. Bộ manga này là một trong những tác phẩm trinh thám nổi tiếng, kéo dài suốt hơn 25 năm. Aoyama Gosho đã góp phần lớn vào sự phát triển của thể loại trinh thám manga và có sự ảnh hưởng lớn đối với ngành công nghiệp manga và anime.'
)

INSERT INTO Authors ([name]) VALUES 
(N'Alan Moore'),
(N'Neil Gaiman'),
(N'Frank Miller'),
(N'Osamu Tezuka'),
(N'Hayao Miyazaki'),
(N'Junji Ito'),
(N'Yoshihiro Togashi'),
(N'Akira Toriyama'),
(N'Naoki Urasawa'),
(N'CLAMP'),
(N'Masashi Kishimoto'),
(N'Rumiko Takahashi'),
(N'Gosho Aoyama'),
(N'Katsuhiro Otomo'),
(N'Brian K. Vaughan'),
(N'Kazuo Koike'),
(N'Fujiko F. Fujio'),
(N'Yukito Kishiro'),
(N'Gengoroh Tagame'),
(N'Shigeru Mizuki'),
(N'Moto Hagio'),
(N'Leiji Matsumoto'),
(N'Takehiko Inoue'),
(N'Shinya Shokudo'),
(N'Kazuo Umezu'),
(N'Naoki Yamamoto'),
(N'Marjane Satrapi'),
(N'Joe Sacco'),
(N'Jiro Taniguchi'),
(N'Makoto Yukimura');
--Genre
INSERT INTO Genres([name]) VALUES (N'Hành động');
INSERT INTO Genres([name]) VALUES (N'Kinh dị');
INSERT INTO Genres([name]) VALUES (N'Hài hước');
INSERT INTO Genres([name]) VALUES (N'Viễn tưởng');
INSERT INTO Genres([name]) VALUES (N'Trinh thám');
INSERT INTO Genres([name]) VALUES (N'Lịch sử');

INSERT INTO Genres([name]) VALUES
(N'Siêu anh hùng'),
(N'Tình cảm'),
(N'Thể thao'),
(N'Phiêu lưu')

--Book
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity, [desc] ) VALUES
(N'Chú thuật hồi chiến tập 1', 1, '2020-01-01', 25500, 'https://i.ibb.co/121hjRg/JJK1.jpg', 10, N'Tập 1 của Jujutsu Kaisen đưa bạn vào một thế giới ma quỷ đầy kịch tính, khi Yuji Itadori phải đối mặt với sức mạnh ma thuật và tham gia vào cuộc chiến chống lại quỷ dữ để bảo vệ thế giới.');
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity, [desc] ) VALUES
(N'Chú thuật hồi chiến tập 2', 1, '2020-01-02', 25500, 'https://i.ibb.co/yfRtXqw/JJK2.jpg', 20, N'Trong tập 2 này, Yuji và nhóm bạn cùng với người thầy Gojo phải đối mặt với nhiều thách thức mới, đối đầu với những thế lực ma quỷ mạnh mẽ và tìm cách ngăn chặn sự bùng nổ của ma quái. Sự pha trộn giữa hành động, kinh dị và hài hước, cùng với nghệ thuật vẽ tuyệt đẹp, khiến Jujutsu Kaisen Vol. 2 trở thành một bộ truyện tranh đáng xem và đầy cuốn hút');
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Chú thuật hồi chiến tập 3', 1, '2020-01-03', 25500, 'https://i.ibb.co/LP19dPr/JJK3.jpg', 30);
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Chú thuật hồi chiến tập 4', 1, '2020-01-04', 25500, 'https://i.ibb.co/k3dMMJX/JJK4.jpg', 40);

INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Tứ phủ xét giả tập 1', 2, '2021-01-05', 30500, 'https://i.ibb.co/KbFvpyc/TPXG1.jpg', 50);
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Tứ phủ xét giả tập 2', 2, '2021-01-06', 30500, 'https://i.ibb.co/d0QgX2q/TPXG2.jpg', 60);
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Tứ phủ xét giả tập 3', 2, '2021-01-06', 30500, 'https://i.ibb.co/zGkNfCD/TPXG3.jpg', 70);

INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Conan tập 96', 3, '2022-01-07', 20000, 'https://i.ibb.co/z596pPJ/conan96.jpg', 80);
INSERT INTO Books (title, authorID, publishedDate, price, thumbnail, quantity) VALUES
(N'Conan tập 97', 3, '2022-01-08', 20000, 'https://i.ibb.co/F6qqDhp/conan97.jpg', 90);

--BelongTo
DECLARE @HaiHuoc int = (SELECT genreID FROM Genres WHERE [name] = N'Hài Hước')
DECLARE @HanhDong int = (SELECT genreID FROM Genres WHERE [name] = N'Hành Động')
DECLARE @KinhDi int = (SELECT genreID FROM Genres WHERE [name] = N'Kinh dị')
DECLARE @LichSu int = (SELECT genreID FROM Genres WHERE [name] = N'Lịch sử')
DECLARE @TrinhTham int = (SELECT genreID FROM Genres WHERE [name] = N'Trinh thám')
DECLARE @VienTuong int = (SELECT genreID FROM Genres WHERE [name] = N'Viễn tưởng')

INSERT INTO BelongTo(bookID, genreID) VALUES (1, @HaiHuoc);
INSERT INTO BelongTo(bookID, genreID) VALUES (1, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (1, @VienTuong);
INSERT INTO BelongTo(bookID, genreID) VALUES (2, @HaiHuoc);
INSERT INTO BelongTo(bookID, genreID) VALUES (2, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (2, @VienTuong);
INSERT INTO BelongTo(bookID, genreID) VALUES (3, @HaiHuoc);
INSERT INTO BelongTo(bookID, genreID) VALUES (3, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (3, @VienTuong);
INSERT INTO BelongTo(bookID, genreID) VALUES (4, @HaiHuoc);
INSERT INTO BelongTo(bookID, genreID) VALUES (4, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (4, @VienTuong);

INSERT INTO BelongTo(bookID, genreID) VALUES (5, @LichSu);
INSERT INTO BelongTo(bookID, genreID) VALUES (5, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (5, @VienTuong);
INSERT INTO BelongTo(bookID, genreID) VALUES (6, @LichSu);
INSERT INTO BelongTo(bookID, genreID) VALUES (6, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (6, @VienTuong);
INSERT INTO BelongTo(bookID, genreID) VALUES (7, @LichSu);
INSERT INTO BelongTo(bookID, genreID) VALUES (7, @HanhDong);
INSERT INTO BelongTo(bookID, genreID) VALUES (7, @VienTuong);

INSERT INTO BelongTo(bookID, genreID) VALUES (8, @TrinhTham);
INSERT INTO BelongTo(bookID, genreID) VALUES (8, @KinhDi);
INSERT INTO BelongTo(bookID, genreID) VALUES (9, @TrinhTham);
INSERT INTO BelongTo(bookID, genreID) VALUES (9, @KinhDi);

----------------------------------SALE
INSERT INTO Carts([userName], bookID, quantity) VALUES ('trinh0403', 1, 10);
INSERT INTO Carts([userName], bookID, quantity) VALUES ('trinh0403', 5, 10);
INSERT INTO Carts([userName], bookID, quantity) VALUES ('trinh0403', 8, 10);
INSERT INTO Carts([userName], bookID, quantity) VALUES ('duong123', 1, 10);
INSERT INTO Carts([userName], bookID, quantity) VALUES ('duong123', 5, 10);
INSERT INTO Carts([userName], bookID, quantity) VALUES ('duong123', 8, 10);

SELECT * FROM Carts
DELETE FROM Carts

---------------------------------ORDER


INSERT INTO Orders(userName, recipientName,  orderDate, phoneNumber, shipAddress) VALUES
('duong123', N'Nguyễn Mạnh Dương', '2016-08-12', '0382293846', N'Hà Đông' );



INSERT INTO OrderDetails (orderID, bookID, quantity) VALUES
(1, 1, 10);
INSERT INTO OrderDetails (orderID, bookID, quantity) VALUES
(1, 2, 10);


GO
CREATE TRIGGER Cart_CartLimitQuantity ON Carts
AFTER INSERT, UPDATE
AS
BEGIN
	IF EXISTS (
		SELECT * FROM 
			Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
		WHERE CA.quantity > bo.quantity
		)
	BEGIN
		UPDATE Carts
		SET quantity = X.quantity
		FROM(
			SELECT CA.bookID, bo.quantity
			FROM
				Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
				WHERE CA.quantity > bo.quantity
			) AS X
		WHERE Carts.bookID = X.bookID
	END
END;
GO
CREATE TRIGGER Book_CartLimitQuantity ON Carts
AFTER INSERT, UPDATE
AS
BEGIN
	IF EXISTS (
		SELECT * FROM 
			Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
		WHERE CA.quantity > bo.quantity
		)
	BEGIN
		UPDATE Carts
		SET quantity = X.quantity
		FROM(
			SELECT CA.bookID, bo.quantity
			FROM
				Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
				WHERE CA.quantity > bo.quantity
			) AS X
		WHERE Carts.bookID = X.bookID
	END
END;
GO
CREATE TRIGGER Order_CartLimitQuantity ON Orders
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
	IF EXISTS (
		SELECT * FROM 
			Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
		WHERE CA.quantity > bo.quantity
		)
	BEGIN
		UPDATE Carts
		SET quantity = X.quantity
		FROM(
			SELECT CA.bookID, bo.quantity
			FROM
				Carts CA JOIN Books bo ON CA.bookID = bo.bookID 
				WHERE CA.quantity > bo.quantity
			) AS X
		WHERE Carts.bookID = X.bookID
	END
END;
GO




--==============================================SELECT===============================================






