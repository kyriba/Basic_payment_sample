package sample.model;

public class TaskStatus {

    private String status;
    private String fileId;
    private String taskId;

    public TaskStatus() {
    }

    public TaskStatus(String status, String fileId, String taskId) {
        this.status = status;
        this.fileId = fileId;
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
                "   status: \"" + status + '\"' +
                ",\n   fileId: \"" + fileId + '\"' +
                ",\n   taskId: \"" + taskId + '\"' +
                "\n}";
    }
}
