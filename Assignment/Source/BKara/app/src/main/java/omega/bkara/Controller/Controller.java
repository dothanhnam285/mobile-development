package omega.bkara.Controller;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import omega.bkara.Model.VideoKaraoke;

/**
 * Created by Darka on 3/9/2016.
 */
public class Controller extends Application {

    private ArrayList<VideoKaraoke> appVideos;

    public void initDummyData() {

        Log.d("bkara controller", "Initialize dummy database...");

        appVideos = new ArrayList<VideoKaraoke>();

        VideoKaraoke vkSauTatCa = new VideoKaraoke("Sau tat ca",
                "https://www.youtube.com/watch?v=eEZpywMUzGI",
                "Erik",
                "Nhac Tre");

        VideoKaraoke vkEmCuaNgayHomQua = new VideoKaraoke("Em cua ngay hom qua",
                "https://www.youtube.com/watch?v=9tQa5B4iVsA",
                "Son Tung",
                "Nhac Tre");
        VideoKaraoke vkChiAnhHieuEm = new VideoKaraoke("Chi anh hieu em",
                "https://www.youtube.com/watch?v=lGi5ZRqGUSQ",
                "Khac Viet",
                "Nhac Tre");

        appVideos.add(vkSauTatCa);
        appVideos.add(vkEmCuaNgayHomQua);
        appVideos.add(vkChiAnhHieuEm);

        Log.d("bkara controller", "Initialize dummy database successfully");
    }

    public VideoKaraoke getVideo(int position) {
        Log.d("bkara controller", "Get video...");
        if (appVideos != null) {
            VideoKaraoke vk = appVideos.get(position);
            Log.d("bkara controller", "Get video successfully");
            return vk;
        }
        else {
            Log.d("bkara controller", "Cannot get video because list is null");
            return null;
        }
    }

    public int getVideoListSize() {
        Log.d("bkara controller", "Get video list size...");
        if (appVideos != null) {
            int size = appVideos.size();
            Log.d("bkara controller", "Get video list size successfully");
            return size;
        }
        else {
            Log.d("bkara controller", "Get video list size successfully - list is null");
            return 0;
        }
    }

}
