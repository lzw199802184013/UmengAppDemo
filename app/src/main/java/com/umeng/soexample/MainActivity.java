package com.umeng.soexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.img);
        findViewById(R.id.xinlang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sinaLogin();
//                sina();
                qqLogin();
//                qq();
//                weiXinLogin();
            }
        });
    }
//    //新浪登录
//    public void sinaLogin() {
//        authorization(SHARE_MEDIA.SINA);
//    }
    //新浪分享
//    public void sina() {
//        ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
//                , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.ic_launcher, SHARE_MEDIA.SINA
//        );
//    }
    //qq登录
public void qqLogin() {
    authorization(SHARE_MEDIA.QQ);
}
//qq分享
////public void qq() {
////    ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
////            , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.ic_launcher, SHARE_MEDIA.QQ
////    );
//}
    //微信登录
//public void weiXinLogin() {
//    authorization(SHARE_MEDIA.WEIXIN);
//}
    //微信分享
//public void weiXin(View view) {
//    ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
//            , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN
//    );
//}
    //微信朋友圈分享
//public void weixinCircle(View view) {
//    ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
//            , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN_CIRCLE
//    );
//}
    //qq空间分享
//public void Qzone(View view) {
//    ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
//            , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.ic_launcher, SHARE_MEDIA.QZONE
//    );
//}

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();
                Picasso.with(MainActivity.this).load(iconurl).fit().into(img);
                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
