package lixiaoqian.bwei.com.haonanshou;

import com.google.gson.Gson;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/21
 */

public class GsonUtil {

    private GsonUtil(){

    }

    private static Gson gson;

    static{
        if(gson == null){
            gson=new Gson();
        }
    }

    public static  <T> T analysisGson(String json,Class<T> tClass){
        T t = null;
        t=gson.fromJson(json,tClass);
        return t;
    }

}