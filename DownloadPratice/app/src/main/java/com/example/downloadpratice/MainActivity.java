package com.example.downloadpratice;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    Button button;
    ImageView practiceIv;
    ImageView getPracticeIv;
    Button button2;
    Button btt;
    String path="https://api.bzqll.com/music/netease/url?id=487379429&key=579621905";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        practiceIv=findViewById(R.id.iv_practice);
        practiceIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(R.drawable.zxy).centerCrop().into(practiceIv);

        player=new MediaPlayer();
        button=findViewById(R.id.play_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //player.setDataSource(path);
                    final File file=new File(view.getContext().getCacheDir(),"my.mp3");
                    if (file.exists()){
                        file.delete();
                    }
                    final FileOutputStream outputStream=new FileOutputStream(file);
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(path).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            InputStream inputStream=response.body().byteStream();
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while( (len=inputStream.read(buffer)) != -1){
                                outputStream.write(buffer, 0, len);
                            }
                            outputStream.close();
                            outputStream.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("tag",file.getName());
                                        player.reset();
                                        player.setDataSource(file.getPath());
                                        player.prepare();
                                        player.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
