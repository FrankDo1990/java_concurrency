import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.frank.concurrency.utils.Utils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 2017/1/28.
 */

public class TestClass {

    @Test
    public  void testFormat(){
        String json = "[101,102,105,108,120,121,148,100001,100002,100003,100004,100005,50000032]";
        List<Integer> res = JSON.parseArray(json, Integer.class);
        System.out.println(res);
    }
}
