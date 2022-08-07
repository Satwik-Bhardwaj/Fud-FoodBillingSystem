package com.company;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

class CreateFrame{
    JFrame window;
    JPanel searchPanel;
    JPanel foodPanel;
    JTextField searchField;
    JButton searchNode;
    JScrollPane foodPanelScroll;
    DefaultMutableTreeNode SuperCategory;
    DefaultMutableTreeNode category1;
    JTree jTree;

    public CreateFrame(){
        window = new JFrame();

        searchPanel = new JPanel();
        foodPanel = new JPanel();

        searchField = new JTextField();
        searchNode = new JButton("Search");

        foodPanelScroll = new JScrollPane(foodPanel);

        SuperCategory = new DefaultMutableTreeNode("Dishes");
        category1 = new DefaultMutableTreeNode("Pizza");

        category1.add(new DefaultMutableTreeNode("OTC Pizza"));
        category1.add(new DefaultMutableTreeNode("Cheez Pizza"));
        category1.add(new DefaultMutableTreeNode("Salad Pizza"));

        SuperCategory.add(category1);
        jTree = new JTree(SuperCategory);
        jTree.setEditable(true);

        foodPanel.setLayout(new BorderLayout());
        foodPanel.add(jTree);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchHit();
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchHit();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchHit();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchHit();
            }
        });
        searchNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchHit();
            }
        });

        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        searchPanel.add(searchField);
        searchPanel.add(searchNode);

        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));

        window.add(searchPanel);
        window.add(foodPanelScroll);
        window.setSize(1280,720);
        window.setVisible(true);
    }

    private void searchHit() {
        foodPanel.remove(jTree);
        String key = searchField.getText().toString();
        if(searchNode(SuperCategory, key)!=null){
            jTree= new JTree(searchNode(SuperCategory, key));
            foodPanel.add(jTree);
            foodPanel.updateUI();
        } else if(searchNode(SuperCategory, key)==null){
            jTree= new JTree(SuperCategory);
            foodPanel.add(jTree);
            foodPanel.updateUI();
        }
    }

    public DefaultMutableTreeNode searchNode(DefaultMutableTreeNode m_rootNode, String nodeStr) {
        DefaultMutableTreeNode node = null;
        Enumeration e = m_rootNode.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (nodeStr.equals(node.getUserObject().toString())) {
                return node;
            }
        }
        return null;
    }
}
public class FoodSectionCreateFrame{
    public static void main(String[] args) {
        new CreateFrame();
    }
}