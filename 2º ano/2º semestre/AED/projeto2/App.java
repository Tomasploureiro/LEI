
//Arvore binaria de pesquisa

import javax.print.attribute.standard.NumberOfDocuments;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;


class No{
    int value;
    No left, right;
    public No(int item){
        value = item;
        left = right = null;
    }
}


class binaryTree{
    No root;

    binaryTree(){
        root = null;
    }

    void insert(int value){
        if(!search(root, value)){
            if(root == null){
                root = new No(value);
            }else{
                insertRecursive(root, value);
            }
        }
    }

    void insertRecursive(No no, int value){
        Random rand = new Random();
        if(no.left == null){
            no.left = new No(value);
        }else if(no.right == null){    
            no.right = new No(value);
        }else{
            boolean direcao = rand.nextBoolean();
            if(direcao){                    
                insertRecursive(no.right, value);
            }else{
                insertRecursive(no.left, value);
            }
        }
    }

    boolean search(No no, int value){
        if (no == null){
            return false;
        }
        if(no.value == value){
            return true;
        }
        return search(no.left, value) || search(no.right, value);
    }

    void printTree(){
        printTree(root, 20);
    }

    int printTree(No no, int limite){
        if (no == null || limite <= 0){
            return limite;
        }
        limite = printTree(no.left, limite);
        if (limite > 0){
        System.out.print(no.value + " ");
        limite--;
        }
        return printTree(no.right,limite);
    }

}


class binarySearchTree{
    No root;

    binarySearchTree(){
        root = null;
    }


    public void insert(int value){
        if(root == null){
            root = new No(value);
        }
        No current = root;
        while(current != null){
            if (value < current.value){
                if (current.left == null){
                    current.left = new No(value);
                }
                else{
                    current = current.left;
                }
            } 
            else if(value > current.value){
                if(current.right == null){
                    current.right = new No(value);
                }
                else{
                    current = current.right;
                }
            }
            else if(current.value == value){
                return;
            }
        }
    
    }

    public void printTree(){
        printTree(root, 20);
    }

    public void printTree(No no, int limite) {
        if (no == null || limite <= 0) {
            return;
        }
        printTree(no.left, limite - 1);
        System.out.print(no.value + " ");
        printTree(no.right, limite - 1);
    }
}


// class NoAvl{
//     int value;
//     NoAvl left, right, parent;
//     public NoAvl(int item){
//         value = item;
//         left = right = parent = null;

//     }
// }

// class AvlTree{
//     int rotacoes;
//     NoAvl root;
//     int currentSize;

//     public AvlTree(){
//         root = null;
//         currentSize = 0;
//     }
//     public void insert(int value){
//         NoAvl no = new NoAvl(value);
//         if(root == null){
//             root = no;
//             currentSize++;
//             return;
//         }
//         insertRecursive(root, no);
//     }

//     public void insertRecursive(NoAvl parent, NoAvl no){
//         if (parent.value < no.value){
//             if(parent.right == null){
//                 parent.right = no;
//                 no.parent = parent;
//                 currentSize ++;
//             }else{
//                 insertRecursive(parent.right, no);
//             }
//         }
//         else if(parent.value > no.value) {
//             if(parent.left == null){
//                 parent.left = no;
//                 no.parent = parent;
//                 currentSize++;
//             }else{
//                 insertRecursive(parent.left, no);
//             }
//         }
//         checkBalance(no);

//     }

//     public int height(NoAvl no){
//         if (no == null){
//             return 0;
//         }
//         int left = 0;
//         int right = 0;
        
//         if(no.left != null){
//             left = height(no.left) + 1;
//         }
//         if(no.right != null){
//             right = height(no.right) + 1;
//         }
//         if (right >= left){
//             return right;
//         }
//         else {
//             return left;
//         }

//     }


//     public void checkBalance(NoAvl no){
//         if((height(no.left) - height(no.right) > 1) || (height(no.left) - height(no.right) < -1)){
//             rebalance(no);
//         }
//         if(no.parent == null){
//             return;
//         }
//         checkBalance(no.parent);
//     }


