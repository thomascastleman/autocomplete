package Autocomplete;

import java.sql.*;  
class Database{  
public static void main(String args[]){  
try{  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://box1275.bluehost.com:3306/jlindber_autocomplete","jlindber_public","Gnx-H7G-BBf-gdk");  
System.out.println("x");
//here sonoo is database name, root is username and password  
Statement stmt=con.createStatement();  
ResultSet rs=stmt.executeQuery("SELECT * from test");  
while(rs.next())  
System.out.println(rs.getInt(1));  
con.close();  
}catch(Exception e){ System.out.println(e);}  
}  
}  
