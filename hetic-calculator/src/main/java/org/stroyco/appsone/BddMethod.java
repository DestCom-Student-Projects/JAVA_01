package org.stroyco.appsone;

public class BddMethod {
  public static String db_url = ConfigReader.getProperties("databaseString");
  public static String db_user = ConfigReader.getProperties("databaseUser");
  public static String db_password = ConfigReader.getProperties("databasePassword");
}
