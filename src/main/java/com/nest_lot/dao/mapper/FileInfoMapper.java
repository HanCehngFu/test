package com.nest_lot.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nest_lot.base.BaseDao;
import com.nest_lot.dao.entity.FileInfo;

public interface FileInfoMapper extends BaseDao<FileInfo> {
	int deleteByPrimaryKey(Long fileId);

	int insert(FileInfo record);

	int insertSelective(FileInfo record);

	FileInfo selectByPrimaryKey(Long fileId);

	int updateByPrimaryKeySelective(FileInfo record);

	int updateByPrimaryKey(FileInfo record);
	
	public List<FileInfo> queryFileInfoByNestUid(@Param("nestUuid") String nestUuid,@Param ("fileUsedType") String fileUsedType,@Param ("fileStatus") String fileStatus);

	int updateFileTypeAndStatusByUUID (FileInfo record);
	
	FileInfo findByUUID(@Param("fileuuid") String fileuuid);
	
	List<FileInfo> findByFileUsedType(@Param("fileUsedType") String fileUsedType);
	
	int deleteByFileUsedUuid(@Param("fileUsedUuid") String fileUsedUuid,@Param("fileUsedType") String fileUsedType);
}