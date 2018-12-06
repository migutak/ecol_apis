package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;
import com.youtube.dao.Oracle308tube;

public class Schema308tube {
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, String PC_PARTS_CODE, String PC_PARTS_MAKER, String PC_PARTS_AVAIL, String PC_PARTS_DESC) 
		throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		try {
			//conn = oraclePcPartsConnection();
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("insert into PC_PARTS " +
					"(PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) " +
					"VALUES ( ?, ?, ?, ?, ? ) ");

			query.setString(1, PC_PARTS_TITLE);
			query.setString(2, PC_PARTS_CODE);
			query.setString(3, PC_PARTS_MAKER);

			//PC_PARTS_AVAIL is a number column, so we need to convert the String into a integer
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, avilInt);

			query.setString(5, PC_PARTS_DESC);
			query.executeUpdate(); //note the new command for insert statement

		} catch(Exception e) {
			e.printStackTrace();
			return 500; //if a error occurs, return a 500
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}
	
	/**
	 * This method will search for a specific brand from the PC_PARTS table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandParts(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblnotifications " +
					"where UPPER(receiver) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getdeptbuckets(String division) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblBucketassign where UPPER(product) = ? ");
			query.setString(1, division.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getcmdaccinfo(String custnumber) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from q_cmd_accinfo where UPPER(custnumber) = ? ");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getinvintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a, tblinvestigators_logs l  where a.id=l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getinvintructions2(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a, tblinvestigators_logs l  where a.id=l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	//
	public JSONArray getmktintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	//
	public JSONArray getmktintructions2(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getdebtintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select a.*,l.serviceprovider,l.dateinst,l.duedate,l.status,l.remarks,l.owner from tbldebtcollectors a left join tbldebtcollectors_logs l on a.id=l.id where UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getdebtintructions2(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbldebtcollectors a,tbldebtcollectors_logs l where a.id=l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	public JSONArray getvaluerintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a,tblvaluers_logs l where a.id=l.id(+) and UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getvaluerintructions2(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a,tblvaluers_logs l where a.id=l.id(+) and UPPER(l.uniq) = ?");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getyardsintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblyards a,tblyards_logs l where a.id=l.id(+) and UPPER(id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray gettransfersintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbltransfers_logs where UPPER(id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getnotices40daysintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblnotices40days_logs where UPPER(id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getauctintructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a  left join qtblauctioneers l on a.id=l.id where UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection a.id,custnumber,accnumber,custname,arocode
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) conn.close();
		}
		return json;
	}
	public JSONArray getauctintructions2(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a,tblauctioneers_logs l where a.id=l.id(+) and UPPER(l.uniq) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection a.id,custnumber,accnumber,custname,arocode
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) conn.close();
		}
		return json;
	}
	public JSONArray getrepointructions(String id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a left join tblrepossessors_logs l on a.id=l.id where UPPER(a.id) = ? ");
			query.setString(1, id.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	//
	
	public JSONArray get_a_provider(String sptype) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			//query = conn.prepareStatement("select * from sptypes where UPPER(spcode) = ? ");
			query = conn.prepareStatement("select s.*, NVL(p.pending,0) pending from sptypes s left join qpending p on s.sptitle=p.serviceprovider where UPPER(s.spcode) = ? order by pending asc ");
			query.setString(1, sptype.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray get_sp_provider(String spcode,String sptitle) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from sptypes where UPPER(spcode) = ? and UPPER(sptitle) = ? ");
			query.setString(1, spcode.toUpperCase());
			query.setString(2, sptitle.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getdeptusers(String division) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblusers where UPPER(division) = ? order by username asc");
			query.setString(1, division.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Notifications(String username) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblnotification where UPPER(receiver) = ? and read ='N' order by datesent desc ");
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}
		
		return json;
	}
	
	public JSONArray readNotifications(String username) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblnotification where UPPER(receiver) = ? and read = 'Y' order by datesent desc ");
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray bouncedNotifications(String username) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblnotification where UPPER(sender) = ? and bounced = 'Y' and notesent = 'N' order by datesent desc ");
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray gettempplans(String username) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from temp_plan_activities where UPPER(aowner)=? ");
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray pendingchats(String username) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from conversations where UPPER(user_to) = ? and userto_status = 'offline' and backonline is null ");
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Pbb(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT sumbalance,sumarrears from q_PBBSCHEME where CUSTNUMBER = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Meta(String custnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Employer, EmpStatus, DoB, DoE, Marital, Phone, Email, Residential, Salary, fileno " +
					"from Meta " +
					"where custnumber = ? ");
			query.setString(1, custnumber); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray UserInfo(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username, firstname, lastname, surname, division, createdate, accessrights, expirydate, email, active, branch, team " +
					"from tblusers where UPPER(username) = ?");
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray ArocodeInfo(String arocode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from arocodes where UPPER(arocode) = ? ");
			query.setString(1, arocode.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getAro(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select arocode,division,owner from arocodes where UPPER(division) = ? ");
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray MyAllocations(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			/*query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, abs(to_number(l.DAYSINARR)) DAYSINARR, l.COLOFFICER,abs(to_number(l.OUSTBALANCE)) OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.LASTACTIONDATE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, m.manager,m.region, l.BUCKET,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, abs(to_number(l.TOTALARREARS)) TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(colofficer) = ?");*/
			
			query = conn.prepareStatement("select * from tbl_q_all t where UPPER(t.colofficer) = ?");
			
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Allbuckets() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Bucket, sum(OUSTBALANCE) PORTFOLIO_VALUE, count(ACCNUMBER) PORTFOLIO_VOLUME from Bucketsdata " +
					"group by Bucket order by Bucket ");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}

	public JSONArray portfoliosummary() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select sum(OUSTBALANCE) PORTFOLIO_VALUE, count(ACCNUMBER) PORTFOLIO_VOLUME " +
					"from Bucketsdata ");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Divbuckets(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Bucket, sum(OUSTBALANCE) PORTFOLIO_VALUE, count(ACCNUMBER) PORTFOLIO_VOLUME " +
					"from Bucketsdata " +
					"where UPPER(section) = ? group by Bucket order by Bucket ");
			
			query.setString(1, division.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Mybuckets(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Bucket, sum(OUSTBALANCE) PORTFOLIO_VALUE, count(ACCNUMBER) PORTFOLIO_VOLUME " +
					"from Bucketsdata " +
					"where UPPER(colofficer) = ? group by Bucket order by Bucket ");
			
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Newcases(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,l.datereceived, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.datereceived = to_char(sysdate,'DD-MON-YY') ");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray portfolioAll(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and (oustbalance+LIMITAMOUNT+TEMPLIMIT)<-1000 and UPPER(SECTION) = ? ");
			
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray portfolioMyallocation(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and (oustbalance+LIMITAMOUNT+TEMPLIMIT)<-1000 and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray portfolioWorklist(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and (oustbalance+LIMITAMOUNT+TEMPLIMIT)<-1000 and TO_DATE(l.REVIEWDATE,'DD-MM-YYYY')<=sysdate and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Dueissued(String branchcode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from demandsdue l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.sentstatus = 'N' and UPPER(l.BRANCHCODE) = ? ");
			
			query.setString(1, branchcode.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Demandcheck(String accnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,l.DEMAND, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from demandsdue l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.sentstatus = 'N' and UPPER(accnumber) = ? ");
			
			query.setString(1, accnumber.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Demandconfirm(String branchcode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,l.DEMAND, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from demandsdue l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.sentstatus = 'PA' and UPPER(l.branchcode) = ? ");
			
			query.setString(1, branchcode.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Reviewerdata(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.ARONAME, m.manager, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(ROUTETOSTATE) = ? ");
			
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryAccountInfo(String account) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.ACCNUMBER,l.CUSTNUMBER,c.FIRSTNAME,l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE,l.COLLECTIONSTATUS," 
					+"l.PRINCARREARS, l.CURING"
					+ "from tbl_PORTFOLIO l,CUSTOMERS_STAGE c "
					+ "where c.CUSTNUMBER = l.CUSTNUMBER and UPPER(ACCNUMBER) = ? ");
			
			query.setString(1, account.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Memo(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select DIVISION, MEMOGROUP, OWNER " +
					"from MEMOGROUPS " +
					"where UPPER(division) = ? ");
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Aro() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select DIVISION, AROCODE, OWNER from AROCODES order by DIVISION");
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray colofficerInDept(String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username, firstname, surname, division, CREATEDATE, ACCESSRIGHTS " +
					"from tblusers " +
					"where  UPPER(substr(division,1,3)) = ? ");
			query.setString(1, division.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Logged(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblusers where  UPPER(username) = ? and active = 'Y'");
			query.setString(1, username.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray checkuserifexist(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblusers where  UPPER(username) = ? and active = 'Y'");
			query.setString(1, username.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray userstatus(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblusers where  UPPER(username) = ? and active = 'Y'");
			query.setString(1, username.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray regions(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select id, region_name, section, COLOFFICER from regions where UPPER(section) = ? order by id ");
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray branchRegion(String Region) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		String sql = "Select 1 from dual";
		
		try {
			
	    	
	    	String sql1 = "select Branchcode, BranchName, Region from qbranchRegionsme";
	    	String sql2 = "select Branchcode, BranchName, Region from qbranchRegionsacco";
	    	String sql3 = "select Branchcode, BranchName, Region from qbranchRegions";
	    	String sql4 = "select Branchcode, BranchName, Region from qbranchRegionipf";
	    	String sql5 = "select Branchcode, BranchName, Region from qbranchRegionasset";
	    	String sql6 = "select Branchcode, BranchName, Region from qbranchRegionmortgage";
	    	String sql7 = "select Branchcode, BranchName, Region from qbranchRegionmicro";
	    	String sql8 = "select Branchcode, BranchName, Region from qbranchRegioncorporate";
	    	String sql9 = "select Branchcode, BranchName, Region from qbranchRegionpbb";
	    	
	    	switch(Region){
		    	case "SME": sql = sql1;
		    			break;
		    	case "SACCO": sql = sql2;
    					break;
		    	case "AGRIBUSINESS": sql = sql3;
    					break;
		    	case "IPF": sql = sql4;
    					break;
		    	case "ASSETFINANCE": sql = sql5;
    					break;
		    	case "MORTGAGE": sql = sql6;
    					break;
		    	case "MICROCREDIT": sql = sql7;
    					break;
		    	case "CORPORATE": sql = sql8;
    					break;
		    	case "PBB": sql = sql9;
    					break;
	    	}
	    	
	    	conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryReturnLetters(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ISSUEDATE, ACCNUMBER, CUSTNUMBER, ARRAMOUNT, OUSTBALANCE, TITLE, FIRSTNAME, ADDRESSLINE1, TOWN, COLOFFICER, DEMANDTYPE, SENT, OWNER " +
					"from V_DEMANDS " +
					"where UPPER(COLOFFICER) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray queryReturnPtp(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ACCNUMBER,ARRAMOUNT,PTPAMOUNT,PTPDATE,PAYMETHOD,OWNER,to_char(ACTIONDATE, 'YYYY-MM-DD HH24:MI:SS') ACTIONDATE,MET " +
					"from PTPS " +
					"where UPPER(ACCNUMBER) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray Directors(String accnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblDirectors where UPPER(CUSTNUMBER) = ? ");
			query.setString(1, accnumber.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray queryReturnNotes(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,ACCNUMBER,NOTEMADE,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,OWNER, NOTESRC, extract(day from (systimestamp - NOTEDATE))*24*60*60 DAYPAST " +
					"from NOTEHIS where UPPER(CUSTNUMBER) = ? order by NOTEDATE desc, noteimp desc");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}

		return json;
	}
	/*public JSONArray Files(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from uploads where UPPER(custnumber) = ? order by stagedate DESC");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
		}

		return json;
	}*/
	public JSONArray queryPinNotes(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,NOTEMADE,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,OWNER, NOTESRC, extract(day from (systimestamp - NOTEDATE))*24*60*60 DAYPAST " +
					"from NOTEHIS where UPPER(CUSTNUMBER) = ? and NOTEIMP = 'Y' order by NOTEDATE DESC");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray queryReturnContacts(String custnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,PERSON,CONTACT,OWNER,to_char(ACTIONDATE, 'YYYY-MM-DD HH24:MI:SS') ACTIONDATE,REL,active " +
					"from TELES " +
					"where UPPER(CUSTNUMBER) = ?");
			query.setString(1, custnumber.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray addedContact(String id) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,PERSON,CONTACT,OWNER,to_char(ACTIONDATE, 'YYYY-MM-DD HH24:MI:SS') ACTIONDATE,REL,active " +
					"from TELES " +
					"where UPPER(ID) = ?");
			query.setString(1, id.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray queryReturnContacts2(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from Teles " +
					"where UPPER(CUSTNUMBER) = ? order by ACTIONDATE desc ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray queryReturnCollateral(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ACCNUMBER,CUSTNUMBER,FCDMKTVAL,IMPRVDVALUE,INSVALUE,OPENMRKVAL,SECURDESC,SECURTYPE,STAGEDATE,VALUEDATE,VALUERNAME " +
					"from TBL_COLATERAL " +
					"where UPPER(CUSTNUMBER) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray deptCollateral(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ACCNUMBER,CUSTNUMBER,COLLATERALNAME,INSURANCEVALUE,VALUATIONDATE,VALUER,MARKETVALUE,REGOWNER,FORCEDSALE,TENURE,COLOFFICER, ID " +
					"from DEPTCOLLATERAL " +
					"where UPPER(CUSTNUMBER) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray queryReturnTels(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT CUSTNUMBER,PERSON,CONTACT,OWNER,to_char(ACTIONDATE, 'YYYY-MM-DD HH24:MI:SS') ACTIONDATE,ACTIVE,REL " +
					"from TELES " +
					"where UPPER(CUSTNUMBER) = ? ");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray queryReturnViews(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT ACCNUMBER,CUSTNUMBER,PRODUCTCODE,OUSTBALANCE OUTSBALANCE, AROCODE from qview " +
					"where UPPER(CUSTNUMBER) = ?");
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray Return_Views_with_ids(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and UPPER(NATIONID) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray searchid(String idnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qidsearch where nationid = ?");
			query.setString(1, idnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryReturnDivision(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.BUCKET, " +
					"l.BRANCHNAME,m.REGION, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(SECTION) = ? and rownum <= 12500");//
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray regional(String section,String regionname) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.BUCKET, " +
					"l.BRANCHNAME,r.REGIONNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m, tblRegionlogs r " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and m.BRANCHCODE = r.BRANCHCODE(+) and UPPER(SECTION) = ? and UPPER(REGIONNAME) = ? and rownum <= 10500 ");
			query.setString(1, section.toUpperCase());
			query.setString(2, regionname.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray divall_lazy(String division,int pagenum,int pagesize,int recordstartindex,int recordendindex,String sortdatafield,String sortorder,int filterscount,String filtervalue,String filtercondition,String filterdatafield,String filteroperator) throws Exception {
		
		int total=  recordstartindex + pagesize;
		int start = pagenum * pagesize;
		
		System.out.print("recordstartindex = "+recordstartindex);
		System.out.print("recordendindex = "+recordendindex);
		System.out.print("pagesize = "+pagesize);
		System.out.print("pagenum = "+pagenum);
		System.out.print("start = " + start);
		System.out.print("\n total = "+total);
		System.out.print("\n filterscount = "+filterscount);
		System.out.print("\n filtercondition = "+filtercondition);
		System.out.print("\n filterdatafield = "+filterdatafield);
		System.out.print("\n filteroperator = "+filteroperator);
		System.out.print("\n filtervalue = "+ filtervalue);

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			/*query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(SECTION) = ? ");
			
			query.setString(1, division.toUpperCase());
			ResultSet rs = query.executeQuery();*/
			
			String sql = null;
			if (filterscount > 0){
				String condition = null;
				String value = null;
				switch (filtercondition){
					case "CONTAINS":
						condition = "LIKE";
						value = "%"+filtervalue+"%";
						break;
					case "STARTS_WITH":
						condition = "LIKE";
						value = filtervalue+"%";
						break;
					case "DOES_NOT_CONTAIN":
						condition = " NOT LIKE ";
						value = "%"+filtervalue+"%";
						break;
					case "EQUAL":
						condition = " = ";
						value = filtervalue;
						break;
					case "NOT_EQUAL":
						condition = " <> ";
						value = filtervalue;
						break;
					case "GREATER_THAN":
						condition = " > ";
						value = filtervalue;
						break;
					case "LESS_THAN":
						condition = " < ";
						value = filtervalue;
						break;
					case "GREATER_THAN_OR_EQUAL":
						condition = " >= ";
						value = filtervalue;
						break;
					case "LESS_THAN_OR_EQUAL":
						condition = " <= ";
						value = filtervalue;
						break;
					case "ENDS_WITH":
						condition = " LIKE ";
						value = "%"+filtervalue;
						break;
					case "NULL":
						condition = " IS NULL ";
						value = "%"+filtervalue+"%";
						break;
					case "NOT_NULL":
						condition = " IS NOT NULL ";
						value = "%"+filtervalue+"%";
						break;
					default:
						condition = " LIKE ";
						value = "%"+filtervalue+"%";
				}
				String where = " WHERE "+filterdatafield+" "+condition+" '"+ value+"'";
				sql = "select * from (select t.*, row_number() over (order by accnumber desc) r from tbl_portfolio_static t) "+where;
			}
			else if(sortdatafield == null){
				//sql = "select * from (select t.*, row_number() over (order by accnumber desc) r from tbl_portfolio_static t) where r between "+start+" and "+total;
				sql = "select * from (select c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, "
						+ "l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,"
						+ "l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS,"
						+ "l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE, rownum r from tbl_portfolio l, CUSTOMERS_STAGE c where c.CUSTNUMBER(+) = l.CUSTNUMBER and UPPER(SECTION) = ? ) where r > "+start+" and r < " + total;
				
			}else {
				//sql = "select * from (select t.*, row_number() over (order by "+sortdatafield+" "+sortorder+") r from tbl_portfolio_static t) where r between "+start+" and "+total;
				sql = "select * from tbl_portfolio where rownum between "+start+" and "+total;
			}
			
			query = conn.prepareStatement(sql);
			query.setString(1, division.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Lazy(int limit, int offset, String search, String sort, String order) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		int total = offset + limit;
		
		//System.out.print("Limit "+limit);
		
		/** Start: Filtering **/
		String sWhere = "";
		
		if( search != null && !search.isEmpty())
		{
		    //sWhere = "(";
			sWhere +="CUSTNAME LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CUSTNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="ACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="EMPLOYER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="EMPLOYERNO LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="DEPTCODE LIKE '%"+search.toUpperCase()+"%'";
		    sWhere += " and ";
		}
		
		/** Start: Ordering **/
		String sOrder = "order by CUSTNAME asc";
		if ( sort != null && !sort.isEmpty())
		{
		    sOrder = "ORDER BY "+sort +" " + order;
		}
		/** END: Ordering **/
		
		String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from watch_stage t) where "+sWhere+" r between "+offset+" and "+total;
		
		//String runq = "select * from (select t.*, row_number() over (order by CUSTNAME asc) r from watch_stage t) where r between 0 and 50";

		try {
			conn = Oracle308tube.LocalConnection();
			//query = conn.prepareStatement("select * from tbl_customers where rownum <= ? ");
			query = conn.prepareStatement(runq);
			//query.setInt(1, limit);
			
			// System.out.println(runq);
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	//
	
	public JSONArray Lazyportfolio(int limit, int offset, String search, String sort, String order) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		int total = offset + limit;
		
		//System.out.print("Limit "+limit);
		
		/** Start: Filtering **/
		String sWhere = "";
		
		if( search != null && !search.isEmpty())
		{
		    sWhere = "where ";
			sWhere +="ACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CUSTNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			//sWhere +="NATIONID LIKE '%"+search.toUpperCase()+"%' OR ";
			//sWhere +="EMPLOYER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="FIRSTNAME LIKE '%"+search.toUpperCase()+"%' OR ";
			//sWhere +="COLOFFICER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="SECTION LIKE '%"+search.toUpperCase()+"%'";
		    //sWhere += " and ";
		}
		
		/** Start: Ordering **/
		String sOrder = "order by abs(TO_NUMBER(OUSTBALANCE)) desc";
		if ( sort != null && !sort.isEmpty())
		{
		    sOrder = "ORDER BY "+sort +" " + order;
		}
		/** END: Ordering **/
		
		//String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from q_portfolio t) where "+sWhere+" r between "+offset+" and "+total;
		//System.out.println(runq);
		String runq = "select * from tbl_q_portfolio "+ sWhere + " " + sOrder +" offset "+ offset +" rows fetch next "+ total +" rows only";

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(runq);
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	//http://192.168.0.51:7001/ecollect2/api/v2/lazymcoop?order=asc&limit=50&offset=0&_=1476691057785
	public JSONArray Lazymcoop(int limit, int offset, String search, String sort, String order) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		int total = offset + limit;
		
		//System.out.print("Limit "+limit);
		
		/** Start: Filtering **/
		String sWhere = "";
		
		if( search != null && !search.isEmpty())
		{
		    sWhere = " WHERE ";
			sWhere +="LOANACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CLIENTNAME LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="IDNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			//sWhere +="EMPLOYER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="PHONENUMBER LIKE '%"+search.toUpperCase()+"%'";
			//sWhere +="COLOFFICER LIKE '%"+search.toUpperCase()+"%' OR ";
			//sWhere +="LOAN_TYPE LIKE '%"+search.toUpperCase()+"%'";
		    sWhere += " ";
		}
		
		/** Start: Ordering **/
		String sOrder = "order by abs(TO_NUMBER(loanamount)) desc";
		if ( sort != null && !sort.isEmpty())
		{
		    sOrder = "ORDER BY "+sort +" " + order;
		}
		/** END: Ordering **/
		
		//String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from q_mcoop t) where "+sWhere+" r between "+offset+" and "+total;
		
		String runq = "select * from q_mcoop "+ sWhere + " " + sOrder + " offset ? rows fetch next ? rows only";

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(runq);
			query.setInt(1, offset);
			query.setInt(2, total);
			
			System.out.println("===Mcoopcashquery==>" + runq);
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}
		return json;
	}
	//
	public String Lazyviewall_branch(int limit, int offset, String search, String sort, String order, String branch) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		String returnString = null;
		
		int total = offset + limit;
		PreparedStatement totalquery = null;
		
		/** Start: Filtering **/
		String sWhere = "";
		
		if( search != null && !search.isEmpty())
		{
		    //sWhere = "(";
			sWhere +="ACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CLIENT_NAME LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CUSTNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="AROCODE LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="SECTION LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(COLOFFICER) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(REGION) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(BRANCHNAME) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="NATIONID LIKE '%"+search.toUpperCase()+"%'";
		    sWhere += " and ";
		    
		}
		
		/** Start: Ordering **/
		String sOrder = "order by abs(TO_NUMBER(OUSTBALANCE)) desc";
		if ( sort != null && !sort.isEmpty())
		{
		    sOrder = "ORDER BY "+sort +" " + order;
		}
		/** END: Ordering **/
		
		String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from q_all t where t.branchcode = '"+branch+"') where "+sWhere+" r between "+offset+" and "+total;
		//String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from watch_stage t) where productcode not in('SAMinBal','CAwOD') and  rownum <= 30000";
		String tquery = "select count(ACCNUMBER) Total from q_all where branchcode = '"+branch+"'";
		
		try {
			System.out.println(runq);
			
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(runq);
			totalquery = conn.prepareStatement(tquery);
			
			ResultSet rs = query.executeQuery();
			ResultSet rstotal = totalquery.executeQuery();
			while (rstotal.next()) {
				//System.out.print("data length ==>" + rstotal.getInt("Total"));
				json = converter.toJSONArray(rs);
				returnString = "{\"total\": "+ rstotal.getInt("Total") +",\"rows\":"+json.toString()+"}";
			}
			
			query.close(); //close connection  getJSONObject
			totalquery.close();
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return returnString;
		}
		finally {
			if (conn != null) conn.close();
		}
		return returnString;
	}
	
	public String Lazyviewall(int limit, int offset, String search, String sort, String order, String division) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		String returnString = null;
		
		int total = offset + limit;
		PreparedStatement totalquery = null;
		
		/** Start: Filtering **/
		String sWhere = "";
		String Where = "";
		
		if( search != null && !search.isEmpty())
		{
		    //sWhere = "(";
			sWhere +="ACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CLIENT_NAME LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="CUSTNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="AROCODE LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="SECTION LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(COLOFFICER) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(REGION) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="upper(BRANCHNAME) LIKE '%"+search.toUpperCase()+"%' OR ";
			sWhere +="NATIONID LIKE '%"+search.toUpperCase()+"%'";
		    sWhere += " and ";
		    
		    Where +="Where ACCNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="CLIENT_NAME LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="CUSTNUMBER LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="AROCODE LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="SECTION LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="upper(COLOFFICER) LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="upper(REGION) LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="upper(BRANCHNAME) LIKE '%"+search.toUpperCase()+"%' OR ";
			Where +="NATIONID LIKE '%"+search.toUpperCase()+"%' ";
		}
		
		/** Start: Ordering **/
		String sOrder = "order by abs(TO_NUMBER(OUSTBALANCE)) desc";
		if ( sort != null && !sort.isEmpty())
		{
		    sOrder = "ORDER BY "+sort +" " + order;
		}
		/** END: Ordering **/
		
		String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from q_all t where t.section = '"+division+"') where "+sWhere+" r between "+offset+" and "+total;
		//String runq = "select * from (select t.*, row_number() over ("+sOrder+") r from watch_stage t) where productcode not in('SAMinBal','CAwOD') and  rownum <= 30000";
		String tquery = "select count(ACCNUMBER) Total from q_all where section = '"+division+"'";
		
		try {
			//System.out.println(runq);
			
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(runq);
			totalquery = conn.prepareStatement(tquery);
			
			ResultSet rs = query.executeQuery();
			ResultSet rstotal = totalquery.executeQuery();
			while (rstotal.next()) {
				//System.out.print("data length ==>" + rstotal.getInt("Total"));
				json = converter.toJSONArray(rs);
				returnString = "{\"total\": "+ rstotal.getInt("Total") +",\"rows\":"+json.toString()+"}";
			}
			
			query.close(); //close connection  getJSONObject
			totalquery.close();
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return returnString;
		}
		catch(Exception e) {
			e.printStackTrace();
			return returnString;
		}
		finally {
			if (conn != null) conn.close();
		}
		return returnString;
	}
	
	public JSONArray getactivs(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from actionplans where upper(pcode) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getaccountplanlogs(String acc) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qaccplans where accnumber = ? ");
			query.setString(1, acc.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	public JSONArray branchAll(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(l.AROCODE) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}
		return json;
	}
	public JSONArray divallbranch(String branchcode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(l.BRANCHCODE) = ?");
			query.setString(1, branchcode.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}
		return json;
	}
	public JSONArray searchbyname(String name) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(c.FIRSTNAME) LIKE ?");
			
			query.setString(1, "%" + name.toUpperCase() + "%");
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray searchbyarocode(String arocode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.BUCKET, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, to_number(l.OUSTBALANCE) OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, to_number(l.TOTALARREARS) TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(l.AROCODE) = ? "); //,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE
			query.setString(1, arocode.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray searchbyrrocode(String rrocode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, l.RROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.BUCKET, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, to_number(l.OUSTBALANCE) OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, to_number(l.TOTALARREARS) TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(l.RROCODE) = ? ");
			query.setString(1, rrocode.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray searchbyidnumber(String idnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_loans_n_cards " +
					"where UPPER(NATIONID) = ? ");
			query.setString(1, idnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	/*
	public JSONArray searchbyidnumber_old(String idnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.BUCKET, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(c.NATIONID) = ? ");
			query.setString(1, idnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	} **/
	
	public JSONArray searchbydeptcode(String arocode) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * " +
					"from q_watchstage where deptcode = ? ");
			query.setString(1, arocode.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray searchbyaccnumber(String accnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_watchstage " +
					"where accnumber = ? ");
			query.setString(1, accnumber);
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray searchbycustnumber(String custnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(c.CUSTNUMBER) = ? ");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
			if (query != null) query.close();
		}
		return json;
	}
	
	public JSONArray closedcmd(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(SECTION) = ? and oustbalance>=0");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryCards(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where primary = 'P' and UPPER(COLOFFICER) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray noplanscc(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where primary = 'P' and accplan = '0' and UPPER(COLOFFICER) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getBranch(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from branches where UPPER(branchcode) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray getLetter(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from uploads where confimation = 'No' and UPPER(ACCNUMBER) = ? order by stagedate desc ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray CardInfo(String cardacct) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where UPPER(CARDACCT) = ? ");//cards_stage where primary = 'P' and
			query.setString(1, cardacct.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Cardnumberinfo(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where primary = 'P' and UPPER(CARDNUMBER) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray otherCard(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where UPPER(CARDACCT) = ? ");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryReturnM(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, REVIEWDATE, l.PRINCARREARS, l.ARONAME, " +
					"l.ORIGDATE, l.CURRENCY, l.ORIGBALANCE, l.SECTION,c.GENDER,c.FIRSTNAME, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.POSTCODE," +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, " +
					"l.BRANCHSTATUS,l.CMDSTATUS,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE " +
					"from tbl_PORTFOLIO l inner join customers_stage c on c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE where COLOFFICER = ? ");
					
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}

	public JSONArray queryAccount(String Accnumber) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,l.CURING,l.EXCUSE_OTHER, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(ACCNUMBER) = ? ");
					
			query.setString(1, Accnumber.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) 
			conn.close();
			query.close();
		}
		return json;
	}
	
	public JSONArray queryReturnCrad(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHNAME,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION,l.LASTCREDDATE,l.LIMITAMOUNT,l.TEMPLIMIT,(l.LIMITAMOUNT+l.TEMPLIMIT) TOTALLIMIT,(l.LIMITAMOUNT+l.TEMPLIMIT+l.OUSTBALANCE) EXCESS,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.DATERECEIVED from tbl_portfolio l, tbl_customers c where l.CUSTNUMBER = c.CUSTNUMBER and PRODUCTCODE IN ('CAwOD') and (l.LIMITAMOUNT+l.TEMPLIMIT+l.OUSTBALANCE) < 0 and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray overdueplans(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, " +
					"c.POSTCODE, l.BRANCHCODE,l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.ARONAME, m.manager,l.BRANCHSTATUS,l.CMDSTATUS,l.BRANCHNAME, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, l.DATERECEIVED,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE " +
					"from tbl_PORTFOLIO l,qexpiredplans e, CUSTOMERS_STAGE c, branches m " +
					"where l.accnumber=e.accnumber and c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray overdueplanscc(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * " +
					"from qcards l,qexpiredplans e " +
					"where l.cardacct=e.accnumber and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray noplans(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHCODE, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.ARONAME, m.manager,l.BRANCHSTATUS,l.CMDSTATUS,l.BRANCHNAME, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, l.DATERECEIVED,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.accplan is null and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}

	public JSONArray queryReturnMW(String brand) throws Exception {

		PreparedStatement query = null;
		
		Connection conn = null;
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			/*query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, abs(to_number(l.DAYSINARR)) DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.LASTACTIONDATE, " +
					"l.BRANCHCODE, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, abs(to_number(l.OUSTBALANCE)) OUSTBALANCE, l.ARONAME, m.manager,l.BRANCHSTATUS,l.CMDSTATUS,l.BRANCHNAME, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS,abs(to_number(l.TOTALARREARS)) TOTALARREARS, l.DATERECEIVED,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE, m.region,l.Bucket " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and TO_DATE(l.REVIEWDATE,'DD-MM-YYYY') <= sysdate and UPPER(COLOFFICER) = ? ");*/
			
			query = conn.prepareStatement("select t.*,sysdate-to_date(REVIEWDATE,'dd/mm/yyyy') OVEDUE from tbl_q_all t where TO_DATE(t.REVIEWDATE,'DD-MM-YYYY') <= sysdate and UPPER(t.colofficer) = ?");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Todayswork(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, abs(to_number(l.DAYSINARR)) DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE,l.Bucket,m.region, " +
					"l.BRANCHCODE, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, abs(to_number(l.OUSTBALANCE)) OUSTBALANCE, l.ARONAME, m.manager,l.BRANCHSTATUS,l.CMDSTATUS,l.BRANCHNAME, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS,abs(to_number(l.TOTALARREARS)) TOTALARREARS, l.DATERECEIVED,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, branches m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.REVIEWDATE = to_char(sysdate,'DD-MM-YYYY') and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Todaysworkmcoop(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_mcoop " +
					"where REVIEWDATE = to_char(sysdate,'DD-MM-YYYY') and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray worklistmcoop(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_mcoop " +
					"where REVIEWDATE <= to_char(sysdate,'DD-MM-YYYY') OR REVIEWDATE is null  and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray myallocationsmcoop(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_mcoop " +
					"where UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	//noplansmcoop
	public JSONArray noplansmcoop(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_mcoop " +
					"where ACCPLAN is null and  UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	//overdueplansmcoop
	public JSONArray overdueplansmcoop(String username) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_mcoop " +
					"where LOANACCNUMBER in (select accnumber from qexpiredplans) and  UPPER(COLOFFICER) = ? ");
			
			query.setString(1, username.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray withFunds(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT distinct l.ACCNUMBER,c.FIRSTNAME CLIENT_NAME, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHCODE, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.ARONAME, m.manager,l.BRANCHSTATUS,l.CMDSTATUS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, l.DATERECEIVED,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, branches m, withfunds_v v " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and l.accnumber = v.loanaccount and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	//and l.REVIEWDATE = to_char(sysdate,'DD-MM-YYYY')
	
	public JSONArray Todaysworkcc(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where Primary = 'P' and TO_DATE(REVIEWDATE,'DD-MM-YYYY') = sysdate and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray queryWorklistcc(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where OUTBALANCE < -1 and Primary = 'P' and TO_DATE(REVIEWDATE,'DD-MM-YYYY')<= sysdate and UPPER(COLOFFICER) = ? ");
			
			query.setString(1, brand.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Reviewerdatacc(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from qcards where Primary = 'P' and UPPER(ROUTETOSTATE) = ? ");
			
			query.setString(1, brand.toUpperCase()); 
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); 
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Notes() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,NOTEMADE,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,OWNER from NOTEHIS ORDER BY notedate desc");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray Ptps() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ACCNUMBER,ARRAMOUNT,PTPAMOUNT,PTPDATE,PAYMETHOD,OWNER,ACTIONDATE,MET from ptps");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	public JSONArray custview() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT ACCNUMBER,CUSTNUMBER,PRODUCTCODE,OUTSBALANCE, AROCODE from custview ");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray Letters() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ISSUEDATE, ACCNUMBER,CUSTNUMBER,ARRAMOUNT,TITLE, FIRSTNAME, ADDRESSLINE1, TOWN, OUSTBALANCE, ARRAMOUNT, COLOFFICER, DEMANDTYPE, SENT from v_demands where sent = 'N'");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
 public JSONArray Users() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select USERNAME,FIRSTNAME,SURNAME,LASTNAME,ACTIVE,CREATEDATE,DATELOGLAST,DIVISION,MANAGER from tblusers");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
	
	public JSONArray Tel() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT CUSTNUMBER,PERSON,CONTACT,OWNER,to_char(ACTIONDATE, 'YYYY-MM-DD HH24:MI:SS') ACTIONDATE,ACTIVE,REL from TELES order by actiondate desc");
			
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}

	public JSONArray queryReturnUsers(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username,firstname||' '||surname fullname,lastname,active,createdate,division,ACCESSRIGHTS from tblusers where UPPER(DIVISION) = ?");
			
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
	
	public JSONArray Reviewers(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username,firstname||' '||surname fullname from tblusers where UPPER(username) = ?");
			
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		return json;
	}
}
