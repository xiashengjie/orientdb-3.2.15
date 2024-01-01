package com.orientechnologies.benchMark;

import com.orientechnologies.utils.OrientdbEnum;

/**
 * @author xsj
 */
public abstract class MMDB {

    /**
     * @param orientdbEnum
     * @param dbUrl
     * @return
     */
    abstract Object Connection(OrientdbEnum orientdbEnum,String dbUrl);
	

/*      Query 1. For a given customer, find his/her all related data including profile,
      orders, invoices, feedback, comments, and posts in the last month,
      return the category in which he/she has bought the largest number of products,
      and return the tag which he/she has engaged the greatest times in the posts.*/

    abstract long Q1(String PersonId,OrientdbEnum orientdbEnum,String dbUrl);
    
    /* Query 2. For a given product during a given period, find the people who commented
      or posted on it, and had bought it.
      */

    abstract long Q2(String ProductId,OrientdbEnum orientdbEnum,String dbUrl);
    
    /* Query 3. For a given product during a given period, find people who have undertaken
      activities related to it, e.g., posts, comments, and review,
      and return sentences from these texts that contain negative sentiments.*/

    abstract long Q3(String ProductId,OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 4. Find the top-2 persons who spend the highest amount of money in orders. 
     * Then for each person, traverse her knows-graph with 3-hop to find the friends, 
     * and finally return the common friends of these two persons.*/

    abstract long Q4(OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 5. Given a start customer and a product category, find persons who are this 
     * customer's friends within 3-hop friendships in Knows graph, besides, they have bought 
     * products in the given category. Finally, return feedback with the 5-rating review of 
     * those bought products.
     * */

    abstract long Q5(String PersonId, String brand,OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 6. Given customer 1 and customer 2, find persons in the shortest path between them 
     * in the subgraph, and return the TOP 3 best sellers from all these persons' purchases.*/

    abstract long Q6(String startPerson, String EndPerson,OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 7. For the products of a given vendor with declining sales compare to the former quarter, 
     * analyze the reviews for these items to see if there are any negative sentiments.*/

    abstract long Q7(String brand,OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 8. For all the products of a given category during a given year, compute its total sales amount, 
     * and measure its popularity in the social media.
     * */

    abstract long Q8(OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 9. Find top-3 companies who have the largest amount of sales at one country, for each company, 
     * compare the number of the male and female customers, and return the most recent posts of them.
     * */

    abstract long Q9(OrientdbEnum orientdbEnum,String dbUrl);
    
    /*
     * Query 10. Find the top-10 most active persons by aggregating the posts during the last year, then calculate 
     * their RFM (Recency, Frequency, Monetary) value in the same period, and return their recent reviews and tags 
     * of interest.
     * */

    abstract long Q10(OrientdbEnum orientdbEnum,String dbUrl);

    abstract void close();
}
