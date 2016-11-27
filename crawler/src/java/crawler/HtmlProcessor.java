package crawler;

import okhttp3.HttpUrl;
import okhttp3.Response;
import okio.Okio;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class HtmlProcessor {

    public void process(HttpUrl baseUrl, Response resp) {
        ContentHandler textHandler = new BodyContentHandler();
        LinkContentHandler linkHandler = new LinkContentHandler();
        TeeContentHandler handler = new TeeContentHandler(linkHandler, textHandler);

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream in = resp.body().byteStream()) {
            parser.parse(in, handler, metadata);
            FileWriter writer = new FileWriter("output.txt");
            linkHandler.getLinks()
                    .stream()
                    .map(link -> {
                        if (link.getUri().startsWith("http")) {
                            return link.getUri() + "\n";
                        } else {
                            return baseUrl.newBuilder()
                                    .addPathSegment(link.getUri())
                                    .build()
                                    .toString() + "\n";
                        }
                    })
                    .forEach(s -> {
                        try {
                            writer.write(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            writer.close();
        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }
    }

}
