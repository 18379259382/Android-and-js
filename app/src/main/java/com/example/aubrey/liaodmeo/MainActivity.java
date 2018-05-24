package com.example.aubrey.liaodmeo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mWebView = (WebView) findViewById(R.id.web_view);

        //默认不支持JS，导致部分网页加载不出来
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(this, "update");

        //mWebView.loadUrl("http://baidu.com");//加载云端数据
        mWebView.loadUrl("file:///android_asset/index.html");//加载本地数据

        //默认用系统浏览器或者第三方浏览器打开页面，设置后改用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:updateHtml()");
            }
        });
    }

    @JavascriptInterface
    public void startFunction() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("标题");
        dialog.setMessage("测试消息");

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //默认按返回键退出应用，改为回退到上一页
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
