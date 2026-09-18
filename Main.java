import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SingleLineTextEditor editor = new SingleLineTextEditor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command (I: Insert, D: Delete, L: Left, R: Right, U: Undo, Y: Redo): ");
            char command = scanner.nextLine().charAt(0);

            switch (command) {
                case 'I': {
                    System.out.print("Enter text to insert: ");
                    String text = scanner.nextLine();
                    editor.insert(text);
                    break;
                }
                case 'D':
                    editor.deleteChar();
                    break;
                case 'L':
                    editor.moveLeft();
                    break;
                case 'R':
                    editor.moveRight();
                    break;
                case 'U':
                    editor.undo();
                    break;
                case 'Y':
                    editor.redo();
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
}
