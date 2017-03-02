package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ScheduleController() {
		super();
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		MysqlDataSource ds = null;
		Connection connect = null;
		Statement statement = null;
		try { 
			ds = new MysqlDataSource();
			ds.setUrl("jdbc:mysql://localhost:3306/mydb?useSSL=false");
			ds.setUser("root");
			ds.setPassword("admin123");
			connect = ds.getConnection();

			// Create the statement to be used to get the results.
			statement = connect.createStatement();
			String query = "SELECT * FROM schedule";
			JSONArray scheduleJsonArray = new JSONArray();
			// Execute the query and get the result set.
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				JSONObject scheduleEntry = new JSONObject();
				try {
					scheduleEntry.put("subject",resultSet.getString("subject"));
					scheduleEntry.put("days",resultSet.getString("days"));
					scheduleEntry.put("time",resultSet.getString("time"));
					scheduleEntry.put("instructor",resultSet.getString("prof"));
					scheduleEntry.put("location",resultSet.getString("class_room"));
					scheduleJsonArray.put(scheduleEntry);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			response.setContentType("application/json");
			response.getWriter().write(scheduleJsonArray.toString());
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			// Close the connection and release the resources used.
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { connect.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
}