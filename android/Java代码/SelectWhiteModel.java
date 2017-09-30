package com.lewic.bracelet.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class SelectWhiteModel {
    public int code;
    public String message;
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        public String phone;
        public String name;
        public int id;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultCode{" +
//                "code='" + code + '\'' +
//                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
