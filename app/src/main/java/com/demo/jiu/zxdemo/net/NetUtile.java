package com.demo.jiu.zxdemo.net;

import android.content.Context;
import android.widget.Toast;

import com.demo.jiu.zxdemo.listener.NetRespontListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetUtile {
    private static OkHttpClient client;
    private static NetUtile net;
    private static NetRespontListener listener;
    private static RequestBody requestBody;
    private static Request request;
    private static Call call;
    private static Request.Builder builder;
    private static Map<String,String> header;

    private static Context context;
    private static Gson gson;

    public void setNetRespontListener(NetRespontListener listener) {
        NetUtile.listener = listener;
    }

    public void setHeader(String key,String value){
        header.put(key,value);
    }

    public void setHeader(Map<String,String> headers){
        header.putAll(headers);
    }


    public static NetUtile getNet( Context context){
        if(net!=null){
            return net;
        }
        return new NetUtile(context);
    }

    private NetUtile( Context context){
        NetUtile.context=context;
        gson=new Gson();
        client=new OkHttpClient();
        header=new HashMap<>();
    }

    public void GetRespond(String url){
        request = new Request.Builder()
                .get()
                .url(url)
                .build();
        RequestCallBack();
    }

    public void PostRespondByJson(String url,Object obj){
        requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gson.toJson(obj));
        builder = new Request.Builder()
                .get()
                .url(url)
                .post(requestBody);
        if(header.size()>0){
            for (String key:header.keySet()) {
                builder.addHeader(key,header.get(key));
            }
        }
        request = builder.build();
        RequestCallBack();
    }

    public void PostRespondByFormBody(String url,Object obj){
        FormBody.Builder formBody = new FormBody.Builder();
        try {
            Class c = obj.getClass();
            Field[]  fields = c.getFields();
            for (Field f:fields) {
                formBody.add(f.getName(),f.get(obj).toString());
            }
            builder = new Request.Builder()
                    .get()
                    .url(url)
                    .post(formBody.build());
            if(header.size()>0){
                for (String key:header.keySet()) {
                    builder.addHeader(key,header.get(key));
                }
            }
            request = builder.build();
            RequestCallBack();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void RequestCallBack(){
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "网络错误!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                RespondInfo respond= gson.fromJson(res,RespondInfo.class);
                if(respond.getCord()!=200){
                    Toast.makeText(context, respond.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.RespondInfo(gson.fromJson(respond.getObj(),Object.class));
            }
        });
    }
}
