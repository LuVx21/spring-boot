package org.luvx.kafka.streams.transformer;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;
import java.util.Locale;

/**
 * @author: Ren, Xie
 */
public class WordCountTransformer implements Transformer<String, String, KeyValue<String, String>> {
    private ProcessorContext               context;
    private KeyValueStore<String, Integer> kvStore;

    @Override
    public void init(final ProcessorContext context) {
        this.context = context;
        this.context.schedule(Duration.ofSeconds(1), PunctuationType.STREAM_TIME, timestamp -> {
            try (final KeyValueIterator<String, Integer> iter = kvStore.all()) {
                while (iter.hasNext()) {
                    final KeyValue<String, Integer> entry = iter.next();
                    context.forward(entry.key, entry.value.toString());
                }
            }
        });
        kvStore = (KeyValueStore<String, Integer>) context.getStateStore("Counts");
    }

    @Override
    public KeyValue<String, String> transform(String key, String value) {
        final String[] words = value.toLowerCase(Locale.getDefault()).split(" ");
        for (final String word : words) {
            final Integer oldValue = kvStore.get(word);
            if (oldValue == null) {
                kvStore.put(word, 1);
            } else {
                kvStore.put(word, oldValue + 1);
            }
        }

        return null;
    }

    @Override
    public void close() {
    }
}
