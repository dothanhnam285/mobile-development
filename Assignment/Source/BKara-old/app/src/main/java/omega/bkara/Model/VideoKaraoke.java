package omega.bkara.Model;

/**
 * Created by Darka on 3/9/2016.
 */
public class VideoKaraoke {
    private String _title;
    private String _link;
    private String _singer;
    private String _type;

    public VideoKaraoke(String title, String link, String singer, String type) {
        _title = title;
        _link = link;
        _singer = singer;
        _type = type;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getLink() {
        return _link;
    }

    public void setLink(String link) {
        this._link = link;
    }

    public String getSinger() {
        return _singer;
    }

    public void setSinger(String singer) {
        this._singer = singer;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }
}
