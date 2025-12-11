import java.awt.*
public class AWTExam AWTExam extend Frame{
    Button b = new Button("HIT ME");
    b.setBounds(30,100,80,30);
    add(b);
    setSize(300,300);
    setTitle("This is AWT");
    setLayout(null);
    setVisible(true);
    Public static void main (String[]args){
        AWTExam aw = new AWTExam();
    }
    }
}