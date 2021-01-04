--Because inserting new topic (or post, or annoucement) need a reference to username, 
--however I guess that SecurityConfig is running after this sql file then insert statement in this file
--don't work without empty username
--therefore I create 2 users and their passwords will not be encrypted for testing purposes.

INSERT INTO users(username, password, enabled) 
	VALUES ('newManager','pass',true);
INSERT INTO users(username, password, enabled) 
	VALUES ('Nam','pass',true);

INSERT INTO topics(username, topicname, topicdate, topictime) 
	VALUES ('newManager', 'Company Lunch Ideas', CURDATE(), '6:20:00');
INSERT INTO topics(username, topicname, topicdate, topictime)
	VALUES ('newManager', 'Marketing Team Project 2021', CURDATE(), '6:00:00');
INSERT INTO topics(username, topicname, topicdate, topictime)
	VALUES ('newManager', 'Company Summer Activites', CURDATE(), '6:10:00');

INSERT INTO posts(topicno, username, postcontent, postdate, posttime) 
	VALUES (1, 'Nam', 'Does anyone want to try some Vietnamese Food today?', CURDATE(), '7:10:00');
INSERT INTO posts(topicno, username, postcontent, postdate, posttime) 
	VALUES (1, 'Nam', 'I used to eat PHO once, its taste is excellent', CURDATE(), '7:11:00');
INSERT INTO posts(topicno, username, postcontent, postdate, posttime) 
	VALUES (1, 'Nam', 'Or you want to try something like BanhMi', CURDATE(), '7:13:00');
INSERT INTO posts(topicno, username, postcontent, postdate, posttime) 
	VALUES (1, 'Nam', 'I think I am going to order the whole menu', CURDATE(), '7:15:00');

INSERT INTO announcements(annTitle, annContent, annDate, annTime,username)
VALUES ('Urgent Meeting','We will have a meeting this afternoon to talk about ...', curdate(),curtime(), 'newManager');