package com.example.adminsever.DAO;

import android.content.Context;
import android.util.Log;

import com.example.adminsever.Model.Dienthoai;

import com.example.adminsever.noUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DtDAO {

    Context context;

    String serverUrl = "http://192.168.1.106:3000";
    List<Dienthoai> list = new ArrayList<Dienthoai>();


    //Khai bao socket
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(serverUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    //lang nghe getAll
    private Emitter.Listener onGetAllDt = new Emitter.Listener() {

        @Override
        public void call(Object... args) {



            noUI noui = new noUI(context);
            Dienthoai dt = new Dienthoai();

            JSONObject jsonObject = (JSONObject) args[0];
            //parser JSON
            try {
                dt._id = jsonObject.getString("_id");
                dt.madt = jsonObject.getString("madt");
                dt.tendt = jsonObject.getString("tendt");
                dt.nhanhieu = jsonObject.getString("nhanhieu");
                dt.mau = jsonObject.getString("mau");
                dt.noisx = jsonObject.getString("noisx");

                list.add(dt);
                Log.i("GetAlldt","Get thanh cong 1 student");



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };



    public DtDAO(Context context) {
        this.context = context;
        mSocket.connect();

        mSocket.on("getdt", onGetAllDt);

    }





    public List<Dienthoai> getAlldt(){

        list.clear();

        mSocket.emit("getdt","Client Android get All Student");



        return list;
    }


}
