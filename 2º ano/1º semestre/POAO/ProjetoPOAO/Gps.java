/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.io.Serializable;

/**
 *
 * @author filip
 */
public final class Gps implements Serializable {
    private double coordenadasLatitude;
    private double coordenadasLongitude;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Gps.
     * 
     */
    public Gps() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Gps.
     * 
     * @param coordenadasLatitude
     * @param coordenadasLongitude 
     */
    public Gps(double coordenadasLatitude, double coordenadasLongitude) {
        this.coordenadasLatitude = coordenadasLatitude;
        this.coordenadasLongitude = coordenadasLongitude;
    }
    
    /**
     * Gets coordenadas latitude.
     * 
     * @return 
     */
    public double getCoordenadasLatitude() {
        return coordenadasLatitude;
    }
    
    /**
     * Sets coordenadas latitude.
     * 
     * @param coordenadasLatitude 
     */
    public void setCoordenadasLatitude(double coordenadasLatitude) {
        this.coordenadasLatitude = coordenadasLatitude;
    }
    
    /**
     * Gets coordenadas longitude.
     * 
     * @return 
     */
    public double getCoordenadasLongitude() {
        return coordenadasLongitude;
    }

    /**
     * Sets coordenadas longitude.
     * 
     * @param coordenadasLongitude 
     */
    public void setCoordenadasLongitude(double coordenadasLongitude) {
        this.coordenadasLongitude = coordenadasLongitude;
    }

    @Override
    public String toString() {
        return coordenadasLatitude + "/" + coordenadasLongitude + " (Latitude/Longitude)";
    }
}
