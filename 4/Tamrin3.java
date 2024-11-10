import java.util.Arrays;

class Tamrin3 {
    private int[] data;
    private int size1, fullSize, front1, front2, rear1, rear2;

    public TwoQueues(int size1, int size2) {
        this.fullSize = size1 + size2;
        this.data = new int[fullSize];
        this.size1 = size1;
        this.front1 = 0;
        this.front2 = size1;
        this.rear1 = -1;
        this.rear2 = size1 - 1;
    }

    public TwoQueues() {
        this(50, 50);  
    }

    public void enqueue(int value, int queueNumber) {
        if (queueNumber == 1) {
            if (rear1 + 1 >= size1) {
                System.out.println("Queue 1 is full.");
            } else {
                rear1++;
                data[rear1] = value;
            }
        } else if (queueNumber == 2) {
            if (rear2 + 1 >= fullSize) {
                System.out.println("Queue 2 is full.");
            } else {
                rear2++;
                data[rear2] = value;
            }
        }
    }

    public int dequeue(int queueNumber) {
        if (queueNumber == 1) {
            if (rear1 < front1) {
                System.out.println("Queue 1 is empty.");
                return -1;
            } else {
                return data[front1++];
            }
        } else if (queueNumber == 2) {
            if (rear2 < front2) {
                System.out.println("Queue 2 is empty.");
                return -1; 
            } else {
                return data[front2++];
            }
        }
        return -1; 
    }

    public void display() {
        System.out.print("Queue 1: ");
        for (int i = front1; i <= rear1; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();

        System.out.print("Queue 2: ");
        for (int i = front2; i <= rear2; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TwoQueues twoQueues = new TwoQueues(50, 50);

        twoQueues.enqueue(10, 1);
        twoQueues.enqueue(20, 1);

        twoQueues.enqueue(100, 2);
        twoQueues.enqueue(200, 2);

        twoQueues.display();

        System.out.println(twoQueues.dequeue(1));
        System.out.println(twoQueues.dequeue(2));

        twoQueues.display();
    }
}