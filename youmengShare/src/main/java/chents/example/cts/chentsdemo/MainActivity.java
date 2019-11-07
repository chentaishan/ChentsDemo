package chents.example.cts.chentsdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks, View.OnClickListener {

    private Button mClick;

    private int REQUEST_FILE_CODE = 99;
    String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    private Button mSina;
    private Button mQq;
    private Button mWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        if (EasyPermissions.hasPermissions(this, permissions)) {

            initView();

        } else {

            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, REQUEST_FILE_CODE, permissions)
                            .setRationale("请确认相关权限！！")
                            .setPositiveButtonText("ok")
                            .setNegativeButtonText("cancal")
//                            .setTheme(R.style.my_fancy_style)
                            .build());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private static final String TAG = "MainActivity";

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Log.d(TAG, "onPermissionsGranted: 用户接受确认权限");
        initView();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {


        Log.d(TAG, "onPermissionsDenied: 用户拒绝权限确认");
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

        Log.d(TAG, "onRationaleAccepted: ");
    }

    @Override
    public void onRationaleDenied(int requestCode) {

        Log.d(TAG, "onRationaleDenied: ");
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("type","book");
                map.put("quantity","3");
                MobclickAgent.onEvent(MainActivity.this, "purchase", map);

                UMImage image = new UMImage(MainActivity.this, "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");//网络图片

                image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                //压缩格式设置
                image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                new ShareAction(MainActivity.this)
                        .withText("hello")// 文本内容
                        .withMedia(image)// 媒体内容
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();

                Log.d(TAG, "onClick: ");
            }
        });
        mSina = (Button) findViewById(R.id.sina);
        mSina.setOnClickListener(this);
        mQq = (Button) findViewById(R.id.qq);
        mQq.setOnClickListener(this);
        mWechat = (Button) findViewById(R.id.wechat);
        mWechat.setOnClickListener(this);
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "成功 了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this, "失  败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sina:
                // TODO 19/07/02

                UMShareAPI umShareAPI = UMShareAPI.get(this);
                umShareAPI.getPlatformInfo( this, SHARE_MEDIA.SINA, new MyUMAuthListener());

                break;
            case R.id.qq:
                // TODO 19/07/02

                  umShareAPI = UMShareAPI.get(this);
                umShareAPI.getPlatformInfo( this, SHARE_MEDIA.QQ, new MyUMAuthListener());

                break;
            case R.id.wechat:
                // TODO 19/07/02
                umShareAPI = UMShareAPI.get(this);
                umShareAPI.getPlatformInfo( this, SHARE_MEDIA.WEIXIN, new MyUMAuthListener());

                break;

        }
    }

    class MyUMAuthListener implements UMAuthListener{

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            for (Map.Entry<String, String> entry :map.entrySet() ) {

                Log.d(TAG, "onComplete: "+entry.getKey()+"--"+entry.getValue());
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    }

}
