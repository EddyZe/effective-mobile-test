insert into users (id, username, start_amount, bank_account, password)
values (111, 'test1', 100, 500, '$2a$10$TLOq9FGsXAJ8EoPmKu65qOQelUXUM11tpcMGLPBw18KVDVwtuTQ7C'),
       (112, 'test2', 100, 100, '$2a$10$TLOq9FGsXAJ8EoPmKu65qOQelUXUM11tpcMGLPBw18KVDVwtuTQ7C');

insert into email (email, owner_email) VALUES ('test2@mail.com', 112);

insert into phone_number(number, owner_phone_number) values ('89999999999', 112)
