import java.util.ArrayList;
import java.util.Collections;

public class Tamrin2 {
    private ArrayList<Integer> queue;
    
    public PriorityQueue(int size) {
        queue = new ArrayList<>(size);
    }

    public PriorityQueue() {
        queue = new ArrayList<>(10);
    }

    public void enqueue(int num) {
        if (queue.size() >= 10) {
            System.out.println("Cannot add anymore elements to the queue");
            return;
        }
        queue.add(num);
    }

    public int dequeue() {
        if (queue.isEmpty()) {
            System.out.println("There are no elements to remove");
            return -1;
        }

        int minIndex = 0;
        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i) < queue.get(minIndex)) {
                minIndex = i;
            }
        }

        int res = queue.get(minIndex);
        queue.remove(minIndex);  
        return res;
    }

    public void print() {
        for (Integer num : queue) {
            System.out.print(num + "  ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue(6);
        pq.enqueue(5);
        pq.enqueue(6);
        pq.enqueue(10);
        pq.enqueue(13);
        pq.enqueue(2);

        pq.print();

        System.out.println(pq.dequeue());
        System.out.println(pq.dequeue());

        pq.enqueue(9);
        pq.enqueue(17);
        pq.print();
    }
}