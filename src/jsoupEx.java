import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

public class jsoupEx
{
	public static String getHtmlAsText( String html, boolean keepLineBreak )
	{
		if ( html == null ) { return null; }

		Document doc = Jsoup.parse( html );
		if ( !keepLineBreak ) { return doc.text(); }
		//makes html() preserve linebreaks and spacing
		doc.outputSettings(
				new Document.OutputSettings().prettyPrint( false ) );
		doc.select( "br" ).append( "\\n" );
		doc.select( "p" ).prepend( "\\n\\n" );
		String s = doc.html().replaceAll( "\\\\n", "\n" );
		return Jsoup.clean( s, "", Whitelist.none(),
				new Document.OutputSettings().prettyPrint( false ) );
	}

	public static Map<String, String> getHtmlLinks( String html )
	{
		if ( html == null ) { return null; }
		Document doc = Jsoup.parse( html );
		Elements selections = doc.select( "a[href], href" );
		Map<String, String> refs = new LinkedHashMap<>();
		for ( Element element : selections )
		{
			refs.put( element.text(), element.attr( "href" ) );
		}
		//		String format = "%-40s %s%n";
		//		for ( Element element : selections) {
		//			System.out.printf(format, element.attr("href"), element.text());
		//		}
		return refs;
	}

	public static void main( String[] args )
	{
		String[] tests = {
				"<ul>  <li>Some text here. </li>  <li>Some new text here. </li>  <li>You get the idea. </li></ul>",
				"<head><title>My Favorites Kinds of Corn</title><link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"path/to/file.css\" /><link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"path/to/anotherFile.css\" /></head>",
				"<html><body><p>And now you know my favorite kinds of corn. </p><script type=\"text/javascript\" src=\"path/to/file.js\"></script><script type=\"text/javascript\" src=\"path/to/anotherFile.js\"></script></body></html>",
				"<html><head><!--set the title of the page to the current date--><title>January 9th, 2009</title></head><body><!--print a message-->When was this webpage created?\\nCheck page's title for the answer.</body></html>",
				"<html><body><font color=\"green\">1</font><font color=\"blue\">2</font><font color=\"gray\">3</font></body></html>",
				"<html><body>1<sup>2</sup> = 1<br />2<sup>2</sup> = 4<br /></body></html>",
				"<html><body><b>Hardware devices</b><ol type=\"I\"><li>CD-ROM drive</li><li>DVD drive</li><li>Hard disk</li><li>Modem</li></ol><b>Web languages</b><ul type=\"square\"><li>HTML</li><li>Javascript</li><li>PHP</li><li>Java</li></ul></body></html>",
				"<html><body><dl>  <dt>HTML</dt>    <dd>A markup language</dd>  <dt>Pen</dt>    <dd>A writing tool</dd>  <dt>Lettuce</dt>    <dd>A getable</dd>\n" +
						"  <dt>Technology</dt>    <dd>The development of tools which serve as a means to certain objectives</dd>  <dt>Megabyte</dt>    <dd>A unit of data consisting of 1024 kilobytes</dd></dl></body></html>",
				"this is a pure text\ttest tab\r\ntest line break",
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
						"<html>\n" +
						"<head>\n" +
						"<title>Kai Xue's Homepage</title>\n" +
						"<meta name=\"Personal Homepage\" content=\"\">\n" +
						"<meta name=\"vs_targetSchema\" content=\"http://schemas.microsoft.com/intellisense/ie5\">\n" +
						"</head>\n" +
						"<body bgcolor=#B4DCB4>\n" +
						"\n" +
						"<h2 id=\"favorites\">Most Frequent Links</h2>\n" +
						"<ul>\n" +
						"        <li><a href=\"https://www.nasdaqomxintranet.com/\" target=\"_blank\">Nasdaq intranet</a></li>\n" +
						"        <li><a href=\"https://nasdaq.okta.com/\" target=\"_blank\">Nasdaq OKTA</a></li>\n" +
						"        <li><a href=\"https://wiki.dev.smbc.nasdaqomx.com/confluence/display/COREDEV/calendar/1eda625f-7ffe-466b-948c-dea7a46d6965?calendarName=SMS%20Team%20Leave\" target=\"_blank\">SMS leave calendar</a></li>\n" +
						"\t\t<li><a href=\"https://bitbucket.dev.smbc.nasdaqomx.com/users/kaixue\" target=\"_blank\">Nasdaq Bitbucket</a></li>\n" +
						"\t\t<li><a href=\"https://car-exch-sys.nasdaqomx.com/connect/PortalMain\" target=\"_blank\">Nasdaq vscode access gateway</a></li>\n" +
						"        <li><a href=\"http://www.wenxuecity.com\" target=\"_blank\">WenXueCity</a></li>"
		};
		for ( String s : tests )
		{
			System.out.println( "\n-------------------\nraw html string" );
			System.out.println( s );
			System.out.println( "-----keep line break--------" );
			System.out.println( getHtmlAsText( s, true ) );
			System.out.println( "-----no line break--------" );
			System.out.println( getHtmlAsText( s, false ) );
			System.out.println( getHtmlLinks( s ) );

		}
	}
}
