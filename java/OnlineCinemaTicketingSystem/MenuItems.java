package OnlineCinemaTicketingSystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class MenuItems{
    private SimpleStringProperty menuname, menudescription;
    private SimpleDoubleProperty menuprice;

    public MenuItems(String menuname, String menudescription, double menuprice) {
        this.menuname = new SimpleStringProperty(menuname);
        this.menudescription = new SimpleStringProperty(menudescription);
        this.menuprice = new SimpleDoubleProperty(menuprice);
    }

    public String getMenuname() {
        return menuname.get();
    }

    public SimpleStringProperty menunameProperty() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public String getMenudescription() {
        return menudescription.get();
    }

    public SimpleStringProperty menudescriptionProperty() {
        return menudescription;
    }

    public void setMenudescription(String menudescription) {
        this.menudescription.set(menudescription);
    }

    public double getMenuprice() {
        return menuprice.get();
    }

    public SimpleDoubleProperty menupriceProperty() {
        return menuprice;
    }

    public void setMenuprice(double menuprice) {
        this.menuprice.set(menuprice);
    }
}