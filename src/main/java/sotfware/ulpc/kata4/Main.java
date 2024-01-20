package sotfware.ulpc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:chinook.db")){
            SqliteTrackLoader loader = new SqliteTrackLoader(connection);
            List<Track> tracks = loader.loadAll();
            for (Track track : tracks){
                System.out.println(track);

            }

        }
    }
}