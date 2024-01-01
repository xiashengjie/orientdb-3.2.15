package com.orientechnologies.benchMark;

import com.orientechnologies.orient.client.remote.OStorageRemote;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.jdbc.OrientJdbcDriver;
import com.orientechnologies.utils.ConfigUtils;
import com.orientechnologies.utils.OrientdbEnum;
import com.orientechnologies.utils.PropertiesEnum;

import java.util.List;
import java.util.Properties;

import static com.orientechnologies.orient.core.config.OGlobalConfiguration.CLIENT_CONNECTION_STRATEGY;
import static org.junit.Assert.assertTrue;

/**
 * @author xsj
 */
public class OrientQueryNew extends MMDB {

//	OrientDB orientDB = null;
//	ODatabaseDocument SF1db = null;
//	ODatabaseDocument SF10db = null;
//	ODatabaseDocument SF30db = null;
//	ODatabaseDocument SF100db = null;

	@Override
	ODatabaseDocument Connection(OrientdbEnum orientdbEnum,String url){
		ODatabaseDocument db = null;
	try {
		Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
		String database = orientdbEnum.getName();
		Class.forName(OrientJdbcDriver.class.getName());
		String username = properties.getProperty("database.username");
		String password = properties.getProperty("database.password");
		OrientDB orientDB = new OrientDB(url, OrientDBConfig.defaultConfig());
		OrientDBConfig config = OrientDBConfig.builder().addConfig(CLIENT_CONNECTION_STRATEGY, "ROUND_ROBIN_CONNECT").build();
		db = orientDB.open(database, username, password, config);
		OStorageRemote storage = (OStorageRemote) ((ODatabaseDocumentInternal) db).getStorage();
		List<String> serverURLs = storage.getServerURLs();
		System.out.println(serverURLs.toString());
	}
	catch (Exception e){
		e.printStackTrace();
	}
		  return db;
	}

//	public OrientQueryNew (String url){
////		Properties properties = ConfigUtils.getConfig(PropertiesEnum.ORIENTDB);
//////		String database = orientdbEnum.getName();
////		try {
////		Class.forName(OrientJdbcDriver.class.getName());
////		String username = properties.getProperty("database.username");
////		String password = properties.getProperty("database.password");
////		orientDB = new OrientDB(url, OrientDBConfig.defaultConfig());
////		OrientDBConfig config = OrientDBConfig.builder().addConfig(CLIENT_CONNECTION_STRATEGY, "ROUND_ROBIN_CONNECT").build();
////		SF1db = orientDB.open(OrientdbEnum.SF1.getName(), username, password, config);
////		SF10db = orientDB.open(OrientdbEnum.SF10.getName(), username, password, config);
////		SF30db = orientDB.open(OrientdbEnum.SF30.getName(), username, password, config);
////		SF100db = orientDB.open(OrientdbEnum.SF100.getName(), username, password, config);
////		}
////		catch (Exception e){
////			e.printStackTrace();
////		}
//	}

    @Override
	public long Q1(String PersonId,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ1=String.format("Select $profile,$orders,$feedback,$posts,$list1,$list2 "
				+ "let $profile=(select from `Customer` where id=%s),"
				+ "$orders=(select Expand(Order) from `Customer` where id=%s),"
				+ "$feedback=(select Expand(Feedback) from `Customer` where id=%s),"
				+ "$posts= (select Out(\'PersonHasPost\') from `Customer` where id=%s),"
				+ "$list1= (select list.brand as brand, count(list.brand) as cnt from (select Order.Orderline as list from `Customer` where id=%s unwind list) group by list.brand ORDER BY cnt DESC),"
				+ "$list2=(select pid, count(pid) from (select Out(\'PersonHasPost\').Out(\'PostHasTag\').productId as pid from `Customer` where id=%s unwind pid) group by pid order by count Desc)",PersonId,PersonId,PersonId,PersonId,PersonId,PersonId);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ1);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 1 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

    @Override
	public long Q2(String ProductId,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ2=String.format("Select $person let $list=(select In(\'PostHasTag\').In(\'PersonHasPost\').id as pid "
				+ "from `Product` where productId=%s),$person=(select PersonId,Orderline.productId from Order "
				+ "where OrderDate>\"2022\" and PersonId in $list and %s in Orderline.productId)",ProductId,ProductId);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ2);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 2 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

