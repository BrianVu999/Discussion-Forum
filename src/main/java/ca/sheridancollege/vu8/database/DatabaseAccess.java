package ca.sheridancollege.vu8.database;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.vu8.beans.Announcement;
import ca.sheridancollege.vu8.beans.Post;
import ca.sheridancollege.vu8.beans.Topic;

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public ArrayList<Topic> takeTopicList() {
		String query = "SELECT topicname, count(postno), topicdate,topictime, topics.username, topics.topicno\r\n" + 
				"FROM topics LEFT OUTER JOIN posts\r\n" + 
				"	ON topics.topicno = posts.topicno\r\n" + 
				"GROUP BY topicname, topicdate, topics.username, topics.topicno ORDER BY topicdate DESC, topictime DESC";
		ArrayList<Topic> list = new ArrayList<Topic>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for (Map<String, Object> row : rows) {
			list.add(new Topic((int)row.get("topicno"),
					(String)row.get("topicname"),
					(Long)row.get("count(postno)"),
					(Date)row.get("topicdate"),
					(Time)row.get("topictime"),
					(String)row.get("username")
					));
		}
		return list;
	}
	public ArrayList<Post> takePostList(int topicNo) {
		String query = "SELECT postno, postcontent, postdate, posttime, posts.username\r\n" + 
				"FROM posts INNER JOIN topics \r\n" + 
				"	ON topics.topicno = posts.topicno\r\n" + 
				"Where posts.topicno = :topicno ORDER BY postdate DESC, posttime DESC";
		HashMap<String, Object> topicItem = new HashMap<String, Object>();
		topicItem.put("topicno", topicNo);
		ArrayList<Post> list = new ArrayList<Post>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, topicItem);
		for (Map<String, Object> row : rows) {
			list.add(new Post(
					(int)row.get("postno"),
					(String)row.get("postContent"),
					(Date)row.get("postdate"),
					(Time)row.get("posttime"),
					(String)row.get("username")
					));
		}
		return list;
	}
	public Topic takeTopicById(int id) {
		String query = "SELECT topicname, count(postno), topicdate, topictime, topics.username, topics.topicno\r\n" + 
				"FROM topics LEFT OUTER JOIN posts\r\n" + 
				"ON topics.topicno = posts.topicno\r\n" + 
				"WHERE topics.topicno = :topicno\r\n" + 
				"GROUP BY topicname, topicdate, topics.username, topics.topicno";
		HashMap<String, Object> topicItem = new HashMap<String, Object>();
		topicItem.put("topicno", id);
		List<Map<String, Object>> rows = jdbc.queryForList(query, topicItem);
		ArrayList<Topic> list = new ArrayList<Topic>();
		for (Map<String, Object> row : rows) {
			list.add(new Topic((int)row.get("topicno"),
					(String)row.get("topicname"),
					(Long)row.get("count(postno)"),
					(Date)row.get("topicdate"),
					(Time)row.get("topictime"),
					(String)row.get("username")
					));
		}
		return list.get(0);
	}
	
	public void addPost(Post post, int topicNo) {
		String query = "INSERT INTO posts(topicno, username, postcontent, postdate, posttime) \r\n" + 
				"VALUES (:topicno,:username,:postcontent,:postdate,:posttime)";
		HashMap<String, Object> newPost = new HashMap<String, Object>();
		newPost.put("topicno", topicNo);
		newPost.put("username", post.getPostCreatedPerson());
		newPost.put("postcontent", post.getPostContent());
		newPost.put("postdate", post.getPostDate());
		newPost.put("posttime", post.getPostTime());
		jdbc.update(query, newPost);
	}
	public void addTopic(Topic topic) {
		String query = "INSERT INTO topics(username, topicname, topicdate, topictime) \r\n" + 
				"VALUES (:username,:topicname,:topicdate,:topictime)";
		HashMap<String, Object> newPost = new HashMap<String, Object>();
		newPost.put("username", topic.getTopicCreatedPerson());
		newPost.put("topicname", topic.getTopicName());
		newPost.put("topicdate", topic.getTopicDate());
		newPost.put("topictime", topic.getTopicTime());
		jdbc.update(query, newPost);
	}
	public ArrayList<Announcement> takeAnnList() {
		String query = "SELECT * from announcements;";
		ArrayList<Announcement> list = new ArrayList<Announcement>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for (Map<String, Object> row : rows) {
			list.add(new Announcement((int)row.get("annNo"),
					(String)row.get("annTitle"),
					(String)row.get("annContent"),
					(Date)row.get("anndate"),
					(Time)row.get("anntime"),
					(String)row.get("username")
					));
		}
		return list;
	}
	public void addAnnouncement(Announcement ann) {
		String query = "INSERT INTO announcements(annTitle, annContent, anndate, anntime, username) \r\n" + 
				"VALUES (:annTitle,:annContent,:anndate,:anntime,:username)";
		HashMap<String, Object> newAnnouncement = new HashMap<String, Object>();
		newAnnouncement.put("annTitle", ann.getAnnTitle());
		newAnnouncement.put("annContent", ann.getAnnContent());
		newAnnouncement.put("anndate", ann.getAnnDate());
		newAnnouncement.put("anntime", ann.getAnnTime());
		newAnnouncement.put("username", ann.getAnnCreatedPerson());
		jdbc.update(query, newAnnouncement);
	}
	public void deleteAnnouncement(int annNum) {
		String query = "DELETE FROM announcements\r\n" + 
				"WHERE annNo = :annNo";
		HashMap<String, Object> deleteAnn = new HashMap<String, Object>();
		deleteAnn.put("annNo", annNum);
		jdbc.update(query, deleteAnn);
	}
	public ArrayList<String> getUserList(){
		String query = "SELECT username from users;";
		ArrayList<String> list = new ArrayList<String>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for (Map<String, Object> row : rows) {
			list.add(row.toString());
		}
		return list;
	}
}
