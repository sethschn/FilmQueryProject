package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		// String sql = "SELECT id, first_name, last_name FROM staff WHERE store_id =
		// ?";
		String sql = "SELECT * FROM actor WHERE actor.id = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, actorId);
		ResultSet rs = pst.executeQuery();
		Actor actorObj = null;
		if (rs.next()) {
			actorObj = new Actor();
			actorObj.setId(rs.getInt("id"));
			actorObj.setFirstName(rs.getString("first_name"));
			actorObj.setLastName(rs.getString("last_name"));
		}
		rs.close();
		pst.close();

		conn.close();
		return actorObj;
	}

	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actorList = new ArrayList();
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		// String sql = "SELECT id, first_name, last_name FROM staff WHERE store_id =
		// ?";
		String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id join film on film_actor.film_id = film.id where film.id = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, filmId);
		ResultSet rs = pst.executeQuery();
		Actor actorObj = null;
		while (rs.next()) {
			actorObj = new Actor();
			actorObj.setId(rs.getInt("id"));
			actorObj.setFirstName(rs.getString("first_name"));
			actorObj.setLastName(rs.getString("last_name"));
			actorList.add(actorObj);
		}
		rs.close();
		pst.close();

		conn.close();

		return actorList;

	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		// String sql = "SELECT id, first_name, last_name FROM staff WHERE store_id =
		// ?";
		String sql = "SELECT * FROM film WHERE film.id = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, filmId);
		ResultSet rs = pst.executeQuery();
		Film filmObj = null;
		if (rs.next()) {
			filmObj = new Film();
			filmObj.setDescription(rs.getString("description"));
			filmObj.setTitle(rs.getString("title"));
			filmObj.setId(rs.getInt("id"));
			filmObj.setLanguageId(rs.getInt("language_id"));
			filmObj.setLanguage(findLanguageById(filmId));
			filmObj.setLength(rs.getInt("length"));
			filmObj.setRating(rs.getString("rating"));
			filmObj.setReleaseYear(rs.getInt("release_year"));
			filmObj.setRentalDuration(rs.getInt("rental_duration"));
			filmObj.setRentalRate(rs.getDouble("rental_rate"));
			filmObj.setReplacementCost(rs.getDouble("replacement_cost"));
			filmObj.setSpecialFeatures(rs.getString("special_features"));
			filmObj.setActorList(findActorsByFilmId(filmId));
		}
		rs.close();
		pst.close();

		conn.close();
		return filmObj;
	}
	
	@Override
	public String findLanguageById(int filmId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		// String sql = "SELECT id, first_name, last_name FROM staff WHERE store_id =
		String sql = "SELECT language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, filmId);
		ResultSet rs = pst.executeQuery();
		String languageName = "";
		if (rs.next()) {
			languageName = rs.getString("language.name");
		}
		rs.close();
		pst.close();

		conn.close();
		return languageName;
		
	}

	@Override
	public List<Film> findFilmBySearchKeyword(String searchWord) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		// select * from film where film.title LIKE "% zorro %";
		// String sql = "SELECT id, first_name, last_name FROM staff WHERE store_id =
		String sql = "select film.id from film where film.title LIKE ? OR film.description LIKE ? ";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%" + searchWord + "%");
		pst.setString(2, "%" + searchWord + "%");
		ResultSet rs = pst.executeQuery();
		List<Film> filmList = new ArrayList<Film>();
		while (rs.next()) {
			Film filmObj = findFilmById(rs.getInt("id"));
			filmList.add(filmObj);

		}
		rs.close();
		pst.close();

		conn.close();
		return filmList;
	}

}
