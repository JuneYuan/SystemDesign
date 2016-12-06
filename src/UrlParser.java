import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {
	private static final String HTML_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*\"?'?([^\"'>\\s]*)";
	//private static final String HTML_HREF_TAG_PATTERN = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";

	public List<String> parseUrls(String content) {
		// Write your code here
		List<String> links = new ArrayList<>();
		Pattern pattern = Pattern.compile(HTML_HREF_TAG_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		String url = null;
		while (matcher.find()) {
			url = matcher.group(1);
			if (url.length() == 0 || url.startsWith("#"))
				continue;
			
			links.add(url);
		}
		
		return links;
	}
}
