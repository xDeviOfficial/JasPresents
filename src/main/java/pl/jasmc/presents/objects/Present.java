package pl.jasmc.presents.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.presents.enums.PresentType;

public class Present implements Cloneable{

    private String name;
    private PresentType type;
    private Location location;
    private String textureID;
    private int id;


    public Present(String name, PresentType type, Location location, int id) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.id = id;



        switch(type) {
            case ORANGE:
                this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVmMGVkOWQ1ODY1ZjE5Nzc5NzMxZjhhYWI1YTg5YzJkYjJjNjkyZWQyN2JkNGFhNGE5MjgyMmQ1MDc2ZGZmNyJ9fX0=";
                break;
            case GREEN:
                this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA5NjcwZDM1MWQ3ODU3ZjE0N2JiYmEzMTViMWE1OWNiMzJjY2EyNzk4MDQyY2FlYTBhMWMxOGE3YTBmMzE4MSJ9fX0=";
                break;
            case BLUE: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhlYmQ4Yzc4NTg1NzViYzlhOTIwN2FkZTRlYWMxZGIwNGQ2OWI5MDllMzYwMGE3MDBkYTZhNzZhM2Q5ZmYyZSJ9fX0=";
                break;
            case PINK: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwNzczN2Q4NzY2YjBhZTQyZjI3NDEzMTg0Zjg0ZWJiMjVlNTQwOWI0MmY0NDhjZDY4YmExYWRjYTI5OTUwMyJ9fX0=";
                break;
            case AQUA: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVmNDFhMTAzODUzNmQ5NDkyMDIzMzJjZmMyZDcxYTI0YjhlZWEzMDFhMjc4NWQ1MjA2YTNhMThmMWVkYWIwNiJ9fX0=";
                break;
            case CYAN: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg0NDljZmQ5ZTZiNDRiZWNkZGNhNjIxN2ZmNzBiODIwNDIxYjZiMzJhYjc1YTBiNDc5M2M3ZTViZTdhIn19fQ==";
                break;
            case GRAY: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYyYjNmN2NkYjU5Mzk0YTBjODJmZTY3ZTY3Mjg0MmQ3NTVjZThlOGVkZGFlZWI5YjVjYzE3M2I1NTAxZThkNiJ9fX0=";
                break;
            case BLACK: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3MTJiMTk3MWM1ZjQyZWVmZjgwNTUxMTc5MjIwYzA4YjgyMTNlYWNiZTZiYzE5ZDIzOGMxM2Y4NmUyYzAifX19";
                break;
            case BROWN: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc4ZjFhODU4ZDY2YjllNzk1MWY3MGFjZWEyYzE5YWI2YzBhZjg4Y2E1ZGI1MTZmMWExZmY1MWYwNmIyYyJ9fX0=";
                break;
            case PURPLE: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJmODFhNDFlOTMxMmM1M2NkZjgwMGRiZDM1NmI1YzllMzY3NjRlMTZmMzc2MDMxYzgzODA3NDFmNWYwIn19fQ==";
                break;
            case YELLOW: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM4NjUyYmZkYjdhZGRlMTI4ZTdlYWNjNTBkMTZlYjlmNDg3YTMyMDliMzA0ZGUzYjk2OTdjZWJmMTMzMjNiIn19fQ==";
                break;
            case MAGENTA: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRjYWFmZDIzM2QzYWZkNGI2ZjIxMzJjNjNhNjk0ZDAxMmJhZDZkOTIzMzE2YjNhYTVjMzc2OGZlZTMzMzkifX19";
                break;
            case LIGHT_BLUE: this.textureID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRlMWM0MmYxMTM4M2I5ZGM4ZTY3ZjI4NDZmYTMxMWIxNjMyMGYyYzJlYzdlMTc1NTM4ZGJmZjFkZDk0YmI3In19fQ==";
                break;
            default:
                this.textureID = "";
                break;

        }
    }

    public String getName() {
        return name;
    }

    public PresentType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getTextureID() {
        return textureID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PresentType type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTextureID(String textureID) {
        this.textureID = textureID;
    }

    public Object clone()throws CloneNotSupportedException{
        return (Present)super.clone();
    }


}
