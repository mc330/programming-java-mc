package tree;

import java.io.BufferedOutputStream;
import java.util.Scanner;

public class Solution {

    static BufferedOutputStream output = new BufferedOutputStream(System.out, 4096);

    public static void main(String[] args) throws Exception {

        TwoThreeTree ct = new TwoThreeTree();
        Scanner sc = new Scanner(System.in);
        String buffer;
        String[] arr;
        int m = Integer.parseInt(sc.nextLine());//dataSize
        for (int i = 0; i < m; i++) {
            buffer = sc.nextLine();
            arr = buffer.split(" ");
            int cost = Integer.parseInt(arr[1]);
            twothree.insert(arr[0], cost, ct);
        }
        int n = Integer.parseInt(sc.nextLine());//select count
        String temp;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            buffer = sc.nextLine();
            arr = buffer.split(" ");
            int compare = arr[0].compareTo(arr[1]);
            if (compare > 0) {
                temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            printNew(ct.root, ct.height, arr[0], arr[1]);
        }
        output.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        output.close();
    }

    static LeafNode getMinNode(Node r) {
        if (r instanceof LeafNode) {
            return (LeafNode) r;
        } else {
            return getMinNode(((InternalNode) r).child0);
        }
    }

    static void printNew(Node r, int h, String first, String second) throws Exception {
        {
            if (r == null) {
                return;
            }
            if (r instanceof LeafNode) {
                if (r.guide.compareTo(first) >= 0 && r.guide.compareTo(second) <= 0) {
                    LeafNode lf = (LeafNode) r;
                    output.write((lf.guide + " " + lf.value + "\n").getBytes());
                }
            } else {
                Node child0 = ((InternalNode) r).child0;
                Node child1 = ((InternalNode) r).child1;
                Node child2 = ((InternalNode) r).child2;
                String guide = getMinNode(child0).guide;
                if (!(guide.compareTo(second) > 0)) {
                    if (child2 != null) {
                        boolean a = !(child0.guide.compareTo(first) < 0);
                        boolean b = !(child1.guide.compareTo(first) < 0);
                        boolean c = !(child2.guide.compareTo(first) < 0);
                        if (a && b && c) {
                            printNew(child0, h - 1, first, second);
                        }
                        if (b && c) {
                            printNew(child1, h - 1, first, second);
                        }
                        if (c) {
                            printNew(child2, h - 1, first, second);
                        }
                    } else {
                        boolean a = !(child0.guide.compareTo(first) < 0);
                        boolean b = !(child1.guide.compareTo(first) < 0);
                        if (a && b ) {
                            printNew(child0, h - 1, first, second);
                        }
                        if (b) {
                            printNew(child1, h - 1, first, second);
                        }

                    }
                }
            }
        }
    }
}

