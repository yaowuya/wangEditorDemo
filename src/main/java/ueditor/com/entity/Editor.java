package main.java.ueditor.com.entity;

public class Editor {
    private Integer editorId;

    private String editorname;

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public String getEditorname() {
        return editorname;
    }

    public void setEditorname(String editorname) {
        this.editorname = editorname == null ? null : editorname.trim();
    }
}