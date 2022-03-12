package rental;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OverviewController implements Initializable {

    public ChoiceBox<Location> cbLocation;
    public ChoiceBox<Category> cbCategory;
    public Label lblPrize;
    public DatePicker datePickUp;
    public DatePicker dateDropOff;
    public TableView<Car> tvCars;
    public TableColumn<Car, String> colDescription;
    public TableColumn<Car, String> colYear;
    public TableColumn<Car, String> colLocation;
    public TableColumn <Car, String>colCategory;
    public Button btnRent;
    public Label lblPlaceholder1;
    public Label lblPlaceholder2;
    public Label lblPlaceholder3;
    public Label lblPlaceholder4;
    public Label lblPrevious1;
    public Label lblPrevious2;
    public Label lblPrevious3;
    public Label lblPrevious4;
    public Label lblPopular1;
    public Label lblPopular2;
    public StackPane futurePane;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private User loggedInUser;
    private Car selectedCar = null;
    private ObservableList<Car> previousReservations;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePickUp.setValue(LocalDate.now());
        dateDropOff.setValue(LocalDate.now());
        LocalDate minDate = LocalDate.now();
        datePickUp.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }}
        );
        dateDropOff.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }}
        );
        // set available locations
        cbLocation.setItems(DBUtils.getLocationList());
        // set categories
        cbCategory.setItems(DBUtils.getCategoryList());
        // display rentable cars
        showCars(null, null);

        tvCars.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue<? extends Car> observableValue, Car car, Car t1) {
                if (tvCars.getSelectionModel().getSelectedItem() != null) {
                    btnRent.setDisable(false);

                    Car c = tvCars.getSelectionModel().getSelectedItem();
                    selectedCar = c;
                    System.out.println("Selected car " + c.getCarId());

                    lblPrize.setText(Double.toString(c.getCategoryFk().getCategoryPrize()));
                }
            }
        });

        showFutureReservations();
        showPreviousReservations();
        showPopularCars();
    }

    private void showPopularCars() {
        // show most popular cars
        ObservableList<Car> popularCars = DBUtils.getMostPopularCars();
        Label[] popularLabels = { lblPopular1, lblPopular2 };
        setEntriesForList(popularCars, popularLabels, 2);
    }

    private void showPreviousReservations() {
        // show previous reservations
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()).minusDays(30);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        previousReservations = DBUtils.getCarList().stream()
                .filter(c -> c.isRented())
                .filter(c -> c.getDropOffDate().after(out))
                .filter(c -> c.getDropOffDate().before(in))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        Label[] previousLabels = { lblPrevious1, lblPrevious2, lblPrevious3, lblPrevious4 };
        setEntriesForList(previousReservations, previousLabels, 4);
    }

    private void showFutureReservations() {
        // show future reservations
        ObservableList<String> futureReservations = DBUtils.getCarList().stream()
                .filter(c -> c.isRented())
                .filter(c -> c.getPickUpDate().after(new Date()))
                .map(c -> c.toString(c, null))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        ListView<String> lvFuture = new ListView<>(futureReservations);
        lvFuture.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new XCell(stringListView);
            }
        });
        futurePane.getChildren().add(lvFuture);

        // Label[] reservationLabels = { lblPlaceholder1, lblPlaceholder2, lblPlaceholder3, lblPlaceholder4 };
        // setEntriesForList(futureReservations, reservationLabels, 4);
    }

    public void rentCar() {
        // set pickUp & dropOff date
        LocalDate localDatePickUp = datePickUp.getValue();
        ZonedDateTime zonedPickUpTime = localDatePickUp.atStartOfDay(ZoneId.systemDefault());
        Date pickUpDate = Date.from(zonedPickUpTime.toInstant());
        selectedCar.setPickUpDate(pickUpDate);
        System.out.println("Set date: " + pickUpDate);
        LocalDate localDateDropOff= dateDropOff.getValue();
        ZonedDateTime zonedDropOffTime = localDateDropOff.atStartOfDay(ZoneId.systemDefault());
        Date dropOffDate = Date.from(zonedDropOffTime.toInstant());
        selectedCar.setDropOffDate(dropOffDate);
        System.out.println("Set date: " + dropOffDate);

        // set user
        selectedCar.setUserFk(loggedInUser);

        // change rent status in db
        DBUtils.rentCar(selectedCar, loggedInUser);
        showCars(null, null);
    }

    private void setEntriesForList(ObservableList<Car> reservationList, Label[] labels, int maximum) {
        for (int i = 0; i < reservationList.size(); i++) {
            if (i > maximum - 1) break; // maximum
            labels[i].setText(reservationList.get(i).getDescription() + " " + reservationList.get(i).getPickUpDate().toString());
        }
    }

    private void showCars(Location location, Category category) {
        ObservableList<Car> rentableCars = DBUtils.getCarList().stream()
                .filter(c -> !c.isRented()).collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (location != null) {
            if (cbCategory.getValue() != null) {
                rentableCars.removeIf(c -> !c.getCategoryFk().getCategoryName().equals(cbCategory.getValue().getCategoryName()));
            }
            rentableCars.removeIf(c -> !c.getLocationFk().getCity().equals(location.getCity()));
        }

        if (category != null) {
            if (cbLocation != null) {
                rentableCars.removeIf(c -> !c.getLocationFk().getCity().equals(cbLocation.getValue().getCity()));
            }
            rentableCars.removeIf(c -> !c.getCategoryFk().getCategoryName().equals(category.getCategoryName()));
        }

        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colLocation.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLocationFk().getCity()));
        colCategory.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategoryFk().getCategoryName()));
        tvCars.setItems(rentableCars);

        showFutureReservations();
        showPreviousReservations();
        showPopularCars();
    }

    public void onLocationChange() {
        showCars(cbLocation.getSelectionModel().getSelectedItem(), null);
    }

    public void onCategoryChange() {
        showCars(null, cbCategory.getSelectionModel().getSelectedItem());
    }

    public void closeWindow() {
        stage.close();
    }

    public void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("This is a car rental tool :)");
        alert.showAndWait();
    }

    public void exportData() {
        Exporter.exportToFile(stage, previousReservations);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
