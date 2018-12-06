package com.youtube.rest.status;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.youtube.dao.*;
import com.youtube.util.ToJSON;

@Path("/status")
public class V1_status {
	
	
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
				"<p>OracleDr DB Date/Time return: " + myString + "</p>";

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn != null)conn.close();
		}

		return returnString; 
	}
	
		@Path("/upload_a_note")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response upload_a_note(
				@QueryParam("custnumber") String custnumber,
				@QueryParam("custnumber") String accnumber,
				@QueryParam("notemade") String notemade,
				@QueryParam("owner") String owner
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement psnote = null;
			Connection con = null;
			int rs = 0;
			//System.out.println(owner);
			try{
				con = Oracle308tube.LocalConnection();
		    	psnote = con.prepareStatement("insert into notehis (CUSTNUMBER,NOTEMADE,OWNER,NOTESRC,accnumber) values (?,?,?,?,?)");
	            
	            String src = "uploaded note";
	            
	            psnote.setString(1, custnumber);
	            psnote.setString(2, notemade);
	            psnote.setString(3, owner);
	            psnote.setString(4, src);
	            psnote.setString(5, accnumber);
	            
	            rs = psnote.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "NOT Successful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}finally{
				if(con != null) con.close();
			}
			return Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		
		@Path("/upload_activity_add/{owner}")
		@GET
		public Response Upload_activity_add(
				@PathParam("owner") String owner
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement psactivity = null;
			Connection con = null;
			int rs = 0;
			//System.out.println(owner);
			try{
				con = Oracle308tube.LocalConnection();
		    	String sqlactivity = "Insert into activitylogs(CUSTNUMBER,ACCOUNTNUMBER,ACTION,REASON,ROUTE,OWNER,NOTEMADE,REVIEWDATE,cmdstatus,BRANCHSTATUS)"
	            		+ " values ('bulkcustnumber','bulkaccnumber','REVW','1','1',?,'bulk note uploaded','1','1','1')";
	            
	            psactivity = con.prepareStatement(sqlactivity);
	            psactivity.setString(1, owner);
	            
	            rs = psactivity.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "NOT Successful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}finally{
				if(con != null) con.close();
			}
			return Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		
		@Path("/upload_a_note_r")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response upload_a_note_r(
				@QueryParam("custnumber") String custnumber,
				@QueryParam("notemade") String notemade,
				@QueryParam("owner") String owner,
				@QueryParam("reviewdate") String reviewdate
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement psnote = null;
			PreparedStatement psreviewdate = null;
			Connection con = null;
			int rs = 0;
			// System.out.println(owner);
			try{
				con = Oracle308tube.LocalConnection();
		    	psnote = con.prepareStatement("insert into notehis (CUSTNUMBER,NOTEMADE,OWNER,NOTESRC,accnumber) values (?,?,?,?,?)");
		    	psreviewdate = con.prepareStatement("update tbl_portfolio_static set reviewdate = ? where custnumber = ? ");
	            
	            String src = "uploaded note";
	            
	            psnote.setString(1, custnumber);
	            psnote.setString(2, notemade);
	            psnote.setString(3, owner);
	            psnote.setString(4, src);
	            psnote.setString(5, custnumber);
	            
	            psreviewdate.setString(1, reviewdate);
	            psreviewdate.setString(2, custnumber);
	            
	            rs = psnote.executeUpdate();
	            
	            if(rs>0){
	            	psreviewdate.executeUpdate();
	            	retunx = "OK";
	            }else{
	            	retunx = "NOT Successful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}finally{
				if(con != null) con.close();
			}
			return Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	
	
	@Path("/branches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnrecovPending() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * From branches order by Branchcode");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	

	@Path("/rembranches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response rembranches() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Branchcode, BranchName from branches where branchcode not in (select branchcode from tblRegionlogs) order by Branchcode");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_investigators")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_investigators() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblinvestigators_logs where status != 'RECEIVED' group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_valuers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_valuers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblvaluers_logs where status != 'RECEIVED' group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/invoicesdue")//to be done payments
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response invoicesdue() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from invoicesdue");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//
	@Path("/invoices")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response invoices() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvoices where status = 'In Process' order by stagedate desc");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/paidinvoices")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response paidinvoices() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvoices where status = 'PAID' ");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	//
	@Path("/get_all_providers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_providers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from sptypes ");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/payments") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response payments() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvoices where status = 'In Process' ");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_debtcollectors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_debtcollectors() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tbldebtcollectors_logs group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_auctioneers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_auctioneers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblauctioneers_logs group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_repossessors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_repossessors() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblrepossessors_logs where status != 'REPOSSESSED' group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_yards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_yards() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblyards_logs group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/chart_marketors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response chart_marketors() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select serviceprovider, count(status) nuofInstructions from tblmarketors_logs group by serviceprovider");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getproviders")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getproviders() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from sptypes");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getactcodes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getactcodes() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from planactivities");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/accplans")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accplans() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from accountplan");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getsla")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getsla() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblsla");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/ipaddress")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ipaddress() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblips");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/departments")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response depart() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select deptcode, deptname From tbldepartments order by deptcode");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/confirmdemands")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmdemands() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from uploads where confimation = 'No' order by stagedate desc");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/managers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response Managers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select Branchcode, BranchName, manager,REGASSETFINANCE,REGMICROCREDIT,REGSME,REGPORTFOLIO From branches order by Branchcode");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/dueissued")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response DueIssued() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, l.OUSTBALANCE, l.ARONAME, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, l.TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS, l.AROCODE " +
					"from demandsdue l, CUSTOMERS_STAGE c " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and l.sentstatus = 'N'");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getbuckets")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnbuckets() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID, UPPERLIMIT, LOWERLIMIT,PRODUCT,COLOFFICER from tblBucketassign order by LOWERLIMIT");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getregion")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getregion() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from regions");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getbranchregion")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getbranchregion() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblRegionlogs");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/Allcards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allcards() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select CARDNUMBER, CARDNAME, CARDSTATUS, ACCOUNTSTATUS, CARDACCT, ACCOUNTNO, to_number(OUTBALANCE) OUTBALANCE, to_number(LIMIT) LIMIT, EXPPMNT, PREVDEBT, PAYMENT, CYCLE, ADDRESS, " +
					"CITY, RPCODE, TEL, MOBILE, EMAIL, AGEINMONTHS, LASTPAYMENTDATE, BASESUPP, DAYSINARREARS, SQNUMBER, COLOFFICER, REVIEWDATE, EXCUSE, ROUTETOSTATE, DATERECEIVED, " +
					"PHONE, EMPSTATUS, MARITAL, cmdstatus, branchstatus " +
					"From qcards where OUTBALANCE not in (0,-1) and primary = 'P'");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/precards_old")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response precards_old() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.CARD_NUMBER, c.CARD_NAME, c.CARD_STATUS, c.ACCOUNT_STATUS, c.CARD_ACCOUNT_NUMBER, c.ACCOUNT_NO, to_number(c.OUT_BALANCE) OUTBALANCE, to_number(LIMITAMNT) LIMIT, " +
					"c.EXP_PAYMENT, c.PAYMENT, c.CYCLE, c.ADDRESS,c.ANALYSED_CATEGORY,c.DUE_DATE,c.NEXT_EXPECTED_PAY_DATE,c.AUTOPAY_ACCOUNT,c.EMPLOYER, " +
					"c.LAST_PAYMENT_DATE,c.POASTAL_CODE,c.CITY, s.TEL, c.MOBILE_NO, c.EMAIL, c.PREV_DEBT, c.AGE_IN_MONTHS, COLOFFICER, REVIEWDATE, EXCUSE, ROUTETOSTATE, cmdstatus, branchstatus " +
					"from TBL_CREDITCARDS c left join TBLCARD_STATIC s on c.CARD_NUMBER = s.CARDNUMBER");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/precards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response precards() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.CARDNUMBER, c.CARDNAME, c.CARDSTATUS, c.ACCOUNTSTATUS, c.CARDACCT, c.ACCOUNTNO, to_number(c.OUTBALANCE) OUTBALANCE, to_number(c.LIMIT) LIMIT, " +
					"c.EXPPMNT, c.PAYMENT, c.CYCLE, c.ADDRESS,c.NATIONID,c.DUEDATE,c.AMOUNTPAID,c.DAYSINARREARS,c.PREVDEBT, " +
					"c.LASTPAYMENTDATE,c.DOB,c.CITY, s.TEL, c.MOBILE, c.EMAIL, c.PREVDEBT, c.AGEINMONTHS, COLOFFICER, REVIEWDATE, EXCUSE, ROUTETOSTATE, cmdstatus, branchstatus " +
					"from CARDS_WATCH_STAGE c left join TBLCARD_STATIC s on c.CARDNUMBER = s.CARDNUMBER");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
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
	
	@Path("/getcard/{cardacct}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getcard(
			@PathParam("cardacct") String cardacct
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			/*query = conn.prepareStatement("select c.CARDNUMBER, c.CARDNAME, c.CARDSTATUS, c.ACCOUNTSTATUS, c.CARDACCT, c.ACCOUNTNO, to_number(c.OUTBALANCE) OUTBALANCE, to_number(c.LIMIT) LIMIT, " +
					"c.EXPPMNT, c.PAYMENT, c.CYCLE, c.ADDRESS,c.NATIONID,c.DUEDATE,c.AMOUNTPAID,c.DAYSINARREARS,c.PREVDEBT, " +
					"c.LASTPAYMENTDATE,c.DOB,c.CITY, s.TEL, c.MOBILE, c.EMAIL, c.PREVDEBT, c.AGEINMONTHS, COLOFFICER, REVIEWDATE, EXCUSE, ROUTETOSTATE, cmdstatus, branchstatus " +
					"from CARDS_WATCH_STAGE c left join TBLCARD_STATIC s on c.CARDNUMBER = s.CARDNUMBER where c.CARDACCT = ?");*/
			query = conn.prepareStatement("Select * from qcards where CARDACCT = ?");
			query.setString(1, cardacct.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null) conn.close();
			if(query != null) query.close();
		}
		return rb;
	}
	
	// precards
	@Path("/getprecard/{cardacct}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getprecard(
			@PathParam("cardacct") String cardacct
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("Select * from CARDS_WATCH_STAGE where CARDACCT = ?");
			query.setString(1, cardacct.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null) conn.close();
			if(query != null) query.close();
		}
		return rb;
	}
	
	@Path("/closedcc")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response closedcc() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select CARDNUMBER, CARDNAME, CARDSTATUS, ACCOUNTSTATUS, CARDACCT, ACCOUNTNO, to_number(OUTBALANCE) OUTBALANCE, to_number(LIMIT) LIMIT, EXPPMNT, PREVDEBT, PAYMENT, CYCLE, ADDRESS, " +
					"CITY, RPCODE, TEL, MOBILE, EMAIL, AGEINMONTHS, LASTPAYMENTDATE, BASESUPP, DAYSINARREARS, SQNUMBER, COLOFFICER, REVIEWDATE, EXCUSE, ROUTETOSTATE, DATERECEIVED, " +
					"PHONE, EMPSTATUS, MARITAL, cmdstatus, branchstatus " +
					"From qcards where OUTBALANCE in (0,-1) and primary = 'P'");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/notestoday")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnLitigators() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select CUSTNUMBER,NOTEMADE,OWNER,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,NOTESRC from NOTEHIS where NOTEDATE > sysdate - 1 order by NOTEDATE desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	//paged noted
	// total notes for a customer
	// cust = 0057181
	
	@Path("/notes/total/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalcustnotes(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select count(custnumber) totalnotes " +
					"from NOTEHIS where UPPER(CUSTNUMBER) = ?");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	};
	
	//paging notes
	
	@Path("/notes/{custnumber}/{startpage}/{endpage}") // test lady loading with 0057181
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response pagedcustnotes(
			@PathParam("custnumber") String custnumber,
			@PathParam("startpage") int startpage,
			@PathParam("endpage") int endpage
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,ACCNUMBER,NOTEMADE,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,OWNER, NOTESRC, extract(day from (systimestamp - NOTEDATE))*24*60*60 DAYPAST " +
					"from NOTEHIS where UPPER(CUSTNUMBER) = ? order by NOTEDATE desc, noteimp desc offset ? rows fetch next ? rows only ");
			query.setString(1, custnumber.toUpperCase());
			query.setInt(2, startpage);
			query.setInt(3, endpage);
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	
//all customer notes	
	@Path("/notes/{custnumber}") // test lady loading with 0057181
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response custnotes(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select ID,CUSTNUMBER,ACCNUMBER,NOTEMADE,to_char(NOTEDATE, 'YYYY-MM-DD HH24:MI:SS') NOTEDATE,OWNER, NOTESRC, extract(day from (systimestamp - NOTEDATE))*24*60*60 DAYPAST " +
					"from NOTEHIS where UPPER(CUSTNUMBER) = ? order by NOTEDATE desc, noteimp desc");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/files/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response files(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from uploads where UPPER(custnumber) = ? order by stagedate DESC");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/planactions/{plantype}/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response planactions(
			@PathParam("plantype") String plantype,
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_actions where UPPER(custnumber) = ? and upper(actionagreed) = ?");
			query.setString(1, custnumber.toUpperCase());
			query.setString(2, plantype.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/files/{custnumber}/{doctype}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response filesithdoctype(
			@PathParam("custnumber") String custnumber,
			@PathParam("doctype") String doctype
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from uploads where UPPER(custnumber) = ? and doctype = ? order by stagedate DESC");
			query.setString(1, custnumber.toUpperCase());
			query.setString(2, doctype);
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", true)
					.header("Access-Control-Allow-Methods", "GET")
					.header("Access-Control-Allow-Headers", "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	
	@Path("/woff")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returninvestigators() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select l.accnumber, l.custnumber, c.firstname client_name, l.INSTAMOUNT, l.DAYSINARR, l.COLOFFICER,l.OUSTBALANCE,l.REVIEWDATE, l.PRINCARREARS, l.INTRATEARR, " +
					"l.ORIGDATE, l.COLLECTIONSTATUS, l.CURRENCY, l.ORIGBALANCE, l.SECTION, c.ADDRESSLINE1, c.DOB, c.CELNUMBER, c.TELNUMBER, c.NATIONID, c.STREETADDRESS, c.POSTCODE, " +
					"l.BRANCHNAME, l.PRODUCTCODE, l.LASTCREDAMNT, l.ROUTETOSTATE, l.EXCUSE, l.LASTCREDDATE, l.NEXTREPAYDATE, to_number(l.OUSTBALANCE) OUSTBALANCE, l.ARONAME, l.AROBRANCH, " +
					"l.FILENO, l.PENALARREARS, l.DATERECEIVED, to_number(l.TOTALARREARS) TOTALARREARS, l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.BRANCHSTATUS,l.CMDSTATUS, l.AROCODE,L.BUCKET " +
					"from tbl_portfolio l, CUSTOMERS_STAGE c " +
					"where c.CUSTNUMBER(+) = l.CUSTNUMBER and UPPER(l.AROCODE) in ('CMDR09','CMDR03') ");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/reviewers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returncollector() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT username, UPPER(firstname||' '||surname||' '||lastname) FName from v_reviewers ");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/callsmade/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response callsmade(
		@PathParam("custnumber") String accnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT count(custnumber) totalactions,sum(toc) calls, sum(rpc) rpc,count (CASE WHEN promiseamount>0 THEN 1 ELSE NULL END) ptp from qactivity where actiondate >= (select TRUNC(SysDate,'YEAR') FROM Dual)");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/v_activity/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response v_activity(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		String sql = "select period label, count(accnumber) value from v_activity where custnumber = ? group by period";
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
			if(query != null)query.close();
		}
		return rb;
	}
	
	@Path("/productcategories/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response productcategories(
		@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select productcode label,sum(abs(oustbalance)) value from watch_stage where custnumber = ? group by productcode");
			query.setString(1, custnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/accountbalance/{accnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accountbalance(
		@PathParam("accnumber") String accnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select count(accnumber) taccnumber, sum(abs(oustbalance)) oustbalance, sum(abs(totalarrears)) totalarrears from tbl_portfolio where custnumber = ? group by custnumber ");
			query.setString(1, accnumber.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	// https://192.168.78.140:4565/api/status/investigators?order=asc&limit=20&offset=0&_=1511363688423
	@Path("/investigators")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response investigators() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a left join qtblinvestigators l on a.id=l.id order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/yards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response yards() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblyards");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/auctioneers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAuction() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a left join qtblauctioneers l on a.id = l.id order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/debtcollectors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response debtcollectors() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select a.*,t.DATEINST,t.EXTDUEDATE,t.DATEOFEXT,t.NOOFDAYSEXT,t.OWNER,t.REMARKS,t.SERVICEPROVIDER,"
					+ "t.STATUS,t.issueid,s.address spaddress, s.contactperson,s.email "
					+ "from tbldebtcollectors a LEFT JOIN qtbldebtcollectors t on t.ID = a.ID left join sptypes s on s.sptitle=t.serviceprovider order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/teleusers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response teleUsers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblusers where telecollector in ('Y') ");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/repos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnRepos() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a left join qtblrepossessors l on a.id=l.id order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/marketors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnMarketors() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			//query = conn.prepareStatement("select a.*,l.issueid IDLOGS, l.dateinst,l.duedate,l.serviceprovider,l.openmarketvalue,l.forcedsalevalue,l.status,l.stagedate from tblmarketors a left join qtblmarketors l on a.id = l.id order by a.stagedate desc");
			query = conn.prepareStatement("select * from tblmarketors a left join qtblmarketors l on a.id = l.id order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//investigators_deleted
	@Path("/investigators_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response investigators_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a,tblinvestigators_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/marketors_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response marketors_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/debtcollectors_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response debtcollectors_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbldebtcollectors a,tbldebtcollectors_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/valuers_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response valuers_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a,tbltblvaluers_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/repos_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response repos_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a,tblrepossessors_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/auctioneers_deleted")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response auctioneers_deleted() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a,tblauctioneers_logs l where a.id = l.id(+) and a.newstatus in ('Deleted') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//investigators_unassigned
	@Path("/investigators_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response investigators_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a,tblinvestigators_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	//investigators_deleted
	
	@Path("/marketors_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response marketors_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/auctioneers_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response auctioneers_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a,tblauctioneers_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/debtcollectors_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response debtcollectors_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbldebtcollectors a,tbldebtcollectors_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/repos_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response repos_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a,tblrepossessors_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/valuers_unassigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response valuers_unassigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a,tblvaluers_logs l where a.id = l.id(+) and a.newstatus in ('unassigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/marketors_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response marketors_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/debtcollectors_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response debtcollectors_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbldebtcollectors a, tbldebtcollectors_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/repos_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response repos_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a,tblrepossessors_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/valuers_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response valuers_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a,tblvaluers_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/investigators_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response investigators_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a,tblinvestigators_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/auctioneers_expired")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response auctioneers_expired() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a,tblauctioneers_logs l where a.id = l.id(+) and TO_DATE(l.duedate, 'DD-Mon-YYYY')<=sysdate order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//investigators_assigned
	@Path("/investigators_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response investigators_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblinvestigators a, tblinvestigators_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/repos_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response repos_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblrepossessors a, tblrepossessors_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/valuers_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response valuers_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a, tblvaluers_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/debtcollectors_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response debtcollectors_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tbldebtcollectors a, tbldebtcollectors_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/auctioneers_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response auctioneers_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblauctioneers a, tblauctioneers_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//
	@Path("/marketors_assigned")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response marketors_assigned() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblmarketors a,tblmarketors_logs l where a.id = l.id(+) and a.newstatus in ('assigned') order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//
	@Path("/valuers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response valuers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from tblvaluers a left join qtblvaluers l on a.id=l.id order by a.stagedate desc");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	/*@Path("/relegated")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnRelegated() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHCODE,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION from tbl_portfolio l, tbl_customers c where l.custnumber = c.custnumber and section = 'CMD'and SUBSTR(ACCNUMBER,3,3) IN ('6C7','6E7','6E8','6F2','9E7','9E9')");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}*/
	
	/*@Path("/caw")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returncawods() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHNAME,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION,l.LASTCREDDATE,l.LIMITAMOUNT,l.TEMPLIMIT,l.TEMPLIMITEXPIRYDATE,l.LIMITEXPIRYDATE,l.DATERECEIVED,(l.LIMITAMOUNT-l.TEMPLIMIT+l.OUSTBALANCE) EXCESS from tbl_portfolio l, tbl_customers c where l.CUSTNUMBER = c.CUSTNUMBER and PRODUCTCODE IN ('CAwOD') and (l.LIMITAMOUNT+l.TEMPLIMIT+l.OUSTBALANCE) < 0");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	} Card operations*/ 
	
	/*@Path("/savings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnsavings() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHCODE,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION from tbl_portfolio l, tbl_customers c where l.CUSTNUMBER = c.CUSTNUMBER and PRODUCTCODE IN ('savings') and oustbalance < 0 and l.AROCODE NOT IN ('R01','R02','R03','R04','R05','R06','R07','R08','R09','R10','R11','R12','R13','R14','R15','R16','R17','R18','R19','R20','R21')");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}*/
	
	/*@Path("/minsav")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnminsav() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHCODE,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION from tbl_portfolio l, tbl_customers c where l.CUSTNUMBER = c.CUSTNUMBER and PRODUCTCODE IN ('SAMinBal') and oustbalance < 0 and l.AROCODE NOT IN ('R01','R02','R03','R04','R05','R06','R07','R08','R09','R10','R11','R12','R13','R14','R15','R16','R17','R18','R19','R20','R21')");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}*/
	
	/*@Path("/loanods")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnloanods() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select c.FIRSTNAME CLIENT_NAME,l.COLLECTIONSTATUS,l.ACCNUMBER,l.CUSTNUMBER,l.PRINCARREARS,l.INTRATEARR,l.AROCODE,l.BRANCHCODE,l.PRODUCTCODE,l.OUSTBALANCE,l.ARONAME," +
					"l.COLOFFICER,l.DAYSINARR,l.SECTION from tbl_portfolio l, tbl_customers c where l.CUSTNUMBER = c.CUSTNUMBER and PRODUCTCODE IN ('LoanOD') and oustbalance < 0 and l.AROCODE NOT IN ('R01','R02','R03','R04','R05','R06','R07','R08','R09','R10','R11','R12','R13','R14','R15','R16','R17','R18','R19','R20','R21')");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}*/
	
	@Path("/users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnUsers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username,UPPER(firstname||' '||surname) fullname,lastname,active,createdate,division,ACCESSRIGHTS,vonline from tblusers where active = 'Y' order by username");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/users/{branchcode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response usersinbranch(
			@PathParam("branchcode") String branchcode
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		String sql = "select username, upper(firstname||' '||lastname||' '||surname) as fullname from tblusers where branch = ?";
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			query.setString(1, branchcode.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/tblusers/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response userdetails(
			@PathParam("username") String branchcode
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		String sql = "select * from tblusers where upper(username) = ?";
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			query.setString(1, branchcode.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/pbbusers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response pbbUsers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username,UPPER(firstname||' '||surname) fullname,lastname,active,createdate,division,ACCESSRIGHTS from tblusers where active = 'Y' and division in('PBBSCORED','PBBSCHEME') order by username");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/getschemecolofficers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getschemecolofficers() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select username,UPPER(firstname||' '||surname) fullname,lastname,active,createdate,division,ACCESSRIGHTS from tblusers where active = 'Y' and division in('PBBSCHEME') order by username");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/letters")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnLetters() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select id,demand,section from demands");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/cmdstatus")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnCmdstatus() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select value,sel from cmdstatus");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/brstatus")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrstatus() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select value,sel from branchstatus");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin","*")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/creditcards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnCreditcards() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from qcards where primary = 'P'");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/activityoc/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activityocwithsearch(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select to_char(actiondate,'YYYY-MM-DD') label, sum(case when action = 'OC' then 1 else 0 end) as value from activitylogs a "
					+ "left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and "
					+ "upper(b.region||u.branch||owner) like ? "
					+ "group by to_char(actiondate,'YYYY-MM-DD') order by to_char(actiondate,'YYYY-MM-DD') asc");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/activityowner/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activityownerwithsearch(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select owner label, sum(case when action = 'OC' then 1 else 0 end) as value from activitylogs a "
					+ "left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and "
					+ "upper(b.region||u.branch||owner) like ? "
					+ "group by owner ");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/activitybranch/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activitybranchwithsearch(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select branchname label, sum(case when action = 'OC' then 1 else 0 end) as value from activitylogs a "
					+ "left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and "
					+ "upper(b.region||u.branch||owner) like ? "
					+ "group by branchname ");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/reasonfordefault/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response reasonfordefault(
		@PathParam("searchstring") String searchstring
			) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		String sql = "select * from (select reason label, count(custnumber) value from activitylogs a left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and upper(b.region||u.branch||owner) like ? group by reason order by count(custnumber) desc) where rownum <=20";
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/tactions/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response tactions (
			@PathParam("searchstring") String searchstring
			) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		
		Response rb = null;
		String sql = "select count(custnumber) actions,sum(case when action='OC' then 1 when action='IC' then 1 else 0 end) as TCalls, sum(case when upper(ptp) = 'YES' then 1 else 0 end) "
				+ "as ptps, count(distinct accountnumber) as workedon, sum(case when party = '1' then 1 else 0 end) as RPC from activitylogs a "
				+ "left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? "
				+ "and upper(b.region||u.branch||owner) like ? ";
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement(sql);
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/activitybyproduct/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activitybyproduct(
		@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select productcode label, count(accountnumber) as value from activitylogs a left join tbl_portfolio p on p.accnumber=a.accountnumber left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch"
					+ " where to_char(a.actiondate,'YYYY-MM-DD') between ? and ? and upper(b.region||u.branch||owner) like ? group by productcode");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/activitybybucket/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activitybybucket(
		@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select bucket label, count(custnumber) as value from activitylogs a left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch"
					+ " where to_char(actiondate,'YYYY-MM-DD') between ? and ? and upper(b.region||u.branch||owner) like ? group by bucket");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/activityamntcollected/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response activityamntcollected(
		@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		//SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	    //Date now = new Date();
	    //String strDate = sdfDate.format(now);
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select colofficer label,sum(recvdamount) value from pamnt where ddate between ? and ? and upper(region||branch||colofficer) like ? group by colofficer");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/actions/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response actions(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select to_char(actiondate,'YYYY-MM-DD') label, count(custnumber) as value from activitylogs a left join "
					+ "tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and "
					+ "upper(b.region||u.branch||owner) like ? group by to_char(actiondate,'YYYY-MM-DD')");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	
	@Path("/ptp/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ptp(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select to_char(actiondate,'YYYY-MM-DD') label, sum(case when upper(ptp) = 'YES' then 1 else 0 end) as value from activitylogs "
					+ "a left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? "
					+ "and upper(b.region||u.branch||owner) like ? group by to_char(actiondate,'YYYY-MM-DD')");
			
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	@Path("/accsworkedon/{searchstring}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accsworkedon(
			@PathParam("searchstring") String searchstring
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select to_char(actiondate,'YYYY-MM-DD') label, count(distinct accountnumber) as value from activitylogs a left join tblusers u on a.owner=u.username left join branches b on b.branchcode = u.branch where to_char(actiondate,'YYYY-MM-DD') between ? and ? and upper(b.region||u.branch||owner) like ? group by to_char(actiondate,'YYYY-MM-DD')");
			query.setString(1, searchstring.substring(0,10).toUpperCase());
			query.setString(2, searchstring.substring(10,20).toUpperCase());
			query.setString(3, "%"+searchstring.substring(20).toUpperCase()+"%");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}

	//view all mcoopcash
	@Path("/divallmcoop")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response divallmcoop() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from q_mcoop ");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close();//close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)conn.close();
		}
		return rb;
	}
	//myallocations mcoopcash
		@Path("/myallocationsmcoop/{username}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response myallocationsmcoop(
				@PathParam("username") String username
				) throws Exception {
			
			PreparedStatement query = null;
			Connection conn = null;
			String returnString = null;
			Response rb = null;
			
			try{
				conn = Oracle308tube.LocalConnection();
				query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
						+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
						+ "from mcoopcash_stage l,mcoopcash_static s where s.LOANACCNUMBER = l.LOANACCNUMBER and upper(COLOFFICER) = ? ");
				query.setString(1, username.toUpperCase());
				ResultSet rs = query.executeQuery();
				
				ToJSON converter = new ToJSON();
				JSONArray json = new JSONArray();
				
				json = converter.toJSONArray(rs);
				query.close();//close connection
				
				returnString = json.toString();
				rb = Response.ok(returnString).build();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(conn != null)conn.close();
			}
			return rb;
		}
		
		//getmcoop mcoopcash
				@Path("/getmcoop/{accnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getmcoop(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						/*query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
								+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
								+ "from mcoopcash_stage l,mcoopcash_static s where s.LOANACCNUMBER = l.LOANACCNUMBER and upper(l.LOANACCNUMBER) = ? ");*/
						
						query = conn.prepareStatement("select * from q_mcoop where LOANACCNUMBER = ?");
						
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//no plans mcoopcash
				@Path("/noplans/{username}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response noplansmcoop(
						@PathParam("username") String username
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
								+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
								+ "from mcoopcash_stage l,mcoopcash_static s where s.LOANACCNUMBER = l.LOANACCNUMBER and s.ACCPLAN is null and upper(s.COLOFFICER) = ? ");
						query.setString(1, username.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//worklist mcoopcash
				@Path("/worklistmcoop/{username}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response worklistmcoop(
						@PathParam("username") String username
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
								+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
								+ "from mcoopcash_stage l,mcoopcash_static s where s.LOANACCNUMBER = l.LOANACCNUMBER and s.REVIEWDATE <= to_char(sysdate,'DD-MM-YYYY') and upper(s.COLOFFICER) = ? ");
						query.setString(1, username.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//todays mcoopcash
				@Path("/todaysmcoop/{username}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response todaysmcoop(
						@PathParam("username") String username
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
								+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
								+ "from mcoopcash_stage l,mcoopcash_static s where s.LOANACCNUMBER = l.LOANACCNUMBER and s.REVIEWDATE = to_char(sysdate,'DD-MM-YYYY') and upper(s.COLOFFICER) = ? ");
						query.setString(1, username.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//getlettersissued

				@Path("/getlettersissued/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getlettersissued(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("SELECT custnumber,letter,owner,actiondate from activitylogs where CUSTNUMBER = ? order by actiondate desc");
						
						query.setString(1, custnumber);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//getsms
				@Path("/getsms/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getsms(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from sms where CUSTNUMBER = ? order by datesent desc");
						query.setString(1, custnumber);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//getallteles
				@Path("/getallteles/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getallteles(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select * from allteles where CUSTNUMBER = ? ");
						query.setString(1, custnumber);
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//get invoice
				
				@Path("/getinvoice/{id}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getinvoice(
						@PathParam("id") String id
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tblinvoices where id = ? ");
						query.setString(1, id);
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getcardnationid/{cardacct}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getcardnationid(
						@PathParam("cardacct") String cardacct
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from cards_stage where cardacct = ? ");
						query.setString(1, cardacct);
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				//card loans
				@Path("/cardnloans/{nationid}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response cardnloans(
						@PathParam("nationid") String nationid
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select * from q_cards_n_loans where nationid = ? ");
						query.setString(1, nationid);
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/multiplediv")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response multiplediv(
						@QueryParam("sqlinner") String sqlinner,
						@QueryParam("sqlouterfinal") String sqlouterfinal
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					//System.out.println(sqlinner);
					//System.out.println(sqlouterfinal);
					
					try{
					    //System.out.print("Select * from (select * from Q_ALL where "+sqlinner+") where"+sqlouterfinal);
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select * from (select * from Q_ALL where "+sqlinner+") where"+sqlouterfinal);
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/employers")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response employers(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from qemployers");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				/*@Path("/watch_stage")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response watch_stage(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from watch_stage ");//where rownum<=10
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}*/
				
				@Path("/cmdccUsers")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response cmdccUsers(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tblusers where upper(division) in ('CMD','CC') and active='Y' ORDER BY username asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getanote/{id}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getanote(
						@PathParam("id") String id
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from notehis where id = ? ");
						query.setString(1, id);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/searchbyid/{nationid}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response searchbyid(
						@PathParam("nationid") String nationid
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from qview where nationid = ? ");
						query.setString(1, nationid);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				//overdue plans mcoopcash
				@Path("/overdueplansmcoop/{username}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response overdueplansmcoop(
						@PathParam("username") String username
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select l.LOANACCNUMBER,LOAN_TYPE,PHONENUMBER,CLIENTNAME,IDNUMBER,ADDRESS,EMPLOYER,LOANAMOUNT,DATEOPEN,LASTPAYMENTDATE,DUEDATE,LOANSTATUS,AMOUNTDISBURSED,INTEREST,DISBURSALDATE,AROCODE,GRADE,ARREARS_CATEGORY,"
								+ "REPAYMENTAMOUNT,EXCUSE_OTHER,EXCUSE,ROUTETOSTATE,REVIEWDATE,CMDSTATUS,BRANCHSTATUS,s.DATERECEIVED,COLOFFICER "
								+ "from mcoopcash_stage l,mcoopcash_static s,qexpiredplans e where l.LOANACCNUMBER = e.ACCNUMBER and s.LOANACCNUMBER = l.LOANACCNUMBER and upper(s.COLOFFICER) = ? ");
						query.setString(1, username.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//getspaccount
				
				@Path("/getspaccount/{sptitle}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getspaccount(
						@PathParam("sptitle") String sptitle
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from sptypes where sptitle = ?");
						query.setString(1, sptitle.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getcmdacc/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getcmdacc(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from (select accnumber,custnumber,outsbalance oustbalance from custview_stage union select CARDNUMBER,CARDACCT,to_number(OUTBALANCE) from qcards) where custnumber = ?");
						query.setString(1, custnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/accountinfo/{accnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response accountinfo(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tbl_q_all where upper(accnumber) = ?");
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				
				
				/*
				 */
				@Path("/getactivity/{accnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getactivity(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tbl_portfolio_static where upper(accnumber) = ?");
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getactivitywatch/{accnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getactivitywatch(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from watch_stage where upper(accnumber) = ?"); //changed from -#- q_watchstage
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					} finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/cardaccinfo/{accnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response cardaccinfo(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from qcards where upper(accnumber) = ?");
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/mcoopwithnationid/{nationid}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response mcoopwithnationid(
						@PathParam("nationid") String nationid
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from MCOOPCASH_STAGE where upper(idnumber) = ?");
						query.setString(1, nationid.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/cardswithnationid/{nationid}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response cardwithnationid(
						@PathParam("nationid") String nationid
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from cards_stage where upper(nationid) = ?");
						query.setString(1, nationid.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/allocateflags")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response allocateflags(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from q_watchstage where allocateflag = 'FLAG' and colofficer is null");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getdepartments")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getdepartments(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tbldepartments");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/allaccounts")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response allaccounts(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select count(*) totalaccounts from q_all");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/allaccounts_branch/{branch}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response allaccounts_branch(
						@PathParam("branch") String branch
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select count(*) totalaccounts from q_all where branchcode = ?");
						query.setString(1, branch.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/allaccounts_division/{division}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response allaccounts_division(
						@PathParam("division") String division
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select count(*) totalaccounts from q_all where upper(section) = ?");
						query.setString(1, division.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					} catch(Exception e){
						e.printStackTrace();
					} finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//
				
				@Path("/totalportfolio")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response totalportfolio(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select count(*) totalaccounts from q_portfolio");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					} catch(Exception e){
						e.printStackTrace();
					} finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//
				
				@Path("/getemployercust/{empcode}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getemployercust(
						@PathParam("empcode") String empcode
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select distinct custnumber from q_watchstage where deptcode = ? ");
						query.setString(1, empcode.toUpperCase());
						ResultSet rs = query.executeQuery();
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getemployer/{empcode}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getemployer(
						@PathParam("empcode") String empcode
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from tblemployers where empcode = ? ");
						query.setString(1, empcode.toUpperCase());
						ResultSet rs = query.executeQuery();
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/getabranch/{branchcode}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response getabranch(
						@PathParam("branchcode") String branchcode
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from branches where upper(branchcode) = ?");
						query.setString(1, branchcode.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/predelinqmyallocations/{colofficer}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response predelinqmyallocations(
						@PathParam("colofficer") String colofficer
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from q_watchstage where upper(colofficer) = ?");
						query.setString(1, colofficer.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/spaccinfo/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response spaccinfo(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from watch_stage where upper(custnumber) = ? order by abs(oustbalance)");
						query.setString(1, custnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/watchstage")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response watchstage(
						@QueryParam("pagenum") int pagenum,
						@QueryParam("pagesize") int pagesize,
						@QueryParam("recordstartindex") int recordstartindex,
						@QueryParam("recordendindex") int recordendindex,
						@QueryParam("filterscount") int filterscount,
						@QueryParam("filtercondition0") String filtercondition0,
						@QueryParam("filterdatafield0") String filterdatafield0,
						@QueryParam("filteroperator0") String filteroperator0,
						@QueryParam("filtervalue0") String filtervalue0,
						@QueryParam("sortdatafield") String sortdatafield,
						@QueryParam("sortorder") String sortorder
						) throws Exception {
					
					PreparedStatement query = null;
					PreparedStatement stmt = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					String sql = null;
					String totalRecords = "0";
					//
					/*System.out.println("pagenum="+pagenum);
					System.out.println("pagesize="+pagesize);
					System.out.println("recordstartindex="+recordstartindex);
					System.out.println("recordendindex="+recordendindex);
					System.out.println("filterscount="+filterscount);
					System.out.print("\n filtercondition = "+filtercondition0);
					System.out.print("\n filterdatafield = "+filterdatafield0);
					System.out.print("\n filteroperator = "+filteroperator0);
					System.out.print("\n filtervalue = "+ filtervalue0);
					System.out.print("\n sortdatafield = "+ sortdatafield);
					System.out.print("\n sortorder = "+ sortorder);*/
					
					String tquery = "select count(accnumber) Total from watch_stage";
					
					if (filterscount > 0){
						String condition = null;
						String value = null;
						switch (filtercondition0){
							case "CONTAINS":
								condition = "LIKE";
								value = "%"+filtervalue0+"%";
								break;
							case "STARTS_WITH":
								condition = "LIKE";
								value = filtervalue0+"%";
								break;
							case "DOES_NOT_CONTAIN":
								condition = " NOT LIKE ";
								value = "%"+filtervalue0+"%";
								break;
							case "EQUAL":
								condition = " = ";
								value = filtervalue0;
								break;
							case "NOT_EQUAL":
								condition = " <> ";
								value = filtervalue0;
								break;
							case "GREATER_THAN":
								condition = " > ";
								value = filtervalue0;
								break;
							case "LESS_THAN":
								condition = " < ";
								value = filtervalue0;
								break;
							case "GREATER_THAN_OR_EQUAL":
								condition = " >= ";
								value = filtervalue0;
								break;
							case "LESS_THAN_OR_EQUAL":
								condition = " <= ";
								value = filtervalue0;
								break;
							case "ENDS_WITH":
								condition = " LIKE ";
								value = "%"+filtervalue0;
								break;
							case "NULL":
								condition = " IS NULL ";
								value = "%"+filtervalue0+"%";
								break;
							case "NOT_NULL":
								condition = " IS NOT NULL ";
								value = "%"+filtervalue0+"%";
								break;
							default:
								condition = " LIKE ";
								value = "%"+filtervalue0+"%";
						}
						String where = " WHERE "+filterdatafield0+" "+condition+" '"+ value.toUpperCase() +"'";
						
						if(sortorder!=null && !sortorder.isEmpty()){
							sql ="SELECT * from (select m.*, rownum r from q_all m "+where+") where r >"+ recordstartindex +" and r < " + recordendindex +"order by "+ sortdatafield +" "+sortorder;
						}else{
							sql ="SELECT * from (select m.*, rownum r from q_all m "+where+") where r >"+ recordstartindex +" and r < " + recordendindex;
						}
						tquery = "SELECT count(accnumber) Total from  q_all " + where;
					}else{
						if(sortorder!=null && !sortorder.isEmpty()){
							sql ="SELECT * from (select m.*, rownum r from q_all m) where r >"+ recordstartindex +" and r < " + recordendindex +" order by "+ sortdatafield +" "+sortorder;
						}else{
							sql ="SELECT * from (select m.*, rownum r from q_all m) where r >"+ recordstartindex +" and r < " + recordendindex;
						}
					}
					//System.out.println(sql);
					try{
						//System.out.println(sql);
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						stmt = conn.prepareStatement(tquery);
						
						ResultSet rs = query.executeQuery();
						ResultSet rsTotal = stmt.executeQuery();
						
						while (rsTotal.next()) {
							totalRecords = rsTotal.getString("Total"); 
						}
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						//returnString = json.toString();
						returnString = "{\"TotalRows\": "+ totalRecords +",\"Rows\":"+json.toString()+"}";
						
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null) conn.close();
					}
					
					return rb;
				}
				
				@Path("/buckets")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response buckets() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket label, sum(oustbalance) value, max(color) color from v_buckets where daysinarr >0 group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/topall")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response topall() throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from (select firstname label, oustbalance value from v_buckets where daysinarr > 0 order by to_number(oustbalance) desc) where rownum <= 10");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//
				@Path("/todaynotes")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response topnotes() throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
				    Date now = new Date();
				    String strDate = sdfDate.format(now);
				    
				    //System.out.println(strDate);
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select fullname label, count(custnumber) value from pnotes where actdate between ? and ? group by fullname");
						query.setString(1, strDate.toUpperCase());
						query.setString(2, strDate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				//
				@Path("/noofaccs_notes")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response noofaccs_notes() throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select fullname label, count(distinct(custnumber)) value from pnotes where actiondate >= sysdate-7 group by fullname");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				//
				@Path("/notestrend/{startdate}/{enddate}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response notestrend(
						@PathParam("startdate") String startdate,
						@PathParam("enddate") String enddate
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select ACTDATE label, count(custnumber) value, max(actiondate) maxactiondate from pnotes where ACTDATE between ? and ? group by ACTDATE order by max(actiondate) asc");
						query.setString(1, startdate.toUpperCase());
						query.setString(2, enddate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/dashbranches/{region}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response dashBbranches(
						@PathParam("region") String region
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(branchcode), branchname FROM V_BUCKETS where region = ?";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, region.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/branches/{region}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response branches(
						@PathParam("region") String region
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(branchcode), branchname from branches where region = ?";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, region.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/dashbranches")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response dashBbranchesx(
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(branchcode), branchname FROM V_BUCKETS where daysinarr > 0 order by branchname asc";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/dasharocodes/{branchcode}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response dasharocodes(
						@PathParam("branchcode") String branchcode
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(arocode), aroname FROM V_BUCKETS where daysinarr > 0 and branchcode = ? order by aroname";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, branchcode.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/dasharocodes")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response dasharocodesx(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(arocode), aroname FROM V_BUCKETS where daysinarr > 0 order by aroname";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/buckets/{searchstring}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response bucketswithsearch(
						@PathParam("searchstring") String searchstring
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket label, sum(oustbalance) value, max(color) color from v_buckets where daysinarr >0 and productcode||region||branchcode||arocode like (?) group by bucket order by max(dsort) asc");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/topall/{searchstring}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response topallwithsearch(
						@PathParam("searchstring") String searchstring
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select * from (select firstname label, oustbalance value from v_buckets where daysinarr > 0 and productcode||region||branchcode||arocode like (?) order by to_number(oustbalance) desc) where rownum <= 10");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/regbuckets")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response regbuckets(
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(region) label from v_buckets where daysinarr > 0";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString).build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/dashgrpbuckets")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response dashgrpbuckets(
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = null;
					
						sql = "select distinct(bucket) label, dsort from v_buckets where daysinarr > 0 order by dsort asc";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/30days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response return30days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '01 - 30 DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/60days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response return60days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '31 - 60 DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/90days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response return90days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '61 - 90 DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/90plusdays")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response return90plusdays() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '90+ DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/120days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response retun120days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '120+ DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/180days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response retun180days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '180+ DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/360days")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response return360days() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select region, sum(oustbalance) value from v_buckets where bucket = '360+ DAYS' group by region");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/central")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response central() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'CENTRAL' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/coast")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response coast() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'COAST' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/nairobieast")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response nairobieast() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'NAIROBI EAST' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/nairobiwest")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response nairobiwest() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'NAIROBI WEST' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/western")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response western() throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'WESTERN' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/riftvalley")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response riftvalley() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'RIFT VALLEY' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/coophse")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response coophse() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'CO-OP HSE & MALL BRS' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/proad")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response proad() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket, sum(oustbalance) value from v_buckets where daysinarr >0 and region = 'P/ROAD & MALL BRS' group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/amntcollected")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response amntcollected() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
				    Date now = new Date();
				    String strDate = sdfDate.format(now);
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select aroname label,sum(recvdamount) value from pamnt where ddate between ? and ? group by aroname");
						query.setString(1, strDate.toUpperCase());
						query.setString(2, strDate.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/amntcollected/{searchstring}/{startdate}/{enddate}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response amntcollectedwithsearch(
						@PathParam("searchstring") String searchstring,
						@PathParam("startdate") String startdate,
						@PathParam("enddate") String enddate
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select aroname label,sum(recvdamount) value from pamnt where arocode||region||branchcode||currency like ? and ddate between ? and ? group by aroname");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						query.setString(2, startdate.toUpperCase());
						query.setString(3, enddate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/productamntcollected/{searchstring}/{startdate}/{enddate}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response productamntcollected(
						@PathParam("searchstring") String searchstring,
						@PathParam("startdate") String startdate,
						@PathParam("enddate") String enddate
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select productcode label,sum(recvdamount) value from pamnt where arocode||region||branchcode||currency like ? and ddate between ? and ? group by productcode");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						query.setString(2, startdate.toUpperCase());
						query.setString(3, enddate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/trendamntcollected")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response trendamntcollected() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select ddate label,sum(recvdamount) value from pamnt where recvddate > to_date('2016-03-01','YYYY-MM-DD') group by ddate order by min(recvddate) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/trendamntcollected/{searchstring}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response trendamntcollectedwithsearch(
						@PathParam("searchstring") String searchstring
						) throws Exception {
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select ddate label,sum(recvdamount) value from pamnt where recvddate > to_date('2016-10-20','YYYY-MM-DD') and arocode||region||branchcode||currency like ? group by ddate order by min(recvddate)");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/bucketsvol")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response bucketsvol() throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket label, count(oustbalance) value from v_buckets where daysinarr >0 group by bucket order by max(dsort) asc");
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}

				@Path("/bucketsvol/{searchstring}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response bucketsvolwithsearch(
						@PathParam("searchstring") String searchstring
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("select bucket label, count(oustbalance) value from v_buckets where daysinarr >0 and productcode||region||branchcode||arocode like (?) group by bucket order by max(dsort) asc");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/sms")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response sms(
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
				    Date now = new Date();
				    String strDate = sdfDate.format(now);
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select fullname label,count(custnumber) value from rsms where SENDDATE between to_date(?,'YYYY-MM-DD') and to_date(?,'YYYY-MM-DD') group by fullname");
						query.setString(1, strDate.toUpperCase());
						query.setString(2, strDate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/sms/{searchstring}/{startdate}/{enddate}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response smswithsearch(
						@PathParam("searchstring") String searchstring,
						@PathParam("startdate") String startdate,
						@PathParam("enddate") String enddate
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("Select * from rsms where searchstring like ? and  SENDDATE between to_date(?,'YYYY-MM-DD') and to_date(?,'YYYY-MM-DD')");
						query.setString(1, "%"+searchstring.toUpperCase()+"%");
						query.setString(2, startdate.toUpperCase());
						query.setString(3, enddate.toUpperCase());
						
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/sppending/{spcode}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response sppending(
						@PathParam("spcode") String spcode
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select s.sptitle label, NVL(p.pending,0) value from sptypes s left join qpending p on s.sptitle=p.serviceprovider where UPPER(s.spcode) = ? order by pending asc";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, spcode.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/{idnumber}/all_loans_withnationid")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response all_loans_withnationid(
						@PathParam("idnumber") String nationid
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select accnumber,custnumber,nationid,custname,branchcode,productcode from watch_stage where UPPER(nationid) = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, nationid.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/{idnumber}/customer_details")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response customer_details(
						@PathParam("idnumber") String nationid
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select custname,custnumber,nationid,dob,addressline1,town from watch_stage  where nationid = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, nationid.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/{accnumber}/loan_details")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response loan_details(
						@PathParam("accnumber") String accnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select accnumber,branchcode,oustbalance,productcode from watch_stage  where accnumber = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, accnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/{custnumber}/all_loans_withcustnumber")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response all_loans_withcustnumber(
						@PathParam("custnumber") String custnumber
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select accnumber,branchcode,oustbalance,productcode from watch_stage  where custnumber = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, custnumber.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/{cardacct}/creditcardinfo")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response creditcardinfo(
						@PathParam("cardacct") String cardacct
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select * from cards_watch_stage  where CARDACCT = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, cardacct.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
						if(query != null)query.close();
					}
					return rb;
				}
				
				
				@Path("/metacc/{custnumber}")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				public Response metacc(
						@PathParam("custnumber") String cardacct
						) throws Exception {
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					String sql = "select Employer, EmpStatus, DoB, EMPDATE, Marital, Phone, Email, Residential, Salary, fileno from tblcard_static  where CARDACCT = ? ";
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement(sql);
						query.setString(1, cardacct.toUpperCase());
						ResultSet rs = query.executeQuery();
						
						ToJSON converter = new ToJSON();
						JSONArray json = new JSONArray();
						
						json = converter.toJSONArray(rs);
						query.close();//close connection
						
						returnString = json.toString();
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin","*")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
					}
					return rb;
				}
				
				@Path("/select_filtered_viewall")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
			public Response returnviewall(@Context UriInfo info) throws Exception {
					
					int pagesize = Integer.parseInt(info.getQueryParameters().getFirst("pagesize"));
					int pagenum = Integer.parseInt(info.getQueryParameters().getFirst("pagenum"));
					String sortdatafield = info.getQueryParameters().getFirst("sortdatafield");
					String sortorder = info.getQueryParameters().getFirst("sortorder");
					int filterscount = Integer.parseInt(info.getQueryParameters().getFirst("filterscount"));
					
					
					String where = "";
					if (filterscount > 0) {
						where = " WHERE (";
						String tmpdatafield = "";
						String tmpfilteroperator = "";
						
						for (Integer i=0; i < filterscount; i++){
							String filtervalue = info.getQueryParameters().getFirst("filtervalue" + i);
							String filtercondition = info.getQueryParameters().getFirst("filtercondition" + i);
							String filterdatafield = info.getQueryParameters().getFirst("filterdatafield" + i);
							String filteroperator = info.getQueryParameters().getFirst("filteroperator" + i);
							
							if (tmpdatafield.equals(""))
							{
								tmpdatafield = filterdatafield;			
							}
							else if (!tmpdatafield.equals(filterdatafield))
							{
								where += ")AND(";
							}
							else if (tmpdatafield.equals(filterdatafield))
							{
								if (tmpfilteroperator.equals("0"))
								{
									where += " AND ";
								}
								else where += " OR ";	
							}
								
							// build the "WHERE" clause depending on the filter's condition, value and datafield.
							switch(filtercondition){
							case "CONTAINS":
								where += " " + filterdatafield + " LIKE '%" + filtervalue + "%'";
								break;
							case "CONTAINS_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN":
								where += " " + filterdatafield + " NOT LIKE '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
								where += " " + filterdatafield + " NOT LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "EQUAL":
								where += " " + filterdatafield + " = '" + filtervalue + "'";
								break;
							case "EQUAL_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "'";
								break;
							case "NOT_EQUAL":
								where += " " + filterdatafield + " NOT LIKE '" + filtervalue + "'";
								break;
							case "NOT_EQUAL_CASE_SENSITIVE":
								where += " " + filterdatafield + " NOT LIKE BINARY '" + filtervalue + "'";
								break;
							case "GREATER_THAN":
								where += " " + filterdatafield + " > '" + filtervalue + "'";
								break;
							case "LESS_THAN":
								where += " " + filterdatafield + " < '" + filtervalue + "'";
								break;
							case "GREATER_THAN_OR_EQUAL":
								where += " " + filterdatafield + " >= '" + filtervalue + "'";
								break;
							case "LESS_THAN_OR_EQUAL":
								where += " " + filterdatafield + " <= '" + filtervalue + "'";
								break;
							case "STARTS_WITH":
								where += " " + filterdatafield + " LIKE '" + filtervalue + "%'";
								break;
							case "STARTS_WITH_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "%'";
								break;
							case "ENDS_WITH":
								where += " " + filterdatafield + " LIKE '%" + filtervalue + "'";
								break;
							case "ENDS_WITH_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "'";
								break;
							case "NULL":
								where += " " + filterdatafield + " IS NULL";
								break;
							case "NOT_NULL":
								where += " " + filterdatafield + " IS NOT NULL";
								break;
							}
							if (i == filterscount - 1)
							{
								where += ")";
							}
								
							tmpfilteroperator = filteroperator;
							tmpdatafield = filterdatafield;
						}
					}
					String orderby = "";
					if (sortdatafield != null && sortorder != null && (sortorder.equals("asc") || sortorder.equals("desc")))
					{
						orderby = "order by " + sortdatafield + " " + sortorder;
					}
					Integer start = pagesize * pagenum;
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("SELECT COUNT(accnumber) AS Count from tbl_q_all"	+ where);
						
						ResultSet rs = query.executeQuery();
						String totalRecords = "";
						while (rs.next()) {
							totalRecords = rs.getString("Count");
						}
						rs.close();
						String sql = "SELECT NVL(client_name,0) client_name,NVL(accnumber,0) accnumber,NVL(custnumber,0) custnumber,NVL(oustbalance,0) oustbalance,NVL(productcode,0) productcode,"
								+ "NVL(daysinarr,0) daysinarr,NVL(totalarrears,0) totalarrears,NVL(bucket,0) bucket,NVL(branchcode,0) branchcode, NVL(arocode,0) arocode,"
								+ "NVL(region,0) region,NVL(section,0) section,NVL(colofficer,0) colofficer,NVL(branchname,0) branchname from tbl_q_all " + where + " " + orderby + " offset ? rows fetch next ? rows only";
						
						//System.out.println("==SQLRUNNINGLOCAL===="+ sql );
						//System.out.println("==start===="+ start );
						//System.out.println("==pagesize===="+ pagesize );
						
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setInt(1, start);
						stmt.setInt(2, pagesize);
						
						ResultSet accounts = stmt.executeQuery();
						
						boolean totalRecordsAdded = false;
						// format returned ResultSet as a JSON array
						JsonArray recordsArray = new JsonArray();
						while (accounts != null && accounts.next()) {
							JsonObject currentRecord = new JsonObject();
							currentRecord.add("ACCNUMBER",	new JsonPrimitive(accounts.getString("ACCNUMBER")));
							currentRecord.add("CUSTNUMBER", new JsonPrimitive(accounts.getString("CUSTNUMBER")));
							currentRecord.add("BRANCHCODE", new JsonPrimitive(accounts.getString("BRANCHCODE")));
							currentRecord.add("BRANCHNAME", new JsonPrimitive(accounts.getString("BRANCHNAME")));
							currentRecord.add("AROCODE", new JsonPrimitive(accounts.getString("AROCODE")));
							currentRecord.add("CLIENT_NAME", new JsonPrimitive(accounts.getString("CLIENT_NAME")));
							currentRecord.add("OUSTBALANCE", new JsonPrimitive(accounts.getString("OUSTBALANCE")));
							currentRecord.add("TOTALARREARS", new JsonPrimitive(accounts.getString("TOTALARREARS")));
							currentRecord.add("DAYSINARR", new JsonPrimitive(accounts.getString("DAYSINARR")));
							currentRecord.add("BUCKET", new JsonPrimitive(accounts.getString("BUCKET")));
							currentRecord.add("PRODUCTCODE", new JsonPrimitive(accounts.getString("PRODUCTCODE")));
							currentRecord.add("REGION", new JsonPrimitive(accounts.getString("REGION")));
							currentRecord.add("SECTION", new JsonPrimitive(accounts.getString("SECTION")));
							currentRecord.add("COLOFFICER", new JsonPrimitive(accounts.getString("COLOFFICER")));
							if (totalRecordsAdded == false) {
								currentRecord.add("totalRecords", new JsonPrimitive(totalRecords));
								totalRecordsAdded = true;
							}
							recordsArray.add(currentRecord);
						}

						accounts.close();
						
						returnString = recordsArray.toString();
						
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
						if(query != null)query.close();
					}
					return rb;
				}

				
				@Path("/select_filtered_test")
				@GET
				@Produces(MediaType.APPLICATION_JSON)
			public Response returntest(@Context UriInfo info) throws Exception {
					
					int pagesize = Integer.parseInt(info.getQueryParameters().getFirst("pagesize"));
					int pagenum = Integer.parseInt(info.getQueryParameters().getFirst("pagenum"));
					String sortdatafield = info.getQueryParameters().getFirst("sortdatafield");
					String sortorder = info.getQueryParameters().getFirst("sortorder");
					int filterscount = Integer.parseInt(info.getQueryParameters().getFirst("filterscount"));
					
					
					String where = "";
					if (filterscount > 0) {
						where = " WHERE (";
						String tmpdatafield = "";
						String tmpfilteroperator = "";
						
						for (Integer i=0; i < filterscount; i++){
							String filtervalue = info.getQueryParameters().getFirst("filtervalue" + i);
							String filtercondition = info.getQueryParameters().getFirst("filtercondition" + i);
							String filterdatafield = info.getQueryParameters().getFirst("filterdatafield" + i);
							String filteroperator = info.getQueryParameters().getFirst("filteroperator" + i);
							
							if (tmpdatafield.equals(""))
							{
								tmpdatafield = filterdatafield;			
							}
							else if (!tmpdatafield.equals(filterdatafield))
							{
								where += ")AND(";
							}
							else if (tmpdatafield.equals(filterdatafield))
							{
								if (tmpfilteroperator.equals("0"))
								{
									where += " AND ";
								}
								else where += " OR ";	
							}
								
							// build the "WHERE" clause depending on the filter's condition, value and datafield.
							switch(filtercondition){
							case "CONTAINS":
								where += " " + filterdatafield + " LIKE '%" + filtervalue + "%'";
								break;
							case "CONTAINS_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN":
								where += " " + filterdatafield + " NOT LIKE '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
								where += " " + filterdatafield + " NOT LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "EQUAL":
								where += " " + filterdatafield + " = '" + filtervalue + "'";
								break;
							case "EQUAL_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "'";
								break;
							case "NOT_EQUAL":
								where += " " + filterdatafield + " NOT LIKE '" + filtervalue + "'";
								break;
							case "NOT_EQUAL_CASE_SENSITIVE":
								where += " " + filterdatafield + " NOT LIKE BINARY '" + filtervalue + "'";
								break;
							case "GREATER_THAN":
								where += " " + filterdatafield + " > '" + filtervalue + "'";
								break;
							case "LESS_THAN":
								where += " " + filterdatafield + " < '" + filtervalue + "'";
								break;
							case "GREATER_THAN_OR_EQUAL":
								where += " " + filterdatafield + " >= '" + filtervalue + "'";
								break;
							case "LESS_THAN_OR_EQUAL":
								where += " " + filterdatafield + " <= '" + filtervalue + "'";
								break;
							case "STARTS_WITH":
								where += " " + filterdatafield + " LIKE '" + filtervalue + "%'";
								break;
							case "STARTS_WITH_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '" + filtervalue + "%'";
								break;
							case "ENDS_WITH":
								where += " " + filterdatafield + " LIKE '%" + filtervalue + "'";
								break;
							case "ENDS_WITH_CASE_SENSITIVE":
								where += " " + filterdatafield + " LIKE BINARY '%" + filtervalue + "'";
								break;
							case "NULL":
								where += " " + filterdatafield + " IS NULL";
								break;
							case "NOT_NULL":
								where += " " + filterdatafield + " IS NOT NULL";
								break;
							}
							if (i == filterscount - 1)
							{
								where += ")";
							}
								
							tmpfilteroperator = filteroperator;
							tmpdatafield = filterdatafield;
						}
					}
					String orderby = "";
					if (sortdatafield != null && sortorder != null && (sortorder.equals("asc") || sortorder.equals("desc")))
					{
						orderby = "order by " + sortdatafield + " " + sortorder;
					}
					Integer start = pagesize * pagenum;
					
					PreparedStatement query = null;
					Connection conn = null;
					String returnString = null;
					Response rb = null;
					
					try{
						conn = Oracle308tube.LocalConnection();
						query = conn.prepareStatement("SELECT COUNT(accnumber) AS Count from tbl_q_all"	+ where);
						
						ResultSet rs = query.executeQuery();
						String totalRecords = "";
						while (rs.next()) {
							totalRecords = rs.getString("Count");
						}
						rs.close();
						String sql = "SELECT NVL(client_name,0) client_name,NVL(accnumber,0) accnumber,NVL(custnumber,0) custnumber,NVL(oustbalance,0) oustbalance,NVL(productcode,0) productcode,"
								+ "NVL(daysinarr,0) daysinarr,NVL(totalarrears,0) totalarrears,NVL(bucket,0) bucket,NVL(branchcode,0) branchcode,"
								+ "NVL(region,0) region,NVL(section,0) section,NVL(colofficer,0) colofficer,NVL(BRANCHNAME,0) branchname,NVL(arocode,0) arocode from tbl_q_all " + where + " " + orderby + " offset ? rows fetch next ? rows only";
						
						//System.out.println("==SQLRUNNINGLOCAL===="+ sql );
						//System.out.println("==start===="+ start );
						//System.out.println("==pagesize===="+ pagesize );
						
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setInt(1, start);
						stmt.setInt(2, pagesize);
						
						ResultSet accounts = stmt.executeQuery();
						
						boolean totalRecordsAdded = false;
						// format returned ResultSet as a JSON array
						JsonArray recordsArray = new JsonArray();
						while (accounts != null && accounts.next()) {
							JsonObject currentRecord = new JsonObject();
							currentRecord.add("ACCNUMBER",	new JsonPrimitive(accounts.getString("ACCNUMBER")));
							currentRecord.add("CUSTNUMBER", new JsonPrimitive(accounts.getString("CUSTNUMBER")));
							currentRecord.add("BRANCHCODE", new JsonPrimitive(accounts.getString("BRANCHCODE")));
							currentRecord.add("BRANCHNAME", new JsonPrimitive(accounts.getString("BRANCHNAME")));
							currentRecord.add("AROCODE", new JsonPrimitive(accounts.getString("AROCODE")));
							currentRecord.add("CLIENT_NAME", new JsonPrimitive(accounts.getString("CLIENT_NAME")));
							currentRecord.add("OUSTBALANCE", new JsonPrimitive(accounts.getString("OUSTBALANCE")));
							currentRecord.add("TOTALARREARS", new JsonPrimitive(accounts.getString("TOTALARREARS")));
							currentRecord.add("DAYSINARR", new JsonPrimitive(accounts.getString("DAYSINARR")));
							currentRecord.add("BUCKET", new JsonPrimitive(accounts.getString("BUCKET")));
							currentRecord.add("PRODUCTCODE", new JsonPrimitive(accounts.getString("PRODUCTCODE")));
							currentRecord.add("REGION", new JsonPrimitive(accounts.getString("REGION")));
							currentRecord.add("SECTION", new JsonPrimitive(accounts.getString("SECTION")));
							currentRecord.add("COLOFFICER", new JsonPrimitive(accounts.getString("COLOFFICER")));
							if (totalRecordsAdded == false) {
								currentRecord.add("totalRecords", new JsonPrimitive(totalRecords));
								totalRecordsAdded = true;
							}
							recordsArray.add(currentRecord);
						}

						accounts.close();
						
						returnString = recordsArray.toString();
						
						rb = Response.ok(returnString)
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.build();
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(conn != null)conn.close();
						if(query != null)query.close();
					}
					return rb;
				}

}
