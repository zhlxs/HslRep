package com.jrwp.payMent.entity;

public class UpdateItem {
    // {"stateType":0,"stateValue":"
    // [{\"fileName\":\":\"KTH.ElectronicPayment.Client.exe\",\\",\"fileSize\":0,\
    // "version\":\":\"2.0.0.1\",\\",\"clientCopyPath\":\"\\\\\",\"
    // fileDownLoadPath\":\":\"http://192.168.0.63:9005/KTH.ElectronicPayment.Client.exe\",\"",\"fileContent\":null,\"isHaveVersion\":1}]",
    // "stateMsg":"获取成功!!!","url":"","validationResults":null}
    private String fileName;
    private int fileSize;
    private String version;
    private String fileDownLoadPath;
    private String fileContent;
    private int isHaveVersion = 1;
    private String clientCopyPath;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileDownLoadPath() {
        return fileDownLoadPath;
    }

    public void setFileDownLoadPath(String fileDownLoadPath) {
        this.fileDownLoadPath = fileDownLoadPath;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public int getIsHaveVersion() {
        return isHaveVersion;
    }

    public void setIsHaveVersion(int isHaveVersion) {
        this.isHaveVersion = isHaveVersion;
    }

    public String getClientCopyPath() {
        return clientCopyPath;
    }

    public void setClientCopyPath(String clientCopyPath) {
        this.clientCopyPath = clientCopyPath;
    }
}
