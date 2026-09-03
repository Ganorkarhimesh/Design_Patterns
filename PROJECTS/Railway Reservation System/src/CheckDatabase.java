import java.sql.*;

public class CheckDatabase {

    public static void main(String[] args) {

        String url = "jdbc:sqlite:railway.db";

        try (Connection conn = DriverManager.getConnection(url)) {

            System.out.println();
            System.out.println("==============================================================");
            System.out.println("                 RAILWAY RESERVATION SYSTEM");
            System.out.println("                    DATABASE REPORT");
            System.out.println("==============================================================");

            // Find all tables
            String tableQuery =
                    "SELECT name FROM sqlite_master " +
                            "WHERE type='table' AND name NOT LIKE 'sqlite_%' " +
                            "ORDER BY name";

            try (Statement stmt = conn.createStatement();
                 ResultSet tables = stmt.executeQuery(tableQuery)) {

                boolean foundTable = false;

                while (tables.next()) {

                    foundTable = true;
                    String tableName = tables.getString("name");

                    System.out.println();
                    System.out.println("==============================================================");
                    System.out.println(" TABLE: " + tableName.toUpperCase());
                    System.out.println("==============================================================");

                    displayTable(conn, tableName);
                }

                if (!foundTable) {
                    System.out.println();
                    System.out.println("No tables found in railway.db");
                }
            }

            System.out.println();
            System.out.println("==============================================================");
            System.out.println("                  DATABASE CHECK COMPLETE");
            System.out.println("==============================================================");
            System.out.println();

        } catch (SQLException e) {

            System.out.println();
            System.out.println("==============================================================");
            System.out.println("                     DATABASE ERROR");
            System.out.println("==============================================================");
            System.out.println(e.getMessage());
        }
    }

    // Display one complete table
    private static void displayTable(Connection conn, String tableName)
            throws SQLException {

        String dataQuery = "SELECT * FROM " + tableName;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(dataQuery)) {

            ResultSetMetaData meta = rs.getMetaData();

            int columnCount = meta.getColumnCount();

            // Store column names
            String[] columnNames = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = meta.getColumnName(i + 1);
            }

            // Store table data
            java.util.ArrayList<String[]> rows = new java.util.ArrayList<>();

            while (rs.next()) {

                String[] row = new String[columnCount];

                for (int i = 0; i < columnCount; i++) {

                    String value = rs.getString(i + 1);

                    if (value == null) {
                        value = "NULL";
                    }

                    row[i] = value;
                }

                rows.add(row);
            }

            // Calculate width of every column
            int[] widths = new int[columnCount];

            for (int i = 0; i < columnCount; i++) {

                widths[i] = columnNames[i].length();

                for (String[] row : rows) {

                    if (row[i].length() > widths[i]) {
                        widths[i] = row[i].length();
                    }
                }

                // Add a little spacing
                widths[i] += 2;
            }

            // Print separator
            printSeparator(widths);

            // Print column headings
            System.out.print("|");

            for (int i = 0; i < columnCount; i++) {

                System.out.printf(
                        " %-" + (widths[i] - 1) + "s|",
                        columnNames[i].toUpperCase()
                );
            }

            System.out.println();

            // Print separator
            printSeparator(widths);

            // Print rows
            if (rows.isEmpty()) {

                System.out.println("| No records found.");

            } else {

                for (String[] row : rows) {

                    System.out.print("|");

                    for (int i = 0; i < columnCount; i++) {

                        System.out.printf(
                                " %-" + (widths[i] - 1) + "s|",
                                row[i]
                        );
                    }

                    System.out.println();
                }
            }

            // Bottom separator
            printSeparator(widths);

            System.out.println("Total records: " + rows.size());
        }
    }

    // Print table separator
    private static void printSeparator(int[] widths) {

        System.out.print("+");

        for (int width : widths) {

            System.out.print("-".repeat(width));
            System.out.print("+");
        }

        System.out.println();
    }
}