package com.app.weblib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import static android.view.View.VISIBLE;
import static com.app.weblib.WebViewMod.callback;


public class ChromeWebClientHelper extends WebChromeClient{

    Context context;

    private FrameLayout frameLayout;
    private View viewCustom;
    private WebView webView;
    private CustomViewCallback customViewCallback;

    public ChromeWebClientHelper(Context context, FrameLayout frameLayout,WebView webView) {
        this.context = context;
        this.frameLayout = frameLayout;
        this.webView = webView;
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (viewCustom == null)
            return;
        webView.setVisibility(VISIBLE);
        frameLayout.setVisibility(VISIBLE);
        viewCustom.setVisibility(View.GONE);
        frameLayout.removeView(viewCustom);
        viewCustom.setVisibility(View.GONE);
        customViewCallback.onCustomViewHidden();
        viewCustom = null;

    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        if (viewCustom != null) {
            callback.onCustomViewHidden();
            return; }
        viewCustom = view;
        webView.setVisibility(View.GONE);
        frameLayout.setVisibility(VISIBLE);
        frameLayout.addView(viewCustom);
        customViewCallback = callback;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
      //  return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        callback = filePathCallback;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        Activity activity = (Activity)webView.getContext();
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), 1);
        return true;
    }


}
