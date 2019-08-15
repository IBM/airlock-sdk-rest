package com.ibm.app.services;

import com.ibm.airlock.rest.model.Product;
import com.ibm.airlock.sdk.AbstractMultiProductManager;
import io.apptik.json.JsonElement;
import io.apptik.json.generator.JsonGenerator;
import io.apptik.json.generator.JsonGeneratorConfig;
import io.apptik.json.schema.Schema;
import io.apptik.json.schema.SchemaV4;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.LogManager;

@RunWith(Parameterized.class)

public class AirlockProductsCreationInParallelTest extends BaseServiceTest {


    protected static final String PRODUCT_NAME_PREFIX = "ProductName_";
    protected static final String DEFAULTS_NAME = "AirlockProductsCreationStressTest.json";
    protected static final int ATTEMPTS = 50;
    protected static final int MAX_THREADS_PRE_SEC = 5;
    protected static int productsNumber = 100;
    protected static int uniqueProductSufix = 0;
    protected int requestPerSecond;
    protected static List<String> productsNames = new ArrayList<>();
    protected static List<String> productInstances = new ArrayList<>();
    protected int requestsCounter = 0;
    protected static int totalRequestsCounter = 0;
    protected static long totalRequestProcessingTime = 0;
    protected String uniqueProductName;
    protected static Object lock = new Object();
    private static String context;


    protected String getDefaultFileName() {
        return DEFAULTS_NAME;
    }

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection getRequestsPerMinutesArgs() {
        LinkedList<Integer> requestPerSecondArgs = new LinkedList<>();
        for (int i = 0; i < ATTEMPTS; i++) {
            int requestsNumber = (int) (Math.random() * MAX_THREADS_PRE_SEC);
            requestPerSecondArgs.add(requestsNumber == 0 ? 1 : requestsNumber);
        }
        return requestPerSecondArgs;
    }

