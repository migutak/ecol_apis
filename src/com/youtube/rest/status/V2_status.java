package com.youtube.rest.status;

import java.sql.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.*;
import com.youtube.util.ToJSON;

@Path("/v2_status")
public class V2_status {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>E-Collect APIs</p>";
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String myString = null;
		String returnString = null;		

		try {			
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select sysdate DATETIME from dual");
			ResultSet rs = query.executeQuery();
			
			while(rs.next()){
				myString = rs.getString("DATETIME");
			}
			query.close();

			returnString = "<p>Database Status</p> " +
				"<p>OracleDB Date/Time return: " + myString + "</p>";

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null)conn.close();
		}

		return returnString; 
	}
	
	@Path("/searchbycustnumber/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbycustnumber(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER, l.REVIEWDATE, l.PRINCARREARS, l.AROCODE, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, m.manager, l.AROBRANCH,(l.PRINCARREARS+l.PENALARREARS+l.INTRATEARR) TOTALARREARS, " +
					"l.FILENO, l.INTRATEARR, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS,l.AROCODE,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO l, CUSTOMERS_STAGE c, BRANCHES m " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.BRANCHCODE(+) = m.BRANCHCODE and UPPER(c.CUSTNUMBER) = ? ");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(conn != null) conn.close();
			if(query != null) query.close();
		}
		return rb;
	}
	
	@Path("/searchclearedaccsbycustnumber/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchclearedbycustnumber(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT c.FIRSTNAME CLIENT_NAME,l.ACCNUMBER, c.CUSTNUMBER, l.COLOFFICER, l.REVIEWDATE, l.AROCODE, " +
					"l.COLLECTIONSTATUS, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHCODE, l.ROUTETOSTATE, l.EXCUSE, " +
					"l.FILENO,l.BRANCHSTATUS,l.CMDSTATUS,sysdate-to_date(l.REVIEWDATE,'dd/mm/yyyy') OVEDUE  " +
					"from tbl_PORTFOLIO_STATIC l, CUSTOMERS_STAGE c " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and UPPER(c.CUSTNUMBER) = ? ");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(conn != null) conn.close();
			if(query != null) query.close();
		}
		return rb;
	}
	
	@Path("/searchbyname/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyname(
			@PathParam("name") String name
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

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
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
			if(query != null) query.close();
		}
		return rb;
	}
	
	@Path("/searchbyidnumber/{idnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyidnumber(
			@PathParam("idnumber") String idnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from q_loans_n_cards " +
					"where UPPER(NATIONID) = ? ");
			query.setString(1, idnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}

}
