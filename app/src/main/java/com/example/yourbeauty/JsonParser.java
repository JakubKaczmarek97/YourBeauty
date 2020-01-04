package com.example.yourbeauty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class JsonParser
{
    private String json;
    private JSONObject jsonObject;
    private LinkedHashMap<String, String> map;
    private int validate = 0;   //Is (success == 1) ?
    private int amount = 0;

    public LinkedHashMap<String,String> parseJson(String s) throws Exception
    {
        json = s;
        map = new LinkedHashMap<>();

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
            amount = jsonObject.getInt("query_amount");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(validate == 1 && amount > 0)
        {
            String[] temp = json.split("(?=\"id_0)");
            json = "{" + temp[1];
            jsonObject = new JSONObject(json);

            for(int i=0; i<amount; i++)
            {
                for(int j=0; j<4; j++)
                {
                    String key = jsonObject.names().getString(i * 4 + j);
                    String value = jsonObject.get(key).toString();

                    map.put(key, value);
                }
            }
        }

        return map;
    }

    public LinkedHashMap<String,String> parseServices(String s) throws Exception
    {
        json = s;
        map = new LinkedHashMap<>();

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
            amount = jsonObject.getInt("query_amount");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(validate == 1 && amount > 0)
        {
            String[] temp = json.split("(?=\"0_id)");
            json = "{" + temp[1];
            jsonObject = new JSONObject(json);

            for(int i=0; i<amount; i++)
            {
                for(int j=0; j<5; j++)
                {
                    String key = jsonObject.names().getString(i * 5 + j);
                    String value = jsonObject.get(key).toString();

                    map.put(key, value);
                }
            }
        }

        return map;
    }

    public LinkedHashMap<String, String> parseLogin(String s) throws Exception
    {
        json = s;
        map = new LinkedHashMap<>();

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(validate > 0)
        {
            jsonObject = new JSONObject(json);

            String key = "id";
            String value = jsonObject.getString("id");

            map.put(key, value);

            key = "userType";
            value = jsonObject.getString("userType");

            map.put(key,value);
        }

        return map;
    }

    public LinkedHashMap<String, String> parseWorkers(String s) throws Exception
    {
        json = s;
        map = new LinkedHashMap<>();

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
            amount = jsonObject.getInt("query_amount");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(validate != 0 && amount != 0)
        {
            jsonObject = new JSONObject(json);

            for(int i=0; i<amount; i++)
            {
                String key = i + "_idWorker";
                String value = jsonObject.getString(key);

                map.put(key,value);

                key = i + "_Name";
                value = jsonObject.getString(key);

                map.put(key,value);

                key = i + "_Surname";
                value = jsonObject.getString(key);

                map.put(key,value);
            }
        }

        return map;
    }

    public String parseSignUp(String s)
    {
        json = s;

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return String.valueOf(validate);
    }

    public LinkedHashMap<String, String> parseHours(String h) throws Exception
    {
        json = h;
        map = new LinkedHashMap<>();

        try
        {
            jsonObject = new JSONObject(json);
            validate = jsonObject.getInt("success");
            if(validate > 0 )
                amount = jsonObject.getInt("query_amount");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        for(int i=0; i<amount; i++)
        {
            String key = i + "_hour";
            String value = jsonObject.getString(key);

            map.put(key, value);
        }
        return map;
    }
}