    @BeforeClass
    public static void createAllProducts() {

        context = "{\"device\": {\"locale\": \"S\", \"localeCountryCode\": \"kNmqW\", \"localeLanguage\": \"skNRQjCrOFJmj\", \"osLanguage\": \"xVjrFvBS\", \"appVersion\": \"EEbOV\", \"appFlavor\": \"Vh\", \"daysSinceAppInstall\": 1226851663, \"screenWidth\": 1.599437592738898E9, \"screenHeight\": 8.326400529673008E8, \"version\": \"OjgWeDU\", \"osVersion\": 982281219, \"hockeyUserID\": \"LxiYsvsQ\", \"isLowPowerMode\": false, \"datetime\": \"AUmFAuryOOjn\", \"screenSize\": \"DLMBQoOdWWsJsH\", \"manufacturer\": \"DKFJTgGIqIFHg\", \"connectionType\": \"LUYhVcoNwNLd\"}, \"viewedLocation\": {\"lat\": 1771054588, \"lon\": 1.77116411780901E9, \"dmaCode\": \"YbLi\", \"region\": \"CDrhhjQAtQsNP\", \"country\": \"C\"}, \"userLocation\": {\"lat\": 138515685, \"lon\": 3.2614304226199543E8, \"region\": \"WK\", \"dmaCode\": \"\", \"country\": \"\"}, \"weatherSummary\": {\"precip\": [{\"eventType\": 1712053688, \"imminence\": 472861316, \"severity\": 423240970, \"startTime\": \"GjIEVs\", \"endTime\": \"\"}, {\"eventType\": 1507124778, \"imminence\": 1005446470, \"severity\": 1758916944, \"startTime\": \"UJSrHo\", \"endTime\": \"ijDaXa\"}, {\"eventType\": 160823110, \"imminence\": 940860064, \"severity\": 740265127, \"startTime\": \"kr\", \"endTime\": \"Qcn\"}, {\"eventType\": 126663839, \"imminence\": 1419065031, \"severity\": 2014725619, \"startTime\": \"FnCvnXHqQJfP\", \"endTime\": \"jIv\"}, {\"eventType\": 1119857587, \"imminence\": 1673551808, \"severity\": 217599895, \"startTime\": \"yLQyTEa\", \"endTime\": \"oIVlCcIWM\"}, {\"eventType\": 561449483, \"imminence\": 138540097, \"severity\": 1743243800, \"startTime\": \"ElTqGvjexGrfM\", \"endTime\": \"VMlnewtyIAHn\"}, {\"eventType\": 1187877166, \"imminence\": 245087632, \"severity\": 1294081714, \"startTime\": \"\", \"endTime\": \"gdJQBfmw\"}, {\"eventType\": 652974020, \"imminence\": 157987598, \"severity\": 188107496, \"startTime\": \"gXSSbsuXKNwlP\", \"endTime\": \"IwnJrM\"}, {\"eventType\": 1944052317, \"imminence\": 1788521186, \"severity\": 915237961, \"startTime\": \"wGcUe\", \"endTime\": \"klp\"}, {\"eventType\": 503808415, \"imminence\": 668440669, \"severity\": 1245265545, \"startTime\": \"fnxJVOWfEQfLtN\", \"endTime\": \"MTjLbXpFsY\"} ], \"observation\": {\"feelsLikeTemperature\": 676331910, \"temperature\": 745802576, \"skyCode\": 374010225, \"dayPart\": \"PVqgnsRy\", \"phrase\": \"PiOoiNDemEGv\", \"obsTime\": \"XWyDWKXdocMob\", \"nextSunrise\": \"bvUWTlebtp\", \"nextSunset\": \"JesCgOJYOxy\", \"basedGPS\": true }, \"lifeStyleIndices\": {\"drivingDifficultyIndex\": 1277373113 }, \"contentMode\": {\"mode\": \"MnHlOcvKFfhlfU\", \"effectiveDateTime\": \"dOIQSVIdInh\", \"eventName\": \"uEfPCErdO\"}, \"nearestSnowAccumulation\": {\"snowRange\": \"B\", \"dayPart\": \"GFnubl\"}, \"tomorrowForecast\": {\"day\": {\"snowRange\": \"fekSKOSb\", \"precipPercentage\": 1364930893, \"precipAmt\": 1500093897, \"temperature\": 1534168669, \"precipType\": \"csfBoEspOXbJ\", \"thunderEnum\": 1042277712, \"dayPartTitle\": \"\", \"windDirection\": \"mrMbGfwa\", \"windSpeed\": \"HxrLxkamFh\", \"windDegrees\": 1758813630, \"uvIndex\": 1906506516, \"humidity\": 1378360012, \"cloudCover\": 1780960700, \"dayPart\": \"RFRUEXJ\"}, \"night\": {\"snowRange\": \"yTBSE\", \"precipPercentage\": 364652853, \"precipType\": \"l\", \"thunderEnum\": 1413710159, \"precipAmt\": 1965037060, \"temperature\": 845700887, \"dayPartTitle\": \"mP\", \"windDirection\": \"qeaGUJHIMyLSP\", \"windSpeed\": \"FKrJXFwNM\", \"windDegrees\": 1172087045, \"uvIndex\": 288117744, \"humidity\": 588473887, \"cloudCover\": 225972530, \"dayPart\": \"YiqeAMdn\"} }, \"todayForecast\": {\"day\": {\"snowRange\": \"\", \"precipPercentage\": 1843222137, \"precipType\": \"vvk\", \"precipAmt\": 1897695734, \"temperature\": 826494183, \"thunderEnum\": 189872830, \"dayPartTitle\": \"\", \"windDirection\": \"VkDCsTUIDq\", \"windSpeed\": \"BLePns\", \"windDegrees\": 1788326891, \"uvIndex\": 1342971328, \"humidity\": 1106552358, \"cloudCover\": 263997929, \"dayPart\": \"NT\"}, \"night\": {\"snowRange\": \"FTKgsJeFKl\", \"precipPercentage\": 1328743837, \"precipType\": \"gmI\", \"thunderEnum\": 694613372, \"precipAmt\": 92369741, \"temperature\": 732300157, \"dayPartTitle\": \"Xg\", \"windDirection\": \"f\", \"windSpeed\": \"ESSTp\", \"windDegrees\": 1227796498, \"uvIndex\": 321392516, \"humidity\": 2005136142, \"cloudCover\": 928300174 } }, \"dailyForecasts\": [{\"day\": {\"snowRange\": \"K\", \"precipPercentage\": 913768733, \"precipType\": \"Tgg\", \"precipAmt\": 420598587, \"temperature\": 983613924, \"thunderEnum\": 623153281, \"dayPartTitle\": \"\", \"icon\": 1826952695, \"iconExtended\": 1743439450, \"windDirection\": \"\", \"windSpeed\": \"wd\", \"windDegrees\": 591421577, \"uvIndex\": 1941026708, \"humidity\": 1776353857, \"cloudCover\": 78867574 }, \"night\": {\"snowRange\": \"CmgAI\", \"precipPercentage\": 1790389476, \"precipType\": \"TfwqeCXljMbDRP\", \"thunderEnum\": 1527987990, \"precipAmt\": 1331342502, \"temperature\": 326089548, \"dayPartTitle\": \"aFEcrvt\", \"icon\": 1322547739, \"iconExtended\": 1706616583, \"windDirection\": \"LJDXM\", \"windSpeed\": \"RhYje\", \"windDegrees\": 790196066, \"uvIndex\": 165427654, \"humidity\": 1739191797, \"cloudCover\": 936350896 } }, {\"day\": {\"snowRange\": \"\", \"precipPercentage\": 1163616799, \"precipType\": \"iPrTPDSieXm\", \"precipAmt\": 1200322926, \"temperature\": 1740920790, \"thunderEnum\": 1343198072, \"dayPartTitle\": \"wXMcJrJEk\", \"icon\": 451588606, \"iconExtended\": 16207800, \"windDirection\": \"mJPEwhJaD\", \"windSpeed\": \"oNn\", \"windDegrees\": 614245554, \"uvIndex\": 319397180, \"humidity\": 1804539175, \"cloudCover\": 1869544845 }, \"night\": {\"snowRange\": \"dC\", \"precipPercentage\": 2024678340, \"precipType\": \"R\", \"thunderEnum\": 1145447509, \"precipAmt\": 417830821, \"temperature\": 430806877, \"dayPartTitle\": \"qCdG\", \"icon\": 1361320409, \"iconExtended\": 2137854480, \"windDirection\": \"AlRvXQiHhwjGsi\", \"windSpeed\": \"j\", \"windDegrees\": 733380055, \"uvIndex\": 675941535, \"humidity\": 181302867, \"cloudCover\": 227093559 } }, {\"day\": {\"snowRange\": \"mtKAWyVd\", \"precipPercentage\": 126871770, \"precipType\": \"\", \"precipAmt\": 257882512, \"temperature\": 757194583, \"thunderEnum\": 898321652, \"dayPartTitle\": \"iOwd\", \"icon\": 1063251691, \"iconExtended\": 1638790860, \"windDirection\": \"BXTEWH\", \"windSpeed\": \"gyvPdwqlP\", \"windDegrees\": 318154943, \"uvIndex\": 815993959, \"humidity\": 1497160598, \"cloudCover\": 536757205 }, \"night\": {\"snowRange\": \"yHQFs\", \"precipPercentage\": 1274379940, \"precipType\": \"dwUjnsEMEDcqK\", \"thunderEnum\": 1610163217, \"precipAmt\": 624603674, \"temperature\": 1580664813, \"dayPartTitle\": \"QnKTy\", \"icon\": 81759053, \"iconExtended\": 101840268, \"windDirection\": \"XTyK\", \"windSpeed\": \"FNTEuprQyq\", \"windDegrees\": 1425252571, \"uvIndex\": 1106509468, \"humidity\": 779850330, \"cloudCover\": 13279677 } }, {\"day\": {\"snowRange\": \"DvAwHLwKdnJEke\", \"precipPercentage\": 316926717, \"precipType\": \"mctKxeaKSmUx\", \"precipAmt\": 1648248258, \"temperature\": 808474612, \"thunderEnum\": 489701005, \"dayPartTitle\": \"yqPWnaEIoybi\", \"icon\": 1750703454, \"iconExtended\": 361617734, \"windDirection\": \"CVkmu\", \"windSpeed\": \"YvmvrR\", \"windDegrees\": 1151863387, \"uvIndex\": 1560475444, \"humidity\": 1604095427, \"cloudCover\": 712103090 }, \"night\": {\"snowRange\": \"n\", \"precipPercentage\": 606043561, \"precipType\": \"hXcVvKmk\", \"thunderEnum\": 564232167, \"precipAmt\": 1814788077, \"temperature\": 1468056518, \"dayPartTitle\": \"nvdhyXW\", \"icon\": 1759801355, \"iconExtended\": 95411092, \"windDirection\": \"h\", \"windSpeed\": \"UdAkqHCXJTrj\", \"windDegrees\": 1906166117, \"uvIndex\": 1379473006, \"humidity\": 304702566, \"cloudCover\": 1596432026 } }, {\"day\": {\"snowRange\": \"JcLcRPpYsm\", \"precipPercentage\": 1732703529, \"precipType\": \"jIKA\", \"precipAmt\": 1812645065, \"temperature\": 1644118387, \"thunderEnum\": 549569161, \"dayPartTitle\": \"\", \"icon\": 534644425, \"iconExtended\": 1123511704, \"windDirection\": \"GaWaponI\", \"windSpeed\": \"IhL\", \"windDegrees\": 1869414087, \"uvIndex\": 946076753, \"humidity\": 1033893124, \"cloudCover\": 1680818491 }, \"night\": {\"snowRange\": \"TugfBDH\", \"precipPercentage\": 1542382339, \"precipType\": \"mcIadSdSVr\", \"thunderEnum\": 277211564, \"precipAmt\": 1791299035, \"temperature\": 1216719599, \"dayPartTitle\": \"ASRJpKp\", \"icon\": 1453310802, \"iconExtended\": 322092858, \"windDirection\": \"XXCbfAOwX\", \"windSpeed\": \"NiQOngHaOlfjK\", \"windDegrees\": 1782744650, \"uvIndex\": 68612840, \"humidity\": 733544383, \"cloudCover\": 505705522 } }, {\"day\": {\"snowRange\": \"roDthsuQb\", \"precipPercentage\": 1910975037, \"precipType\": \"EaMmlQsJasGfoh\", \"precipAmt\": 1966318802, \"temperature\": 1908861600, \"thunderEnum\": 1066490387, \"dayPartTitle\": \"ehbJBSbDFBPjw\", \"icon\": 460023087, \"iconExtended\": 236696076, \"windDirection\": \"nkOKcVPbUAb\", \"windSpeed\": \"HvOdWrQNnLdn\", \"windDegrees\": 957055277, \"uvIndex\": 1792992793, \"humidity\": 1284562176, \"cloudCover\": 1267230095 }, \"night\": {\"snowRange\": \"WixBWmeoKcVv\", \"precipPercentage\": 340020706, \"precipType\": \"WYE\", \"thunderEnum\": 1875169517, \"precipAmt\": 1159741656, \"temperature\": 1800014918, \"dayPartTitle\": \"tRVchIQJPLX\", \"icon\": 499075854, \"iconExtended\": 224303242, \"windDirection\": \"VkXeVoExhtiy\", \"windSpeed\": \"BubXuTwVk\", \"windDegrees\": 1064290744, \"uvIndex\": 1424033760, \"humidity\": 623536793, \"cloudCover\": 1182697107 } }, {\"day\": {\"snowRange\": \"WhQQFFmH\", \"precipPercentage\": 995765260, \"precipType\": \"KxilcnFIVop\", \"precipAmt\": 1286800986, \"temperature\": 1622288694, \"thunderEnum\": 337958238, \"dayPartTitle\": \"AYeGj\", \"icon\": 1241867931, \"iconExtended\": 1607987757, \"windDirection\": \"vl\", \"windSpeed\": \"IOFLEJtkfDPMRG\", \"windDegrees\": 603149824, \"uvIndex\": 1343400056, \"humidity\": 1382388366, \"cloudCover\": 1983417229 }, \"night\": {\"snowRange\": \"PlUTpWHeOPQ\", \"precipPercentage\": 551136410, \"precipType\": \"AJSUXThchqlwo\", \"thunderEnum\": 479516008, \"precipAmt\": 896193334, \"temperature\": 483609965, \"dayPartTitle\": \"RUtrsAdwsakA\", \"icon\": 757609261, \"iconExtended\": 1776927681, \"windDirection\": \"ot\", \"windSpeed\": \"COOxghEqMfbKU\", \"windDegrees\": 218019119, \"uvIndex\": 1303508833, \"humidity\": 170933613, \"cloudCover\": 1281392847 } }, {\"day\": {\"snowRange\": \"jQBTGC\", \"precipPercentage\": 950419674, \"precipType\": \"\", \"precipAmt\": 1388993022, \"temperature\": 1343390247, \"thunderEnum\": 1977454249, \"dayPartTitle\": \"Q\", \"icon\": 1381224671, \"iconExtended\": 636322558, \"windDirection\": \"vu\", \"windSpeed\": \"mdenJbi\", \"windDegrees\": 852390125, \"uvIndex\": 1045084212, \"humidity\": 821293372, \"cloudCover\": 2119107159 }, \"night\": {\"snowRange\": \"BxesEsrvMM\", \"precipPercentage\": 824079197, \"precipType\": \"yWEwNeeWdmXRF\", \"thunderEnum\": 2013090495, \"precipAmt\": 900403570, \"temperature\": 652455045, \"dayPartTitle\": \"tUgIgeuyFxPYS\", \"icon\": 1201225398, \"iconExtended\": 773837061, \"windDirection\": \"JonVu\", \"windSpeed\": \"ktPBQdOCrLRr\", \"windDegrees\": 1356583079, \"uvIndex\": 1288012278, \"humidity\": 1111883249, \"cloudCover\": 793402999 } }, {\"day\": {\"snowRange\": \"kYVVPHYUKYUu\", \"precipPercentage\": 1217210631, \"precipType\": \"KEsGoQyr\", \"precipAmt\": 1702508066, \"temperature\": 472141193, \"thunderEnum\": 981970844, \"dayPartTitle\": \"xRnnfBswGg\", \"icon\": 1991907602, \"iconExtended\": 521361509, \"windDirection\": \"wRVgwsa\", \"windSpeed\": \"rUICGCGl\", \"windDegrees\": 1308597731, \"uvIndex\": 1484043276, \"humidity\": 1999210243, \"cloudCover\": 1607651155 }, \"night\": {\"snowRange\": \"DJTAqSGDKs\", \"precipPercentage\": 1192304054, \"precipType\": \"Lb\", \"thunderEnum\": 977045037, \"precipAmt\": 1442928152, \"temperature\": 874423419, \"dayPartTitle\": \"GjPowEDCvtqk\", \"icon\": 473517787, \"iconExtended\": 402217385, \"windDirection\": \"UNlsO\", \"windSpeed\": \"IeLSnvBavwxys\", \"windDegrees\": 754865132, \"uvIndex\": 211402176, \"humidity\": 1883394416, \"cloudCover\": 1782322400 } }, {\"day\": {\"snowRange\": \"PhXgEwEREpvyff\", \"precipPercentage\": 1515825843, \"precipType\": \"bViwJrEO\", \"precipAmt\": 1505460505, \"temperature\": 555715774, \"thunderEnum\": 93264900, \"dayPartTitle\": \"YrlIcVDyJpUVPl\", \"icon\": 995564662, \"iconExtended\": 632006336, \"windDirection\": \"hfJHwWuhqSKQ\", \"windSpeed\": \"SjCd\", \"windDegrees\": 859655434, \"uvIndex\": 1804263717, \"humidity\": 72342546, \"cloudCover\": 1125235539 }, \"night\": {\"snowRange\": \"VFqGeYKeWQ\", \"precipPercentage\": 1217447529, \"precipType\": \"wEIFiodLXEne\", \"thunderEnum\": 1100511246, \"precipAmt\": 1661897352, \"temperature\": 2035190270, \"dayPartTitle\": \"WpsVciwA\", \"icon\": 532370457, \"iconExtended\": 547842030, \"windDirection\": \"qTSR\", \"windSpeed\": \"G\", \"windDegrees\": 295004880, \"uvIndex\": 1760711268, \"humidity\": 1242333675, \"cloudCover\": 1751629725 } } ], \"runWeatherIndex\": [{\"dayIndicator\": \"wYWIYTrgrhPa\", \"longRunWeatherIndex\": 1499075045, \"shortRunWeatherIndex\": 1914728022, \"localDateTime\": \"YYGTfh\", \"temperature\": 1587065325, \"dewPoint\": 52612144, \"windSpeed\": 342524078, \"cloudCover\": 137890073, \"precipChance\": 361815908 }, {\"dayIndicator\": \"WOvUNqkva\", \"longRunWeatherIndex\": 1115359568, \"shortRunWeatherIndex\": 150815974, \"localDateTime\": \"VuAt\", \"temperature\": 1900602438, \"dewPoint\": 663605103, \"windSpeed\": 1506054487, \"cloudCover\": 720625136, \"precipChance\": 1854631912 }, {\"dayIndicator\": \"KQGMCeXCVuXfWo\", \"longRunWeatherIndex\": 1787747701, \"shortRunWeatherIndex\": 1812946699, \"localDateTime\": \"auKgTySAwMv\", \"temperature\": 1960890990, \"dewPoint\": 1902906383, \"windSpeed\": 2016493921, \"cloudCover\": 1896906305, \"precipChance\": 1281778951 }, {\"dayIndicator\": \"ibigbVgMqwbcNw\", \"longRunWeatherIndex\": 844247688, \"shortRunWeatherIndex\": 168526851, \"localDateTime\": \"wFryBIFuvXbit\", \"temperature\": 295351727, \"dewPoint\": 1979964025, \"windSpeed\": 1198364714, \"cloudCover\": 1573243499, \"precipChance\": 290182917 }, {\"dayIndicator\": \"KXuQPh\", \"longRunWeatherIndex\": 1856607073, \"shortRunWeatherIndex\": 1495206818, \"localDateTime\": \"dXhCJl\", \"temperature\": 253308755, \"dewPoint\": 770537493, \"windSpeed\": 55148462, \"cloudCover\": 256135962, \"precipChance\": 844133093 }, {\"dayIndicator\": \"nn\", \"longRunWeatherIndex\": 1540123729, \"shortRunWeatherIndex\": 2122578787, \"localDateTime\": \"wmPtxKdkl\", \"temperature\": 950511055, \"dewPoint\": 1941279423, \"windSpeed\": 885443632, \"cloudCover\": 359590645, \"precipChance\": 172379670 }, {\"dayIndicator\": \"S\", \"longRunWeatherIndex\": 255308628, \"shortRunWeatherIndex\": 935543050, \"localDateTime\": \"E\", \"temperature\": 903315284, \"dewPoint\": 1083113456, \"windSpeed\": 79371338, \"cloudCover\": 1120258201, \"precipChance\": 654308746 }, {\"dayIndicator\": \"ShlnOodY\", \"longRunWeatherIndex\": 1733609188, \"shortRunWeatherIndex\": 1229363150, \"localDateTime\": \"r\", \"temperature\": 17486721, \"dewPoint\": 1233267544, \"windSpeed\": 1080437642, \"cloudCover\": 1263465197, \"precipChance\": 847302585 }, {\"dayIndicator\": \"TnMVDQtwCnOKk\", \"longRunWeatherIndex\": 1829645517, \"shortRunWeatherIndex\": 1406936530, \"localDateTime\": \"\", \"temperature\": 869962577, \"dewPoint\": 495929305, \"windSpeed\": 1188778935, \"cloudCover\": 1767542299, \"precipChance\": 1680680029 }, {\"dayIndicator\": \"jOBqbpGxtnmAX\", \"longRunWeatherIndex\": 124533406, \"shortRunWeatherIndex\": 539455074, \"localDateTime\": \"WGATSxRHiIo\", \"temperature\": 1569362337, \"dewPoint\": 126940042, \"windSpeed\": 763672788, \"cloudCover\": 2029785578, \"precipChance\": 1867536653 } ] } }";

        Arrays.stream(LogManager.getLogManager().getLogger("").getHandlers()).forEach(h -> h.setLevel(Level.SEVERE));
    }

