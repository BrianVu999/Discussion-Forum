DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS topics;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS announcements;
DROP TABLE IF EXISTS users;

create table users (
 username varchar(50) not null primary key,
 password varchar(120) not null,
 enabled boolean not null
);
create table authorities (
 username varchar(50) not null,
 authority varchar(50) not null,
 foreign key (username) references users (username)
);
CREATE TABLE topics(
	topicno INT NOT NULL AUTO_INCREMENT,
	username varchar(50) NOT NULL,
    topicname VARCHAR(30) NOT NULL,
    topicdate DATE NOT NULL,
    topictime TIME NOT NULL,
    PRIMARY KEY (topicno),
    FOREIGN KEY (username) REFERENCES users(username)
);
CREATE TABLE posts(
	postno INT NOT NULL AUTO_INCREMENT,
    topicno INT NOT NULL,
	username varchar(50) NOT NULL,
    postcontent VARCHAR(200) NOT NULL,
    postdate DATE NOT NULL,
    posttime TIME NOT NULL,
    PRIMARY KEY (postno),
    FOREIGN KEY (topicno) REFERENCES topics(topicno),
    FOREIGN KEY (username) REFERENCES users(username)
);

Create table announcements(
	annNo INT NOT NULL auto_increment,
	annTitle varchar(50) not null,
	annContent VARCHAR(200) NOT NULL,
	annDate Date not null,
	annTime Time not null,
	username varchar(50) not null,
	PRIMARY KEY (annNo),
	FOREIGN KEY (username) REFERENCES users(username)
);