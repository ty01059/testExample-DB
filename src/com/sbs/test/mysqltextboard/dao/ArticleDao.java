package com.sbs.test.mysqltextboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;

public class ArticleDao {

	private Connection con;

	public ArticleDao() {
		con = null;
	}

	public List<Map<String, Object>> getArticles() {

		SecSql sql = new SecSql();
		sql.append("select *");
		sql.append("from article");
		sql.append("order by id desc");

		List<Map<String, Object>> articles = MysqlUtil.selectRows(sql);

		return articles;
	}

	public int add(String title, String body, int memberId) {

		SecSql sql = new SecSql();
		sql.append("insert into article");
		sql.append("SET regdate = NOW()");
		sql.append("updatedate = NOW()");
		sql.append("title = ?", title);
		sql.append("`body` = ?", body);
		sql.append("memberId = ?", memberId);
		sql.append("boardId = ?", memberId);

		int id = MysqlUtil.insert(sql);
		return id;
	}

	public Article update(int index) {
		String sql = "update article ";
		sql += "set updatedate = NOW() ";
		sql += "where id = ?";

		Article article = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, index);

			pstmt.execute();

			article = getArticle(index);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return article;
	}

	public void modify(int index, String title, String body, int memberId) {

		String sql = "update article ";
		sql += "SET title = ?, ";
		sql += "`body` = ?, ";
		sql += "updatedate = NOW() ";
		sql += "where id = ? and memberId = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, index);
			pstmt.setInt(4, memberId);

			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Article getArticle(int index) {
		String sql = "select * from article ";
		sql += "where id = ?";

		Article article = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updatedate"),
						rs.getString("title"), rs.getString("body"), rs.getInt("memberId"), rs.getInt("boardId"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return article;
	}

	public void delete(int index) {

		String sql = "delete from article ";
		sql += "where id = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
