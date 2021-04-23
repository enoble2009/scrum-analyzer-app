insert into users (email, password, external_id, user_role) 
values 
('samuel.noble@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '557058:4f993a97-f4d4-4965-8321-6df6dc5ce821', 1),
('jefferson.torquato@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5b7eb051eb18d0589b9b2607', 0),
('ronaldo.lidio@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5b7eb053ed4f842a6ddb50a8', 1),
('eduardo.vieira@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5e32e344fae00f0cb3ad1019', 1),
('felipe.correa@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5e32e313a7f27d0e6c6d457d', 1),
('emanuel.desouza@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5f9c232581b2880078cc15c5', 1),
('taua.brandao@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5fbd6c8f3b4f5900689fb2b5', 1),
('daniel.fucci@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '6054f7a886b0dd00719746ee', 1),
('ricardo.maciel@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5b368d55fc76f66e7d59a82d', 2),
('cesar.cabral@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5c9925c1520ecd756a46dd7d', 2),
('anderson.albuquerque@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5f7772e9a29096007510b8d9', 2),
('eric.liz@salsatechnology.com', '$2a$10$cmztQnMnO7yqmnXu7RlbG.kAyUBFCB/o0v/4ndExRNqV8xkUhyvM.', '5f777315837bb8006830a095', 2);

insert into projects (name, description) values ('Salsa Platform', 'Salsa platform team in charge of the casino clients sites');

insert into status_metadata (jira_status_id, type) select id as jira_status_id, 0 from jira_statuses where external_id in (1, 4, 10318, 12675, 12612, 10307, 10315, 10316); -- NOT_SORTED
insert into status_metadata (jira_status_id, type) select id as jira_status_id, 1 from jira_statuses where external_id in (10304); -- ANALYSIS
insert into status_metadata (jira_status_id, type) select id as jira_status_id, 2 from jira_statuses where external_id in (10000); -- BACKLOG
insert into status_metadata (jira_status_id, type) select id as jira_status_id, 3 from jira_statuses where external_id in (12665, 10400, 10314, 10319); -- NOT_STARTED
insert into status_metadata (jira_status_id, type) select id as jira_status_id, 4 from jira_statuses where external_id in (3, 10310, 10321, 10309, 10322, 10313); -- STARTED
insert into status_metadata (jira_status_id, type) select id as jira_status_id, 5 from jira_statuses where external_id in (6, 12611, 12610, 10311); -- FINISHED

insert into project_jira_sprints ([project_id], [jira_sprint_id]) SELECT 1, id FROM [dev-league].[dbo].[jira_sprints] where name like '%PG Plataforma - Sprint%' order by name asc;