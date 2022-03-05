package rental;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;

public class Exporter {

    private static final String CSV_SEPARATOR = ";";

    public static void exportToFile(Stage stage, ObservableList<Car> carList)
    {
        try
        {
            String directory = chooseDirectory(stage);
            if (directory == null) {
                return;
            }
            File file = new File(directory + "\\rentals.txt");
            if (!file.exists()) { // in case file already exists
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Car car : carList)
            {
                User customer = car.getUserFk();
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(car.getPickUpDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(customer.getMail());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(customer.getAddress());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(car.getDescription());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(car.getCategoryFk().getCategoryPrize() * 20.0) / 20.0); // round prize to 0.05 CHF
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if(selectedDirectory == null){
            System.out.println("Please select a directory!");
        }else{
            System.out.println(selectedDirectory.getAbsolutePath());
        }
        return selectedDirectory.getAbsolutePath();
    }
}
