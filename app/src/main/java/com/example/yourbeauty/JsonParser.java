package com.example.yourbeauty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class JsonParser
{
    public LinkedHashMap<String,String> parseJson(String s) throws Exception
    {
        String json = s;

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

        System.out.println("Validate: " + validate + " amount: " + amount);

        if(validate == 1 && amount > 0)
        {
            String[] temp = json.split("(?=\"id_0)");
            json = "{" + temp[1];
            jsonObject = new JSONObject(json);
            System.out.println("Firms: " + temp[1]);

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
        String json = s;

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

        System.out.println("Validate: " + validate + " amount: " + amount);

        if(validate == 1 && amount > 0)
        {
            String[] temp = json.split("(?=\"0_id)");
            json = "{" + temp[1];
            jsonObject = new JSONObject(json);
            System.out.println("Firms: " + temp[1]);

            for(int i=0; i<amount; i++)
            {

                for(int j=0; j<5; j++)
                {
                    String key = jsonObject.names().getString(i * 5 + j);
                    String value = jsonObject.get(key).toString();

                    System.out.println("Services: " + key + " " + value);

                    map.put(key, value);
                }
            }
        }

        return map;
    }
}