//     public void rebalance(NoAvl no){
//         if (height(no.left) - height(no.right) > 1){
//             if(height(no.left.left) > height(no.left.right)){
//                 no = rightRotate(no);
//             }else {
//                 no = leftRightRotate(no);
//             }
//         }else{
//             if(height(no.right.left) > height(no.right.right)){
//                 no = leftRotate(no);
//             }else{
//                 no = rightLeftRotate(no);
//             }
//         }
//         if(no.parent == null){
//             root = no;
//         }
//     }

//     public NoAvl rightRotate(NoAvl no){
//         rotacoes++;
//         NoAvl temp = no.right;
//         no.right = temp.left;
//         temp.left = no;
//         return temp;
//     }

//     public NoAvl leftRotate(NoAvl no){
//         rotacoes++;
//         NoAvl temp = no.left;
//         no.left = temp.right;
//         temp.right = no;
//         return temp;
//     }

//     public NoAvl rightLeftRotate(NoAvl no){
//         no.right = rightRotate(no.right);
//         return leftRotate(no);
//     }

//     public NoAvl leftRightRotate(NoAvl no){
//         no.left = leftRotate(no.left);
//         return rightRotate(no); 
//     }

//     public void printTree(){
//         printTree(root, 20);
//     }

//     public void printTree(NoAvl no, int limite) {
//         if (no != null || limite <= 0) {
//             printTree(no.left, limite - 1);
//             System.out.print(no.value + " ");
//             printTree(no.right, limite - 1);
//         }
//     }
// }

class NoAvl {
    int value;
    NoAvl left, right;
    int height;

    NoAvl(int value) {
        this.value = value;
        height = 1;
    }
}

class AVLTree {
    int rotacoes;
    NoAvl root;

