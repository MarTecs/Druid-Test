package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * Ҫʵ�ֵ���ģʽ����֤ȫ��ֻ��һ�����ݿ����ӳ�
 */
public class DBPoolConnection {
	static Logger log = Logger.getLogger(DBPoolConnection.class);
	private static DBPoolConnection dbPoolConnection = null;
	private static DruidDataSource druidDataSource = null;

	static {
		Properties properties = loadPropertiesFile("db_server.properties");
		try {
			druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties); // DruidDataSrouce����ģʽ
		} catch (Exception e) {
			log.error("��ȡ����ʧ��");
		}
	}

	/**
	 * ���ݿ����ӳص���
	 * 
	 * @return
	 */
	public static synchronized DBPoolConnection getInstance() {
		if (null == dbPoolConnection) {
			dbPoolConnection = new DBPoolConnection();
		}
		return dbPoolConnection;
	}

	/**
	 * ����druid���ݿ�����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DruidPooledConnection getConnection() throws SQLException {
		return druidDataSource.getConnection();
	}

	/**
	 * @param string
	 *            �����ļ���
	 * @return Properties����
	 */
	private static Properties loadPropertiesFile(String fullFile) {
		String webRootPath = null;
		if (null == fullFile || fullFile.equals("")) {
			throw new IllegalArgumentException("Properties file path can not be null" + fullFile);
		}
		webRootPath = DBPoolConnection.class.getClassLoader().getResource("").getPath();
		webRootPath = new File(webRootPath).getParent();
		InputStream inputStream = null;
		Properties p = null;
		try {
			inputStream = new FileInputStream(new File(webRootPath + File.separator + fullFile));
			p = new Properties();
			p.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return p;
	}

}
