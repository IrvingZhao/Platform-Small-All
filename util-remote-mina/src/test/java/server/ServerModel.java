package server;

public class ServerModel {

    public ServerModel() {
    }

    public ServerModel(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    private String data1;
    private String data2;

    public String getData1() {
        return data1;
    }

    public ServerModel setData1(String data1) {
        this.data1 = data1;
        return this;
    }

    public String getData2() {
        return data2;
    }

    public ServerModel setData2(String data2) {
        this.data2 = data2;
        return this;
    }

    @Override
    public String toString() {
        return "ServerModel{" +
                "data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                '}';
    }

}
