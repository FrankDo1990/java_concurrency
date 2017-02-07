import com.frank.concurrency.utils.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Frank on 2017/1/28.
 */
public class TestClass {

    @BeforeClass
    public static void init(){

    }

    @Test
    public  void testFormat(){
        Utils.formatPrintLn("hello ? {} no {} worlf {}", true, 2, 3);
    }
}
