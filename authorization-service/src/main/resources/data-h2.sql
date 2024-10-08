-- Insert sample data into the users table
INSERT INTO Users (id) VALUES ('69f11be2-a155-46a4-8022-35b02ad408f0');
INSERT INTO Users (id) VALUES ('2115be7f-68c2-419d-9781-789d16588071');
INSERT INTO Users (id) VALUES ('05b9aa99-e832-43a7-a802-9cef5d58c3ab');

INSERT INTO Groups (id, name) VALUES ('0099d4fd-fd98-4c02-99a9-bcf290a017b4', 'USERS');
INSERT INTO Groups (id, name) VALUES ('38b556cd-f707-42f1-974a-f9395cc3beb4', 'ADMINS');

INSERT INTO User_Groups (user_id, group_id) VALUES ('69f11be2-a155-46a4-8022-35b02ad408f0', '0099d4fd-fd98-4c02-99a9-bcf290a017b4');
INSERT INTO User_Groups (user_id, group_id) VALUES ('2115be7f-68c2-419d-9781-789d16588071', '38b556cd-f707-42f1-974a-f9395cc3beb4');
INSERT INTO User_Groups (user_id, group_id) VALUES ('05b9aa99-e832-43a7-a802-9cef5d58c3ab', '0099d4fd-fd98-4c02-99a9-bcf290a017b4');

INSERT INTO Roles (id, name) VALUES ('af0abcc7-b695-4e0d-9792-da98adee0b47', 'ACCOUNT_VIEWERS');
INSERT INTO Roles (id, name) VALUES ('98e02799-6179-492c-a43b-82941b8a4093', 'ACCOUNT_MODIFIERS');

INSERT INTO Permissions (id, name) VALUES ('a096e437-dfb4-494b-b11f-cf04f60d8d4e', 'VIEW_ACCOUNT');
INSERT INTO Permissions (id, name) VALUES ('0664151c-d923-4bc8-9e70-14dc1f36a694', 'CREATE_ACCOUNT');

INSERT INTO Role_Permissions (role_id, permission_id) VALUES ('af0abcc7-b695-4e0d-9792-da98adee0b47', 'a096e437-dfb4-494b-b11f-cf04f60d8d4e');
INSERT INTO Role_Permissions (role_id, permission_id) VALUES ('98e02799-6179-492c-a43b-82941b8a4093', 'a096e437-dfb4-494b-b11f-cf04f60d8d4e');
INSERT INTO Role_Permissions (role_id, permission_id) VALUES ('98e02799-6179-492c-a43b-82941b8a4093', '0664151c-d923-4bc8-9e70-14dc1f36a694');

INSERT INTO Group_Roles (group_id, role_id) VALUES ('0099d4fd-fd98-4c02-99a9-bcf290a017b4', 'af0abcc7-b695-4e0d-9792-da98adee0b47');
INSERT INTO Group_Roles (group_id, role_id) VALUES ('38b556cd-f707-42f1-974a-f9395cc3beb4', '98e02799-6179-492c-a43b-82941b8a4093');