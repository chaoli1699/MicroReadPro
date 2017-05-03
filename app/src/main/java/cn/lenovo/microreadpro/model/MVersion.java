package cn.lenovo.microreadpro.model;

/**
 * Created by Aaron on 2017/4/9.
 */

public class MVersion {

    private String code;
    private String info;
    private Version version;

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

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public static class Version{

        private int vid;
        private int version_code;
        private String version_name;
        private String introduce;
        private String download_path;
        private String start_path;

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getDownload_path() {
            return download_path;
        }

        public void setDownload_path(String download_path) {
            this.download_path = download_path;
        }

        public String getStart_path() {
            return start_path;
        }

        public void setStart_path(String start_path) {
            this.start_path = start_path;
        }
    }

}
