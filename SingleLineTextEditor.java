import java.util.Stack;

class SingleLineTextEditor {
    private Node head;
    private Node cursor;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public SingleLineTextEditor() {
        Node cursorNode = new Node('|');
        this.head = cursorNode;
        this.cursor = cursorNode;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    private String text2String() {
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            curr = curr.next;
        }
        return sb.toString();
    }

    private void string2Text(String str) {
        head = null;
        cursor = null;
        Node prevNode = null;
        boolean cursorFound = false;

        for (char c : str.toCharArray()) {
            Node newNode = new Node(c);
            if (head == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
                newNode.prev = prevNode;
            }
            prevNode = newNode;
            if (c == '|') {
                cursor = newNode;
                cursorFound = true;
            }
        }

        if (!cursorFound) {
            Node cursorNode = new Node('|');
            if (prevNode != null) {
                prevNode.next = cursorNode;
                cursorNode.prev = prevNode;
            } else {
                head = cursorNode;
            }
            cursor = cursorNode;
        }
    }

    private void saveState() {
        undoStack.push(text2String());
        redoStack.clear();
    }

    public void insert(String text) {
        saveState();
        for (char c : text.toCharArray()) {
            Node newNode = new Node(c);
            newNode.prev = cursor.prev;
            newNode.next = cursor;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            cursor.prev = newNode;
        }
        displayText();
    }

    public void deleteChar() {
        saveState();
        if (cursor.prev != null) {
            Node toDelete = cursor.prev;
            cursor.prev = toDelete.prev;
            if (toDelete.prev != null) {
                toDelete.prev.next = cursor;
            } else {
                head = cursor;
            }
        }
        displayText();
    }

    public void moveLeft() {
        if (cursor.prev != null) {
            char temp = cursor.data;
            cursor.data = cursor.prev.data;
            cursor.prev.data = temp;
            cursor = cursor.prev;
        }
        displayText();
    }

    public void moveRight() {
        if (cursor.next != null) {
            char temp = cursor.data;
            cursor.data = cursor.next.data;
            cursor.next.data = temp;
            cursor = cursor.next;
        }
        displayText();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(text2String());
            string2Text(undoStack.pop());
        }
        displayText();
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(text2String());
            string2Text(redoStack.pop());
        }
        displayText();
    }

    public void displayText() {
        System.out.println("------------------------ Text Editor Start ------------------------");
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data);
            curr = curr.next;
        }
        System.out.println();
        System.out.println("------------------------- Text Editor End -------------------------");
    }
}
