package com.youtube.rest.status;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.*;
import com.youtube.util.ToJSON;
import java.util.Date;

@Path("/accplans")
public class accplans_status {
	
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
	
	@Path("/planid/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response planid(
			@PathParam("planid") String planid
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plalnid where planid = ? " );
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/background/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response background2(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_background where upper(planid) = ? and deleted = 'N' order by DATEUPDATED desc" );
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/background/{custnumber}/curren")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response background(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_background where planid = ? and curren = 'Y' order by dateupdated desc ");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/background/{id}/current")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response makecurrentbackground(
			@PathParam("id") String id
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_background where id = ? ");
			query.setString(1, id.toUpperCase());
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

	@Path("/problemdefinition/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response problemdefinition2(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_problemdefinition where planid = ? and deleted = 'N' order by DATEUPDATED desc" );
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/problemdefinition/{custnumber}/current")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response problemdefinition(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_problemdefinition where planid = ? and deleted = 'N' and curren = 'Y' order by dateupdated desc");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}

	@Path("/swot/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response swot(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_swot where planid = ? and deleted = 'N' order by DATEUPDATED desc" );
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/swot/{custnumber}/curren")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response swot2(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_swot where planid = ? and curren = 'Y' order by dateupdated desc");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/abilitytopay/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response abilitytopay(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_ability where planid = ? and deleted = 'N' order by DATEUPDATED desc");
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/remedialofferings/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getremedialofferings(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_remedialofferings where planid = ? and deleted = 'N' order by DATEUPDATED desc");
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/abilitytopay/{custnumber}/curren")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response abilitytopay2(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_ability where planid = ? and curren = 'Y' order by dateupdated desc");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}

	@Path("/customerproposals/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerproposals(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_customerproposals where planid = ? and deleted = 'N' order by DATEUPDATED desc" );
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/customerproposals/{custnumber}/current")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerproposals2(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_customerproposals where planid = ? and deleted = 'N' and curren = 'Y' order by dateupdated desc");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}

	@Path("/bankproposals/{planid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response bankproposals(
			@PathParam("planid") String planid
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_bankproposals where planid = ? and deleted = 'N' order by DATEUPDATED desc" );
			
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/bankproposals/{custnumber}/current")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response bankproposals2(
			@PathParam("custnumber") String custnumber
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_bankproposals where planid = ? and deleted = 'N' and curren = 'Y' order by dateupdated desc");
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
		return rb;
	}
	
	@Path("/planlogs/{custnumber}/{planname}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response planlogs(
			@PathParam("custnumber") String custnumber,
			@PathParam("planname") String planname
			) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;

		try {
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("select * from plan_logs where planid = ? and planname = ? ");
			query.setString(1, custnumber.toUpperCase());
			query.setString(2, planname.toUpperCase());
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

	@Path("/planid/{custnumber}/{owner}/update")
	public class planid {
		
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnplanid(
				@PathParam("custnumber") String custnumber,
				@PathParam("owner") String owner
				)
						throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
		    	ps = con.prepareStatement("update planid set lastupdate = ?, updateby = ? where planid = ?");
	            ps.setString(1, dateFormat.format(date));
	            ps.setString(2, owner);
	            ps.setString(3, custnumber);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}

	}
	
	@Path("/background/{id}/{custnumber}/curren")
	public class background {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@PathParam("custnumber") String custnumber,
				@PathParam("id") String id
				)
						throws Exception {
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_background set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_background set curren = 'Y' where id = ?");
	            ps2.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}

	}
	
	// actions start
	@Path("/initiation")
	public class initiation {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@PathParam("custnumber") String custnumber,
				@PathParam("accnumber") String accnumber
				)
						throws Exception {
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			System.out.println(accnumber);
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_background set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_background set curren = 'Y' where id = ?");
	            ps2.setString(1, accnumber);
	            
	            // rs = ps.executeUpdate();
	            // rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}

	}
	
	@Path("/background/{id}/delete")
	public class background_delete {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@PathParam("id") String id
				)
						throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("update plan_background set deleted = 'Y', curren = 'N' where id = ?");
	            ps.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
	}

	@Path("/problemdefinition/{id}/delete")
	public class problemdefinition_delete {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnproblemdefinition(
				@PathParam("id") String id
				)
						throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("update plan_problemdefinition set deleted = 'Y', curren = 'N' where id = ?");
	            ps.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}


	@Path("/swot/{id}/delete")
	public class swot_delete {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnproblemdefinition(
				@PathParam("id") String id
				)throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("update plan_swot set deleted = 'Y', curren = 'N' where id = ?");
	            ps.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}	

	@Path("/swot/{id}/{custnumber}/curren")
	public class swot {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnswot(
				@PathParam("custnumber") String custnumber,
				@PathParam("id") String id
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_swot set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_swot set curren = 'Y' where id = ?");
	            ps2.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	
	@Path("/abilitytopay/{id}/{custnumber}/curren")
	public class abilitytopay {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnabilitytopay(
				@PathParam("custnumber") String custnumber,
				@PathParam("id") String id
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_ability set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_ability set curren = 'Y' where id = ?");
	            ps2.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
		

	@Path("/customerproposals/{id}/{custnumber}/curren")
	public class customerproposals {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returncustomerproposals(
				@PathParam("custnumber") String custnumber,
				@PathParam("id") String id
				)
						throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_customerproposals set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_customerproposals set curren = 'Y' where id = ?");
	            ps2.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	
	@Path("/bankproposals/{id}/{custnumber}/curren")
	public class bankproposals {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbankproposals (
				@PathParam("custnumber") String custnumber,
				@PathParam("id") String id
				)
						throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				//reset all to curren N
				ps = con.prepareStatement("update plan_bankproposals set curren = 'N' where planid = ?");
	            ps.setString(1, custnumber);
	            //update curren to Y
		    	ps2 = con.prepareStatement("update plan_bankproposals set curren = 'Y' where id = ?");
	            ps2.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            rs = ps2.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	
	@Path("/bankproposals")
	public class bankproposals_add {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbankproposals(
				@FormParam("planid") String planid,
				@FormParam("accnumber") String accnumber,
				@FormParam("bankproposals") String bankproposals,
				@FormParam("owner") String owner
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("insert into plan_bankproposals(planid,custnumber,accnumber,bankproposals,owner,dateupdated,curren) values(?,?,?,?,?,?,?)");
	            ps.setString(1, planid);
	            ps.setString(2, planid);
	            ps.setString(3, accnumber);
	            ps.setString(4, bankproposals);
	            ps.setString(5, owner);
	            ps.setString(6, dateFormat.format(date));
	            ps.setString(7, "Y");
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	

	@Path("/customerproposals/{id}/delete")
	public class customerproposals_delete {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@PathParam("id") String id
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("update plan_customerproposals set deleted = 'Y', curren = 'N' where id = ?");
	            ps.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	
	@Path("/bankproposals/{id}/delete")
	public class bankproposals_delete {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@PathParam("id") String id
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("update plan_bankproposals set deleted = 'Y', curren = 'N' where id = ?");
	            ps.setString(1, id);
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}
	
	@Path("/planlogs")
	public class planlogs_add {
		@POST
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		public Response returnbackground(
				@FormParam("planid") String planid,
				@FormParam("planname") String planname,
				@FormParam("details") String details,
				@FormParam("owner") String owner
				) throws Exception {
			
			String retunx = null;
			String returnString = null;
			PreparedStatement ps = null;
			Connection con = null;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			int rs = 0;
			try{
				con = Oracle308tube.LocalConnection();
				ps = con.prepareStatement("insert into plan_logs(planid,planname,details,owner,dateupdated) values(?,?,?,?,?)");
	            ps.setString(1, planid);
	            ps.setString(2, planname);
	            ps.setString(3, details);
	            ps.setString(4, owner);
	            ps.setString(5, dateFormat.format(date));
	            
	            rs = ps.executeUpdate();
	            
	            if(rs>0){
	            	retunx = "OK";
	            }else{
	            	retunx = "update unsuccessful";
	            }
			    
				returnString = retunx;
				
			}catch(Exception e){
				e.printStackTrace();
				retunx = "Error";
				returnString = retunx;
			}
			return Response.ok(returnString).build();
		}
	}

	
	@Path("/paymentplans/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response paymentplans(
			@PathParam("custnumber") String planid
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_ptpplans where custnumber = ? order by dateupdated desc" );
			query.setString(1, planid.toUpperCase());
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
	
	@Path("/actionplans/{custnumber}/{planaction}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response actionplans(
			@PathParam("custnumber") String custnumber,
			@PathParam("planaction") String planaction
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT * from plan_actions_logs where custnumber = ? and actionagreed = ?" );
			query.setString(1, custnumber);
			query.setString(2, planaction);
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
	
	
	@Path("/actionplansoverdue/{custnumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response actionplansoverdue(
			@PathParam("custnumber") String custnumber
			) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = Oracle308tube.LocalConnection();
			query = conn.prepareStatement("SELECT count(*) overdueactions from plan_actions_logs where custnumber = ? and nextreview <= sysdate and completed = 'N'" );
			query.setString(1, custnumber);
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
}
