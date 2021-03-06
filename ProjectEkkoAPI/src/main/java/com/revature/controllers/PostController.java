package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.daos.FriendListDao;
import com.revature.models.Post;
import com.revature.models.PostDto;
import com.revature.models.User;
import com.revature.services.PostService;
import com.revature.util.Log;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private Log log;
	
	@Autowired
	private FriendListDao flDao;

	@GetMapping("/posts/{uid}/{page}")
	public ResponseEntity<List<Post>> findByUserId(@PathVariable("uid") int uid, @PathVariable("page") int page) {
		log.info("Method: GET, uri: /posts/" + uid + "(user id)/" + page + "(page)");
		
		List<Post> posts = postService.findByUserId(uid, page);
		if (posts.size() == 0) {
			log.info("No record found.");
			return ResponseEntity.noContent().build();
		} else {
			String maxPage = postService.getMaxPageByUserId(uid);
			return ResponseEntity.status(HttpStatus.OK).header("X-page", maxPage).body(posts);
		}
	}
	
	@GetMapping("/posts/friends/{uid}/{page}")
	public ResponseEntity<List<Post>> findByFriends(@PathVariable("uid") int uid, @PathVariable("page") int page) {
		log.info("Method: GET, uri: /posts/friends/" + uid + "(user id)/" + page + "(page)");
		
		List<User> friends = flDao.findAll(uid);
		List<Post> posts = postService.findByFriends(friends, page);
		if (posts.size() == 0) {
			log.info("No record found.");
			return ResponseEntity.noContent().build();
		} else {
			String maxPage = postService.getMaxPageByFriends(friends);
			return ResponseEntity.status(HttpStatus.OK).header("X-page", maxPage).body(posts);
		}
	}

	@PostMapping("/posts")
	public ResponseEntity<Post> save(@RequestBody PostDto postDto) {
		log.info("Method: POST, uri: /posts");
		log.info("Data transfered: " + postDto);
		
		if (postDto != null) {
			postService.save(postDto.getUid(), new Post(postDto.getContent()));
			log.info("Successfully inserted the Post");
			return ResponseEntity.ok().build();
		} else {
			log.info("Request Body is not found.");
			return ResponseEntity.badRequest().build();
		}

	}
}
