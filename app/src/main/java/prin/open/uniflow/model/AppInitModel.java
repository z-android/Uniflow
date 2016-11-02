package prin.open.uniflow.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class AppInitModel extends BmobObject {
    public String app_version_code;
    public String app_update_uri;
    public String app_update_tag;
    public String issue_id;
    public String sumary;
    public String result_gif;
    public String update_at;

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getResult_gif() {
        return result_gif;
    }

    public void setResult_gif(String result_gif) {
        this.result_gif = result_gif;
    }

    public String getApp_version_code() {
        return app_version_code;
    }

    public void setApp_version_code(String app_version_code) {
        this.app_version_code = app_version_code;
    }

    public String getApp_update_uri() {
        return app_update_uri;
    }

    public void setApp_update_uri(String app_update_uri) {
        this.app_update_uri = app_update_uri;
    }

    public String getApp_update_tag() {
        return app_update_tag;
    }

    public void setApp_update_tag(String app_update_tag) {
        this.app_update_tag = app_update_tag;
    }

    public String getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(String issue_id) {
        this.issue_id = issue_id;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }
}
