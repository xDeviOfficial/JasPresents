package pl.jasmc.presents.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.presents.enums.PresentType;

public class Present {

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
            case CYAN: this.textureID = "";
                break;
            case GRAY: this.textureID = "";
                break;
            case BLACK: this.textureID = "";
                break;
            case BROWN: this.textureID = "";
                break;
            case PURPLE: this.textureID = "";
                break;
            case YELLOW: this.textureID = "";
                break;
            case MAGENTA: this.textureID = "";
                break;
            case LIGHT_BLUE: this.textureID = "";
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


}
