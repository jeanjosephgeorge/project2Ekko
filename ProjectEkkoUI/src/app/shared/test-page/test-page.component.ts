import { Component, OnInit, OnDestroy } from '@angular/core';
import { Reaction } from 'src/app/models/reaction.model';
import { AppUser } from 'src/app/models/user.model';
import { Comment } from 'src/app/models/comment.model';
import { Post } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.css']
})
export class TestPageComponent implements OnInit, OnDestroy {

  currentUserSubscription: Subscription;
  currentUser: AppUser;

  testPosts: Post[];
  postsSubscription: Subscription;


  constructor(private authService: AuthService, private postService: PostService) { }

  ngOnInit() {
    this.currentUserSubscription = this.authService.$currentUser.subscribe(user => {
      this.currentUser = user;

      this.authService.setTargetUser(this.currentUser);
    });

    this.postsSubscription = this.postService.$posts.subscribe(posts => {
      this.testPosts = posts;
    });
  }

  ngOnDestroy() {
    if (this.postsSubscription !== undefined) {
      this.postsSubscription.unsubscribe();
    }
  }

}
