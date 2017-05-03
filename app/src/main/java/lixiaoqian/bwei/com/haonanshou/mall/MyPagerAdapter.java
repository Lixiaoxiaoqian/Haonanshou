package lixiaoqian.bwei.com.haonanshou.mall;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import lixiaoqian.bwei.com.haonanshou.DisplayImageOptionsUtils;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/28
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<GsonBean.ResultBean.AdvsBean> imageUrls;
    private MallActivity context;
    private Handler handler;

    public MyPagerAdapter( List<GsonBean.ResultBean.AdvsBean> imageUrls, MallActivity context,
                          Handler handler) {
        super();
        this.imageUrls = imageUrls;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 手指按下的时候,停止自动轮播的任务
                        // 移除所有的消息及回调 null 移除所有
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        ImageLoader.getInstance().displayImage(imageUrls.get(position % imageUrls.size()).getPic(),imageView, DisplayImageOptionsUtils.initOptions());
        Log.d("zzz",imageUrls.get(position % imageUrls.size()).getUrl()+"zzz");
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
