package prin.open.uniflow.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhongzihuan on 2016/10/12.
 */
public class WebPagerObject extends BmobObject {

    public String wp_from;
    public String wp_url;
    public String wp_read_times;

    public String getWp_from() {
        return wp_from;
    }

    public void setWp_from(String wp_from) {
        this.wp_from = wp_from;
    }

    public String getWp_url() {
        return wp_url;
    }

    public void setWp_url(String wp_url) {
        this.wp_url = wp_url;
    }

    public String getWp_read_times() {
        return wp_read_times;
    }

    public void setWp_read_times(String wp_read_times) {
        this.wp_read_times = wp_read_times;
    }
}