    //executed before each test
    @Before
    @Override
    public void resetAndInit() {
        List<Product> products = client.target(serverUrl + "/products/").request().get(List.class);

        if (products.size() == 0) {
            ExecutorService threads = Executors.newFixedThreadPool(MAX_THREADS_PRE_SEC);
            List<Callable<Boolean>> toRun = new ArrayList(MAX_THREADS_PRE_SEC);
            for (int index = 0; index < productsNumber; index++) {
                toRun.add(new AirlockProductsCreationInParallelTest.ProductCreateRequestSender());
            }

            // all tasks executed in different threads, at 'once'.
            try {
                threads.invokeAll(toRun);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }
        }

        GenericType<List<Product>> gType = new GenericType<List<Product>>() {};

        products = client.target(serverUrl + "/products/").request().get(gType);
        for (Product product : products) {
            productInstances.add(product.getInstanceId());
        }
    }


    public AirlockProductsCreationInParallelTest(int requestPerSecond) {
        this.uniqueProductName = PRODUCT_NAME_PREFIX;
        this.productId = uniqueProductName;
        encryptionKey = null;
        this.requestPerSecond = requestPerSecond;
    }


    private static String updateDefaultName(String defaults, String uniqueProductName) {
        JSONObject defaultsJson = new JSONObject(defaults);
        defaultsJson.put("productName", uniqueProductName);
        return defaultsJson.toString();
    }

