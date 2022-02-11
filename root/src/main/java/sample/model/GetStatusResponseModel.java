package sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStatusResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("subTasks")
    private List<String> subTasks;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<String> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        String subTasks = "";
        for (int i = 0; i < getSubTasks().size(); i++) {
            if (i != getSubTasks().size() - 1) {
                subTasks = subTasks.concat(getSubTasks().get(i)).concat(" ");
            } else {
                subTasks = subTasks.concat(getSubTasks().get(i));
            }
        }
        return "{\n" +
                "   status: \"" + status + '\"' +
                ",\n   subTasks: [" + subTasks + ']' +
                "\n}";
    }
}
