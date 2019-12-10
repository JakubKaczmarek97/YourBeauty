package com.example.yourbeauty;

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

    public String[] splitString(String s)
    {
        String string = s;
        String[] parts = string.split(",");

        String[] validate = parts[0].split(":");

        if(validate[1] == "1")
        {
            String temp = parts[1];
            System.out.println("Parser: " + temp);
            return parts;
        }
        else
        {
            return parts;
        }
    }

}
