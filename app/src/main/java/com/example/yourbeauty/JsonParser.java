package com.example.yourbeauty;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<HashMap> splitString(String s)
    {
        String string = s;
        String[] parts = string.split(",");     //Podział JSON na części, separator = przecinek

        String[] validate = parts[0].split(":"); //Validate to "success = ?"

        ArrayList<HashMap> myTableOfFuckingMaps = new ArrayList<>(); //Wynikowa ArrayLista, zawierająca w sobie mapy(taki bajer :P)


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

                    myTableOfFuckingMaps.add(map);
                }
            }
        }
        return myTableOfFuckingMaps;
    }

}
