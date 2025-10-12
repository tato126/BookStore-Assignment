INSERT INTO category (id, name, level, parent_id) VALUES (1, '문학', 'LARGE', NULL);
INSERT INTO category (id, name, level, parent_id) VALUES (2, '인문', 'LARGE', NULL);
INSERT INTO category (id, name, level, parent_id) VALUES (3, 'IT', 'LARGE', NULL);

INSERT INTO category (id, name, level, parent_id) VALUES (4, '한국소설', 'MEDIUM', 1);
INSERT INTO category (id, name, level, parent_id) VALUES (5, '외국소설', 'MEDIUM', 1);
INSERT INTO category (id, name, level, parent_id) VALUES (6, '시/에세이', 'MEDIUM', 1);

INSERT INTO category (id, name, level, parent_id) VALUES (7, '철학', 'MEDIUM', 2);
INSERT INTO category (id, name, level, parent_id) VALUES (8, '심리학', 'MEDIUM', 2);
INSERT INTO category (id, name, level, parent_id) VALUES (9, '역사', 'MEDIUM', 2);

INSERT INTO category (id, name, level, parent_id) VALUES (10, '기술', 'MEDIUM', 3);
INSERT INTO category (id, name, level, parent_id) VALUES (11, '언어', 'MEDIUM', 3);

INSERT INTO category (id, name, level, parent_id) VALUES (12, '현대 한국소설', 'SMALL', 4);
INSERT INTO category (id, name, level, parent_id) VALUES (13, '고전 한국소설', 'SMALL', 4);
INSERT INTO category (id, name, level, parent_id) VALUES (14, '미국소설', 'SMALL', 5);
INSERT INTO category (id, name, level, parent_id) VALUES (15, '영국소설', 'SMALL', 5);
INSERT INTO category (id, name, level, parent_id) VALUES (16, '시', 'SMALL', 6);
INSERT INTO category (id, name, level, parent_id) VALUES (17, '에세이', 'SMALL', 6);

INSERT INTO category (id, name, level, parent_id) VALUES (18, '동양철학', 'SMALL', 7);
INSERT INTO category (id, name, level, parent_id) VALUES (19, '서양철학', 'SMALL', 7);
INSERT INTO category (id, name, level, parent_id) VALUES (20, '인지심리학', 'SMALL', 8);
INSERT INTO category (id, name, level, parent_id) VALUES (21, '상담심리학', 'SMALL', 8);
INSERT INTO category (id, name, level, parent_id) VALUES (22, '한국사', 'SMALL', 9);
INSERT INTO category (id, name, level, parent_id) VALUES (23, '세계사', 'SMALL', 9);

INSERT INTO category (id, name, level, parent_id) VALUES (24, '소프트웨어공학', 'SMALL', 10);
INSERT INTO category (id, name, level, parent_id) VALUES (25, '네트워크', 'SMALL', 10);
INSERT INTO category (id, name, level, parent_id) VALUES (26, 'Java', 'SMALL', 11);
INSERT INTO category (id, name, level, parent_id) VALUES (27, 'Python', 'SMALL', 11);

-- 자동 증가 값이 꼬이지 않도록 시퀀스 값 보정 (H2 기준)
ALTER TABLE category ALTER COLUMN id RESTART WITH 28;
