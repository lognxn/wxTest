import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: ff
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年12月10日 下午6:52:08
 * @version 1.0
 */
public class ff {
	public static void main(String[] args) {
		String hex = "1 22 3 4 23 09";
		String[] split = hex.split(" ");
		
		Arrays.asList(split).stream()
				.peek(e -> System.out.println(e)).collect(Collectors.toList());
	}
}
