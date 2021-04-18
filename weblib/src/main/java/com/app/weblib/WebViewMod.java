package com.app.weblib;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.view.ViewTreeObserver;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewMod extends AppCompatActivity implements WebSettingHelper{



    public static ValueCallback<Uri[]> callback;
    public String filePath;


    public  WebView webView;
    Context context;
    public FrameLayout frameLayout;
    public WebSettings webSettings;

    private boolean exitByLastStep;
    private TouchListener touchListener;




    public WebViewMod(WebView webView, FrameLayout frameLayout,Context context) {
        this.webView = webView;
        this.frameLayout = frameLayout;
        this.context = context;
    }


    public void setHtml5CorrectSettings()
    {

        frameLayout.addView(webView);
        ChromeWebClientHelper chromeWebClientHelper = new ChromeWebClientHelper(context,frameLayout,webView);
        webView.setWebChromeClient(chromeWebClientHelper);
    }
    public void setCorrectKeyboardWithoutActionBar()
    {
        webView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                webView.getWindowVisibleDisplayFrame(r);
                int height = webView.getDisplay().getHeight();
                int botHeight = 0;
                int diff = height - r.bottom;
                if (diff != 0) {
                    if (webView.getRootView().getPaddingBottom() != diff) {
                        webView.getRootView().setPadding(0, 0, 0, diff+botHeight);
                    }
                } else {
                    if (webView.getRootView().getPaddingBottom() != 0) {
                        webView.getRootView().setPadding(0, 0, 0, 0);
                    }
                }
            }
        });
    }
    public FrameLayout getFrameLayout()
    {
        return frameLayout;
    }


    public void setHistorySwipeNavigate()
    {

        touchListener = new TouchListener() {
            @Override
            public void onSwipeLeft() {

                if(webView.canGoForward())
                {
                    webView.goForward();
                }

            }

            @Override
            public void onSwipeRight() {
                if(webView.canGoBack())
                {
                    webView.goBack();
                }
            }
        };
        webView.setOnTouchListener( new OnSwipeWebviewTouchListener(context , touchListener));
    }
    public void setUploadFile()
    {
    }
    public void setTruePageNavigate(boolean exitByLastStep)
    {
      this.exitByLastStep = exitByLastStep;
    }


    public WebView getCorrectWebView()
    {
        return webView;
    }


    @Override
    public void setBaseSetting() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webView.setWebViewClient(new WebViewClient());
    }



}
