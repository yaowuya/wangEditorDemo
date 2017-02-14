package ueditor.com.dao;

import ueditor.com.entity.WangEditor;

public interface WangEditorMapper {
    int deleteByPrimaryKey(Integer wangId);

    int insert(WangEditor record);

    int insertSelective(WangEditor record);

    WangEditor selectByPrimaryKey(Integer wangId);

    int updateByPrimaryKeySelective(WangEditor record);

    int updateByPrimaryKeyWithBLOBs(WangEditor record);

    int updateByPrimaryKey(WangEditor record);
}