import { Component, OnInit } from '@angular/core';
import { News } from '../news.model';
import { NewsService} from '../news.service';
import { NewsBarComponent } from '../news-bar/news-bar.component'

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  news$;
  news: News;
  newsCollection: any[];

  constructor( private newsService: NewsService) { }

  ngOnInit() {
    this.getNewsComponent('top-headlines?country=us');
  }

  getNewsComponent(category: String){
    this.newsService.getNews(category)
    .subscribe
    (data => {
      this.newsCollection = data.articles;
    });
  }

}