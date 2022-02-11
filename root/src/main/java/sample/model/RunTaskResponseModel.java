package sample.model;

import com.google.gson.annotations.SerializedName;

public class RunTaskResponseModel {

    @SerializedName("fileId")
    private String fileId;

    @SerializedName("taskId")
    private String taskId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   fileId: \"" + fileId + '\"' +
                ",\n   taskId: \"" + taskId + '\"' +
                "\n}";
    }
}
