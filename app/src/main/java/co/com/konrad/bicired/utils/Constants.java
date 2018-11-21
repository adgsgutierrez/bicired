package co.com.konrad.bicired.utils;

public class Constants {

    public static String TAG_ERROR = "APP_CRASH";
    public static String TAG_LOG = "APP_CONTROL";
    public static String PLATAFORMA = "B";


    private static String ENDPOINT = "http://bicired.dxexperience.com/";
    public static String URL_LOGIN = ENDPOINT + "back_end/usuario/";
    public static String URL_PUBLICACION = ENDPOINT + "back_end/publicacion/";
    public static String URL_FOTOS = ENDPOINT + "back_end/publicacion/subirfoto.php";
    public static String VIEW_FOTOS = ENDPOINT +"back_end/publicacion/";

    public static int SERVICES_OK = 200;

    public static String PREFERENCE_USER = "US_PREF";
    public static String PREFERENCE_USER_DATA = "US_PREF_DATA";
    public static String PREFERENCE_USER_IS_LOGIN = "US_PREF_LOGIN";

    public static String PARAMETRO_EVENTO = "EVT";
    public static String PARAMETRO_LATITUD_ORIGEN = "PLATOR";
    public static String PARAMETRO_LONGITUD_ORIGEN = "PLONOR";

    public static String PARAMETRO_LATITUD_DESTINO = "PLATOD";
    public static String PARAMETRO_LONGITUD_DESTINO = "PLONOD";

    public static String PARAMETRO_RUTA = "ParameRuta";

    public static String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`\\\\{|\\\\}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    public static String EMAIL_REG = "^[a-zA-Z0-9.!#$%&'*+/=?^_`\\{|\\}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static String TEXT_REGEX = "^[A-Za-z0-9\\s]+$";

}
