package com.company;

import java.awt.geom.QuadCurve2D;
import java.sql.SQLOutput;
import java.util.*;

class GFG<E>{
    Node<E> rootNode;
    public static void main(String[] args) {
        Node<String> node1 = new Node<>("A");
        Node<String> node2 = new Node<>("B");
        Node<String> node3 = new Node<>("C");
        Node<String> node4 = new Node<>("D");
        Node<String> node5 = new Node<>("E");
        Node<String> node6 = new Node<>("F");

        node3.addNode(node6);

        node2.addNode(node4);
        node2.addNode(node5);

        node1.addNode(node2);
        node1.addNode(node3);

        GFG<String> gfg = new GFG<>(node1);
        gfg.LevelOrderTraversal();
    }
    GFG(Node rootNode){
        this.rootNode = rootNode;
    }

    Node<E> tempNode;
    Queue<Node> nodeQueue = new LinkedList<>();
    public void LevelOrderTraversal(){
        tempNode = rootNode;
        System.out.println(tempNode.data);
        nodeQueue.add(tempNode);
        do {
            NodeOrderTraversal(nodeQueue.poll());
        } while (!nodeQueue.isEmpty());
    }
    public void NodeOrderTraversal(Node parentNode){
        if (!parentNode.vector.isEmpty()) {
            Iterator<Node> levelNodes = parentNode.vector.iterator();
            while (levelNodes.hasNext()) {
                Node tempNode = levelNodes.next();
                System.out.print(tempNode.data+" ");
                nodeQueue.add(tempNode);
            }
            System.out.println("");
        }
    }
}
class Node<E>{
    E data;
    Vector<Node> vector;
    public Node(E data){
        this.data = data;
        vector = new Vector<>();
    }
    public void addNode(Node newNode){
        vector.add(newNode);
    }
    public Node getChildAt(int index){
        if(vector.isEmpty()) return null;
        return vector.get(index);
    }
}