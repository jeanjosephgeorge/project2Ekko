import { Component, OnInit } from '@angular/core';
import { News } from '../news.model';
import { NewsService} from '../news.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  news$;
  news: News;
  newsCollection: any[];
  category: String;

  constructor( private newsService: NewsService) { }

  ngOnInit() {
  }

  getNewsComponent(category: String){
    this.newsService.getNews(category)
    .subscribe
    (data => {
      this.newsCollection = data.articles;
      this.ngOnInit();
    })
  }
}