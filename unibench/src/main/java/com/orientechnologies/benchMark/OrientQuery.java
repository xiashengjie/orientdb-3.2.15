package com.orientechnologies.benchMark;

import java.sql.*;
import java.util.Properties;

import com.orientechnologies.orient.jdbc.OrientJdbcConnection;
import com.orientechnologies.orient.jdbc.OrientJdbcDriver;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.CpolarUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;

/**
 * @author xsj
 */
public class OrientQuery extends MMDB {
	@Override
	OrientJdbcConnection Connection(OrientdbEnum orientdbEnum,String url){
		OrientJdbcConnection conn=null;
	try {
		Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
		String database = orientdbEnum.getName();
		Class.forName(OrientJdbcDriver.class.getName());
			  String dbUrl = url+database;
			  String username = properties.getProperty("database.username");
			  String password = properties.getProperty("database.password");
			  //Create Connection
			  Properties info = new Properties();
			  info.put("user", username);
			  info.put("password", password);
			  System.out.println(dbUrl);
			  conn = (OrientJdbcConnection) DriverManager.getConnection("jdbc:orient:" + dbUrl, info);
			  System.out.println("-------------------------"+conn);
	}
//	catch (ClassNotFoundException e) {
//		e.printStackTrace();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
	catch (Exception e){
//		QueryUtils.refreshDatabase(OrientdbEnum.OSYSTEM);
//		Connection(orientdbEnum);
		//CpolarUtils.getOrientdbUrl();
		e.printStackTrace();
	}
		  return conn;
	}


