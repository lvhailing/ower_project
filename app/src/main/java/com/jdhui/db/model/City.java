package com.jdhui.db.model;


import com.jdhui.db.afinal.annotation.sqlite.Id;
import com.jdhui.db.afinal.annotation.sqlite.Table;

@Table(name = "tb_city")
public class City {

    @Id
    private int _id;
    private String name;    //市的名字
    private String cid;     //市的id
    private String pid;     //它所属的省份的id

    public City() {
        super();
    }

    public City(String name, String pid) {
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
