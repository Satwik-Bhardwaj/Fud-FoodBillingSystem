package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;

import static com.company.FoodSelector.refreshBillPanel;

class BillPlotter extends JPanel{
    static int allItemCount;

    JPanel outerBillPanel;
    JSplitPane billPanel;
    JScrollPane billInfo;
    static JPanel billInfoPanel;
    JScrollPane billItem;
    static JPanel billItemPanel;
    JPanel printPanel;
    JButton printButton;
    JButton newBillButton;

    // bill Info elements
    JLabel cashierName;
    JLabel billId;
    JLabel billDate;
    JLabel billTime;
    static JLabel itemCountLabel;
    JLabel billComment;

    BillPlotter(){
        billInfoPanel = new JPanel();
        billInfo = new JScrollPane(billInfoPanel);
        billItemPanel = new JPanel();
        billItem = new JScrollPane(billItemPanel);
        billPanel = new JSplitPane();
        outerBillPanel = new JPanel();
        printPanel = new JPanel();
        printButton = new JButton("Print Bill");
        newBillButton = new JButton("New Bill");

        // using dataApplier class to apply the bill information
        DataApplier dataApplier = new DataApplier();

        cashierName = new JLabel("CASHIER NAME: " + dataApplier.getCashierName());
        billId = new JLabel("BILL ID: " + dataApplier.getBillId());
        billDate = new JLabel("DATE: " + dataApplier.getBillDate());
        billTime = new JLabel("TIME: " + dataApplier.getBillTime());
        itemCountLabel = new JLabel("Items: "+ allItemCount);
        billComment = new JLabel("Comment: " + dataApplier.getComment());


        billInfoPanel.setLayout(new BoxLayout(billInfoPanel, BoxLayout.Y_AXIS));
        billInfoPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        billInfoPanel.add(cashierName);
        billInfoPanel.add(billId);
        billInfoPanel.add(billDate);
        billInfoPanel.add(billTime);
        billInfoPanel.add(itemCountLabel);
        billInfoPanel.add(billComment);

        billItemPanel.setLayout(new BoxLayout(billItemPanel, BoxLayout.Y_AXIS));

        billItem.getVerticalScrollBar().setUnitIncrement(16);

        billPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        billPanel.setDividerSize(3);
        billPanel.setContinuousLayout(true);
        billPanel.setTopComponent(billInfo);
        billPanel.setBottomComponent(billItem);
        billPanel.setDividerLocation(100);

        outerBillPanel.setLayout(new BorderLayout());
        outerBillPanel.add(billPanel);

        // new Bill button actions
        newBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBillPanel();
            }
        });

        // print button actions
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BillMaker billMaker = new BillMaker();
            }
        });
        printPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        printPanel.add(outerBillPanel);
        printPanel.add(newBillButton);
        printPanel.add(printButton);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(outerBillPanel);
        add(printPanel);

        setSize(1280, 720);
        setVisible(true);
    }

//    Bill items organizing variables and functions
    static int itemCount=0;
    static int value=1000;
    static JPanel[] itemPanel = new JPanel[value];
    static JLabel[] itemName = new JLabel[value];
    static JButton[] decreaseQuantity = new JButton[value];
    static JTextField[] quantityField = new JTextField[value];
    static JButton[] increaseQuantity = new JButton[value];
    static JLabel[] itemPrice = new JLabel[value];
    public static void ItemOptions(String ItemName, Float ProductPrice){
        allItemCount++;
        itemCountLabel.setText("Items: "+ allItemCount);
        billInfoPanel.updateUI();
        int i = itemCount;
        itemPanel[i]=new JPanel(new GridLayout(1, 5, 5, 0 ));
        itemPanel[i].setBorder(new EmptyBorder(2,2,2,2));

        itemName[i] = new JLabel(ItemName);
        itemName[i].setHorizontalAlignment(SwingConstants.CENTER);

        decreaseQuantity[i] = new JButton(new ImageIcon(Objects.requireNonNull(BillPlotter.class.getResource("img/minus.png"))));
        decreaseQuantity[i].setOpaque(false);
        decreaseQuantity[i].setContentAreaFilled(false);
        decreaseQuantity[i].setBorderPainted(false);
        decreaseQuantity[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allItemCount--;
                itemCountLabel.setText("Items: "+ allItemCount);
                billInfoPanel.updateUI();

                int quant = Integer.parseInt(quantityField[i].getText());
                float newProductPrice = ProductPrice*(quant-1);
                quantityField[i].setText(String.valueOf(quant-1));
                itemPrice[i].setText("₹ "+newProductPrice);
                if (Integer.parseInt(quantityField[i].getText())<=0){
                    itemRemoval(i);
                }
            }
        });

        quantityField[i] = new JTextField("1");
        quantityField[i].setHorizontalAlignment(JTextField.CENTER);

        increaseQuantity[i] = new JButton(new ImageIcon(Objects.requireNonNull(BillPlotter.class.getResource("img/plus.png"))));
        increaseQuantity[i].setOpaque(false);
        increaseQuantity[i].setContentAreaFilled(false);
        increaseQuantity[i].setBorderPainted(false);
        increaseQuantity[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allItemCount++;
                itemCountLabel.setText("Items: "+ allItemCount);
                billInfoPanel.updateUI();
                int quant = Integer.parseInt(quantityField[i].getText());
                float newProductPrice = ProductPrice*(quant+1);
                quantityField[i].setText(String.valueOf(quant+1));
                itemPrice[i].setText("₹ "+newProductPrice);
            }
        });
        itemPrice[i] = new JLabel("₹ "+ProductPrice);
        itemPrice[i].setHorizontalAlignment(SwingConstants.CENTER);

        itemPanel[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        itemPanel[i].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                itemPanel[i].setBackground(new Color(0xADADAD));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                itemPanel[i].setBackground(null);
            }
        });

        itemPanel[i].add(itemName[i]);
        itemPanel[i].add(decreaseQuantity[i]);
        itemPanel[i].add(quantityField[i]);
        itemPanel[i].add(increaseQuantity[i]);
        itemPanel[i].add(itemPrice[i]);

        billItemPanel.add(itemPanel[i]);
        billItemPanel.revalidate();
        itemCount++;
    }

    private static void itemRemoval(int index){
        billItemPanel.remove(itemPanel[index]);
        billItemPanel.updateUI();
    }
}

class BillMaker{
    int itemsCount;
    ArrayList<Item> itemList;

    BillMaker(){
        itemsCount = BillPlotter.itemCount;
        itemList = new ArrayList<>();

        for(int i=0; i<itemsCount; i++){
            itemList.add(new Item(Float.parseFloat(BillPlotter.itemPrice[i].getText().replaceAll("[₹ ]*", "")), BillPlotter.itemName[i].getText(), Integer.parseInt(BillPlotter.quantityField[i].getText())));
        }

        printBill();
    }

    private void printBill() {
        System.out.println("\tItem Name \tQuantity \tPrice \tTotalPrice");
        System.out.println("---------------------------------------------------------------------------------------");
        Item temp;
        for(int i=0; i<itemList.size(); i++){
            temp = itemList.get(i);
            System.out.println("\t"+ temp.itemName +" \t"+ temp.quantity +" \t"+ temp.singlePrice +" \t"+temp.totalPrice);
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

}
class Item{
    float singlePrice;
    float totalPrice;
    String itemName;
    int quantity;

    Item(float price, String itemName, int quantity){
        this.quantity = quantity;
        this.singlePrice = price / quantity;
        this.itemName = itemName;
        this.totalPrice = price;
    }
}
