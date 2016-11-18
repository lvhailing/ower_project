package com.jdhui.mould;

import java.util.LinkedList;
import java.util.List;

public class TaskManager {
    private List<MouldAsyncTask> taskList = new LinkedList<MouldAsyncTask>();

    public TaskManager() {

    }

    public synchronized void addTask(MouldAsyncTask task) {
        if (task != null) {
            taskList.add(task);
            task.parallelExecute();
        }
    }

    public synchronized void removeTask(MouldAsyncTask task) {
        if (task != null) {
            taskList.remove(task);
        }
    }

    public synchronized void cancelAllTask() {
        for (int i = taskList.size() - 1; i >= 0; i--) {
            MouldAsyncTask task = taskList.remove(i);
            task.cancel(true);
        }
    }

    public synchronized void cancelTask(String taskType) {
        if (taskType == null) {
            return;
        }
        for (int i = taskList.size() - 1; i >= 0; i--) {
            MouldAsyncTask task = taskList.get(i);
            if (taskType.equals(task.getTaskType())) {
                taskList.remove(i);
                task.cancel(true);
            }
        }
    }
}
