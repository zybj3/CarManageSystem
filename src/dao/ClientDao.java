package dao;

import entity.CarFashionAnalyzeCondition;
import entity.FindCondition;
import entity.StatisticsClientSource;
import entity.StatisticsOrder;
import util.JDBCUtil;
import util.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.CarFashionAnalyze;

public class ClientDao {
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	public List<CarFashionAnalyze> findCarFashion(CarFashionAnalyzeCondition analyzeCondition){
		List<CarFashionAnalyze> result = new ArrayList<CarFashionAnalyze>();
		CarFashionAnalyze carFashionAnalyze = null;
		conn = JDBCUtil.getConnection();
		String s_name = "";
		if (analyzeCondition.getS_name().equals("不限")){
			s_name = "s_name";
		}else{
			s_name = "'" + analyzeCondition.getS_name() + "'";
		}
		if (analyzeCondition.getCar_condition().equals("car_type") && analyzeCondition.getAnalyze_condition().equals("family")){
			String sql = "select elt(interval(fashionanalyze.c_family,1,2,3,4),'1','2','3','大于等于4') as family_level,"
					+ "sum(o_num) as count,car_type as type from fashionanalyze where o_date between '1001' and '1002' "
					+ "and s_name=1003 group by elt(interval(fashionanalyze.c_family,1,2,3,4),'1','2','3','大于等于4'),car_type;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String family_level = rs.getString("family_level");
					System.out.println(family_level);
					int count = rs.getInt("count");
					String car_type = rs.getString("type");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setFamily_level(family_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_type(car_type);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getCar_condition().equals("car_type") && analyzeCondition.getAnalyze_condition().equals("career")){
			String sql = "select sum(o_num) as count, car_type as type, c_profession as career from fashionanalyze "
					+ "where o_date between '1001' and  '1002' and s_name=1003 group by car_type,c_profession;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String career = rs.getString("career");
					int count = rs.getInt("count");
					String car_type = rs.getString("type");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setCareer(career);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_type(car_type);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getCar_condition().equals("car_type") && analyzeCondition.getAnalyze_condition().equals("age")){
			String sql = " select elt(interval(fashionanalyze.c_age,0,20,30,40,50,60),'小于20','20至30','30至40','40至50', '50至60',"
					+ "'大于等于60') as age,sum(o_num) as count,car_type as type from fashionanalyze "
					+ "where o_date between '1001' and '1002' and s_name=1003 "
					+ "group by elt(interval(fashionanalyze.c_age,0,20,30,40,50,60),'小于20','20至30','30至40','40至50','50至60','大于60'),car_type;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String age_level = rs.getString("age");
					int count = rs.getInt("count");
					String car_type = rs.getString("type");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setAge_level(age_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_type(car_type);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getCar_condition().equals("car_class") && analyzeCondition.getAnalyze_condition().equals("family")){
			String sql = "select elt(interval(fashionanalyze.c_family,1,2,3,4),'1','2','3','大于等于4') as family_level,"
					+ "sum(o_num) as count,car_class as class from fashionanalyze where o_date between '1001' and '1002' "
					+ "and s_name=1003 group by elt(interval(fashionanalyze.c_family,1,2,3,4),'1','2','3','大于等于4'),car_class;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String family_level = rs.getString("family_level");
					int count = rs.getInt("count");
					String car_class = rs.getString("class");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setFamily_level(family_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_class(car_class);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getCar_condition().equals("car_class") && analyzeCondition.getAnalyze_condition().equals("career")){
			String sql = "select sum(o_num) as count, car_class as class, c_profession as career from fashionanalyze "
					+ "where o_date between '1001' and  '1002' and s_name=1003 group by car_class,c_profession;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String career = rs.getString("career");
					int count = rs.getInt("count");
					String car_class = rs.getString("class");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setCareer(career);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_class(car_class);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getCar_condition().equals("car_class") && analyzeCondition.getAnalyze_condition().equals("age")){
			String sql = " select elt(interval(fashionanalyze.c_age,0,20,30,40,50,60),'小于20','20至30','30至40','40至50', '50至60',"
					+ "'大于等于60') as age,sum(o_num) as count,car_class as class from fashionanalyze "
					+ "where o_date between '1001' and '1002' and s_name=1003 "
					+ "group by elt(interval(fashionanalyze.c_age,0,20,30,40,50,60),'小于20','20至30','30至40','40至50','50至60','大于60'),car_class;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String age_level = rs.getString("age");
					int count = rs.getInt("count");
					String car_class = rs.getString("class");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setAge_level(age_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setCar_class(car_class);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}
		return result;
		
	}

	public List<CarFashionAnalyze> findCarPrice(CarFashionAnalyzeCondition analyzeCondition) {
		List<CarFashionAnalyze> result = new ArrayList<CarFashionAnalyze>();
		CarFashionAnalyze carFashionAnalyze = null;
		conn = JDBCUtil.getConnection();
		String s_name = "";
		if (analyzeCondition.getS_name().equals("不限")){
			s_name = "s_name";
		}else{
			s_name = "'" + analyzeCondition.getS_name() + "'";
		}
		if (analyzeCondition.getAnalyze_condition().equals("family")){
			String sql = "select elt(interval(priceanalyze.c_family,1,2,3,4),'1','2','3','大于等于4') as family_level, "
					+ "elt(interval(priceanalyze.car_price,0,100000,200000,300000,400000,500000,600000),'小于10万','10万至20万','20万至30万'"
					+ ",'30万至40万','40万至50万','50万至60万','大于60万') as price_level,sum(o_num) as count "
					+ "from priceanalyze where o_date between '1001' and '1002' and s_name=1003 "
					+ "group by price_level,family_level;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String family_level = rs.getString("family_level");
					int count = rs.getInt("count");
					String price_level = rs.getString("price_level");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setFamily_level(family_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setPrice_level(price_level);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getAnalyze_condition().equals("career")){
			String sql = "select elt(interval(priceanalyze.car_price,0,100000,200000,300000,400000,500000,600000),'小于10万','10万至20万',"
					+ "'20万至30万','30万至40万','40万至50万','50万至60万','大于60万') as price_level,sum(o_num) as count,c_profession as career "
					+ "from priceanalyze where o_date between '1001' and '1002' and s_name=1003 group by price_level,career;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String career = rs.getString("career");
					int count = rs.getInt("count");
					String price_level = rs.getString("price_level");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setCareer(career);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setPrice_level(price_level);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (analyzeCondition.getAnalyze_condition().equals("age")){
			String sql = " select elt(interval(priceanalyze.c_age,0,20,30,40,50,60),'小于20','20至30',"
					+ "'30至40','40至50','50至60','大于等于60') as age, "
					+ "elt(interval(priceanalyze.car_price,0,100000,200000,300000,400000,500000,600000),'小于10万',"
					+ "'10万至20万','20万至30万','30万至40万','40万至50万','50万至60万','大于60万') as price_level,"
					+ "sum(o_num) as count from priceanalyze "
					+ "where o_date between '1001' and '1002' and s_name=1003 group by price_level,age;";
			sql = sql.replaceAll("1001", analyzeCondition.getStart_time());
			sql = sql.replaceAll("1002", analyzeCondition.getEnd_time());
			sql = sql.replaceAll("1003", s_name);
			System.out.println(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String age_level = rs.getString("age");
					int count = rs.getInt("count");
					String price_level = rs.getString("price_level");
					carFashionAnalyze = new CarFashionAnalyze();
					carFashionAnalyze.setAge_level(age_level);
					carFashionAnalyze.setStatistics_num(count);
					carFashionAnalyze.setPrice_level(price_level);
					result.add(carFashionAnalyze);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}
		return result;
	}

	public List<StatisticsClientSource> findClientSource(FindCondition condition){
		List<StatisticsClientSource> result = new ArrayList<>();
		StatisticsClientSource statisticsClientSource = null;
		conn = JDBCUtil.getConnection();
		String s_name = "";
		String year = "";
		if (condition.getS_name().equals("不限")){
			s_name = "s_name";
		}else{
			s_name = "'" + condition.getS_name() + "'";
		}
		//如果输入的年份为空，则自动获取当前系统年份
		if (condition.getYear() == ""){
			year = TimeUtil.getYear();
		}else{
			year = condition.getYear();
		}
		if (condition.getPeriod().equals("month")){
			String sql = " select month,num,source from "
					+ "(select date_format(r_date,'%m') as month, count(r_id) as num, where_from as source "
					+ "from receptionview where s_name=1001 and date_format(r_date,'%Y')='1002' "
					+ "group by month,source order by month) as a";
			sql = sql.replaceAll("1001", s_name);
			sql = sql.replace("1002", year);
			System.out.print(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String month = rs.getString("month");
					String source = rs.getString("source");
					int num = rs.getInt("num");
					statisticsClientSource = new StatisticsClientSource();
					statisticsClientSource.setMonth(month);
					statisticsClientSource.setClient_source(source);
					statisticsClientSource.setStatistics_num(num);
					result.add(statisticsClientSource);
				}
			}catch(SQLException e){
					e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (condition.getPeriod().equals("season")){
			String sql = "select season,num,source from "
					+ "(select quarter(r_date) as season, count(r_id) as num, where_from as source "
					+ "from receptionview where s_name=1001 and date_format(r_date,'%Y')='1002' "
					+ "group by season,source order by season) as a;";
			sql = sql.replaceAll("1001", s_name);
			sql = sql.replace("1002", year);
			System.out.print(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String season = rs.getString("season");
					String source = rs.getString("source");
					int num = rs.getInt("num");
					statisticsClientSource = new StatisticsClientSource();
					statisticsClientSource.setSeason(season);
					statisticsClientSource.setClient_source(source);
					statisticsClientSource.setStatistics_num(num);
					result.add(statisticsClientSource);
				}
			}catch(SQLException e){
					e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}else if (condition.getPeriod().equals("year")){
			String sql = "select year,num,source from "
					+ "(select date_format(r_date,'%Y') as year, count(r_id) as num, where_from as source "
					+ "from receptionview where s_name=1001 and date_format(r_date,'%Y')='1002' "
					+ "group by year,source order by year) as a;";
			sql = sql.replaceAll("1001", s_name);
			sql = sql.replace("1002", year);
			System.out.print(sql);
			try{
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while(rs.next()){
					String year1 = rs.getString("year");
					String source = rs.getString("source");
					int num = rs.getInt("num");
					statisticsClientSource = new StatisticsClientSource();
					statisticsClientSource.setYear(year1);
					statisticsClientSource.setClient_source(source);
					statisticsClientSource.setStatistics_num(num);
					result.add(statisticsClientSource);
				}
			}catch(SQLException e){
					e.printStackTrace();
			}finally{
				JDBCUtil.closeConnection(conn, stmt, psmt, rs);
			}
		}
		return result;
	}
}
