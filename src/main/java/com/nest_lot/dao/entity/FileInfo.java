package com.nest_lot.dao.entity;

public class FileInfo {
	private Long fileId;

	private String fileUuid;

	private String fileStatus;

	private String fileType;

	private String fileDesc;

	private String fileName;

	private String fileUrl;

	private String fileSize;

	private String fileUsedType;

	private String fileUsedUuid;

	private String fileSaveName;

	private String createTime;

	private String updateTime;

	private String createOneUuid;

	private String updateOneUuid;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid == null ? null : fileUuid.trim();
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus == null ? null : fileStatus.trim();
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc == null ? null : fileDesc.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl == null ? null : fileUrl.trim();
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize == null ? null : fileSize.trim();
	}

	public String getFileUsedType() {
		return fileUsedType;
	}

	public void setFileUsedType(String fileUsedType) {
		this.fileUsedType = fileUsedType == null ? null : fileUsedType.trim();
	}

	public String getFileUsedUuid() {
		return fileUsedUuid;
	}

	public void setFileUsedUuid(String fileUsedUuid) {
		this.fileUsedUuid = fileUsedUuid == null ? null : fileUsedUuid.trim();
	}

	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName == null ? null : fileSaveName.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getCreateOneUuid() {
		return createOneUuid;
	}

	public void setCreateOneUuid(String createOneUuid) {
		this.createOneUuid = createOneUuid == null ? null : createOneUuid.trim();
	}

	public String getUpdateOneUuid() {
		return updateOneUuid;
	}

	public void setUpdateOneUuid(String updateOneUuid) {
		this.updateOneUuid = updateOneUuid == null ? null : updateOneUuid.trim();
	}
}