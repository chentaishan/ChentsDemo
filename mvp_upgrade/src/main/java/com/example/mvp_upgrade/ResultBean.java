package com.example.mvp_upgrade;

public class ResultBean {

    private int id;
    private String result;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "id=" + id +
                ", result='" + result + '\'' +
                '}';
    }
}
