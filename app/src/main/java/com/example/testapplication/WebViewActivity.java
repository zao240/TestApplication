package com.example.testapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //开启调试模式
        //在谷歌浏览器输入:chrome://inspect进入
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }


        webView = findViewById(R.id.web_view);
        //方法1
        webView.loadUrl("https://www.baidu.com");
        //方法2
        webView.loadUrl("file://" + Environment.getExternalStorageDirectory().getPath());
        //方法3
        webView.loadUrl("file://android_asset/index.html");
        //方法4
        webView.loadData("<h1>html</h1>", "text/html", "utf-8");
        //方法5
        //加载图片,调用webView.goBack时会返回搜狐网站
//        webView.loadDataWithBaseURL("https://www.baidu.com/",
//                "<img src=\"static/img.png\">", "text/html",
//                "utf-8", "https://www.souhu.com");
        //方法6
        webView.setWebViewClient(new WebViewClient() {
            //当网页访问发送错误时进行回调
            //此重载方法适用于所有Android版本
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
            //此重载方法只适用于Android6.0以上的版本
            //可以从此方法中，给用户一个良好的体验
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            //webview要加载新的url时候进行回调
            //当返回true时，表示此次请求不需要WebView去处理，这时候我们可以做一些自定义的处理
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Map<String, String> map = new HashMap<>();
                map.put("android-test", "android-demo");
                webView.loadUrl("https://www.baidu.com", map);
                return super.shouldOverrideUrlLoading(view, url);
            }
            //此重载方法在Android7.0以上才可以使用
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            //进行资源请求时进行回调
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }
            //进行资源请求时回调，Android5.0以上才会回调这个方法，并且一并回调上边那个重载的方法
            //当返回为null时，WebView表示不拦截，显示原本的url
            //当返回值不为null，WebView会认为要显示我们自定义的WebResourceResponse
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);//return null
            }
            //当页面已经开始加载时回调
            //第三个参数为网站图标
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            //加载资源之前回调
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            //网页加载完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            //在页面加载的过程中进行回调，newprogress为进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: newProgress:" + newProgress);
                super.onProgressChanged(view, newProgress);
            }
            //获取网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG, "onReceivedTitle: title:" + title);
                super.onReceivedTitle(view, title);
            }
            //在js将要调用alert时回调
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, "onJsAlert: ");
                return super.onJsAlert(view, url, message, result);
            }
            //在js将要调用confim时回调
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, "onJsConfirm: ");
                return super.onJsConfirm(view, url, message, result);
            }

            //在js中将要弹出prompt时进行回调，若返回true则说明WebView会接手这次处理
            //返回true如果不做处理，WebView则会卡住，原因是js还在等待我们的处理
            //通常来说可以通过JsPromptResult参数来做处理，如展示我们自定义的对话框来替换掉js中的prompt
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG, "onJsPrompt: ");
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void handleAndroidCallJs(){
        //当在API19以下的机器像接收JS的返回值时，可以使用WebViewChromeClient的onJsAlert方法来取巧接收
        webView.loadUrl("javascript:alert(sum(2,3))");
        //此方法只能在API19以后才能使用，非常方便可用于接收js的返回值
        webView.evaluateJavascript("javascript:alert(sum(2,3))", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(TAG, "onReceiveValue: value:" + value);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void handleJsCallAndroid(){
        //可以使用WebViewClient的shouldOverrideUrlLoading方法进行对url拦截，通过自定义协议来判断调用Android的哪个方法
        //也可以使用@JavaScriptInterface注解来调用对应的Java代码
        //例如下列代码，只需要在Js中调用name.methodName即可
        webView.addJavascriptInterface(new Object(),"name");
    }
    public void handleForwardOrBack(){
        //判断是否可用前进、后退，带参数的方法：正数为前进几次，负数为后退几次
        webView.canGoBack();
        webView.canGoForward();
        webView.canGoBackOrForward(2);
        //立马后退、前进
        webView.goBack();
        webView.goForward();
        webView.goBackOrForward(2);
        //清除历史记录
        webView.clearHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        //恢复所有webView的状态
//        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
//        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    public void webViewSetting(){
        WebSettings settings = webView.getSettings();
        //允许运行Js代码
        settings.setJavaScriptEnabled(true);

        //是否支持缩放
        settings.setSupportZoom(true);
        //设置内置的缩放控件
        settings.setBuiltInZoomControls(true);
        //是否隐藏原生的缩放控件
        settings.setDisplayZoomControls(true);
        //只要有缓存，只会使用本地缓存，没有才用网络
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //只去使用缓存，不使用网络
        settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        //默认，根据cache-control决定是否从网页中获取
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //永远不使用缓存，只从网络中获取
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
}