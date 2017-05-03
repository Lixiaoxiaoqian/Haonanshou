package lixiaoqian.bwie.com.commonadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView id_lv_main;
    private ListView id_lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        List<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("青花" + i);
        }
        CommonAdapter<String> mAdapter = new CommonAdapter<String>(getApplicationContext()
                , mDatas, R.layout.lv_item) {
            @Override
            public void convert(ViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.textView, item);
            }
        };
        id_lv_main.setAdapter(mAdapter);


        List<Bean> mData2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData2.add(new Bean("蓝色土耳其"+i,"关不上的窗"+i));
        }
        CommonAdapter<Bean> mAdapter2 = new CommonAdapter<Bean>(getApplicationContext()
                , mData2, R.layout.lv2_item) {
            @Override
            public void convert(ViewHolder viewHolder, Bean item) {
                viewHolder.setText(R.id.textView2, item.getStr1());
                viewHolder.setText(R.id.textView3, item.getStr2());
            }
        };
        id_lv2.setAdapter(mAdapter2);


    }

    private void initView() {
        id_lv_main = (ListView) findViewById(R.id.id_lv_main);
        id_lv2 = (ListView) findViewById(R.id.id_lv2);

    }

}
