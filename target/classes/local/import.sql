INSERT INTO app_role(id, role) VALUES (1, 'ADMIN');
INSERT INTO app_role(id, role) VALUES (2, 'MANAGER');
INSERT INTO app_role(id, role) VALUES (3, 'RESPONSIBLE');

-- password forensic
INSERT INTO public.app_user(id, active, email, first_name, last_name, username,password) VALUES (1, true, 'admin@evidata.com', 'Admin', 'Administrator', 'admin', '$2a$10$KIu95NKIB8mb8cSU3onSm.nqU4nrGV8Oc/MEG.0spP4bS2Leil49m');

INSERT INTO public.user_role(user_id, role_id) VALUES (1, 1);


INSERT INTO department(id, name) VALUES (1, 'BECTS');
INSERT INTO department(id, name) VALUES (2, 'CIJ');
INSERT INTO department(id, name) VALUES (3, 'CFL');

INSERT INTO equipment(id, name) VALUES (1, 'Lucia Forensic');
INSERT INTO equipment(id, name) VALUES (2, 'Microscope');
INSERT INTO equipment(id, name) VALUES (3, 'Docubox Dragon');

INSERT INTO expertisetype(id, name) VALUES (1, 'Documents');
INSERT INTO expertisetype(id, name) VALUES (2, 'Dactiloscopic');
INSERT INTO expertisetype(id, name) VALUES (3, 'Ballistic');
INSERT INTO expertisetype(id, name) VALUES (4, 'Images');
INSERT INTO expertisetype(id, name) VALUES (5, 'Traseological');

INSERT INTO rank(id, name)VALUES (1, 'Subinspector');
INSERT INTO rank(id, name)VALUES (2, 'Inspector');
INSERT INTO rank(id, name)VALUES (3, 'Inspector-principal');

INSERT INTO public.experience(id, level) VALUES (1, 'Tehnician');
INSERT INTO public.experience(id, level) VALUES (2, 'Specialist');
INSERT INTO public.experience(id, level) VALUES (3, 'Expert');

