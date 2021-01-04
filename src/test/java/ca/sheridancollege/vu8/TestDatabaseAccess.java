package ca.sheridancollege.vu8;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.vu8.beans.Announcement;
import ca.sheridancollege.vu8.beans.Post;
import ca.sheridancollege.vu8.beans.Topic;
import ca.sheridancollege.vu8.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
public class TestDatabaseAccess {
	@Autowired
	private DatabaseAccess db;
	
	@Test
	public void testTakeTopicList() throws Exception {
		// Initial Topic Number
		int originalSize = 3;
		int foundSize = db.takeTopicList().size();
		assertThat(originalSize).isEqualTo(foundSize);
	}
	
	@Test
	public void testTakePostList() throws Exception {
		// Initial Topic 1's Post Number
		int originalSize = 4;
		int foundSize = db.takePostList(1).size();
		assertThat(foundSize).isGreaterThanOrEqualTo(originalSize);
	}
	
	@Test
	public void testTakeTopicById() throws Exception {
		// Initial Topic Data Match With Inserted Tested Data From SQL file 
		Topic topic = db.takeTopicById(1);
		assertThat(topic.getTopicName()).isEqualTo("Company Lunch Ideas");
		assertThat(topic.getTopicCreatedPerson()).isEqualTo("newManager");
	}
	
	@Test
	public void testAddPost() throws Exception {
		int originalSize = db.takePostList(1).size();
		Post newPost = new Post();
		newPost.setPostContent("123");
		newPost.setPostCreatedPerson("admin");
		db.addPost(newPost, 1);
		int foundSize = db.takePostList(1).size();
		assertThat(originalSize+1).isEqualTo(foundSize);
	}
	
	@Test
	public void testAddTopic() throws Exception {
		int originalSize = db.takeTopicList().size();
		Topic newTopic = new Topic();
		newTopic.setTopicCreatedPerson("admin");
		newTopic.setTopicName("asd");
		db.addTopic(newTopic);
		int foundSize = db.takeTopicList().size();
		assertThat(originalSize+1).isEqualTo(foundSize);
	}
	
	@Test
	public void testTakeAnnList() throws Exception {
		// Initial Announcement Number Match 
		// With Inserted Tested Data From SQL file 
		int originalSize = 1;
		int foundSize = db.takeAnnList().size();
		assertThat(foundSize).isGreaterThanOrEqualTo(originalSize);
	}
	
	@Test
	public void testAddAnnouncement() throws Exception {
		int originalSize = db.takeAnnList().size();
		Announcement newAnn = new Announcement();
		newAnn.setAnnCreatedPerson("admin");
		newAnn.setAnnTitle("asd");
		newAnn.setAnnContent("asd");
		db.addAnnouncement(newAnn);
		int foundSize = db.takeAnnList().size();
		assertThat(originalSize+1).isEqualTo(foundSize);
	}
	
	@Test
	public void testDeleteAnn() throws Exception {
		int originalSize = db.takeAnnList().size();
		db.deleteAnnouncement(1);
		int foundSize = db.takeAnnList().size();
		assertThat(originalSize).isEqualTo(foundSize+1);
	}
	
	@Test
	public void testUserList() throws Exception {
		// Initial User Number Match 
		// With Inserted Tested Data From SQL file 
		int originalSize = 4;
		int foundSize = db.getUserList().size();
		assertThat(foundSize).isGreaterThanOrEqualTo(originalSize);
	}
}
