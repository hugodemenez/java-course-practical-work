package fr.isen.java2.db.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Genre;

import static fr.isen.java2.db.daos.DataSourceFactory.getDataSource;

public class GenreDao {

	public List<Genre> listGenres() {
		List<Genre> listofGenres = new ArrayList<>();

		try(Connection connection = getDataSource().getConnection()){
			try(Statement statement = connection.createStatement()){
				try(ResultSet results = statement.executeQuery("SELECT * FROM genre")){
					while (results.next()){
						Genre genre = new Genre(
								results.getInt("idgenre"),
								results.getString("name"));
						listofGenres.add(genre);
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return listofGenres;
	}

	public Genre getGenre(String name) {


		try(Connection connection = getDataSource().getConnection()){
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE name = ?")){
				statement.setString(1,name);
				try(ResultSet results = statement.executeQuery()){
					if (results.next()){
						return new Genre(
								results.getInt("idgenre"),
								results.getString("name"));

					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void addGenre(String name) {
		try (Connection connection = getDataSource().getConnection()){
			String sqlQuery = "insert into genre(name) VALUES(?)";

			try(PreparedStatement statement = connection.prepareStatement(
					sqlQuery,Statement.RETURN_GENERATED_KEYS)){
				statement.setString(1,name);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
