package com.jsstech.newsappusingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
String title,desc,content,imageURL,url;

private TextView titleTv,subDesTv,contentTv;
private ImageView newsIMgV;
private Button readBt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");

        titleTv=findViewById(R.id.idtvTitle);
        subDesTv=findViewById(R.id.idtvdesc);
        contentTv=findViewById(R.id.idcontent);
        newsIMgV=findViewById(R.id.idimg);
        readBt=findViewById(R.id.btReadfullNews);

        titleTv.setText(title);
        subDesTv.setText(desc);
        contentTv.setText(content);

        Picasso.get().load(imageURL).into(newsIMgV);

        readBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });






    }
}