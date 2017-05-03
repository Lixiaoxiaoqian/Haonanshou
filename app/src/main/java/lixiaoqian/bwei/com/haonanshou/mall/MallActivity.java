package lixiaoqian.bwei.com.haonanshou.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import lixiaoqian.bwei.com.haonanshou.GsonUtil;
import lixiaoqian.bwei.com.haonanshou.R;

public class MallActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout ll_dots;
    private GridView brand_gv;
    private TextView text_nation;
    private GridView goods_gv;
    private List<GsonBean.ResultBean.NationsBean> mNations;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                // 获取viewPager当前所在的页码索引值
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        };
    };
    private List<ImageView> dotsList=new ArrayList<>();
    private List<GsonBean.ResultBean.AdvsBean> mAdvs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getXutil(this);
        text_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MallActivity.this,NationActivity.class);
                startActivity(intent);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < dotsList.size(); i++) {
                    if (i == arg0 % mAdvs.size()) {
                        dotsList.get(i).setImageResource(R.drawable.dots_focus);
                    } else {
                        dotsList.get(i)
                                .setImageResource(R.drawable.dots_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private void initDots() {
        dotsList = new ArrayList<ImageView>();
        dotsList.clear();
        ll_dots.removeAllViews();
        for (int i = 0; i < mAdvs.size(); i++) {
            // 画出圆点
            ImageView imageView = new ImageView(this);
            //
            if (i == 0) {
                imageView.setImageResource(R.drawable.dots_focus);
            } else {
                imageView.setImageResource(R.drawable.dots_normal);
            }
            // 宽和高 dp 相对像素
            // 如果是真正开发，需要转成 px值 屏幕适配 dp---px
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(10, 10);
            // 放到什么位置
           // params.setMargins(5, 0, 5, 0);
            dotsList.add(imageView);
            ll_dots.addView(imageView, params);
        }

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        brand_gv = (GridView) findViewById(R.id.brand_gv);
        text_nation = (TextView) findViewById(R.id.text_nation);
        goods_gv = (GridView) findViewById(R.id.goods_gv);
    }

    public void getXutil(final Context context) {
        RequestParams params = new RequestParams("http://www.babybuy100.com/API/getShopOverview.ashx");

        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                GsonBean gsonBean = GsonUtil.analysisGson(result, GsonBean.class);
                GsonBean.ResultBean gsonBeanResult = gsonBean.getResult();
                mAdvs = gsonBeanResult.getAdvs();
                if(mAdvs.size()!=0){
                    viewPager.setAdapter(new MyPagerAdapter(mAdvs, MallActivity.this, handler));
                    // 设置当前的一个条目值
                    viewPager.setCurrentItem(mAdvs.size() * 6000);
                    // 初始化小圆点
                    initDots();
                }
                List<GsonBean.ResultBean.BrandsBean> brands = gsonBeanResult.getBrands();
                List<GsonBean.ResultBean.IndexProductsBean> indexProducts = gsonBeanResult.getIndexProducts();
                CommonAdapter<GsonBean.ResultBean.BrandsBean> brandAdapter = new CommonAdapter<GsonBean.ResultBean.BrandsBean>(context, brands, R.layout.brand_item) {
                    @Override
                    public void convert(ViewHolder helper, GsonBean.ResultBean.BrandsBean item) {
                        helper.setText(R.id.brand_tv,item.getTitle());
                    }
                };
                brand_gv.setAdapter(brandAdapter);
                CommonAdapter<GsonBean.ResultBean.IndexProductsBean> goodsAdapter = new CommonAdapter<GsonBean.ResultBean.IndexProductsBean>(context, indexProducts, R.layout.goods_item) {
                    @Override
                    public void convert(ViewHolder helper, GsonBean.ResultBean.IndexProductsBean item) {
                        helper.setText(R.id.goods_tv1,item.getName());
                        helper.setText(R.id.goods_tv2,item.getPrice()+"");
                    }
                };
                goods_gv.setAdapter(goodsAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
    }
    
}
