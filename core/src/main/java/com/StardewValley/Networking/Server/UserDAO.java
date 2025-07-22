package com.StardewValley.Networking.Server;

import com.StardewValley.Models.Enums.Question;
import com.StardewValley.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final Gson gson = new Gson();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    username TEXT PRIMARY KEY,
                    password TEXT,
                    nickname TEXT,
                    email TEXT,
                    gender TEXT,
                    question TEXT,
                    answer TEXT,
                    games_money TEXT,
                    avatar_path TEXT
                );
                """;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(User user) {
        String sql = "INSERT INTO users (username, password, nickname, email, gender, question, answer, games_money, avatar_path) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getGender());
            pstmt.setString(6, user.getQuestion() == null ? null : user.getQuestion().name());
            pstmt.setString(7, user.getAnswer());
            pstmt.setString(8, gson.toJson(user.getGamesMoney()));
            pstmt.setString(9, user.getAvatarPath());

            pstmt.executeUpdate();
            System.out.println("User inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("gender"),
                    rs.getString("answer"),
                    gson.fromJson(rs.getString("games_money"), type),
                    rs.getString("avatar_path")
                );

                String questionName = rs.getString("question");
                if (questionName != null) {
                    user.setQuestion(Question.getQuestionByName(questionName));
                }

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
