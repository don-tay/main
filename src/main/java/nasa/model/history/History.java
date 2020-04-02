package nasa.model.history;

import java.util.List;
import java.util.Stack;

/**
 * Abstract History class to store users actions.
 */
public abstract class History<T> {

    private Stack<T> stack;

    private T item;

    History() {
        item = null;
        stack = new Stack<T>();
    }

    History(Stack<T> history) {
        item = null;
        stack = history;
    }

    /**
     * Push an item into stack from item.
     * @param item must not be null.
     */
    public void push(T item) {
        if (this.item != null) {
            stack.push(this.item);
        }
        this.item = item;
    }

    /**
     * Push an item directly into the stack.
     * @param item must not be null.
     */
    public void pushDirectly(T item) {
        stack.push(item);
    }

    /**
     * Pop an item from the stack. To get previous item.
     * @return boolean
     */
    public boolean pop() {
        if (!isEmpty()) {
            item = stack.pop();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Pop an item from the stack directly.
     * @return T.
     */
    public T popDirectly() {
        return stack.pop();
    }

    public boolean hasItem() {
        return item != null;
    }

    public T getPop() {
        return item;
    }

    public void resetItem() {
        this.item = null;
    }

    public void setStack(List<T> stack) {
        this.stack.addAll(stack);
    }

    public Stack<T> getStack() {
        return stack;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
