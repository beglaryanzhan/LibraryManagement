import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UI extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem addBookMenuItem, removeSelected, exitMenuItem, exportToCSV, importFromCSV;
    private JDialog addBookDialog;
    private JButton addButton;

    private JList<Book> bookList;

    private DefaultListModel<Book> bookListModel;

    private Library newOne;
    private JTable bookTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter;


    public UI() {
        super("Library Management System");

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        addBookMenuItem = new JMenuItem("Add Book");
        removeSelected = new JMenuItem("Remove Selected");
        importFromCSV = new JMenuItem("Import from CSV");
        exportToCSV = new JMenuItem("Export to CSV");
        exitMenuItem = new JMenuItem("Exit");


        newOne = new Library();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Genre");

        tableModel = createTableModel(new Object[0][0], new Object[]{"ID", "Title", "Author", "Genre"});

        bookTable = new JTable(tableModel);
        scrollPane = new JScrollPane(bookTable);

        add(scrollPane);

        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);

        addBookListener();
        removeSelectedListener();

        viewAvailableBooksListener();

        fileMenu.add(addBookMenuItem);
        fileMenu.add(removeSelected);
        fileMenu.add(importFromCSV);
        fileMenu.add(exportToCSV);
        fileMenu.add(exitMenuItem);


        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        exportToCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        importFromCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromCSV();
            }
        });


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void addBookListener() {
        addBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addBookDialog = new JDialog(UI.this, "Add Book", true);
                addBookDialog.setSize(300, 200);
                addBookDialog.setLayout(new FlowLayout());

                JTextField titleField = new JTextField(25);
                JTextField authorField = new JTextField(25);
                JTextField genreField = new JTextField(25);
                addBookDialog.add(new JLabel("Title: "));
                addBookDialog.add(titleField);
                addBookDialog.add(new JLabel("Author: "));
                addBookDialog.add(authorField);
                addBookDialog.add(new JLabel("Genre: "));
                addBookDialog.add(genreField);

                addButton = new JButton("Add");
                addBookDialog.add(addButton);

                addBookDialog.setLocationRelativeTo(UI.this);


                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String title = titleField.getText().trim();
                        String author = authorField.getText().trim();
                        String genre = genreField.getText().trim();

                        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                            JOptionPane.showMessageDialog(addBookDialog, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Book newBook = new Book(title, author, genre);

                        newOne.addBook(newBook);

                        bookListModel.addElement(newBook);

                        titleField.setText("");
                        authorField.setText("");
                        genreField.setText("");


                        viewAvailableBooksListener();
                    }
                });
                addBookDialog.setVisible(true);
            }
        });
    }


    public void removeSelectedListener() {
        removeSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = bookTable.getSelectedRows();
                Integer[] sortedRows = new Integer[selectedRows.length];
                for (int i = 0; i < selectedRows.length; i++) {
                    sortedRows[i] = bookTable.convertRowIndexToModel(selectedRows[i]);
                }
                Arrays.sort(sortedRows, Collections.reverseOrder());

                tableModel = (DefaultTableModel) bookTable.getModel();
                for (int row : sortedRows) {
                    newOne.displayBooks().removeAt(row);
                    tableModel.removeRow(row);
                }
                viewAvailableBooksListener();
            }
        });
    }


    public void importFromCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import from CSV");
        filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showOpenDialog(UI.this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                clearLibraryData();

                tableModel.setRowCount(0);
                String headerLine = reader.readLine();
                String[] columnNames = headerLine.split(",");

                if (!hasRequiredColumns(columnNames)) {
                    JOptionPane.showMessageDialog(UI.this, "Invalid CSV file. Missing required columns.(ID,Title,Author,Genre)", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split(",");
                    Book newBook = new Book(rowData[1], rowData[2], rowData[3]);
                    newOne.addBook(newBook);
                    tableModel.addRow(rowData);
                }
                reader.close();
                viewAvailableBooksListener();
                bookTable.setModel(tableModel);
                JOptionPane.showMessageDialog(UI.this, "CSV file imported successfully.", "Import Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(UI.this, "Error importing CSV file.", "Import Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public void clearLibraryData() {
        newOne.displayBooks().clear();
    }
    private boolean hasRequiredColumns(String[] columns) {
        for (String column : columns) {
            if (column.trim().equalsIgnoreCase("ID") ||
                    column.trim().equalsIgnoreCase("Title") ||
                    column.trim().equalsIgnoreCase("Author") ||
                    column.trim().equalsIgnoreCase("Genre")) {
                return true;
            }
        }
        return false;
    }
    public void exportToCSV() {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as CSV");
        int userSelection = fileChooser.showSaveDialog(UI.this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                FileWriter writer = new FileWriter(filePath);

                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.append(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        writer.append(tableModel.getValueAt(row, col).toString());
                        if (col < tableModel.getColumnCount() - 1) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(UI.this, "Table data exported to CSV successfully.", "Export Successful", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(UI.this, "Error exporting table data to CSV.", "Export Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void viewAvailableBooksListener() {
        tableModel = (DefaultTableModel) bookTable.getModel();
        tableModel.setRowCount(0);

        for (int i = 0; i < newOne.displayBooks().getSize(); i++) {
            Book book = newOne.displayBooks().getAt(i);
            Object[] rowData = { i, book.getTitle(), book.getAuthor(), book.getGenre() };
            tableModel.addRow(rowData);
        }
    }

    private DefaultTableModel createTableModel(Object[][] data, Object[] columnNames) {
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                String columnName = getColumnName(column);
                return !columnName.equals("ID");
            }
        };
    }
}

