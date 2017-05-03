package lixiaoqian.bwei.com.haonanshou;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/24
 */

public class DisplayImageOptionsUtils {

    public static DisplayImageOptions initOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)            //加载图片时的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)         //没有图片资源时的默认图片
                .showImageOnFail(R.mipmap.ic_launcher)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                .build();
        return  options;
    }

}
