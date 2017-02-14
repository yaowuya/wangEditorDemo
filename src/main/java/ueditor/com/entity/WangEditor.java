package ueditor.com.entity;

public class WangEditor {
    private Integer wangId;

    private String wangName;

    private byte[] wangHtml;

    public WangEditor(Integer wangId, String wangName, byte[] wangHtml) {
        this.wangId = wangId;
        this.wangName = wangName;
        this.wangHtml = wangHtml;
    }

    public WangEditor() {
        super();
    }

    public Integer getWangId() {
        return wangId;
    }

    public void setWangId(Integer wangId) {
        this.wangId = wangId;
    }

    public String getWangName() {
        return wangName;
    }

    public void setWangName(String wangName) {
        this.wangName = wangName == null ? null : wangName.trim();
    }

    public byte[] getWangHtml() {
        return wangHtml;
    }

    public void setWangHtml(byte[] wangHtml) {
        this.wangHtml = wangHtml;
    }
}