INSERT INTO MEMBER (NAME, EMAIL, PASSWORD)
VALUES
    ('멤버1', 'member1@email.com', 'password'),
    ('멤버2', 'member2@email.com', 'password');


INSERT INTO LECTURE (SPORT, DATE)
VALUES
    ('야구', CURRENT_DATE + 1),
    ('농구', CURRENT_DATE + 2),
    ('축구', CURRENT_DATE + 3),
    ('배구', CURRENT_DATE + 4);

INSERT INTO RESERVATION (MEMBER_ID, LECTURE_ID, RESERVED_AT, RESERVE_COUNT)
VALUES
    (1, 1, CURRENT_DATE - 3, 1),
    (1, 2, CURRENT_DATE - 2, 1),
    (2, 1, CURRENT_DATE - 2, 1);
