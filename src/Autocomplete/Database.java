package Autocomplete;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;  

class Database extends Main{  
	public static void main(String args[]){  
		try{  
			Connection con=DriverManager.getConnection("jdbc:mysql://box1275.bluehost.com:3306/jlindber_autocomplete","jlindber_public","Gnx-H7G-BBf-gdk");  
			constructCharTree(con);
			Statement stmt=con.createStatement();  
			//Boolean r=stmt.execute("INSERT INTO `jlindber_autocomplete`.`charTree` (`address`, `content`, `priority`, `isWord`, `children`) VALUES (NULL, 't', '0', '1', NULL);"); 
			ResultSet rs=stmt.executeQuery("SELECT * FROM `charTree`");  
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println(rsmd);
			while(rs.next()){  
				for (int i = 1; i<6; i++){
				System.out.print(rsmd.getColumnTypeName(i) +": "+rs.getObject(i) + "     ");  
				}
				System.out.println(" ");
			}
			con.close();  
		}catch(Exception e){ 
			System.out.println(e);
		}  
	}  

	public static void constructCharTree(Connection con){
		try{  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM `charTree`"); 
			HashMap<Node,Integer[]> hm=new HashMap<Node,Integer[]>(); 
			while(rs.next()){
				//Node n = new Node();
				for (int i = 1; i<6; i++){
				System.out.print(rs.getObject(i) + "     ");  
				}
				System.out.println(" ");
			} 
		}catch(Exception e){ 
			System.out.println(e);
		}  
		
	
	}

}


/*
Create hashmap (a) of Node objects  --> int arrays
for element in query result
	Create Node Object with id, content, priority... of element
	Create int array of element.children
	Add relation to hashmap (a) of Node object --> int array
Create arraylist (d) of nodes from hashmap 

for element in arraylist (d)
	for c in a.get(element)
		find node in arraylist (d) where id == c and append it to element
Create tree object
Find origin node in arraylist of Node objects and move it to origin pointer in tree object
*/
