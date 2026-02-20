package project20280.exercises;

import project20280.interfaces.Stack;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import project20280.stacksqueues.ArrayStack;

class Wk3 {
    static String convertToBinary(long num){
        if (num == 0){
            return "0";
        }

        Stack<Long> stack = new ArrayStack<>();

        while (num > 0){
            stack.push(num % 2);
            num /= 2;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    @Test
    void testConvertToBinary(){
        assertEquals("10111", convertToBinary(23));
        String expectedLarge = "111001000000101011000010011101010110110001100010000000000000";
        assertEquals(expectedLarge, convertToBinary(1027010000000000000L));
    }
}