    @Override
	public long Q3(String ProductId,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ3=String.format("Select $post,$feedback "
				+ "let $post=(select Expand(In(\'PostHasTag\')) from `Product` "
				+ "where productId=%s),"
				+ "$feedback=(select * from `Feedback` where asin=%s and feedback.charAt(1).asInteger() <5)",ProductId,ProductId);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ3);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 3 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q4(OrientdbEnum orientdbEnum,String dbUrl) {
    	String OQ4="SELECT commonset.size() from (SELECT intersect($set1,$set2) as commonset "
    			+ "let $person = (select pid from (select PersonId as pid, SUM(TotalPrice) as sum from Order Group by PersonId order by sum desc limit 2)),"
    			+ "$set1=(TRAVERSE out(\"Knows\") FROM (select from Customer where PersonId=$person.pid[0]) while $depth <= 3 STRATEGY BREADTH_FIRST),"
    			+ "$set2=(TRAVERSE out(\"Knows\") FROM (select from Customer where PersonId=$person.pid[1]) while $depth <= 3 STRATEGY BREADTH_FIRST))";
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ4);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 4 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q5(String PersonId, String brand,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ5=String.format("Select Out(\'PersonHasPost\').Out(\'PostHasTag\') as tags from (select Expand(Out(\'Knows\')) from Customer where id=%s) Where %s in Order.Orderline.brand unwind tags",PersonId,brand);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ5);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 5 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q6(String startPerson, String EndPerson,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ6=String.format("SELECT transactions, count(transactions) as cnt "
				+ "FROM(SELECT Order.Orderline.productId as transactions from(SELECT EXPAND(path) from(SELECT shortestPath($from, $to) AS path "
				+ "LET $from = (SELECT FROM Customer WHERE id=%s),"
				+ "$to = (SELECT FROM Customer WHERE id=%s))) unwind transactions) GROUP BY transactions Order by cnt DESC LIMIT 5",startPerson,EndPerson);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ6);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 6 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q7(String brand,OrientdbEnum orientdbEnum,String dbUrl) {
		String OQ7=String.format("Select feedback from Feedback where asin in "
				+ "(Select dlist from(Select set(dlist) as dlist  "
				+ "from(Select $declineList.asin as dlist "
				+ "let $list1 = (Select asin,count(asin) as cnt from "
				+ "(Select ol_unwind.asin as asin, ol_unwind.brand as brand from "
				+ "(Select Orderline as ol_unwind from (Select From Order Where OrderDate>\"2018\" and OrderDate<\"2019\" and %s in Orderline.brand) unwind ol_unwind)) "
				+ "where brand=%s group by asin order by cnt DESC), "
				+ "$list2=(Select asin,count(asin) as cnt from (Select ol_unwind.asin as asin, ol_unwind.brand as brand from "
				+ "(Select Orderline as ol_unwind from (Select From Order Where OrderDate>\"2019\" and OrderDate<\"2020\" and %s in Orderline.brand) unwind ol_unwind)) "
				+ "where brand=%s group by asin order by cnt DESC), $declineList=compareListTest($list1,$list2))))",brand,brand,brand,brand);
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ7);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 7 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q8(OrientdbEnum orientdbEnum,String dbUrl) {
    	String OQ8="Select Sum(Popularity) from(Select In(\'PostHasTag\').size() as Popularity "
    			+ "from `Product` Where productId in (Select  Distinct(Orderline.productId) "
    			+ "From (Select Orderline From Order let  $brand=(select name as brand from `Vendor` where country='China') "
    			+ "Where OrderDate>\"2018\" and OrderDate<\"2019\" unwind Orderline) Where Orderline.brand in $brand.brand))";
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ8);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 8 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q9(OrientdbEnum orientdbEnum,String dbUrl) {
    	String OQ9="Select Orderline.brand, count(*) from(Select PersonId, Orderline From Order "
    			+ "Let  $brand=(select name as brand from `Vendor` where country='China') Where OrderDate>\"2018\" and OrderDate<\"2019\" unwind Orderline) "
    			+ "Where Orderline.brand in $brand.brand Group by Orderline.brand Order by count DESC LIMIT 3";
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		String name = orientdbEnum.getName();
		oDatabaseDocument.query(OQ9);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 9 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	public long Q10(OrientdbEnum orientdbEnum,String dbUrl) {
    	String OQ10="SELECT id, max(Order.OrderDate) as Recency,Order.size() as Frequency,sum(Order.TotalPrice) as Monetary FROM Customer "
    			+ "Where id in(Select id, count(id) as cnt from (Select IN(\'PersonHasPost\').id[0] as id From Post "
    			+ "Where creationDate>= date( \'2012-10-01\', \'yyyy-MM-dd\')) Group by id  Order by cnt DESC limit 10) GROUP BY id";
		ODatabaseDocument oDatabaseDocument = this.Connection(orientdbEnum, dbUrl);
		long millisStart1 = System.currentTimeMillis();
		oDatabaseDocument.query(OQ10);
		long millisEnd1 = System.currentTimeMillis();
		System.out.println("Query 10 took "+(millisEnd1 - millisStart1) + " ms");
		oDatabaseDocument.close();
		return (millisEnd1 - millisStart1);
    }

	@Override
	void close() {
//		orientDB.close();
	}
}
