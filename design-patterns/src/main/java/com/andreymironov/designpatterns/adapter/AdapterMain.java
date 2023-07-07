package com.andreymironov.designpatterns.adapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdapterMain {
    //two independent interfaces with implementations
    public interface StringJsonParser {
        Object parse(String source) throws IOException;
    }

    public static class StringJsonParserImpl implements StringJsonParser {
        @Override
        public Object parse(String source) throws IOException {
            System.out.println("Parsing string...");
            return null;
        }
    }

    public interface StreamJsonParser {
        Object parse(InputStream inputStream) throws IOException;
    }

    public static class StreamJsonParserImpl implements StreamJsonParser {
        @Override
        public Object parse(InputStream inputStream) throws IOException {
            System.out.println("Parsing stream...");
            return null;
        }
    }

    //consumer of the 2nd interface
    public static class StreamJsonParserConsumer {
        private final StreamJsonParser streamJsonParser;

        public StreamJsonParserConsumer(StreamJsonParser streamJsonParser) {
            this.streamJsonParser = streamJsonParser;
        }

        public void process(InputStream inputStream) throws IOException {
            Object parsed = streamJsonParser.parse(inputStream);
            System.out.println("processing parsed object...");
        }
    }

    //adapter to use the 1st interface where the 2nd one is needed
    public static class StreamToStringJsonParserAdapter implements StreamJsonParser {

        private final StringJsonParser stringJsonParser;

        public StreamToStringJsonParserAdapter(StringJsonParser stringJsonParser) {
            this.stringJsonParser = stringJsonParser;
        }

        @Override
        public Object parse(InputStream inputStream) throws IOException {
            System.out.println("Adapting stream for string parser...");
            String source = new String(inputStream.readAllBytes());
            return stringJsonParser.parse(source);
        }

    }

    public static void main(String[] args) throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[]{'H', 'e', 'l', 'l', 'o'});

        //consume stream parser without adapter
        StreamJsonParser streamJsonParser = new StreamJsonParserImpl();
        StreamJsonParserConsumer consumer = new StreamJsonParserConsumer(streamJsonParser);
        consumer.process(is);

        //consume stream parser with adapter
        StringJsonParser stringJsonParser = new StringJsonParserImpl();
        consumer = new StreamJsonParserConsumer(new StreamToStringJsonParserAdapter(stringJsonParser));
        consumer.process(is);
    }
}
