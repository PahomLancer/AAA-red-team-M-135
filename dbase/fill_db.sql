INSERT INTO AUTH(ID,NAME,LOGIN,HASH,SALT) VALUES
       (1, 'John Doe', 'jdoe',
       '40b0ecaea2a65fb758954491ca886409',
       'saltsaltsalt'), -- sup3rpaZZ
       (2, 'Jane Row', 'jrow',
       '17895583aaf0eee816b02e0456e2c83b',
       'supersalt'); -- Qweqrty12

INSERT INTO ROLES (ID,AUTH_ID,ROLE,RESOURCE) VALUES
       (1,1,'READ', 'a'),
       (2,1,'WRITE', 'a.b'),
       (3,2,'EXECUTE', 'a.b.c'),
       (4,1,'EXECUTE', 'a.bc');