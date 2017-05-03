package lixiaoqian.bwei.com.haonanshou.mall;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import lixiaoqian.bwei.com.haonanshou.GsonUtil;
import lixiaoqian.bwei.com.haonanshou.R;

public class NationActivity extends AppCompatActivity {

    private ListView nation_lv;
    private List<GsonBean.ResultBean.NationsBean> mNations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation);
        initView();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getXutil(this);
    }

    private void initView() {
        nation_lv = (ListView) findViewById(R.id.nation_lv);
    }

    public void getXutil(final Context context) {
        RequestParams params = new RequestParams("http://www.babybuy100.com/API/getShopOverview.ashx");
        params.setCacheMaxAge(1000*50);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return true;
            }

            public void onSuccess(String result) {
                GsonBean gsonBean = GsonUtil.analysisGson(result, GsonBean.class);
                GsonBean.ResultBean gsonBeanResult = gsonBean.getResult();
                mNations = gsonBeanResult.getNations();
                CommonAdapter<GsonBean.ResultBean.NationsBean> nationAdapter = new CommonAdapter<GsonBean.ResultBean.NationsBean>(context, mNations, R.layout.nation_item) {
                    @Override
                    public void convert(ViewHolder helper, GsonBean.ResultBean.NationsBean item) {
                        helper.setText(R.id.nation_tv,item.getName());
                        helper.setImageByUrl(R.id.nation_img,item.getFlagPic());
                    }
                };
                nation_lv.setAdapter(nationAdapter);
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
