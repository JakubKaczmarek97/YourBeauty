package com.example.yourbeauty;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser
{
    //Stworzyć hashmapę, w środku np. ID = 1, blabla = "eeee" itd
    //Każda metoda zwraca hashmapę
    //Metoda przyjmuje Stringa (json w stringu)
    //Zrobić wczytanie od " do "

    /*
        String response
        if(response[1] == ")
        {
            czytaj do kolejnego "
     */

    public void splitString(String s) throws Exception
    {
        String json = s;

        JSONObject jsonObject = null;
        int validate = 0;
        int amount = 0;
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

        if(validate == 1)
        {
            String[] temp = json.split("(?=\"id_0)");
            json = "{" + temp[1];
            jsonObject = new JSONObject(json);
            System.out.println("Firms: " + temp[1]);

            for(int i=0; i<amount; i++)
            {
                System.out.println("Firma " + i + ": ");
                for(int j=0; j<4; j++)
                {
                    String key = jsonObject.names().getString(i*4+j);
                    String value = jsonObject.get(key).toString();
                    System.out.println(key + ": " + value);
                    //ArrayList<String> keys = jsonObject.keys().forEachRemaining(list::add);
                }
            }
        }


/*
        String string = s;
        String[] parts = string.split(",");     //Podział JSON na części, separator = przecinek

        String[] validate = parts[0].split(":"); //Validate to "success = ?"

        ArrayList<HashMap> myArrayOfMaps = new ArrayList<>(); //Wynikowa ArrayLista, zawierająca w sobie mapy(taki bajer :P)


        System.out.println("Validate:" + validate[1]);


        if(validate[1].equals("1"))      //if success == 1 to parsujemy
        {
            String[] query_amount = parts[2].split(":");    //Wycięcie z JSONa stringa "query_amount == ?"
            int amount = Integer.parseInt(query_amount[1]);       //Przepisanie wartości query_amount do int


            for(int i=0; i<amount; i++)             //Los pętelkos des JSON'os
            {
                for(int j=3; j<7; j++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    String[] temp = parts[j].split(":");

                    System.out.println("Temp " + j + " " + temp[0] + " " + temp[1]);

                    map.put(temp[0],temp[1]);

                    myArrayOfMaps.add(map);
                }
            }
        }

 */
    }

}
