package ca.sheridancollege.vu8.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sheridancollege.vu8.beans.Announcement;
import ca.sheridancollege.vu8.beans.Post;
import ca.sheridancollege.vu8.beans.Topic;
import ca.sheridancollege.vu8.beans.UserRegistration;
import ca.sheridancollege.vu8.database.DatabaseAccess;

// Student: Bao Nam Vu - Assignment 3 - Basic Description Of Application
// Port 8080
// Normal Flow: index -> login(may access register) -> home(may access admin) ->  topic
// You can test on 2 accounts
//	"user"  / pass:"pass" /user role 
//  "admin" / pass:"pass" /user role and manager role 

// Application Functions
// + Announcements	-Read 
//                 	-Post new one(only manager)									
//					-Delete (only manager, NEED to use refresh button to update)- Apply JavaScript
// + Topic			-Read
// 					-Post new one(only manager)
// + Topic-Post		-Read
//					-Post new one
// + List User Name	-Read (only manager) - Apply JavaScript

// Some button you can miss:
// - backToDiscuss in topic page
// - adminPage (Top-Left) in home page, NormalUserPage button in admin page (Top-Left)
// - delete announcement (X symbol) in admin page
// - list button (list user) in admin page


@Controller
public class MyController {
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private DatabaseAccess db;

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/")
	public String getIndex() {
		return "index";
	}

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("addedTopic", new Topic());
		model.addAttribute("topicList", db.takeTopicList());
		model.addAttribute("annList",db.takeAnnList());
		return "home";
	}

	// When opening a specific topic
	@GetMapping("/topic/{topicNo}")
	public String getPost(Model model, @PathVariable int topicNo) {
		model.addAttribute("postList", db.takePostList(topicNo));
		model.addAttribute("topic", db.takeTopicById(topicNo));
		model.addAttribute("addedPost", new Post());
		return "topic";
	}

	@PostMapping("/addTopic")
	public String addTopic(@ModelAttribute Topic addedTopic) {
		// Get current log-in user in Spring Security
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		addedTopic.setTopicCreatedPerson(username);
		db.addTopic(addedTopic);
		return "redirect:/admin";
	}
	

	@PostMapping("/addPost/{topicNo}")
	public String addPost(@ModelAttribute Post post, @PathVariable int topicNo) {

		// Get current log-in user in Spring Security
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		post.setPostCreatedPerson(username);
		;
		db.addPost(post, topicNo);

		String redirect = "redirect:/topic/" + topicNo;

		return redirect;
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("addedAnn", new Announcement());
		model.addAttribute("annList",db.takeAnnList());
		model.addAttribute("addedTopic", new Topic());
		model.addAttribute("topicList", db.takeTopicList());
		return "admin";
	}
	
	@PostMapping("/addAnnouncement")
	public String addAnnouncement(@ModelAttribute Announcement addedAnn) {
		// Get current log-in user in Spring Security
				String username;
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (principal instanceof UserDetails) {
					username = ((UserDetails) principal).getUsername();
				} else {
					username = principal.toString();
				}
		addedAnn.setAnnCreatedPerson(username);
		db.addAnnouncement(addedAnn);
		return "redirect:/admin";
	}
	@GetMapping("/deleteAnn/{id}")
	@ResponseBody
	public String deleteAnn(@PathVariable int id){
			db.deleteAnnouncement(id);
		return "redirect:/admin";
	}
	
	
	@GetMapping(value="/getUserList", produces = "application/json")
	@ResponseBody
	public ArrayList<String> getUserList(){
		return db.getUserList();
	}
	
	@GetMapping("/register")
	public String register(Model model, UserRegistration user) {
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register")
	public String processRegister(@ModelAttribute UserRegistration user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		User newuser = new User(user.getUsername(), encodedPassword, authorities);
		jdbcUserDetailsManager.createUser(newuser);
		return "redirect:/";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied.html";
	}

}