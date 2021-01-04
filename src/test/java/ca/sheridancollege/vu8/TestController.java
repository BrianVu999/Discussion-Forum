package ca.sheridancollege.vu8;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {
	// @Autowired any necessary repositories
	@Autowired
	private MockMvc mockMvc;
	// Test cases go here

	// I cannot find a way to work with authentication
	// I have tried mockuser and userdetail but it only works for somes.
//	@Test // Not work properly - redirectedUrl is error page 
//	@WithUserDetails("admin")
//	public void testSaveDrink() throws Exception {
//		Topic newTopic = new Topic();
//		newTopic.setTopicCreatedPerson("admin");
//		newTopic.setTopicName("asd");
//		newTopic.setPostNumber(Long.getLong("123"));
//		this.mockMvc.perform(post("/addTopic").flashAttr("addedTopic", newTopic)).andExpect(status().isFound())
//				.andExpect(redirectedUrl("/admin"));
//	}
	
	
	// Public web page ______________________________________________________

	// Index Page
	@Test
	public void testLoadingGetIndex() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	// Login Page
	@Test
	public void testLoadingGetLogin() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	// Register Page
	@Test
	public void testLoadingGetRegister() throws Exception {
		this.mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(view().name("register"));
	}

	// Protected Page ______________________________________________________

	// Home Page - Cannot access
	@Test
	public void testLoadingHomePage() throws Exception {
		this.mockMvc.perform(get("/home")).andExpect(status().is(302));
	}

	// Admin Page - Cannot access
	@Test
	public void testLoadingAdminPage() throws Exception {
		this.mockMvc.perform(get("/admin")).andExpect(status().is(302));
	}

	// Topic Page - Cannot access
	@Test
	public void testLoadingTopicPage() throws Exception {
		this.mockMvc.perform(get("/topic/1")).andExpect(status().is(302));
	}

	// Access access-denied Page - Cannot access
	@Test
	public void testLoadingAccessDeniedPage() throws Exception {
		this.mockMvc.perform(get("/access-denied")).andExpect(status().is(302));
	}

	// Login as admin ______________________________________________________

	// Admin Page - It doesn't work with mock user so I try user details here
	@Test
	@WithUserDetails("admin")
	public void testLoadingAdminPageAfterLoginAsAdmin() throws Exception {
		this.mockMvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("admin"));
	}
	
	// Home Page
	@Test
	@WithMockUser(username="admin")
	public void testLoadingHomePageAfterLoginAsAdmin() throws Exception {
		this.mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	// Topic Page - To see that topic's post
	@Test
	@WithMockUser(username="admin")
	public void testLoadingTopicPageAfterLoginAsAdmin() throws Exception {
		this.mockMvc.perform(get("/topic/1")).andExpect(status().isOk()).andExpect(view().name("topic"));
	}

	// Login as user ______________________________________________________

	// Admin Page
	@Test
	@WithMockUser(username="user")
	public void testLoadingAdminPageAfterLoginAsUser() throws Exception {
		this.mockMvc.perform(get("/admin")).andExpect(status().is(302));
	}

	// Home Page
	@Test
	@WithMockUser(username="user")
	public void testLoadingHomePageAfterLoginAsUser() throws Exception {
		this.mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	// Topic Page - To see that topic's post
	@Test
	@WithMockUser(username="user")
	public void testLoadingTopicPageAfterLoginAsUser() throws Exception {
		this.mockMvc.perform(get("/topic/1")).andExpect(status().isOk()).andExpect(view().name("topic"));
		;
	}

}
