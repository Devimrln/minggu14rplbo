package com.rplbo.jdbc;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ManipulasiTableMahasiswa {
    private String url = "jdbc:sqlite:mahasiswa.db";

    public Connection connect(){
        Connection conn = null;
        try {
            //create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Koneksi berhasil.");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }
    public void createNewDatabase(){
        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Nama: " + meta.getDriverName());
                System.out.println("Database tercipta");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void createNewTable(){
        //SQL statment for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS mahasiswa (\n"
                + "  nim varchar(8) PRIMARY KEY,\n"
                + "  nama text NOT NULL,\n"
                + "  ipk real\n"
                + ");";
//        String sql2 = " INSERT INTO mahasiswa values" + "('12345670','Anton',3.5),('12345679','Adi',3.2)";

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){
            //create a new table
            stmt.execute(sql);
            System.out.println("Table tercipta!");
//            stmt.execute(sql2);
//            System.out.println("Table terisi!");

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectAll(){
        String sql = "SELECT nim,nama,ipk FROM mahasiswa";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            //loop throught the result set
            while (rs.next()){
                System.out.println(rs.getString("nim") + "\t" + rs.getString("nama") + "\t" + rs.getDouble("ipk"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertTable(String nama, String nim, String ipk){
        String sql = "INSERT INTO mahasiswa VALUES" + "('"+nim+"','"+nama+"','"+ipk+")";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();){
           stmt.execute(sql);
            System.out.println("Insert berhasil!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateTable(String nim){
        Scanner scn = new Scanner(System.in);
        System.out.print("Masukkan ipk baru: ");
        String ipk = scn.nextLine();
        String sql = "UPDATE mahasiswa SET ipk = "+ipk+" WHERE nim = '"+nim+"';";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Data berhasil diubah!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ManipulasiTableMahasiswa data = new ManipulasiTableMahasiswa();
        data.createNewDatabase();
        data.connect();
        data.createNewTable();
        data.insertTable("devi","12345671", "3");
        data.selectAll();
    }
}
