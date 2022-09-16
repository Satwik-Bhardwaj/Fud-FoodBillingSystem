package com.company;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import static com.company.BillPlotter.ItemOptions;

class FoodSelect extends JPanel{
    //    JPanel foodSelectFrame;
    JPanel searchPanel;
    JPanel foodPanel;
    JTextField searchField;
    JButton searchNode;
    JScrollPane foodPanelScroll;
    DefaultMutableTreeNode SuperCategory;
    static JTree jTree;
    int i;
    int j;
    int k;

    DataFetcher dataFetcher;

    public FoodSelect(){

        searchPanel = new JPanel();
        foodPanel = new JPanel();

        searchField = new JTextField();
        searchNode = new JButton("Search");

        foodPanelScroll = new JScrollPane(foodPanel);

        dataFetcher = new DataFetcher();

        DataApplier dataApplier = new DataApplier();
        SuperCategory = dataApplier.makeJTreeModel();

        jTree = new JTree(SuperCategory);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // getting a selected node
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

                // if selected node is last then only we add the item
                if(SuperCategory.getDepth()==selectedNode.getLevel()){
                    Float price = dataApplier.getFoodPrice(selectedNode.toString());
                    // PutItemInside(selectedNode);
                    ItemOptions(selectedNode.toString(), price);
                }
            }
        });

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

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(searchPanel);
        add(foodPanelScroll);

        // setting UX of this panel
//        searchPanel.setBackground(new Color(0x2B2B2B));
//        searchField.setBackground(new Color(0x2B2B2B));
//        searchField.setForeground(new Color(0xAEB0B2));
//        searchField.setBorder(null);
//        searchNode.setBackground(new Color(0x003D98));
//        searchNode.setForeground(new Color(0xFFFFFF));


        setSize(1280,720);
        setVisible(true);
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
    // This function is need to check ---------------
    private TreePath find(DefaultMutableTreeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }

    // function finds the node which matches string(name)
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