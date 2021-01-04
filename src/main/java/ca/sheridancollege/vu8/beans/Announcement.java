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

public class Announcement {
	private int annNo;
	private String annTitle;
	private String annContent;
	private Date annDate = new Date(System.currentTimeMillis());
	private Time annTime = new Time(System.currentTimeMillis());
	private String annCreatedPerson;
}
