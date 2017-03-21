package cn.lenovo.microreadpro.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aaron on 2017/3/2.
 */

public class ArticalBox implements Serializable{

    private String box_title;
    private String box_path;
    private List<Artical> articals;



    public String getBox_title() {
        return box_title;
    }



    public void setBox_title(String box_title) {
        this.box_title = box_title;
    }



    public String getBox_path() {
        return box_path;
    }



    public void setBox_path(String box_path) {
        this.box_path = box_path;
    }



    public List<Artical> getArticals() {
        return articals;
    }



    public void setArticals(List<Artical> articals) {
        this.articals = articals;
    }



    public static class Artical implements Serializable{

        private String title;
        private String author;
        private String publishDate;
        private String detailPath;
        private String shortDesc;
        private String imagePath;
        private String label;
        private String otherAttr;
        private String content;
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
        public String getPublishDate() {
            return publishDate;
        }
        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }
        public String getDetailPath() {
            return detailPath;
        }
        public void setDetailPath(String detailPath) {
            this.detailPath = detailPath;
        }
        public String getImagePath() {
            return imagePath;
        }
        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public String getOtherAttr() {
            return otherAttr;
        }
        public void setOtherAttr(String otherAttr) {
            this.otherAttr = otherAttr;
        }
        public String getShortDesc() {
            return shortDesc;
        }
        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
