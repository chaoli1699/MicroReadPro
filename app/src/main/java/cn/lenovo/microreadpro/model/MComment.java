package cn.lenovo.microreadpro.model;

import java.util.List;

/**
 * Created by Aaron on 2017/4/9.
 */

public class MComment {

    private String code;
    private String info;
    private List<Comment> comments;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment{

        private int acid;
        private String username;
        private String comment;
        private String com_time;

        public int getAcid() {
            return acid;
        }

        public void setAcid(int acid) {
            this.acid = acid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCom_time() {
            return com_time;
        }

        public void setCom_time(String com_time) {
            this.com_time = com_time;
        }
    }
}
