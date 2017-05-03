package cn.lenovo.microreadpro.model;

import java.util.List;

/**
 * Created by Aaron on 2017/5/3.
 */

public class MUFeture {

    private int code;
    private String info;
    private List<UFeture> ufetures;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<UFeture> getUfetures() {
        return ufetures;
    }

    public void setUfetures(List<UFeture> ufetures) {
        this.ufetures = ufetures;
    }

    public class UFeture{
        private int ufid;
        private String name_eg;
        private String name_cn;
        private String head_path;
        private String username;
        private String icon_path;
        private int show_rrow;
        private int is_button;
        private int notify_num;
        private String group;
        private int group_top;
        private String value;

        public UFeture(int ufid, String name_eg, String name_cn, String head_path, String username, String icon_path, int show_rrow, int is_button, int notify_num, String group, int group_top, String value) {
            this.ufid = ufid;
            this.name_eg = name_eg;
            this.name_cn = name_cn;
            this.head_path = head_path;
            this.username = username;
            this.icon_path = icon_path;
            this.show_rrow = show_rrow;
            this.is_button = is_button;
            this.notify_num = notify_num;
            this.group = group;
            this.group_top = group_top;
            this.value=value;
        }

        public int getUfid() {
            return ufid;
        }

        public void setUfid(int ufid) {
            this.ufid = ufid;
        }

        public String getName_eg() {
            return name_eg;
        }

        public void setName_eg(String name_eg) {
            this.name_eg = name_eg;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getHead_path() {
            return head_path;
        }

        public void setHead_path(String head_path) {
            this.head_path = head_path;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIcon_path() {
            return icon_path;
        }

        public void setIcon_path(String icon_path) {
            this.icon_path = icon_path;
        }

        public int getShow_rrow() {
            return show_rrow;
        }

        public void setShow_rrow(int show_rrow) {
            this.show_rrow = show_rrow;
        }

        public int getIs_button() {
            return is_button;
        }

        public void setIs_button(int is_button) {
            this.is_button = is_button;
        }

        public int getNotify_num() {
            return notify_num;
        }

        public void setNotify_num(int notify_num) {
            this.notify_num = notify_num;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public int getGroup_top() {
            return group_top;
        }

        public void setGroup_top(int group_top) {
            this.group_top = group_top;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
