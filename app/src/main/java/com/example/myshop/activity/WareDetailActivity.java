package com.example.myshop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.myshop.R;
import com.example.myshop.common.Contants;
import com.example.myshop.models.Ware;
import com.example.myshop.service.CartService;
import com.example.myshop.util.ToastUtils;

import java.io.Serializable;

import dmax.dialog.SpotsDialog;

//一个商品详的细信息
//
public class WareDetailActivity extends Activity {

    private Ware ware;
    private WebView webView;
    private ImageView backImg;
    private CartService cartService;
    private SpotsDialog mDialog;
    private WebAppInterface mAppInterfce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        cartService = new CartService(this);
        webView = (WebView) findViewById(R.id.webView_ware);
        backImg = (ImageView) findViewById(R.id.ware_detail_back_img);
        mDialog = new SpotsDialog(this, "loading....");
        mDialog.show();

        Serializable serializable = getIntent().getSerializableExtra(Contants.WARE);
        if (serializable == null) {
            this.finish();
        } else {
            ware = (Ware) serializable;
        }
        initEvent();
        initWebView();
    }

    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareDetailActivity.this.finish();
            }
        });
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);


        webView.loadUrl(Contants.API.WARES_DETAIL);

        mAppInterfce = new WebAppInterface(this);
        webView.addJavascriptInterface(mAppInterfce, "appInterface");
        webView.setWebViewClient(new MyWebViewClient(this));
    }


    static class MyWebViewClient extends WebViewClient {
        private WareDetailActivity activity;

        public MyWebViewClient(WareDetailActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            if (activity.mDialog != null && activity.mDialog.isShowing()) {
                activity.mDialog.dismiss();
            }
            activity.mAppInterfce.showDetail();
        }
    }

    //webweivew调用的方法
    static class WebAppInterface {
        private WareDetailActivity activity;

        public WebAppInterface(WareDetailActivity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void showDetail() {
            //runOnUiThread:如果ui线程有其他任务就等待，没有就在ui线程执行
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    activity.webView.loadUrl("javascript:showDetail(" + activity.ware.getId() + ")");

                }
            });
        }

        @JavascriptInterface
        public void buy(long id) {
            activity.cartService.put(activity.ware);
            ToastUtils.show(activity, "已添加到购物车");

        }

        //收藏
        @JavascriptInterface
        public void addFavorites(long id) {

        }

    }


}
