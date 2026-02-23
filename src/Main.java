import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Main <archivo_entrada>");
            return;
        }

        String inputPath = args[0];

        try (FileReader reader = new FileReader(inputPath)) {
            Lexer lexer = new Lexer(reader);
            Token token;

            do {
                token = lexer.nextToken();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF && token.getType() != TokenType.ERROR);

            if (token.getType() == TokenType.ERROR) {
                System.err.println("Se encontró un error léxico en la entrada.");
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
