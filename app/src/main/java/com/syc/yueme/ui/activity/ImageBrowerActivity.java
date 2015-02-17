package com.syc.yueme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.syc.yueme.adapter.ChatMsgAdapter;
import com.syc.yueme.R;

/**
 * Created by lzw on 14-9-21.
 */
public class ImageBrowerActivity extends BaseActivity {
  String url;
  String path;
  ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chat_image_brower_layout);
    imageView = (ImageView) findViewById(R.id.imageView);
    Intent intent = getIntent();
    url = intent.getStringExtra("url");
    path = intent.getStringExtra("path");
    ChatMsgAdapter.displayImageByUri(imageView, path, url);
  }
}
