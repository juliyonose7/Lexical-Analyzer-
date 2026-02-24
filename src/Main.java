import java.io.FileReader;
import java.io.IOException;

/**
 * Punto de entrada del analizador léxico.
 *
 * <p>Modo de operación:
 * <ul>
 *   <li>Sin argumentos: arranca la GUI interactiva ({@link LexerGUI}).</li>
 *   <li>Con ruta de archivo: procesa en modo consola e imprime tokens secuencialmente.</li>
 * </ul>
 */
public class Main {
    /**
     * Ejecuta el programa en modo GUI o modo consola según argumentos.
     *
     * @param args argumentos de línea de comandos; {@code args[0]} se interpreta
     *             como ruta de archivo de entrada en modo consola.
     */
    public static void main(String[] args) {
        // Si no hay archivo de entrada, se prioriza la experiencia interactiva.
        if (args.length == 0) {
            LexerGUI.launch();
            return;
        }

        String inputPath = args[0];

        try (FileReader reader = new FileReader(inputPath)) {
            Lexer lexer = new Lexer(reader);
            Token token;

            // Se consume el stream léxico hasta EOF o hasta un token de error.
            do {
                token = lexer.nextToken();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF && token.getType() != TokenType.ERROR);

            // El error léxico se informa explícitamente para facilitar depuración.
            if (token.getType() == TokenType.ERROR) {
                System.err.println("Se encontró un error léxico en la entrada.");
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
