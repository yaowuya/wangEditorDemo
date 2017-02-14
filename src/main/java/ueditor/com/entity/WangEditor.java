package ueditor.com.entity;

public class WangEditor {
    private Integer wangId;

    private byte[] wangHtml;

    public WangEditor(Integer wangId, byte[] wangHtml) {
        this.wangId = wangId;
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

    public byte[] getWangHtml() {
        return wangHtml;
    }

    public void setWangHtml(byte[] wangHtml) {
        this.wangHtml = wangHtml;
    }
}