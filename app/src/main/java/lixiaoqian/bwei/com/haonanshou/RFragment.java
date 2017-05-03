package lixiaoqian.bwei.com.haonanshou;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/22
 */

public class RFragment extends Fragment {

    private ListView right_lv;
    private List<GsonBean.DatalistBean> datalist=new ArrayList<>();
    private List<GsonBean.DatalistBean> list=new ArrayList<>();
    private View mView;
    private ConnectivityManager manager;
    private Context mcontext;
    private MyAdapter mMyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.right_fragment, null);
        right_lv= (ListView) mView.findViewById(R.id.right_lv);
        return mView;
    }

    public  void initData(Context context) {
        mcontext=context;
        boolean f=isOpenNetwork(context);
        Log.d("zzz",f+"");
        getXutil(context);
        }

    private void writeDataToLocal(Context context,String json, String path, long time) {
       if(context!=null){
           File cacheDir = context.getCacheDir();
           File file = null;
           try {
               file = new File(cacheDir, path);
           } catch (Exception e) {
               e.printStackTrace();
           }
           if (!file.exists()) {
               try {
                   file.createNewFile();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           BufferedWriter bufferedWriter = null;
           try {
               bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
               //bufferedWriter.write(System.currentTimeMillis() + time + "\r\n");
               bufferedWriter.write(json);
               bufferedWriter.flush();

           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               try {
                   if (bufferedWriter != null)
                       bufferedWriter.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

    }

    private String getDataFromLocal(Context context,String path, long time) {
        if(context!=null){
            File cacheDir = context.getCacheDir();
            File file = null;
            try {
                file = new File(cacheDir, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                //之前时间＋有效时间
                //long t = Long.parseLong(bufferedReader.readLine());
                //在有效时间之内
                //90 +10
               // if (System.currentTimeMillis() - t < 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
               // }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public boolean isOpenNetwork(Context context) {
        Log.d("zzz",context+"zz");
        if(context!=null){
            ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connManager.getActiveNetworkInfo() != null) {
                return connManager.getActiveNetworkInfo().isAvailable();
            }
        }

        return false;
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=View.inflate(mcontext,R.layout.rlv_item,null);
                holder=new ViewHolder();
                holder.tv1= (TextView) convertView.findViewById(R.id.textView2);
                holder.tv2= (TextView) convertView.findViewById(R.id.textView3);
                holder.img= (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv1.setText(list.get(position).getCourse_tname());
            holder.tv2.setText(list.get(position).getCourse_name());
            ImageLoader.getInstance().displayImage(list.get(position).getCourse_pic(),holder.img,DisplayImageOptionsUtils.initOptions());
            return convertView;
        }
    }

    class ViewHolder{
        TextView tv1;
        TextView tv2;
        ImageView img;
    }

    public  void  getXutil(final Context context){
        RequestParams params = new RequestParams("http://www.meirixue.com/api.php?c=list&a=index");

        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                GsonBean gasonBean = GsonUtil.analysisGson(result, GsonBean.class);
                writeDataToLocal(context,result, "mydata", 10000);
                Log.d("zzz",result.toString());
                datalist = gasonBean.getDatalist();
                Bundle arguments = getArguments();
                int num1 = arguments.getInt("num1");
                int num2 = arguments.getInt("num2");
                for(int i=0;i<datalist.size();i++){
                    if(i==num1){
                        list.add(datalist.get(i));
                    }else if(i==num2){
                        list.add(datalist.get(i));
                    }
                }
                mMyAdapter = new MyAdapter();
                right_lv.setAdapter(mMyAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                String mydata = getDataFromLocal(context,"mydata", 30000);
                if(mydata!=null){
                    try {
                        HEHE hehe = GsonUtil.analysisGson(mydata, HEHE.class);
                        List<GsonBean.DatalistBean> datalist = hehe.getDatalist();
                        Log.d("zzz",datalist.size()+"");
                        if(datalist.size()!=0){
                            Bundle arguments = getArguments();
                            int num1 = arguments.getInt("num1");
                            int num2 = arguments.getInt("num2");
                            for(int i = 0; i< datalist.size(); i++){
                                if(i==num1){
                                    list.add(datalist.get(i));
                                }else if(i==num2){
                                    list.add(datalist.get(i));
                                }
                            }
                            Log.d("zzz",list.size()+"");
                            MyAdapter myAdapter = new MyAdapter();
                            right_lv.setAdapter(myAdapter);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

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
