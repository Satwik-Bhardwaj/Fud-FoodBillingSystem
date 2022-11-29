package com.company;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.company.BillPlotter.ItemOptions;

//public class DataHandler {
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        BillItemList billItemList = new BillItemList();
//        billItemList.add("Aaloo tikki", 50);
//        billItemList.add("Chowmeen", 20);
//        billItemList.add("Chowmeen", 20);
//        for (int i = 0; i< billItemList.size(); i++){
//            System.out.println(billItemList.get(i).itemName);
//            System.out.println(billItemList.get(i).itemPrice);
//            System.out.println(billItemList.get(i).itemQuantity);
//            System.out.println(billItemList.get(i).serialNo);
//            System.out.println("-------------------------------------");
//        }
//
//    }
//}

class DataFetcher{
    static int billCount;

    Connection connection =null;
    Statement statement=null;
    ResultSet resultSetFoodItem=null;

    DataFetcher(){
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodbillsystem", "root", "");
            } catch (CommunicationsException e) {
                System.out.println("Connection not established");
                System.exit(0);
            }

            statement = connection.createStatement();
            resultSetFoodItem = statement.executeQuery("select * from fooddata");

        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error Occur");
        }
    }

    //For cashier
    // For Default Setting ---- need to be edit
    public String getUser() throws SQLException {
        ResultSet resultSet_Bill = statement.executeQuery("select * from billuser where UserID = 'aryl'");
        while (resultSet_Bill.next()){
            return resultSet_Bill.getString("UserName");
        }
        return null;
    }
    public String billDate(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        return localDate.format(dateFormatter);
    }
    public String billTime(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalTime localTime = LocalTime.now();
        return localTime.format(timeFormatter);
    }
    public String billIdMaker(){
        LocalTime localTime = LocalTime.now();
        String min = String.valueOf(localTime.getMinute());
        String hr = String.valueOf(localTime.getHour());
        if (billCount>=9999){
            billCount = 0;
        }
        billCount++;
        String id = String.format("%04d", billCount)+"-"+min+hr;
        return id;
    }
    public String commentStatement(){
        String statement = "JAI SHREE GANESH.\nThanks to visit the Restaurant.";
        return statement;
    }

}
class DataApplier extends DataFetcher{

    // Bill Identification Details
    String cashierName;
    String billId;
    String billDate;
    String billTime;
    String comment;

    DataApplier(){
        try {
            billInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void billInfo() throws SQLException {
        cashierName = getUser();
        billId = billIdMaker();
        billDate = billDate();
        billTime = billTime();
        comment = commentStatement();
    }
    public String getCashierName(){
        return cashierName;
    }
    public String getBillId(){
        return billId;
    }
    public String getBillDate(){
        return billDate;
    }
    public String getBillTime(){
        return billTime;
    }
    String getComment(){
        return comment;
    }

    public DefaultMutableTreeNode makeJTreeModel() {
        DefaultMutableTreeNode RootNode = new DefaultMutableTreeNode("Dishes");
        DefaultMutableTreeNode[] foodItem = new DefaultMutableTreeNode[100];
        DefaultMutableTreeNode[] SubCategory = new DefaultMutableTreeNode[100];
        DefaultMutableTreeNode[] Category = new DefaultMutableTreeNode[100];

        String subcatName;
        String catName;

        int i=0; int j=0; int k=0;
        boolean SubCatExist=false;
        boolean CatExist=false;
        int tempIndexSub=-1;
        int tempIndexCat=-1;
        try {
            ResultSet foodItemData = statement.executeQuery("select * from fooddata");
            while (foodItemData.next()){
                foodItem[i] = new DefaultMutableTreeNode(filterBlank(foodItemData.getString("FoodItem")));
                subcatName = filterBlank(foodItemData.getString("SubCategory"));

                // check for existence of subcategory node
                for (int x=0; x<j; x++){
                    if (SubCategory[x].getUserObject().toString().equals(subcatName)){
                        SubCatExist = true;
                        tempIndexSub=x;
                    }
                }
                // if exists we add food item to that place, if not we add it to new place
                if (!SubCatExist){
                    SubCategory[j] = new DefaultMutableTreeNode(subcatName);
                    SubCategory[j].add(foodItem[i]);

                    // for new node we have to check for its category node
                    catName = foodItemData.getString("Category");
                    for(int y=0; y<k; y++){
                        if (Category[y].getUserObject().toString().equals(catName)){
                            CatExist = true;
                            tempIndexCat=y;
                        }
                    }
                    //if exists we add sub-category to that place, if not we add it to new place
                    if (!CatExist){
                        Category[k] = new DefaultMutableTreeNode(catName);
                        Category[k].add(SubCategory[j]);
                        RootNode.add(Category[k]);
                        k++;
                    }else{
                        Category[tempIndexCat].add(SubCategory[j]);
                    }
                    j++;
                }else{
                    SubCategory[tempIndexSub].add(foodItem[i]);
                }
                i++;

                SubCatExist = false;
                CatExist = false;

                tempIndexSub = -1;
                tempIndexCat = -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RootNode;
    }
    public Float getFoodPrice(String foodItem){
        Float price=0f;
        try {
            ResultSet resultSet = statement.executeQuery("select * from fooddata where FoodItem=\'"+foodItem+"\'");
            resultSet.next();
            price = Float.parseFloat(resultSet.getString("Price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    private String filterBlank(String string) {
        if (string.equals("")){
            return "Other";
        }
        return string;
    }
}
// Bill Items List

class BillItemList{

    class AnItem{
        int serialNo;
        String itemName;
        int itemQuantity;
        float itemPrice;

        AnItem(int serialNo, String itemName, int itemQuantity, float itemPrice){
            this.serialNo = serialNo;
            this.itemName = itemName;
            this.itemQuantity = itemQuantity;
            this.itemPrice = itemPrice;
        }
    }

    private int index=0;

    private ArrayList<AnItem> anItemArrayList = new ArrayList<>();
    private AnItem tempItem;

    // add function to add new item
    public void add(String ItemName, float itemPrice){
        for (int i=0; i < anItemArrayList.size(); i++){
            tempItem = anItemArrayList.get(i);
            if (ItemName.equals(anItemArrayList.get(i).itemName)) {
                tempItem.itemQuantity++;
                tempItem.itemPrice *= 2;
                return;
            }
        }
        anItemArrayList.add(new AnItem(index, ItemName, 1, itemPrice));

        // displaying the item
//        ItemOptions(ItemName, itemPrice, );
        index++;
    }

    // get function to return an AnItem object
    public AnItem get(int index){
        return anItemArrayList.get(index);
    }

    // size function return size of the list of items in bill
    public int size(){
        return anItemArrayList.size();
    }
}
