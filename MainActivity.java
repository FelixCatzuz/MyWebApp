package com.expl.mygithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.expl.mygithub.databinding.ActivityMainBinding;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    String url = "https://github.com/FelixCatzuz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.webView.loadUrl(url);

        //Поддрежка JavaScript
        WebSettings webSettings = activityMainBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Отображение контента по ширине браузера
        webSettings.setLoadWithOverviewMode(true);

        //Ссылка будет открываться непосредственно в браузере
        activityMainBinding.webView.setWebViewClient(new WebViewClient(){

            String currentUrl;

            //Создаем новый WebView-клиент и задаем открытие ссылки либо по протоколу
            //        http, либо https
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                currentUrl = url;
                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                }

                if (url.startsWith("Intent")){

                    //Сайт открывается напрямую в интернете
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        String fallbackUrl = intent.getStringExtra("browser_fallback_url");

                        if (fallbackUrl != null){
                            activityMainBinding.webView.loadUrl(fallbackUrl);
                            return true;
                        }
                    } catch (URISyntaxException e){
                        //Открытие не через Intent
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityMainBinding.webView.goBack();
    }
}