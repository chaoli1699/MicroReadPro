package cn.lenovo.microreadpro.model;

import java.util.List;

/**
 * Created by Aaron on 2017/5/5.
 */

public class MMessage {

    private String code;
    private String info;
    private List<Message> messages;

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public static class Message{
        private int mid;
        private int acid;
        private String head_path;
        private String username;
        private String comment;
        private int status;
        private String time_to_now;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getAcid() {
            return acid;
        }

        public void setAcid(int acid) {
            this.acid = acid;
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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTime_to_now() {
            return time_to_now;
        }

        public void setTime_to_now(String time_to_now) {
            this.time_to_now = time_to_now;
        }
    }
}
