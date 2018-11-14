package es.unex.asee.proyectoasee.database.Entities.Comics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ComicData")
public class ComicData {

    public ComicData(@NonNull Integer id, @NonNull String name, @NonNull String thumbnailPath, @NonNull String thumbnailExtension) {
        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "thumbnailPath")
    private String thumbnailPath;

    @NonNull
    @ColumnInfo(name = "thumbnailExtension")
    private String thumbnailExtension;


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(@NonNull String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @NonNull
    public String getThumbnailExtension() {
        return thumbnailExtension;
    }

    public void setThumbnailExtension(@NonNull String thumbnailExtension) {
        this.thumbnailExtension = thumbnailExtension;
    }
}
