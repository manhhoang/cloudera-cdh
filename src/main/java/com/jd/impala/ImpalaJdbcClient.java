package com.jd.impala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImpalaJdbcClient {
	// static String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
	static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	// private static final String CONNECTION_URL =
	// "jdbc:impala://ec2-54-151-149-245.ap-southeast-1.compute.amazonaws.com:21050/gkadmin;principal=impala/ip-10-167-7-239.ap-southeast-1.compute.internal@EXAMPLE.COM";

	private static final String CONNECTION_URL = "jdbc:hive2://ec2-54-151-149-245.ap-southeast-1.compute.amazonaws.com:21050/gkadmin;principal=impala/ip-10-167-7-239.ap-southeast-1.compute.internal@EXAMPLE.COM";

	// private static final String CONNECTION_URL =
	// "jdbc:impala://54.251.55.194:21050/gkadmin";

	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "gss-jaas.conf");
		System.setProperty("java.security.krb5.conf", "krb5.conf");
		System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
		System.setProperty("sun.security.krb5.debug", "false");

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from emstestevents order by 'timestamp' DESC limit 100";
		// String query = "SHOW TABLES";
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(CONNECTION_URL);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(String.valueOf(rs.getLong(1)) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));
				// System.out.println(String.valueOf(rs.getString(1)));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Perform clean up
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException se1) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException se4) {
			}
		}
	}
}