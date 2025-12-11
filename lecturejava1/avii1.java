class Person {
    private String name;
    private int age;
    private String address;

    // Default constructor
    public Person() {
        this.name = "Unknown";
        this.age = 0;
        this.address = "Not available";
    }

    // Constructor with only name parameter
    public Person(String name) {
        this.name = name;
        this.age = 0;
        this.address = "Not available";
    }

    // Constructor with name and age parameters
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.address = "Not available";
    }

    // Constructor with all parameters
    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Method to display person details
    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
    }

    public static void main(String[] args) {
        // Creating objects using different constructors
        Person person1 = new Person();
        Person person2 = new Person("Alice");
        Person person3 = new Person("Bob", 25);
        Person person4 = new Person("Charlie", 30, "123 Street, City");

        // Displaying the details of each person
        person1.display();
        person2.display();
        person3.display();
        person4.display();
    }
}

