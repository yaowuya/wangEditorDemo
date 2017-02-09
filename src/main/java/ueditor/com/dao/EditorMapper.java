package main.java.ueditor.com.dao;

import main.java.ueditor.com.entity.Editor;

public interface EditorMapper {
    int deleteByPrimaryKey(Integer editorId);

    int insert(Editor record);

    int insertSelective(Editor record);

    Editor selectByPrimaryKey(Integer editorId);

    int updateByPrimaryKeySelective(Editor record);

    int updateByPrimaryKey(Editor record);
}