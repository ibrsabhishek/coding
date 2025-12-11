public class MyArray {
         int[] array;
         int size;
         int capacity;
    
        public MyArray(int capacity) {
            this.capacity = capacity;
            this.array = new int[capacity];
            this.size = 0;
        }
    
        public void add(int element) {
            if (size < capacity) {
                array[size] = element;
                size++;
            } else {
                System.out.println("Array is full. Cannot add more elements.");
            }
        }
    
        public int get(int index) {
            if (index >= 0 && index < size) {
                return array[index];
            } else {
                throw new IndexOutOfBoundsException("Index out of bounds.");
            }
        }
    
        public void set(int index, int element) {
            if (index >= 0 && index < size) {
                array[index] = element;
            } else {
                throw new IndexOutOfBoundsException("Index out of bounds.");
            }
        }
    
        public int size() {
            return size;
        }
    
        public static void main(String[] args) {
            MyArray myArray = new MyArray(5);
            myArray.add(10);
            myArray.add(20);
            myArray.add(30);
            myArray.add(40);
            myArray.add(50);
    
            System.out.println("Array elements:");
            for (int i = 0; i < myArray.size(); i++) {
                System.out.println(myArray.get(i));
            }
    
            // Trying to add more elements than the capacity
            myArray.add(70);
    
            // Changing element at index 2
            myArray.set(2, 35);
    
            System.out.println("Array elements after modification:");
            for (int i = 0; i < myArray.size(); i++) {
                System.out.println(myArray.get(i));
            }
        }
    }
    

