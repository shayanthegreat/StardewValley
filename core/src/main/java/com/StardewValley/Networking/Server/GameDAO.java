package com.StardewValley.Networking.Server;

import com.StardewValley.Networking.Common.GameDetails;
import com.StardewValley.Networking.Common.PlayerDetails;
import com.google.gson.*;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

public class GameDAO {
    private static final String DB_URL = "jdbc:sqlite:games.db";

    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    try {
                        java.lang.reflect.Field field = f.getDeclaringClass().getDeclaredField(f.getName());

                        if (f.getDeclaringClass() == GameDetails.class) {
                            return f.getName().equals("connections") ||
                                    f.getName().equals("chat") ||
                                    f.getName().equals("isRunning");
                        }

                        if (f.getDeclaringClass() == PlayerDetails.class) {
                            // Never skip fields in PlayerDetails, including 'data'
                            return false;
                        }

                        // For all other classes, skip transient fields
                        return java.lang.reflect.Modifier.isTransient(field.getModifiers());

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
        .create();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS games (
                    game_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    admin_username TEXT,
                    game_json TEXT
                );
                """;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertGame(GameDetails game) {
        String sql = "INSERT INTO games (admin_username, game_json) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, game.getAdminUsername());

            String json = gson.toJson(game);
            pstmt.setString(2, json);

            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int generatedId = keys.getInt(1);
                game.setGameId(generatedId); // Set gameId in object
                return generatedId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // failed
    }

    public static GameDetails getGameById(int id) {
        String sql = "SELECT game_json FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String json = rs.getString("game_json");
                GameDetails game = gson.fromJson(json, GameDetails.class);
                game.setDefaultReadies();
                return game;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<GameDetails> getAllGames() {
        ArrayList<GameDetails> games = new ArrayList<>();
        String sql = "SELECT game_json FROM games";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String json = rs.getString("game_json");
                GameDetails game = gson.fromJson(json, GameDetails.class);
                games.add(game);
                game.setDefaultReadies();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public static boolean deleteGame(int id) {
        String sql = "DELETE FROM games WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int getFirstAvailableId() {
        String sql = "SELECT MAX(game_id) AS max_id FROM games";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1; // first available id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean updateGame(GameDetails game) {
        String sql = "UPDATE games SET admin_username = ?, game_json = ? WHERE game_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getAdminUsername());
            String json = gson.toJson(game); // serialize with your exclusion strategy
            pstmt.setString(2, json);
            pstmt.setInt(3, game.getGameId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
