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
public class Topic {
	private int topicNo;
	private String topicName;
	private Long postNumber;
	private Date topicDate = new Date(System.currentTimeMillis());
	private Time topicTime = new Time(System.currentTimeMillis());
	private String topicCreatedPerson;
}
