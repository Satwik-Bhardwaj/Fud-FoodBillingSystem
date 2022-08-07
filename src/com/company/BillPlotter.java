package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

class BillPlotterCreateFrame{
    JFrame window;
    JPanel outerBillPanel;
    JSplitPane billPanel;
    JScrollPane billInfo;
    JPanel billInfoPanel;
    JScrollPane billItem;
    JPanel billItemPanel;
    JPanel printPanel;
    JButton printButton;

    // bill Info elements
    JLabel cashierName;
    JLabel billId;
    JLabel billDate;
    JLabel itemCount;
    JLabel billComment;

    BillPlotterCreateFrame(){
        window = new JFrame("Bill Plotter");
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
        itemCount = new JLabel("Items: 05");
        billComment = new JLabel("Comment: This is the bill of Raghav Restaurant. Thank you for visiting here.");


        billInfoPanel.setLayout(new BoxLayout(billInfoPanel, BoxLayout.Y_AXIS));
        billInfoPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        billInfoPanel.add(cashierName);
        billInfoPanel.add(billId);
        billInfoPanel.add(billDate);
        billInfoPanel.add(itemCount);
        billInfoPanel.add(billComment);

        billItemPanel.setLayout(new BoxLayout(billItemPanel, BoxLayout.Y_AXIS));

        int value=100;
        JPanel[] itemPanel = new JPanel[value];
        JLabel[] itemName = new JLabel[value];
        JButton[] decreaseQuantity = new JButton[value];
        JTextField[] quantityField = new JTextField[value];
        JButton[] increaseQuantity = new JButton[value];
        JLabel[] itemPrice = new JLabel[value];

        for (int i=0; i<value; i++){
            itemPanel[i]=new JPanel(new GridLayout(1, 5, 5, 0 ));
            itemPanel[i].setBorder(new EmptyBorder(2,2,2,2));

            itemName[i] = new JLabel("Item "+i);
            itemName[i].setHorizontalAlignment(SwingConstants.CENTER);

            decreaseQuantity[i] = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("img/minus.png"))));

            quantityField[i] = new JTextField("1");
            quantityField[i].setHorizontalAlignment(JTextField.CENTER);

            increaseQuantity[i] = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("img/plus.png"))));

            itemPrice[i] = new JLabel("$ 234.34");
            itemPrice[i].setHorizontalAlignment(SwingConstants.CENTER);

            itemPanel[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            int finalI = i;
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
                    itemPanel[finalI].setBackground(new Color(0xADADAD));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemPanel[finalI].setBackground(null);
                }
            });
            itemPanel[i].add(itemName[i]);
            itemPanel[i].add(decreaseQuantity[i]);
            itemPanel[i].add(quantityField[i]);
            itemPanel[i].add(increaseQuantity[i]);
            itemPanel[i].add(itemPrice[i]);

            billItemPanel.add(itemPanel[i]);
        }
        billItem.getVerticalScrollBar().setUnitIncrement(16);

        billPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        billPanel.setTopComponent(billInfo);
        billPanel.setBottomComponent(billItem);
        billPanel.setDividerLocation(100);

        outerBillPanel.setLayout(new BorderLayout());
        outerBillPanel.add(billPanel);

        printPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        printPanel.add(outerBillPanel);
        printPanel.add(printButton);
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));

        window.add(outerBillPanel);
        window.add(printPanel);

        window.setSize(1280, 720);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

public class BillPlotter {
    public static void main(String[] args) {
        new BillPlotterCreateFrame();
    }
}
