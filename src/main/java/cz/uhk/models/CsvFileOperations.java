package cz.uhk.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileOperations implements FileOperations{
    private final static String FILE_NAME = "./shoppingList.csv";
    private final static char DELIMETER = ';';

    @Override
    public ShoppingList load() {
        String line = "";
        String splitBy = ";";
        ShoppingList shoppingList = new ShoppingList();
        //List<ShoppingItem> shoppingList = new ArrayList<>();
        try
        {

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            line = br.readLine();
            String[] tokens = line.split(": ");
            shoppingList.setName(tokens[1]);
            while ((line = br.readLine()) != null)
            {
                tokens = line.split(splitBy);
                ShoppingItem shoppingItem = new ShoppingItem(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]));
                shoppingItem.setBought(Boolean.parseBoolean(tokens[3]));
                shoppingList.addItem(shoppingItem);
            }
            return shoppingList;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
/*
        FileReader reader = new FileReader(FILE_NAME);
        CSVReader csvReader = new CSVReader(reader).skipLines(1);
        List<ShoppingItem> polozkyTemp = new ArrayList<>();
        String[] tokens = new String[];

        while ((tokens = csvReader.readNext()) != null) {
            polozkyTemp.add(new ShoppingItem(tokens[1], Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3])));
            item.setBought(Boolean.parseBoolean(tokens[4]));
        }
*/
        return new ShoppingList();
    }

    @Override
    public void write(ShoppingList model) {
        StringBuilder csvText = new StringBuilder();
        csvText.append("name: "+model.getName()).append("\n");

        for (ShoppingItem item:
             model.getItems()) {
            csvText
                    .append(item.getName())
                    .append(DELIMETER)
                    .append(item.getPrice())
                    .append(DELIMETER)
                    .append(item.getPieces())
                    .append(DELIMETER)
                    .append(item.isBought())
                    .append("\n");
        }
        try{
            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(csvText.toString());
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
