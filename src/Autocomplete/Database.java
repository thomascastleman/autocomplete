package Autocomplete;

import java.sql.*;
import java.util.*;


class Database extends Main {  
	public static Connection con = null;
	public static void CreateConnection(){  
		try{  
			con=DriverManager.getConnection("jdbc:mysql://165.227.73.128/autocorrect?autoReconnect=true&useSSL=false","public","JFC-xNc-U2x-YqN");  
//			//constructTree(con, TreeType.WORDTREE);
//			Statement stmt=con.createStatement();  
//			//Boolean r=stmt.execute("INSERT INTO `jlindber_autocomplete`.`charTree` (`address`, `content`, `priority`, `isWord`, `children`) VALUES (NULL, 't', '0', '1', NULL);"); 
//			ResultSet rs=stmt.executeQuery("SELECT * FROM `wordTree`");  
//			ResultSetMetaData rsmd = rs.getMetaData();
//			System.out.println(rsmd);
//			while(rs.next()){  
//				for (int i = 1; i<5; i++){
//				System.out.print(rsmd.getColumnTypeName(i) +": "+rs.getObject(i) + "     ");  
//				}
//				System.out.println(" ");
//			}
		}catch(Exception e){ 
			System.out.println(e);
		}  
	}  

	public static void constructTree(TreeType t){
		CreateConnection();
		try{ 
			Statement stmt=con.createStatement();
			ResultSet rs = null;
			if (t == TreeType.CHARTREE){
				rs=stmt.executeQuery("SELECT * FROM `charTree`"); 
			}else{
				rs=stmt.executeQuery("SELECT * FROM `wordTree`");
			}
			HashMap<Node,int[]> nodeChildren =new HashMap<Node,int[]>(); 
			ArrayList<Node> nodes = new ArrayList<Node>();
			while(rs.next()){
				Node n = null;
				if (t == TreeType.CHARTREE){
					n = new Node(rs.getInt("id"), rs.getString("content"),rs.getInt("priority"), rs.getBoolean("isWord"));
				}else{
					n = new Node(rs.getInt("id"), rs.getString("content"),rs.getInt("priority"),false);
				}
				nodes.add(n);
				String childString = rs.getString("children");
				if (childString!=null){
					String[] childrenStringArray = childString.split(",");
					int[] childrenIntArray = new int[childrenStringArray.length];
					for(int i = 0; i<childrenStringArray.length; i++) {
						childrenIntArray[i] = Integer.parseInt(childrenStringArray[i]);
					}
					nodeChildren.put(n, childrenIntArray);
				}else{
					nodeChildren.put(n, null);
					}
			} 
			
			for(int i = 0; i<nodes.size(); i++) {
				int[] c = nodeChildren.get(nodes.get(i));
				if (c != null){
					for(int child = 0; child<c.length; child++) {
						nodes.get(i).children.addAll(searchForNodeWithId(c[child], nodes));
						//System.out.println(child);
					}
				}
			}
			if (t == TreeType.CHARTREE){
				Main.charTree.origin = searchForNodeWithId(1, nodes).get(0);
			}else{
				Main.wordTree.origin = searchForNodeWithId(1, nodes).get(0);
			}
			//Main.charTree.origin = searchForNodeWithId(1, nodes).get(0);
			//System.out.println(Arrays.toString(nodeChildren.get(nodes.get(2))));
			//System.out.println(nodes);

		}catch(Exception e){ 
			System.out.println(e);
		}  
		
	
	}
	
	
	public static void constructWordTree(Connection con){
		try{  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM `wordTree`"); 
			HashMap<Node,int[]> nodeChildren =new HashMap<Node,int[]>(); 
			ArrayList<Node> nodes = new ArrayList<Node>();
			while(rs.next()){
				Node n = new Node(rs.getInt("id"), rs.getString("content"),rs.getInt("priority"),false);
				nodes.add(n);
				String childString = rs.getString("children");
				if (childString!=null){
					String[] childrenStringArray = childString.split(",");
					int[] childrenIntArray = new int[childrenStringArray.length];
					for(int i = 0; i<childrenStringArray.length; i++) {
						childrenIntArray[i] = Integer.parseInt(childrenStringArray[i]);
					}
					nodeChildren.put(n, childrenIntArray);
				}else{
					nodeChildren.put(n, null);
					}
			} 
			
			for(int i = 0; i<nodes.size(); i++) {
				int[] c = nodeChildren.get(nodes.get(i));
				if (c != null){
					for(int child = 0; child<c.length; child++) {
						nodes.get(i).children.addAll(searchForNodeWithId(c[child], nodes));
						System.out.println(child);
					}
				}
			}
			con.close();
			Main.wordTree.origin = searchForNodeWithId(1, nodes).get(0);
			//System.out.println(Arrays.toString(nodeChildren.get(nodes.get(2))));
			System.out.println(Main.wordTree.origin.children.get(0).children.get(0).content);

		}catch(Exception e){ 
			System.out.println(e);
		}  
		
	
	}
	public static ArrayList<Node> searchForNodeWithId(int id, ArrayList<Node> n){
		ArrayList<Node> f = new ArrayList<Node>();
		for(int i = 0; i<n.size(); i++) {
			if (n.get(i).id == id){
				f.add(n.get(i));
			}
		}
		return f;
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
