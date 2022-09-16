package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

class BillPlotter extends JPanel{
    JPanel outerBillPanel;
    JSplitPane billPanel;
    JScrollPane billInfo;
    JPanel billInfoPanel;
    JScrollPane billItem;
    static JPanel billItemPanel;
    JPanel printPanel;
    JButton printButton;

    // bill Info elements
    JLabel cashierName;
    JLabel billId;
    JLabel billDate;
    JLabel itemCountLabel;
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

        cashierName = new JLabel("CASHIER NAME: Raghav Sen");
        billId = new JLabel("BILL ID: 2344");
        billDate = new JLabel("DATE: 04/04/2022");
        itemCountLabel = new JLabel("Items: 05");
        billComment = new JLabel("Comment: This is the bill of Raghav Restaurant. Thank you for visiting here.");


        billInfoPanel.setLayout(new BoxLayout(billInfoPanel, BoxLayout.Y_AXIS));
        billInfoPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        billInfoPanel.add(cashierName);
        billInfoPanel.add(billId);
        billInfoPanel.add(billDate);
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

        printPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        printPanel.add(outerBillPanel);
        printPanel.add(printButton);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(outerBillPanel);
        add(printPanel);

        setSize(1280, 720);
        setVisible(true);
    }

    static int itemCount=0;
    static int value=1000;
    static JPanel[] itemPanel = new JPanel[value];
    static JLabel[] itemName = new JLabel[value];
    static JButton[] decreaseQuantity = new JButton[value];
    static JTextField[] quantityField = new JTextField[value];
    static JButton[] increaseQuantity = new JButton[value];
    static JLabel[] itemPrice = new JLabel[value];

    public static void ItemOptions(String ItemName, Float ProductPrice){
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
                int quant = Integer.parseInt(quantityField[i].getText());
                float newProductPrice = ProductPrice*(quant-1);
                quantityField[i].setText(String.valueOf(quant-1));;
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
