INSERT INTO USER(ID,NAME,LOGIN,PASSWORD,SALT) VALUES
       (1, 'John Doe', 'jdoe',
       'fc1f96bd2e2406dcc1c61f1dfa50e0d32bca9c2368a8812b9e97090b771c4f83',
       '4a2161b6a262709e4afd7217c3be29aa772ea56ce3f50eeea5cab69cc8e2f07a'), -- sup3rpaZZ
       (2, 'Jane Row', 'jrow',
       '8f265141d92bff83e6ca9052da9717206d971de4aa0a321eae4f98fe51b02eec',
       'd97e188ba5a2094195729a665f9d60a5e7cbc1d68d9e186041ee32947e0e5716'); -- Qweqrty12

INSERT INTO AUTHORITY (ID,USER_ID,ROLE,SITE) VALUES
       (1,1,'READ', 'a'),
       (2,1,'WRITE', 'a.b'),
       (3,2,'EXECUTE', 'a.b.c'),
       (4,1,'EXECUTE', 'a.bc');
