package cn.gx.modules.bug.bean;

/**
 * Created by always on 16/5/26.
 */
public class TaskCount {
    private int todo;//任务
    private int join;//参与
    private int apply;//提交


    public TaskCount() {
    }

    public TaskCount(int todo, int join, int apply) {
        this.todo = todo;
        this.join = join;
        this.apply = apply;
    }

    public int getTodo() {
        return todo;
    }


    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }
}
