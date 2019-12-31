package com.example.yourbeauty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class JsonParser
{
    private String json;

    public LinkedHashMap<String,String> parseJson(String s) throws Exception
    {
        json = s;

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        JSONObject jsonObject;
        int validate = 0;       //Is success == 1 ?
        int amount = 0;         //Amount of firms
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

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        JSONObject jsonObject;
        int validate = 0;       //Is success == 1 ?
        int amount = 0;         //Amount of firms

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
        JSONObject jsonObject;
        int validate = 0;       //Is success == 1 ?

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

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

            System.out.println("JSON 2: " + key + " " + value);

            map.put(key,value);
        }

        return map;
    }

    public LinkedHashMap<String, String> parseWorkers(String s) throws Exception
    {
        json = s;
        JSONObject jsonObject;
        int validate = 0;       //Is success == 1 ?
        int amount = 0;         //Number of workers

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

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
                String key = i + "_Name";
                String value = jsonObject.getString(key);

                System.out.println("JSON 1: " + key + " " + value);

                map.put(key,value);

                key = i + "_Surname";
                value = jsonObject.getString(key);

                System.out.println("JSON 2: " + key + " " + value);

                map.put(key,value);
            }

        }

        return map;
    }
}