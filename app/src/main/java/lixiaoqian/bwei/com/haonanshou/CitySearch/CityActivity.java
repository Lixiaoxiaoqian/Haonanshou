package lixiaoqian.bwei.com.haonanshou.CitySearch;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lixiaoqian.bwei.com.haonanshou.GsonUtil;
import lixiaoqian.bwei.com.haonanshou.R;
import lixiaoqian.bwei.com.haonanshou.mall.CommonAdapter;
import lixiaoqian.bwei.com.haonanshou.mall.ViewHolder;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    CommonAdapter<CityBean.ResultBean.RowsBean> adapter=null;
    private Button button;
    private EditText editText;
    private ListView lv;
    private TextView text_empty;
    private static File CacheRoot;
    private CommonAdapter<CityBean.ResultBean.RowsBean> mAdapter;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnected(this)) {
            getXutil(this);
        } else {
            String myCache = readJson(this, "MyCache");
            if (myCache != null) {
                CityBean cityBean = GsonUtil.analysisGson(myCache, CityBean.class);
                CityBean.ResultBean resultBean = cityBean.getResult();
                List<CityBean.ResultBean.RowsBean> rows = resultBean.getRows();
                CommonAdapter<CityBean.ResultBean.RowsBean> adapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(this, rows, R.layout.city_item) {
                    @Override
                    public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                        helper.setText(R.id.textView4, item.getInfo().getLoupan_name());
                        helper.setText(R.id.textView5, item.getInfo().getRegion_title());
                        helper.setImageByUrl(R.id.imageView2, item.getInfo().getDefault_image());
                    }
                };
                lv.setAdapter(adapter);
            }

        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void setNetworkMethod(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        }).show();
    }

    @SuppressWarnings("resource")
    public static String readJson(Context c, String fileName) {

        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String result = new String();
        File des = new File(CacheRoot, fileName);
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0)
                result = (String) ois.readObject();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        return result;
    }

    public static void writeJson(Context c, String json, String fileName,
                                 boolean append) {

        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            File ff = new File(CacheRoot, fileName);
            boolean boo = ff.exists();
            fos = new FileOutputStream(ff, append);
            os = new ObjectOutputStream(fos);
            if (append && boo) {
                FileChannel fc = fos.getChannel();
                fc.truncate(fc.position() - 4);

            }

            os.writeObject(json);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            if (os != null) {

                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public void getXutil(final Context context) {
        RequestParams params = new RequestParams("http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20160411091603");
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                writeJson(context, result, "MyCache", true);
                CityBean cityBean = GsonUtil.analysisGson(result, CityBean.class);
                CityBean.ResultBean resultBean = cityBean.getResult();
                List<CityBean.ResultBean.RowsBean> rows = resultBean.getRows();
                adapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(context, rows, R.layout.city_item) {
                    @Override
                    public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                        helper.setText(R.id.textView4, item.getInfo().getLoupan_name());
                        helper.setText(R.id.textView5, item.getInfo().getRegion_title());
                        helper.setText(R.id.textView6, item.getInfo().getNew_price_value());
                        helper.setImageByUrl(R.id.imageView2, item.getInfo().getDefault_image());
                    }
                };
                lv.setAdapter(adapter);
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

    public void getSearchXutil(final Context context, final String address) {
        RequestParams params = new RequestParams("http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20160411091603");
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                String myCache = readJson(context, "MyCache");
                if (myCache == null) {
                    writeJson(context, result, "MyCache", true);
                }
                CityBean cityBean = GsonUtil.analysisGson(result, CityBean.class);
                CityBean.ResultBean resultBean = cityBean.getResult();
                List<CityBean.ResultBean.RowsBean> rows = resultBean.getRows();
                List<CityBean.ResultBean.RowsBean> selist = new ArrayList<CityBean.ResultBean.RowsBean>();
                boolean flag = false;
                for (CityBean.ResultBean.RowsBean rb : rows) {
                    if (rb.getInfo().getRegion_title().equals(address)) {
                        selist.add(rb);
                        flag = true;
                    }
                }
                if (flag) {
                    text_empty.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    adapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(context, selist, R.layout.city_item) {
                        @Override
                        public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                            helper.setText(R.id.textView4, item.getInfo().getLoupan_name());
                            helper.setText(R.id.textView5, item.getInfo().getRegion_title());
                            helper.setText(R.id.textView6, item.getInfo().getNew_price_value());
                            helper.setImageByUrl(R.id.imageView2, item.getInfo().getDefault_image());
                        }
                    };
                    adapter.notifyDataSetChanged();
                } else {
                    text_empty.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.GONE);
                }
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

    private void initView() {
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        lv = (ListView) findViewById(R.id.lv);
        text_empty = (TextView) findViewById(R.id.text_empty);

        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String s = editText.getText().toString().trim();
                if (isNetworkConnected(this)) {
                    getSearchXutil(this, s);
                } else {
                    String myCache = readJson(this, "MyCache");
                    if (myCache != null) {
                        CityBean cityBean = GsonUtil.analysisGson(myCache, CityBean.class);
                        CityBean.ResultBean resultBean = cityBean.getResult();
                        List<CityBean.ResultBean.RowsBean> rows = resultBean.getRows();
                        List<CityBean.ResultBean.RowsBean> selist = new ArrayList<CityBean.ResultBean.RowsBean>();
                        boolean flag = false;
                        for (CityBean.ResultBean.RowsBean rb : rows) {
                            if (rb.getInfo().getRegion_title().equals(s)) {
                                selist.add(rb);
                                flag = true;
                            }
                        }
                        if (flag) {
                            text_empty.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            CommonAdapter<CityBean.ResultBean.RowsBean> adapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(this, selist, R.layout.city_item) {
                                @Override
                                public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                                    helper.setText(R.id.textView4, item.getInfo().getLoupan_name());
                                    helper.setText(R.id.textView5, item.getInfo().getRegion_title());
                                    helper.setText(R.id.textView6, item.getInfo().getNew_price_value());
                                    helper.setImageByUrl(R.id.imageView2, item.getInfo().getDefault_image());
                                }
                            };
                            lv.setAdapter(adapter);
                        } else {
                            text_empty.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                        }

                      /*  boolean flag=false;
                        for(CityBean.ResultBean.RowsBean rb:rows){
                            if(rb.getInfo().getRegion_title().equals(s)){
                                list.add(rb);
                                flag=true;
                            }
                        }

                        if(flag){
                            Log.d("zzz",list.size()+"hhzzz");
                            text_empty.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            mAdapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(this,list, R.layout.city_item) {
                                @Override
                                public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                                    helper.setText(R.id.textView4,item.getInfo().getLoupan_name());
                                    helper.setText(R.id.textView5,item.getInfo().getRegion_title());
                                    helper.setImageByUrl(R.id.imageView2,item.getInfo().getDefault_image());
                                }
                            };
                            lv.setAdapter(mAdapter);
                        }else{
                            text_empty.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                        }*/

                    }
                }

                break;
            case R.id.button2:
                getHighXutil(CityActivity.this);
                Toast.makeText(CityActivity.this, "从高到低排序", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:

                break;
        }
    }

    public void getHighXutil(final Context context) {
        RequestParams params = new RequestParams("http://api.fang.anjuke.com/m/android/1.3/shouye/recInfosV3/?city_id=14&lat=40.04652&lng=116.306033&api_key=androidkey&sig=9317e9634b5fbc16078ab07abb6661c5&macid=45cd2478331b184ff0e15f29aaa89e3e&app=a-ajk&_pid=11738&o=PE-TL10-user+4.4.2+HuaweiPE-TL10+CHNC00B260+ota-rel-keys%2Crelease-keys&from=mobile&m=Android-PE-TL10&cv=9.5.1&cid=14&i=864601026706713&v=4.4.2&pm=b61&uuid=1848c59c-185d-48d9-b0e9-782016041109&_chat_id=0&qtime=20160411091603");
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                //writeJson(context, result, "MyCache", true);
                CityBean cityBean = GsonUtil.analysisGson(result, CityBean.class);
                CityBean.ResultBean resultBean = cityBean.getResult();
                List<CityBean.ResultBean.RowsBean> rows = resultBean.getRows();
                List<CityBean.ResultBean.RowsBean> rows1 = new ArrayList<CityBean.ResultBean.RowsBean>();
                List<CityBean.ResultBean.RowsBean> rows2 = new ArrayList<CityBean.ResultBean.RowsBean>();
                for(CityBean.ResultBean.RowsBean crr:rows){
                   if(crr.getInfo().getNew_price_back().equals("元/m²")){
                        rows2.add(crr);
                    }else{
                        rows1.add(crr);
                   }
                }
                //Collections.sort(rows1,new CalendarComparator());
                Collections.sort(rows2,new CalendarComparator());
                rows.clear();
                rows.addAll(rows2);
                rows.addAll(rows2.size(),rows1);
                for(int x=0; x< rows.size(); x++){
                    Log.d("zzz",rows.get(x).getInfo().getNew_price_value()+rows.get(x).getInfo().getNew_price_back());
                }
                adapter = new CommonAdapter<CityBean.ResultBean.RowsBean>(context, rows, R.layout.city_item) {
                    @Override
                    public void convert(ViewHolder helper, CityBean.ResultBean.RowsBean item) {
                        helper.setText(R.id.textView4, item.getInfo().getLoupan_name());
                        helper.setText(R.id.textView5, item.getInfo().getRegion_title());
                        helper.setText(R.id.textView6, item.getInfo().getNew_price_value());
                        helper.setImageByUrl(R.id.imageView2, item.getInfo().getDefault_image());
                    }
                };
                lv.setAdapter(adapter);
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

    static class CalendarComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            CityBean.ResultBean.RowsBean p1 = (CityBean.ResultBean.RowsBean) object1; // 强制转换
            CityBean.ResultBean.RowsBean p2 = (CityBean.ResultBean.RowsBean) object2;
            return Integer.valueOf(p2.getInfo().getNew_price_value()).compareTo(Integer.valueOf(p1.getInfo().getNew_price_value()));

        }
    }
}
