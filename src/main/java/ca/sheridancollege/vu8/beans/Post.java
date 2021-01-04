package ca.sheridancollege.vu8.beans;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	private int postNo;
	private String postContent;
	private Date postDate = new Date(System.currentTimeMillis());
	private Time postTime = new Time(System.currentTimeMillis());
	private String postCreatedPerson;
}
