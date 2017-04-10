package cn.lenovo.microreadpro.model;

import java.util.List;

/**
 * Created by Aaron on 2017/4/9.
 */

public class MComment {

    private String code;
    private String info;
    private int com_count;
    private List<PComment> comments;

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

    public int getCom_count() {
        return com_count;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }

    public List<PComment> getComments() {
        return comments;
    }

    public void setComments(List<PComment> comments) {
        this.comments = comments;
    }

    public static class CComment extends Comment {
        private int accid;

        public int getAccid() {
            return accid;
        }

        public void setAccid(int accid) {
            this.accid = accid;
        }
    }

    public static class PComment extends Comment {
        private int acid;
        private List<CComment> child_com;

        public int getAcid() {
            return acid;
        }

        public void setAcid(int acid) {
            this.acid = acid;
        }

        public List<CComment> getChild_com() {
            return child_com;
        }

        public void setChild_com(List<CComment> child_com) {
            this.child_com = child_com;
        }
    }

    public static class Comment{

        private String username;
        private String comment;
        private String time_to_now;

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

        public String getTime_to_now() {
            return time_to_now;
        }

        public void setTime_to_now(String time_to_now) {
            this.time_to_now = time_to_now;
        }
    }
}
