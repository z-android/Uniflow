package prin.open.uniflow.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import prin.open.uniflow.R;
import prin.open.uniflow.utils.ZToastUtil;

/**
 * Created by tmac on 2016/1/29.
 */
public class ShareActivity extends FragmentActivity implements PlatformActionListener {

    private static final String TITLE_URL = "http://www.aimoge.com/app/download/kuaidi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        //1、分享的初始化
        ShareSDK.initSDK(this);
    }


    @OnClick({R.id.txt_wx_friends, R.id.txt_wx_circle, R.id.txt_qq_share, R.id.txt_qzone_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_wx_friends:
                shareAction(Wechat.NAME);
                break;
            case R.id.txt_wx_circle:
                shareAction(WechatMoments.NAME);
                break;
            case R.id.txt_qq_share:
                shareAction(QQ.NAME);
                break;
            case R.id.txt_qzone_share:
                shareAction(QZone.NAME);
                break;
        }
    }

    private void shareAction(String platform) {

        Platform.ShareParams sp = new Platform.ShareParams();

        if (platform.equals(WechatMoments.NAME) || platform.equals(Wechat.NAME)) {
            sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
            sp.setUrl(TITLE_URL);
        } else {
            sp.setTitleUrl(TITLE_URL);  //网友点进链接后，可以看到分享的详情
        }

        String title = getString(R.string.share_title);
        sp.setTitle(title);

        String content = getString(R.string.share_content);
        sp.setText(content);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.share);
        sp.setImageData(bitmap);

        //3、非常重要：获取平台对象
        Platform qq = ShareSDK.getPlatform(platform);
        qq.setPlatformActionListener(this); // 设置分享事件回调
        // 执行分享
        qq.share(sp);
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ZToastUtil.show("分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        ZToastUtil.show("分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ZToastUtil.show("分享取消");
    }
}
