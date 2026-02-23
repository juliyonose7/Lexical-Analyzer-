import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class LexerGUI extends JFrame {
    private final JTextArea inputArea;
    private final JLabel statusLabel;
    private final DefaultTableModel tableModel;

    public LexerGUI() {
        setTitle("Analizador Léxico - Interfaz Aero Azul/Blanco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 640);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.setBackground(new Color(245, 250, 255));
        setContentPane(content);

        JLabel title = new JLabel("Analizador Léxico Interactivo", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(8, 72, 146));

        JPanel header = new AeroHeaderPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.add(title, BorderLayout.CENTER);

        content.add(header, BorderLayout.NORTH);

        inputArea = new JTextArea();
        inputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputArea.setText("int contador = 10;\nwhile (contador > 0) {\n    contador--;\n}");
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(130, 180, 235), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Código de entrada"));

        tableModel = new DefaultTableModel(new Object[] {"Tipo", "Lexema", "Línea", "Columna"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tokenTable = new JTable(tableModel);
        tokenTable.setRowHeight(22);
        tokenTable.setFillsViewportHeight(true);
        tokenTable.getTableHeader().setBackground(new Color(210, 232, 255));
        tokenTable.getTableHeader().setForeground(new Color(8, 72, 146));

        JScrollPane tableScroll = new JScrollPane(tokenTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Tokens detectados"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputScroll, tableScroll);
        splitPane.setResizeWeight(0.45);
        splitPane.setDividerSize(8);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        content.add(splitPane, BorderLayout.CENTER);

        JButton analyzeButton = new JButton("Analizar");
        analyzeButton.setFocusPainted(false);
        analyzeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        analyzeButton.setBackground(new Color(30, 128, 226));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setPreferredSize(new Dimension(130, 36));
        analyzeButton.addActionListener(e -> analyzeInput());

        statusLabel = new JLabel("Listo");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(25, 86, 155));

        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setOpaque(false);
        footer.add(analyzeButton, BorderLayout.WEST);
        footer.add(statusLabel, BorderLayout.CENTER);

        content.add(footer, BorderLayout.SOUTH);
    }

    private void analyzeInput() {
        tableModel.setRowCount(0);
        int total = 0;

        try (StringReader reader = new StringReader(inputArea.getText())) {
            Lexer lexer = new Lexer(reader);
            Token token;

            do {
                token = lexer.nextToken();
                tableModel.addRow(new Object[] {
                        token.getType().name(),
                        token.getLexeme(),
                        token.getLine(),
                        token.getColumn()
                });
                total++;
            } while (token.getType() != TokenType.EOF && token.getType() != TokenType.ERROR);

            if (token.getType() == TokenType.ERROR) {
                statusLabel.setText("Finalizó con error léxico. Tokens mostrados: " + total);
                statusLabel.setForeground(new Color(176, 24, 24));
            } else {
                statusLabel.setText("Análisis completado correctamente. Tokens: " + total);
                statusLabel.setForeground(new Color(25, 86, 155));
            }
        } catch (IOException ex) {
            statusLabel.setText("Error de lectura: " + ex.getMessage());
            statusLabel.setForeground(new Color(176, 24, 24));
        }
    }

    public static void launch() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            LexerGUI gui = new LexerGUI();
            gui.setVisible(true);
        });
    }

    private static class AeroHeaderPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint paint = new GradientPaint(
                    0, 0, new Color(222, 240, 255),
                    0, getHeight(), new Color(171, 212, 250)
            );
            g2.setPaint(paint);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.setColor(new Color(90, 155, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.dispose();
        }
    }
}