    @Test
    public void productsCreationsStressTest() {
        CountDownLatch doneAll = new CountDownLatch(1);
        ExecutorService threads = Executors.newFixedThreadPool(requestPerSecond);
        List<Callable<Boolean>> toRun = new ArrayList(requestPerSecond);

        for (int index = 0; index < requestPerSecond; index++) {
            toRun.add(new AirlockProductsCreationInParallelTest.CalculationRequestSender());
        }

        // all tasks executed in different threads, at 'once'.
        try {
            List<Future<Boolean>> futures = threads.invokeAll(toRun);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        // no more need for the threadpool
        threads.shutdown();
    }

    private static class ProductCreateRequestSender implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            int delta = (int) (Math.random() * 2000);
            try {
                Thread.sleep(2000 - delta);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }

            String productName = null;
            synchronized (lock) {
                uniqueProductSufix++;
                productName = PRODUCT_NAME_PREFIX + uniqueProductSufix;
            }

            Locale.setDefault(new Locale("en", "US"));
            try {
                Response newProduct = client.target(serverUrl + "/products/init").queryParam("appVersion", appVersion).queryParam("encryptionKey", "")
                        .request().post(Entity.json(updateDefaultName(readFile(ClassLoader.getSystemResource(DEFAULTS_NAME).getPath()), productName)));
                Product product = newProduct.readEntity(Product.class);
                productsNames.add(productName);
                //productInstances.add(product.getInstanceId());
                Assert.assertNotNull(newProduct);

                Response pullResponse = client.target(serverUrl + "/products/" + product.getInstanceId() + "/pull").queryParam("locale", "en_US")
                        .request().put(Entity.json(""));
                Assert.assertEquals(200, pullResponse.getStatus());

                Response response = client.target(serverUrl + "/products/" + product.getInstanceId() + "/context").request().
                        put(Entity.entity(context, MediaType.APPLICATION_JSON), Response.class);
                Assert.assertEquals(200, response.getStatus());
                Assert.assertEquals(newProduct.getStatus(), 200);
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }

            return true;
        }
    }

    private class CalculationRequestSender implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            int delta = (int) (Math.random() * 2000);
            try {
                Thread.sleep(2000 - delta);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }

            Locale.setDefault(new Locale("en", "US"));
            try {
                long start = System.currentTimeMillis();
                Response response = client.target(serverUrl + "/products/" + productInstances.get((int) (Math.random() * productsNames.size())) + "/calculate").queryParam("sync", true).
                        request().put(Entity.json(""));
                totalRequestProcessingTime = totalRequestProcessingTime + (System.currentTimeMillis() - start);
                Assert.assertNotNull(response);
                Assert.assertEquals(response.getStatus(), 200);
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
            requestsCounter++;
            totalRequestsCounter++;
            if (requestsCounter % (requestPerSecond) == 0) {
                System.out.println("So far were sent " + requestsCounter + " calc requests");
                System.out.println("Processing time per request: " + totalRequestProcessingTime / totalRequestsCounter);
            }


            return true;
        }
    }
}