    int height(NoAvl node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int balanceFactor(NoAvl node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    NoAvl rightRotate(NoAvl y) {
        NoAvl x = y.left;
        NoAvl T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        rotacoes++;
        return x;
    }

    NoAvl leftRotate(NoAvl x) {
        NoAvl y = x.right;
        NoAvl T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        rotacoes++;
        return y;
    }
    
    void insert(int value) {
        root = insert(root, value);
    }
    NoAvl insert(NoAvl node, int value) {
        if (node == null)
            return new NoAvl(value);

        if (value < node.value)
            node.left = insert(node.left, value);
        else if (value > node.value)
            node.right = insert(node.right, value);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        // Left Left Case
        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void preOrder(NoAvl node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    int getRotacoes(){
        return rotacoes;
    }
}

class Node {
    int min, max;
    Node summary;
    Node[] clusters;
    
    public Node(int universeSize) {
        min = -1;
        max = -1;
        if (universeSize > 2) {
            int upperSquareRoot = (int) Math.ceil(Math.sqrt(universeSize));
            int lowerSquareRoot = (int) Math.floor(Math.sqrt(universeSize));
            summary = new Node(upperSquareRoot);
            clusters = new Node[upperSquareRoot];
            for (int i = 0; i < upperSquareRoot; i++) {
                clusters[i] = new Node(lowerSquareRoot);
            }
        }
    }
}

class VPTree {
    

    private Node root;
    private int universeSize;

    public VPTree(int universeSize) {
        this.universeSize = universeSize;
        root = new Node(universeSize);
    }
    
    public void insert(int x) {
        insert(root, x);
    }
    private void insert(Node node, int x) {
        if (node.min == -1) {
            node.min = x;
            node.max = x;
        } else {
            if (x < node.min) {
                int temp = node.min;
                node.min = x;
                x = temp;
            }
            if (node.max < x) {
                node.max = x;
            }
    
            if (node.clusters != null) {
                int upperSquareRoot = (int) Math.ceil(Math.sqrt(universeSize));
                int cluster = x / upperSquareRoot;
                int offset = x % upperSquareRoot;
                if (node.clusters[cluster] == null) {
                    node.clusters[cluster] = new Node(upperSquareRoot);
                }
                insert(node.clusters[cluster], offset);
                if (node.summary == null) {
                    node.summary = new Node(upperSquareRoot);
                }
                insert(node.summary, cluster);
            }
        }
    }

    public boolean contains(int x) {
        if (x == root.min || x == root.max) {
            return true;
        } else if (root.clusters != null) {
            int upperSquareRoot = (int) Math.ceil(Math.sqrt(universeSize));
            int cluster = x / upperSquareRoot;
            int offset = x % upperSquareRoot;
            return root.clusters[cluster].min != -1 && contains(root.clusters[cluster], offset);
        }
        return false;
    }

    private boolean contains(Node node, int x) {
        if (x == node.min || x == node.max) {
            return true;
        } else if (node.clusters != null) {
            int upperSquareRoot = (int) Math.ceil(Math.sqrt(universeSize));
            int cluster = x / upperSquareRoot;
            int offset = x % upperSquareRoot;
            return node.clusters[cluster].min != -1 && contains(node.clusters[cluster], offset);
        }
        return false;
    }
}
class inputs{
    Random rand = new Random();
    inputs(){}


    public ArrayList<Integer> conjuntoA(int size){
        ArrayList<Integer> array = new ArrayList<>(Collections.nCopies(size, 0));
        for (int i = 0; i<size - (size/10); i++){
            array.set(i, i);
        }
        for (int i = 0; i<size/10; i++){
            array.set(i+size - (size/10), rand.nextInt(size - (size/10)));
        }

        Collections.sort(array);

        return array;
    }

    public ArrayList<Integer> conjuntoB(int size){
        ArrayList<Integer> array = new ArrayList<>(Collections.nCopies(size, 0));
        for (int i = 0; i<size - (size/10); i++){
            array.set(i, i);
        }
        for (int i = 0; i<(size/10); i++){
            array.set(i + size - (size/10), rand.nextInt(size - (size/10)));
        }

        Collections.sort(array, Comparator.reverseOrder());

        return array;
    }

    public ArrayList<Integer> conjuntoC(int size){
        ArrayList<Integer> array = new ArrayList<>(Collections.nCopies(size, 0));
        for (int i = 0; i<size - (size/10); i++){
            array.set(i, i);
        }
        for (int i = 0; i<(size/10); i++){
            array.set(i + size - (size/10), rand.nextInt(size - (size/10)));
        }

        Collections.shuffle(array);

        return array;
    }

    public ArrayList<Integer> conjuntoD(int size){
        ArrayList<Integer> array = new ArrayList<>(Collections.nCopies(size, 0));
        for (int i = 0; i<(size/10); i++){
            array.set(i, i);
        }
        for (int i = 0; i< size - (size/10); i++){
            array.set(i + (size/10), rand.nextInt(size/10));
        }

        Collections.shuffle(array);

        return array;
    }

}



//Main
public class App{
        public static void main(String[] args) {
        inputs input = new inputs();
        int size;
        for (size = 600000; size<1000001; size+=200000){

        
       
        ArrayList<Integer> conjuntoA = input.conjuntoA(size);
        ArrayList<Integer> conjuntoB = input.conjuntoB(size);
        ArrayList<Integer> conjuntoC = input.conjuntoC(size);
        ArrayList<Integer> conjuntoD = input.conjuntoD(size);
      
        binaryTree bt = new binaryTree();
        binarySearchTree bst = new binarySearchTree();
        AVLTree avl = new AVLTree();
        VPTree vp = new VPTree(size);

    
        long startTime = System.nanoTime();
        for (Integer value : conjuntoA) {
            bt.insert(value);
        }
        long endTime = System.nanoTime();
        long durationBT_A = (endTime - startTime) / 1000000; 
        System.out.println("BT-A");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoA) {
//             bst.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationBST_A = (endTime - startTime) / 1000000; 
//         System.out.println("BST-A");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoA) {
//             avl.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationAVL_A = (endTime - startTime) / 1000000; 

//         startTime = System.nanoTime();
//         for (Integer value : conjuntoA) {
//             vp.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationVP_A = (endTime - startTime) / 1000000; 
//         System.out.println("VP-A");;

        startTime = System.nanoTime();
        for (Integer value : conjuntoB) {
            bt.insert(value);
        }
        endTime = System.nanoTime();
        long durationBT_B = (endTime - startTime) / 1000000; 
        System.out.println("BT-B");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoB) {
//             bst.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationBST_B = (endTime - startTime) / 1000000; 
//         System.out.println("BST-B");;
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoB) {
//             avl.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationAVL_B = (endTime - startTime) / 1000000; 

//         startTime = System.nanoTime();
//         for (Integer value : conjuntoB) {
//             vp.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationVP_B = (endTime - startTime) / 1000000;
//         System.out.println("VP-B");;
        startTime = System.nanoTime();
        for (Integer value : conjuntoC) {
            bt.insert(value);
        }
        endTime = System.nanoTime();
        long durationBT_C = (endTime - startTime) / 1000000;
        System.out.println("BT-C");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoC) {
//             bst.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationBST_C = (endTime - startTime) / 1000000;
//         System.out.println("BST-C");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoC) {
//             avl.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationAVL_C = (endTime - startTime) / 1000000; 

//         startTime = System.nanoTime();
//         for (Integer value : conjuntoC) {
//             vp.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationVP_C = (endTime - startTime) / 1000000; 
//         System.out.println("VP-C");
        startTime = System.nanoTime();
        for (Integer value : conjuntoD) {
            bt.insert(value);
        }
        endTime = System.nanoTime();
        long durationBT_D = (endTime - startTime) / 1000000; 
        System.out.println("BT-D");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoD) {
//             bst.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationBST_D = (endTime - startTime) / 1000000; 
//         System.out.println("BST-D");
//         startTime = System.nanoTime();
//         for (Integer value : conjuntoD) {
//             avl.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationAVL_D = (endTime - startTime) / 1000000; 

//         startTime = System.nanoTime();
//         for (Integer value : conjuntoD) {
//             vp.insert(value);
//         }
//         endTime = System.nanoTime();
//         long durationVP_D = (endTime - startTime) / 1000000; 
//         System.out.println("VP-D");
        System.out.println("\nTamanho: " + size + "\n");
        System.out.println("Tempo de inserção para o conjunto A:");
        System.out.println("Binary Tree: " + durationBT_A + " ms");
//         System.out.println("Binary Search Tree: " + durationBST_A + " ms");
//         System.out.println("AVL Tree: " + durationAVL_A + " ms " + avl.getRotacoes());
//         System.out.println("VP Tree: " + durationVP_A + " ms\n");

        System.out.println("Tempo de inserção para o conjunto B:");
        System.out.println("Binary Tree: " + durationBT_B + " ms");
//         System.out.println("Binary Search Tree: " + durationBST_B + " ms");
//         System.out.println("AVL Tree: " + durationAVL_B + " ms " + avl.getRotacoes());
//         System.out.println("VP Tree: " + durationVP_B + " ms\n");

        System.out.println("Tempo de inserção para o conjunto C:");
        System.out.println("Binary Tree: " + durationBT_C + " ms");
//         System.out.println("Binary Search Tree: " + durationBST_C + " ms");
//         System.out.println("AVL Tree: " + durationAVL_C + " ms " + avl.getRotacoes());
//         System.out.println("VP Tree: " + durationVP_C + " ms\n");

        System.out.println("Tempo de inserção para o conjunto D:");
        System.out.println("Binary Tree: " + durationBT_D + " ms");
//         System.out.println("Binary Search Tree: " + durationBST_D + " ms");
//         System.out.println("AVL Tree: " + durationAVL_D + " ms " + avl.getRotacoes());
//         System.out.println("VP Tree: " + durationVP_D + " ms");

    }}
}