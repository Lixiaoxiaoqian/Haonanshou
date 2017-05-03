package lixiaoqian.bwei.com.haonanshou;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/22
 */

public class LeftFragment extends Fragment {

    private ListView left_lv;
    private List<GsonBean.DatalistBean> datalist;
    List<ListBean> list = new ArrayList<>();
    private int location=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, null);
        initView(view);
        initData1();
        return view;
    }

    private void initData1() {
        for(int i=0;i<10;i=i+2){
            list.add(new ListBean(i,(i+1)));
        }
        MyAdapter myAdapter = new MyAdapter();
        left_lv.setAdapter(myAdapter);
        left_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=location){
                    parent.getChildAt(location).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.YELLOW);
                RFragment rFragment = new RFragment();
                Bundle b=new Bundle();
                b.putInt("num1",list.get(position).getNum1());
                b.putInt("num2",list.get(position).getNum2());
                rFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.right_linear,rFragment,null).commit();
                rFragment.initData(getActivity());
                location=position;
            }
        });

    }

    private void initView(View view) {
        left_lv = (ListView) view.findViewById(R.id.left_lv);
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
            final ViewHolder holder;
            if(convertView==null){
                convertView=View.inflate(getActivity(),R.layout.lv_item,null);
                holder=new ViewHolder();
                holder.tv1= (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv1.setText("条目"+list.get(position).getNum1()+"  "+list.get(position).getNum2());
            return convertView;
        }
    }

    class ViewHolder{
        TextView tv1;
    }


}
