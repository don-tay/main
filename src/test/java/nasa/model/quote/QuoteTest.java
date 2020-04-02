package nasa.model.quote;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class QuoteTest {

    @Test
    void initialisation() {
        Quote.readFile();
        assertFalse(Quote.getLines().isEmpty());
    }

    @Test
    void getQuoteTest() {
        Quote.readFile();

        assertFalse(Quote.getQuote().isEmpty());
        assertFalse(Quote.getQuote().isEmpty());
        assertFalse(Quote.getQuote().isEmpty());
        assertFalse(Quote.getQuote().isEmpty());
        assertFalse(Quote.getQuote().isEmpty());
        assertFalse(Quote.getQuote().isEmpty());
    }

}
