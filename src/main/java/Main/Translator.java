package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Translator {
    String language;

    public HashMap<String, String> text = new HashMap<String, String>();

    public Translator(String _language) throws IOException {
        this.language = _language;
        getLanguage(this.language);

    }

    public void getLanguage(String language) throws IOException {
        String file ="Language/Language_" + language.toLowerCase() + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        //text = new HashMap<String, String>();

        String line;
        while((line = reader.readLine()) != null){

            String[] parts = line.split("//",2);
            if(parts.length >= 2){
            String key = parts[0];
            String value = parts[1];
            text.put(key,value);
            }

        }

        reader.close();

    }


}


// Add keys and (Country, City)

