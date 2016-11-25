package com.jdhui.db.model;


import com.jdhui.db.afinal.annotation.sqlite.Id;
import com.jdhui.db.afinal.annotation.sqlite.Table;

@Table(name = "tb_province")
public class Province {

    @Id
    private int _id;
    private String pid;     //省id    根据省id查市
    private String name;    //省名称

    public Province() {
        super();
    }

    public Province(String name, String pid) {
        this.name = name;
        this.pid = pid;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
