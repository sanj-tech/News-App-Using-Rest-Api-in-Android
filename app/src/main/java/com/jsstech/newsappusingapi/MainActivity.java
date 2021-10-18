package com.jsstech.newsappusingapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryRvAdapter.CategoryClickInterface {
    //638862861e22488484510e9065e82784

    private RecyclerView newsRcv, categoryRcv;
    private ProgressBar loadingpd;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryRvModelArrayList;
    private CategoryRvAdapter categoryRvAdapter;
    private NewsRvAdapter newsRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRcv = findViewById(R.id.rvNewsVER);
        categoryRcv = findViewById(R.id.rvCategoriesHor);
        loadingpd = findViewById(R.id.progressBarLoad);

        articlesArrayList = new ArrayList<>();
        categoryRvModelArrayList = new ArrayList<>();

        newsRvAdapter = new NewsRvAdapter(articlesArrayList,this);
        categoryRvAdapter = new CategoryRvAdapter(categoryRvModelArrayList,this,this::onCreategoryClick);
        newsRcv.setLayoutManager(new LinearLayoutManager(this));
        newsRcv.setAdapter(newsRvAdapter);

        //categoryRcv.setLayoutManager(new LinearLayoutManager(this));
        categoryRcv.setAdapter(categoryRvAdapter);
        getCategory();
        getNews("All");
        newsRvAdapter.notifyDataSetChanged();
    }

    private void getCategory() {

        categoryRvModelArrayList.add(new CategoryModel("All","https://images.unsplash.com/photo-1570900808791-d530855f79e3?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YWxsfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("Technology","https://media.istockphoto.com/photos/abs-hologram-data-flow-grid-picture-id1254730455?b=1&k=20&m=1254730455&s=170667a&w=0&h=nANxB96R7w4W7Olnc2Wvq4bBlyRoq1Shif2jjtL1m2M="));
        categoryRvModelArrayList.add(new CategoryModel("Science","https://images.unsplash.com/photo-1507668077129-56e32842fceb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("Sports","https://images.unsplash.com/photo-1517649763962-0c623066013b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c3BvcnRzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("General","https://images.unsplash.com/photo-1493612276216-ee3925520721?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("Business","https://images.unsplash.com/photo-1508385082359-f38ae991e8f2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8YnVzaW5lc3N8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryModel("Health","https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aGVhbHRofGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRvAdapter.notifyDataSetChanged();

    }

    private void getNews(String category) {
        loadingpd.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=638862861e22488484510e9065e82784";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedat&language=en&apikey=638862861e22488484510e9065e82784";

        String BaseUrl = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetofitApi retofitApi = retrofit.create(RetofitApi.class);
        Call<NewsModel> call;
        if (category.equals("All")) {
            call = retofitApi.getAllNews(url);
        } else {
            call = retofitApi.getNewsByCategory(categoryUrl);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call,Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingpd.setVisibility(View.GONE);

                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),
                            articles.get(i).getContent()));
                }
                newsRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call,Throwable t) {
                Toast.makeText(MainActivity.this,"fail to get news",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreategoryClick(int position) {
        String category = categoryRvModelArrayList.get(position).getCategory();
        getNews(category);
    }
}