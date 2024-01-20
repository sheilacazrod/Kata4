package sotfware.ulpc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader {
    private final Connection connection;
    private String queryAllSql = "SELECT tracks.name As track, composer, milliseconds, title, genres.name As genre, artists.name as artist FROM tracks, albums, artists, genres WHERE " +
            "tracks.AlbumId = albums.AlbumId AND " +
            "tracks.GenreId = genres.GenreId AND " +
            "albums.ArtistId = artists.ArtistId";

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> loadAll() {
        try {
            return load(resultOfSet(queryAllSql));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        while (resultSet.next()){
            tracks.add(trackFrom(resultSet));
        }
        return tracks;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return  new Track(
                resultSet.getString("track"),
                resultSet.getString("composer"),
                resultSet.getInt("milliseconds"),
                resultSet.getString("title"),
                resultSet.getString("genre"),
                resultSet.getString("artist")
        );
    }

    private ResultSet resultOfSet(String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
