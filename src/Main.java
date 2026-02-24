import java.io.FileReader;
import java.io.IOException;

/**
 * Entry point for the lexical analyzer.
 *
 * <p>Operating modes:
 * <ul>
 *   <li>No arguments: launches the interactive GUI ({@link LexerGUI}).</li>
 *   <li>With file path: runs in console mode and prints tokens sequentially.</li>
 * </ul>
 */
public class Main {
    /**
     * Executes the program in GUI or console mode based on CLI arguments.
     *
     * @param args command-line arguments; {@code args[0]} is interpreted
     *             as the input file path in console mode.
     */
    public static void main(String[] args) {
        // If no input file is provided, prioritize interactive usage.
        if (args.length == 0) {
            LexerGUI.launch();
            return;
        }

        String inputPath = args[0];

        try (FileReader reader = new FileReader(inputPath)) {
            Lexer lexer = new Lexer(reader);
            Token token;

            // Consume token stream until EOF or an error token is found.
            do {
                token = lexer.nextToken();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF && token.getType() != TokenType.ERROR);

            // Lexical error is reported explicitly for easier debugging.
            if (token.getType() == TokenType.ERROR) {
                System.err.println("Se encontró un error léxico en la entrada.");
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
