package sample.model;

import com.google.gson.annotations.SerializedName;

public class UploadDataResponseModel {

    @SerializedName("fileId")
    private String fileId;

    @SerializedName("fileName")
    private String fileName;

    @SerializedName("hashSum")
    private String hashSum;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHashSum() {
        return hashSum;
    }

    public void setHashSum(String hashSum) {
        this.hashSum = hashSum;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   fileId: \"" + fileId + '\"' +
                ",\n   fileName: \"" + fileName + '\"' +
                ",\n   hashSum: \"" + hashSum + '\"' +
                "\n}";
    }
}
