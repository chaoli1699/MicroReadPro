package cn.lenovo.microreadpro.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aaron on 2017/4/8.
 */

public class MCollection {

    private String code;
    private String info;
    private List<Artical> collections;

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

    public List<Artical> getCollections() {
        return collections;
    }

    public void setCollections(List<Artical> collections) {
        this.collections = collections;
    }

    public static class Artical {

        private int aid;
        private String title;
        private String author;
        private String source;
        private int atid;
        private String image_path;
        private String detail_path;
        private String content;
        private int com_count;
        private String create_time;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getAtid() {
            return atid;
        }

        public void setAtid(int atid) {
            this.atid = atid;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

        public String getDetail_path() {
            return detail_path;
        }

        public void setDetail_path(String detail_path) {
            this.detail_path = detail_path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCom_count() {
            return com_count;
        }

        public void setCom_count(int com_count) {
            this.com_count = com_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