    @Override
	public long Q1(String PersonId,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
		String OQ1="Select $profile,$orders,$feedback,$posts,$list1,$list2 "
				+ "let $profile=(select from `Customer` where id=?),"
				+ "$orders=(select Expand(Order) from `Customer` where id=?),"
				+ "$feedback=(select Expand(Feedback) from `Customer` where id=?),"
				+ "$posts= (select Out(\'PersonHasPost\') from `Customer` where id=?),"
				+ "$list1= (select list.brand as brand, count(list.brand) as cnt from (select Order.Orderline as list from `Customer` where id=? unwind list) group by list.brand ORDER BY cnt DESC),"
				+ "$list2=(select pid, count(pid) from (select Out(\'PersonHasPost\').Out(\'PostHasTag\').productId as pid from `Customer` where id=? unwind pid) group by pid order by count Desc)";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
		try {
			PreparedStatement stmt1 = conn.prepareStatement(OQ1);
			stmt1.setString(1, PersonId);
			stmt1.setString(2, PersonId);
			stmt1.setString(3, PersonId);
			stmt1.setString(4, PersonId);
			stmt1.setString(5, PersonId);
			stmt1.setString(6, PersonId);
			long millisStart1 = System.currentTimeMillis();
			ResultSet rs1 = stmt1.executeQuery();
			long millisEnd1 = System.currentTimeMillis();
			time = millisEnd1 - millisStart1;
			System.out.println("Query 1 took "+(millisEnd1 - millisStart1) + " ms");
			rs1.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    } 
    
    @Override
	public long Q2(String ProductId,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ2="Select $person let $list=(select In(\'PostHasTag\').In(\'PersonHasPost\').id as pid "
    			+ "from `Product` where productId=?),$person=(select PersonId,Orderline.productId from Order "
    			+ "where OrderDate>\"2022\" and PersonId in $list and ? in Orderline.productId)";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart2 = System.currentTimeMillis();
			PreparedStatement stmt2 = conn.prepareStatement(OQ2);
			stmt2.setString(1, ProductId);
			stmt2.setString(2, ProductId);
			ResultSet rs2 = stmt2.executeQuery();
			long millisEnd2 = System.currentTimeMillis();
			time = millisStart2 - millisEnd2;
			System.out.println("Query 2 took "+(millisEnd2 - millisStart2) + " ms");
			rs2.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    } 
    
    @Override
	public long Q3(String ProductId,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ3="Select $post,$feedback "
    			+ "let $post=(select Expand(In(\'PostHasTag\')) from `Product` "
    			+ "where productId=?),"
    			+ "$feedback=(select * from `Feedback` where asin=? and feedback.charAt(1).asInteger() <5)";
    	OrientJdbcConnection conn =  this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart3 = System.currentTimeMillis();
			PreparedStatement stmt3 = conn.prepareStatement(OQ3);
			stmt3.setString(1, ProductId);
			stmt3.setString(2, ProductId);
			ResultSet rs3 = stmt3.executeQuery();
			long millisEnd3 = System.currentTimeMillis();
			time = millisEnd3 - millisStart3;
			System.out.println("Query 3 took "+(millisEnd3 - millisStart3) + " ms");
			rs3.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q4(OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ4="SELECT commonset.size() from (SELECT intersect($set1,$set2) as commonset "
    			+ "let $person = (select pid from (select PersonId as pid, SUM(TotalPrice) as sum from Order Group by PersonId order by sum desc limit 2)),"
    			+ "$set1=(TRAVERSE out(\"Knows\") FROM (select from Customer where PersonId=$person.pid[0]) while $depth <= 3 STRATEGY BREADTH_FIRST),"
    			+ "$set2=(TRAVERSE out(\"Knows\") FROM (select from Customer where PersonId=$person.pid[1]) while $depth <= 3 STRATEGY BREADTH_FIRST))";
    	OrientJdbcConnection conn =  this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart4 = System.currentTimeMillis();
			PreparedStatement stmt4 = conn.prepareStatement(OQ4);
			ResultSet rs4 = stmt4.executeQuery();
			long millisEnd4 = System.currentTimeMillis();
			time = millisEnd4 - millisStart4;
			System.out.println("Query 4 took "+(millisEnd4 - millisStart4) + " ms");
			rs4.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q5(String PersonId, String brand,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ5="Select Out(\'PersonHasPost\').Out(\'PostHasTag\') as tags from (select Expand(Out(\'Knows\')) from Customer where id=?) Where ? in Order.Orderline.brand unwind tags";
    	OrientJdbcConnection conn =  this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart5 = System.currentTimeMillis();
			PreparedStatement stmt5 = conn.prepareStatement(OQ5);
			String Q5PersonId=PersonId;
			String Q5Brand=brand;
			stmt5.setString(1, Q5PersonId);
			stmt5.setString(2, Q5Brand);
			ResultSet rs5 = stmt5.executeQuery();
			long millisEnd5 = System.currentTimeMillis();
			time = millisEnd5 - millisStart5;
			System.out.println("Query 5 took "+(millisEnd5 - millisStart5) + " ms");
			rs5.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q6(String startPerson, String EndPerson,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ6="SELECT transactions, count(transactions) as cnt "
    			+ "FROM(SELECT Order.Orderline.productId as transactions from(SELECT EXPAND(path) from(SELECT shortestPath($from, $to) AS path "
    			+ "LET $from = (SELECT FROM Customer WHERE id=?),"
    			+ "$to = (SELECT FROM Customer WHERE id=?))) unwind transactions) GROUP BY transactions Order by cnt DESC LIMIT 5";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart6 = System.currentTimeMillis();
			PreparedStatement stmt6 = conn.prepareStatement(OQ6);
			String Q6PersonId1=startPerson;
			String Q6PersonId2=EndPerson;
			stmt6.setString(1, Q6PersonId1);
			stmt6.setString(2, Q6PersonId2);
			ResultSet rs6 = stmt6.executeQuery();
			long millisEnd6 = System.currentTimeMillis();
			time = millisEnd6 - millisStart6;
			System.out.println("Query 6 took "+(millisEnd6 - millisStart6) + " ms");
			rs6.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q7(String brand,OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ7="Select feedback from Feedback where asin in "
    			+ "(Select dlist from(Select set(dlist) as dlist  "
    			+ "from(Select $declineList.asin as dlist "
    			+ "let $list1 = (Select asin,count(asin) as cnt from "
    			+ "(Select ol_unwind.asin as asin, ol_unwind.brand as brand from "
    			+ "(Select Orderline as ol_unwind from (Select From Order Where OrderDate>\"2018\" and OrderDate<\"2019\" and ? in Orderline.brand) unwind ol_unwind)) "
    			+ "where brand=? group by asin order by cnt DESC), "
    			+ "$list2=(Select asin,count(asin) as cnt from (Select ol_unwind.asin as asin, ol_unwind.brand as brand from "
    			+ "(Select Orderline as ol_unwind from (Select From Order Where OrderDate>\"2019\" and OrderDate<\"2020\" and ? in Orderline.brand) unwind ol_unwind)) "
    			+ "where brand=? group by asin order by cnt DESC), $declineList=compareListTest($list1,$list2))))";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart7 = System.currentTimeMillis();
			PreparedStatement stmt7 = conn.prepareStatement(OQ7);
			String Q7Brand=brand;
			stmt7.setString(1, Q7Brand);
			stmt7.setString(2, Q7Brand);
			stmt7.setString(3, Q7Brand);
			stmt7.setString(4, Q7Brand);
			ResultSet rs7 = stmt7.executeQuery();
			long millisEnd7 = System.currentTimeMillis();
			time = millisEnd7 - millisStart7;
			System.out.println("Query 7 took "+(millisEnd7 - millisStart7) + " ms");
			rs7.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q8(OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ8="Select Sum(Popularity) from(Select In(\'PostHasTag\').size() as Popularity "
    			+ "from `Product` Where productId in (Select  Distinct(Orderline.productId) "
    			+ "From (Select Orderline From Order let  $brand=(select name as brand from `Vendor` where country='China') "
    			+ "Where OrderDate>\"2018\" and OrderDate<\"2019\" unwind Orderline) Where Orderline.brand in $brand.brand))";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart8 = System.currentTimeMillis();
			PreparedStatement stmt8 = conn.prepareStatement(OQ8);
			ResultSet rs8 = stmt8.executeQuery();
			long millisEnd8 = System.currentTimeMillis();
			time = millisEnd8 - millisStart8;
			System.out.println("Query 8 took "+(millisEnd8 - millisStart8) + " ms");
			rs8.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q9(OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ9="Select Orderline.brand, count(*) from(Select PersonId, Orderline From Order "
    			+ "Let  $brand=(select name as brand from `Vendor` where country='China') Where OrderDate>\"2018\" and OrderDate<\"2019\" unwind Orderline) "
    			+ "Where Orderline.brand in $brand.brand Group by Orderline.brand Order by count DESC LIMIT 3";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart9 = System.currentTimeMillis();
			PreparedStatement stmt9 = conn.prepareStatement(OQ9);
			ResultSet rs9 = stmt9.executeQuery();
			long millisEnd9 = System.currentTimeMillis();
			time = millisEnd9 - millisStart9;
			System.out.println("Query 9 took "+(millisEnd9 - millisStart9) + " ms");
			rs9.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	public long Q10(OrientdbEnum orientdbEnum,String dbUrl) {
		long time;
    	String OQ10="SELECT id, max(Order.OrderDate) as Recency,Order.size() as Frequency,sum(Order.TotalPrice) as Monetary FROM Customer "
    			+ "Where id in(Select id, count(id) as cnt from (Select IN(\'PersonHasPost\').id[0] as id From Post "
    			+ "Where creationDate>= date( \'2012-10-01\', \'yyyy-MM-dd\')) Group by id  Order by cnt DESC limit 10) GROUP BY id";
    	OrientJdbcConnection conn = this.Connection(orientdbEnum,dbUrl);
	    try {
			long millisStart10 = System.currentTimeMillis();
			PreparedStatement stmt10 = conn.prepareStatement(OQ10);
			ResultSet rs10 = stmt10.executeQuery();
			long millisEnd10 = System.currentTimeMillis();
			time = millisEnd10 - millisStart10;
			System.out.println("Query 10 took "+(millisEnd10 - millisStart10) + " ms");
			rs10.close();
			conn.close();
		} catch (SQLException e) {
			time = 0;
			e.printStackTrace();
		}
		return time;
    }

	@Override
	void close() {

	}
}
