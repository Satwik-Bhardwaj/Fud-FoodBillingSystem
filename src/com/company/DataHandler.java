package com.company;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import javax.swing.text.DateFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataHandler {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataFetcher dataFetcher = new DataFetcher();
        System.out.println(dataFetcher.billDate());
        System.out.println(dataFetcher.billTime());
        System.out.println(dataFetcher.billIdMaker());
        System.out.println(dataFetcher.commentStatement());

    }
}

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

    DataApplier(){

    }

    // Bill Identification Details
    String cashierName;
    String billId;
    String billDate;
    String billTime;
    String comment;

    void billInfo() throws SQLException {
        cashierName = getUser();
        billId = billIdMaker();
        billDate = billDate();
        billTime = billTime();
        comment = commentStatement();
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
                System.out.println("// "+foodItem[i].getUserObject());
                subcatName = filterBlank(foodItemData.getString("SubCategory"));

                // check for existence of subcategory node
                for (int x=0; x<j; x++){
                    System.out.println("--called");
                    if (SubCategory[x].getUserObject().toString().equals(subcatName)){
                        SubCatExist = true;
                        tempIndexSub=x;
                    }
                }
                // if exists we add food item to that place, if not we add it to new place
                if (!SubCatExist){
                    SubCategory[j] = new DefaultMutableTreeNode(subcatName);
                    SubCategory[j].add(foodItem[i]);
                    System.out.println("//// "+SubCategory[j].getUserObject());

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
                        System.out.println("////// "+Category[k].getUserObject());
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
